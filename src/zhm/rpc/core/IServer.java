/**
 * IServer.java
 * zhm.rpc.core
 * 2018年1月16日下午9:18:30
 *
 */
package zhm.rpc.core;

/**
 * @author zhuheming
 * IServer
 * 2018年1月16日下午9:18:30
 */
public interface IServer {
	public IApp getapp(String requestUrl);
	//public IApp getIa();
}
