/**
 * IProvider.java
 * zhm.rpc.core
 * 2018年1月25日下午9:03:15
 *
 */
package zhm.rpc.core;

/**
 * @author zhuheming
 * IProvider
 * 2018年1月25日下午9:03:15
 */
public class ServerProvider {
	
	private String ServerIP=null;
	
	private int ServerPort=0;

	
	/**
	 * 最近一次活跃时间
	 * **/
	private int lastLiveTime=0;
	
	/**
	 * 权重
	 * **/
	private int weight =0;
	

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
	 * @return the serverIP
	 */
	public String getServerIP() {
		return ServerIP;
	}

	/**
	 * @param serverIP the serverIP to set
	 */
	public void setServerIP(String serverIP) {
		ServerIP = serverIP;
	}

	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return ServerPort;
	}

	/**
	 * @param serverPort the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		ServerPort = serverPort;
	}
	
	
	
}
