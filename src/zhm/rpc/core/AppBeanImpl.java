/**
 * AppBeanImpl.java
 * zhm.rpc.core
 * 2018��1��16������9:23:59
 *
 */
package zhm.rpc.core;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * һ��app��·����һ��app
 * 	APPһ�� �� ����ṩ��
 * 	Appһ�� �� һ��ServerList
 * 	һ��ServerList �Զ�� Server����ͬ��ServerName��һ��ServerList��ͬname��Serverֻ��һ����
 * 	һ��Server·�� ��һ��Server
 * 	һ��Server�����Ŷ��IP��PORT�������Ӧ��App�ж���ṩ�ߵĻ���
 * 
 * @author zhuheming
 * AppBeanImpl
 * 2018��1��16������9:23:59
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
