/**
 * ServerBeanImpl.java
 * zhm.rpc.core
 * 2018��1��16������9:22:58
 *
 */
package zhm.rpc.core;

import java.util.ArrayList;
import java.util.List;

import zhm.rpc.ioc.BeanContext;
import zhm.rpc.loadbalance.ILoadBalance;
import zhm.rpc.loadbalance.LoadBalanceManager;

/**
 *  * һ��app��·����һ��app
 * 	APPһ�� �� ����ṩ��
 * 	Appһ�� �� һ��ServerList
 * 	һ��ServerList �Զ�� Server����ͬ��ServerName��һ��ServerList��ͬname��Serverֻ��һ����
 * 	һ��Server·�� ��һ��Server
 * 	һ��Server�����Ŷ��IP��PORT�������Ӧ��App�ж���ṩ�ߵĻ���
 * @author zhuheming
 * ServerBeanImpl
 * 2018��1��16������9:22:58
 */
public class ServerBeanImpl implements IServer {
	
	private IApp ia=null;
	
	private String name=null;
	
	private String title=null;
	
	private String version=null;
	
	//�����Ծʱ�䣬���һ�ʽ��׵Ļ�Ծʱ�䣬Խ���ʾ����Խ�������ں���
	private int lastLiveTime=0;
	
	//����ṩ�ߣ������ṩ�߾ʹ�ż�����
	private List<ServerProvider> iProvider=new ArrayList<ServerProvider>();
	
	/*
	 * server�Ѿ�ȷ������������ݸ��ؾ����㷨�Ӽ��������ṩ����ѡ�񣬵õ�ĳ�������Server
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
