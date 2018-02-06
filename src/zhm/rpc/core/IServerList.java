/**
 * IServerList.java
 * zhm.rpc.core
 * 2018��1��16������9:31:21
 *
 */
package zhm.rpc.core;

import java.util.List;

/**
 * @author zhuheming
 * IServerList
 * 2018��1��16������9:31:21
 */
public interface IServerList {
	public IServer getServer(String requestUrl);
	public void addServer(String serverName,IServer is);
}
