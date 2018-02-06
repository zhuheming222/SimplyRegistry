/**
 * BeanLoaderImpl.java
 * zhm.rpc.ioc
 * 2017年12月21日下午10:12:18
 *
 */
package zhm.rpc.ioc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import zhm.rpc.base.ElementLoaderImpl;
import zhm.rpc.base.XmlDocumentHolder;
import zhm.rpc.core.AppBeanImpl;
import zhm.rpc.core.IApp;
import zhm.rpc.core.IServer;
import zhm.rpc.core.ServerBeanImpl;
import zhm.rpc.core.ServerListImpl;
import zhm.rpc.core.ServerProvider;
import zhm.rpc.loadbalance.ILoadBalance;
import zhm.rpc.loadbalance.LoadBalanceManager;
import zhm.rpc.util.ServerManagerUtil;

/**
 * 
 * @author zhuheming BeanLoaderImpl 2018年1月18日下午8:51:46
 */
public class BeanContext implements BeanLoader {

	private static final String APP_SIGN = "App";

	private static final String SERVER_SIGN = "Service";

	private static final String LOADBALANCE_SIGN = "LoadBalance";
	
	private static final String PROVIDER_SIGN = "PROVIDER";

	private static final String XML_PATH = "D:\\workspace\\SimplyRegistry\\MyRPC.xml";

	// 存放服务提供者，应用级别 string为 应用名
	private Map<String, IApp> iaMap = null;

	private Iterator<Entry<String, IApp>> mapIt = null;

	private LoadBalanceManager lbm = null;
	
	public LoadBalanceManager getLoadBalanceManager(){
		return this.lbm;
	}

	/**
	 * 从url得到对应的app
	 * url格式为 /应用名/服务名
	 * **/
	public IApp getApp(String requestUrl) {
		if (requestUrl != null) {
			IApp ia = null;
			String[] appStr = requestUrl.split("/");
			if (appStr != null && appStr.length != 0) {
				ia = iaMap.get(appStr[1]);
			}
			return ia;
		}
		return null;
	}

	/**
	 * 如果循环得到下一个，就返回下一个 如果到最后一个，那么重新循环 IApp
	 * 
	 * @return
	 * 
	 */
	public IApp getCycleApp() {
		if (mapIt.hasNext()) {
			return mapIt.next().getValue();
		} else {
			mapIt = iaMap.entrySet().iterator();
			return mapIt.next().getValue();
		}
	}

	/**
	 * 下线这个应用，当这个App不可用时，将它下线，就是从Map里面删除 boolean
	 * 
	 * @return
	 * 
	 */
	public void offLineApp(IApp ia) {
		AppBeanImpl abi = (AppBeanImpl) ia;
		this.iaMap.remove(abi.getAppId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see zhm.rpc.ioc.BeanLoader#createBean(org.dom4j.Document)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IApp> createBean() {
		// TODO Auto-generated method stub
		if (iaMap != null)
			return iaMap;
		else {
			XmlDocumentHolder xmlDocumentHolder = new XmlDocumentHolder();
			ElementLoaderImpl elementLoaderImpl = new ElementLoaderImpl();
			// xmlDocumentHolder.getDocument("MyRPC.xml");
			// 得到xml配置文件的配置信息
			Map<String, Element> seMap = elementLoaderImpl.getDocument(xmlDocumentHolder.getDocument(XML_PATH));
			Iterator<Entry<String, Element>> it = seMap.entrySet().iterator();
			iaMap = new ConcurrentHashMap<String, IApp>();
			// 先取app的属性，再取server的属性
			while (it.hasNext()) {
				// 解析xml配置文件
				Entry<String, Element> entry = it.next();
				// 如果是App这个标签，初始化iaMap
				if (APP_SIGN.equalsIgnoreCase(entry.getKey()) && iaMap != null) {
					// 如何取得XML中配置的属性
					List<Attribute> listAttr = entry.getValue().attributes();
					AppBeanImpl iapp = new AppBeanImpl();
					iapp.setAppId(listAttr.get(0).getValue());
					iapp.setWeight(Integer.parseInt(listAttr.get(1).getValue()));
					iapp.setLoadBalance(listAttr.get(2).getValue());
					// 判断服务提供者服务是否可用
					if (ServerManagerUtil.judgmentAvailable(iapp)) {
						if (!iaMap.containsKey(listAttr.get(0).getValue())) {
							iaMap.put(listAttr.get(0).getValue(), (IApp) iapp);
						}
					}

				} else if (LOADBALANCE_SIGN.equalsIgnoreCase(entry.getKey())) {// 加载负载均衡的配置
					if(lbm==null){
						lbm=new LoadBalanceManager();
					}
					List<Attribute> listAttr = entry.getValue().attributes();

					String loadBalanceid = listAttr.get(0).getValue();
					String loadBalanceClassName = listAttr.get(1).getValue();
					ILoadBalance loadBalanceObject = null;
					Class clazz=null;

					try {
						clazz = Class.forName(loadBalanceClassName);
						loadBalanceObject = (ILoadBalance) clazz.newInstance();
						
						lbm.addLoadBalanceMap(loadBalanceid, loadBalanceObject);
						
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			Iterator<Entry<String, Element>> its = seMap.entrySet().iterator();
			// iaMap=new HashMap<String,IApp>();
			while (its.hasNext()) {
				// 解析xml配置文件
				Entry<String, Element> entry = it.next();
				if (SERVER_SIGN.equalsIgnoreCase(entry.getKey()) && iaMap != null) {
					// 如何取得XML中配置的属性
					List<Attribute> listAttr = entry.getValue().attributes();
					// String defalultObjectStr=listAttr.get(0).getValue();

					String appIdStr = listAttr.get(3).getValue();
					ServerBeanImpl is = new ServerBeanImpl();

					AppBeanImpl iapp = (AppBeanImpl) iaMap.get(appIdStr);
					is.setIa(iapp);
					is.setName(listAttr.get(0).getValue());
					is.setVersion(listAttr.get(1).getValue());
					is.setTitle(listAttr.get(2).getValue());
					iapp.getiServerList().addServer(listAttr.get(0).getValue(), is);
				}else if(PROVIDER_SIGN.equalsIgnoreCase(entry.getKey()) && iaMap != null){
					List<Attribute> listAttr = entry.getValue().attributes();
					// String defalultObjectStr=listAttr.get(0).getValue();

					String appIdStr = listAttr.get(3).getValue();
					AppBeanImpl iapp = (AppBeanImpl) iaMap.get(appIdStr);
					ServerProvider iProvider=new ServerProvider();
					iapp.setiPorvider(iProvider);
					((ServerListImpl)iapp.getiServerList()).addServerProvider(iProvider);
				}
			}
			// 为了后面循环时使用
			mapIt = iaMap.entrySet().iterator();
		}
		return iaMap;
	}

	public Map<String, IApp> getDefaultBean() {
		if (iaMap == null) {
			createBean();
		}
		return iaMap;
	}
	
	
   /**
    * 考虑使用懒加载，内部静态类方式进行单例模式加载
    * 
    * **/
	public void createDefaultBean() {
		if (iaMap == null) {
			createBean();
		}
	}

}
