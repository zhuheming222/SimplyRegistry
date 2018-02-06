/**
 * ILoadBalance.java
 * 
 * 2018��1��20������12:11:58
 *
 */
package zhm.rpc.loadbalance;

import java.util.List;

import zhm.rpc.core.IServer;
import zhm.rpc.core.IServerList;
import zhm.rpc.core.ServerProvider;

/**
 * @author zhuheming
 * ILoadBalance
 * 2018��1��20������12:11:58
 */
public interface ILoadBalance {

	public ServerProvider selectServer(List<ServerProvider> isl,String requestMess);
	
}
