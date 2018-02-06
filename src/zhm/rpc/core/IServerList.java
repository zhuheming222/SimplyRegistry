/**
 * IServerList.java
 * zhm.rpc.core
 * 2018年1月16日下午9:31:21
 *
 */
package zhm.rpc.core;

import java.util.List;

/**
 * @author zhuheming
 * IServerList
 * 2018年1月16日下午9:31:21
 */
public interface IServerList {
	public IServer getServer(String requestUrl);
	public void addServer(String serverName,IServer is);
}
