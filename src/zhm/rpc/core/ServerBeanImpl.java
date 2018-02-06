/**
 * ServerBeanImpl.java
 * zhm.rpc.core
 * 2018年1月16日下午9:22:58
 *
 */
package zhm.rpc.core;

import java.util.ArrayList;
import java.util.List;

import zhm.rpc.ioc.BeanContext;
import zhm.rpc.loadbalance.ILoadBalance;
import zhm.rpc.loadbalance.LoadBalanceManager;

/**
 *  * 一个app的路径对一个app
 * 	APP一个 对 多个提供者
 * 	App一个 对 一个ServerList
 * 	一个ServerList 对多个 Server（不同的ServerName，一个ServerList相同name的Server只有一个）
 * 	一个Server路径 对一个Server
 * 	一个Server下面存放多个IP和PORT，如果对应的App有多个提供者的话。
 * @author zhuheming
 * ServerBeanImpl
 * 2018年1月16日下午9:22:58
 */
public class ServerBeanImpl implements IServer {
	
	private IApp ia=null;
	
	private String name=null;
	
	private String title=null;
	
	private String version=null;
	
	//最近活跃时间，最近一笔交易的活跃时间，越大表示交易越慢，排在后面
	private int lastLiveTime=0;
	
	//存放提供者，几个提供者就存放几个。
	private List<ServerProvider> iProvider=new ArrayList<ServerProvider>();
	
	/*
	 * server已经确定，在这里根据负载均衡算法从几个服务提供者里选择，得到某个具体的Server
	 * */
	public ServerProvider getServerProvider(BeanContext bc,String requestUrl){
		ILoadBalance lb=bc.getLoadBalanceManager().getLoadBalance(((AppBeanImpl)this.ia).getLoadBalance());
		ServerProvider sp=lb.selectServer(iProvider,requestUrl);
		return sp;
	};
	
	/**
	 * @return the ia
	 */
	public IApp getIa() {
		return ia;
	}

	/**
	 * @param ia the ia to set
	 */
	public void setIa(IApp ia) {
		this.ia = ia;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public IApp getapp(String requestUrl){
		return null;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the lastLiveTime
	 */
	public int getLastLiveTime() {
		return lastLiveTime;
	}

	/**
	 * @param lastLiveTime the lastLiveTime to set
	 */
	public void setLastLiveTime(int lastLiveTime) {
		this.lastLiveTime = lastLiveTime;
	}

	/**
	 * @return the iProvider
	 */
	public List<ServerProvider> getiProvider() {
		return iProvider;
	}

	/**
	 * @param iProvider the iProvider to set
	 */
	public void setiProvider(List<ServerProvider> iProvider) {
		this.iProvider = iProvider;
	}


}
