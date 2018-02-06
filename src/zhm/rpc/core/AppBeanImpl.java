/**
 * AppBeanImpl.java
 * zhm.rpc.core
 * 2018年1月16日下午9:23:59
 *
 */
package zhm.rpc.core;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 一个app的路径对一个app
 * 	APP一个 对 多个提供者
 * 	App一个 对 一个ServerList
 * 	一个ServerList 对多个 Server（不同的ServerName，一个ServerList相同name的Server只有一个）
 * 	一个Server路径 对一个Server
 * 	一个Server下面存放多个IP和PORT，如果对应的App有多个提供者的话。
 * 
 * @author zhuheming
 * AppBeanImpl
 * 2018年1月16日下午9:23:59
 */
public class AppBeanImpl implements IApp {
	
	private String appId=null;
	
	private int weight=0;
	
	private String loadBalance=null;
	
	private IServerList iServerList=null;
	
	private ServerProvider  iPorvider=null;
	
//	public void addServer(String serverName,IServer is){
//		IServerList isl=(ServerListImpl)isMap.get(serverName);
//		if(isl!=null)isl.addServer(is);
//		else{
//			isl=new ServerListImpl();
//			isl.addServer(is);
//		}
//	}
	

	/**
	 * @return the iServerList
	 */
	public IServerList getiServerList() {
		return iServerList;
	}

	/**
	 * @param iServerList the iServerList to set
	 */
	public void setiServerList(IServerList iServerList) {
		this.iServerList = iServerList;
	}

	/**
	 * @return the iPorvider
	 */
	public ServerProvider getiPorvider() {
		return iPorvider;
	}

	/**
	 * @param iPorvider the iPorvider to set
	 */
	public void setiPorvider(ServerProvider iPorvider) {
		this.iPorvider = iPorvider;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}


	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the loadBalance
	 */
	public String getLoadBalance() {
		return loadBalance;
	}

	/**
	 * @param loadBalance the loadBalance to set
	 */
	public void setLoadBalance(String loadBalance) {
		this.loadBalance = loadBalance;
	}
}
