/**
 * IServer.java
 * zhm.rpc.core
 * 2018��1��16������9:18:30
 *
 */
package zhm.rpc.core;

/**
 * @author zhuheming
 * IServer
 * 2018��1��16������9:18:30
 */
public interface IServer {
	public IApp getapp(String requestUrl);
	//public IApp getIa();
}
