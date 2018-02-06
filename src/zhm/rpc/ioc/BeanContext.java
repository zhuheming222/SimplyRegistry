/**
 * BeanLoaderImpl.java
 * zhm.rpc.ioc
 * 2017��12��21������10:12:18
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
 * @author zhuheming BeanLoaderImpl 2018��1��18������8:51:46
 */
public class BeanContext implements BeanLoader {

	private static final String APP_SIGN = "App";

	private static final String SERVER_SIGN = "Service";

	private static final String LOADBALANCE_SIGN = "LoadBalance";
	
	private static final String PROVIDER_SIGN = "PROVIDER";

	private static final String XML_PATH = "D:\\workspace\\SimplyRegistry\\MyRPC.xml";

	// ��ŷ����ṩ�ߣ�Ӧ�ü��� stringΪ Ӧ����
	private Map<String, IApp> iaMap = null;

	private Iterator<Entry<String, IApp>> mapIt = null;

	private LoadBalanceManager lbm = null;
	
	public LoadBalanceManager getLoadBalanceManager(){
		return this.lbm;
	}

	/**
	 * ��url�õ���Ӧ��app
	 * url��ʽΪ /Ӧ����/������
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
	 * ���ѭ���õ���һ�����ͷ�����һ�� ��������һ������ô����ѭ�� IApp
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
	 * �������Ӧ�ã������App������ʱ���������ߣ����Ǵ�Map����ɾ�� boolean
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
			// �õ�xml�����ļ���������Ϣ
			Map<String, Element> seMap = elementLoaderImpl.getDocument(xmlDocumentHolder.getDocument(XML_PATH));
			Iterator<Entry<String, Element>> it = seMap.entrySet().iterator();
			iaMap = new ConcurrentHashMap<String, IApp>();
			// ��ȡapp�����ԣ���ȡserver������
			while (it.hasNext()) {
				// ����xml�����ļ�
				Entry<String, Element> entry = it.next();
				// �����App�����ǩ����ʼ��iaMap
				if (APP_SIGN.equalsIgnoreCase(entry.getKey()) && iaMap != null) {
					// ���ȡ��XML�����õ�����
					List<Attribute> listAttr = entry.getValue().attributes();
					AppBeanImpl iapp = new AppBeanImpl();
					iapp.setAppId(listAttr.get(0).getValue());
					iapp.setWeight(Integer.parseInt(listAttr.get(1).getValue()));
					iapp.setLoadBalance(listAttr.get(2).getValue());
					// �жϷ����ṩ�߷����Ƿ����
					if (ServerManagerUtil.judgmentAvailable(iapp)) {
						if (!iaMap.containsKey(listAttr.get(0).getValue())) {
							iaMap.put(listAttr.get(0).getValue(), (IApp) iapp);
						}
					}

				} else if (LOADBALANCE_SIGN.equalsIgnoreCase(entry.getKey())) {// ���ظ��ؾ��������
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
				// ����xml�����ļ�
				Entry<String, Element> entry = it.next();
				if (SERVER_SIGN.equalsIgnoreCase(entry.getKey()) && iaMap != null) {
					// ���ȡ��XML�����õ�����
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
			// Ϊ�˺���ѭ��ʱʹ��
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
    * ����ʹ�������أ��ڲ���̬�෽ʽ���е���ģʽ����
    * 
    * **/
	public void createDefaultBean() {
		if (iaMap == null) {
			createBean();
		}
	}

}
