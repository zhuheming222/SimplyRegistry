/**
 * ServerListImpl.java
 * zhm.rpc.core
 * 2018年1月16日下午9:31:41
 *
 */
package zhm.rpc.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Element;

/**
 * 一个app的路径对一个app
 * 	APP一个 对 多个提供者
 * 	App一个 对 一个ServerList
 * 	一个ServerList 对多个 Server（不同的ServerName，一个ServerList相同name的Server只有一个）
 * 	一个Server路径 对一个Server
 * 	一个Server下面存放多个IP和PORT，如果对应的App有多个提供者的话。
 * 
 * @author zhuheming
 * ServerListImpl
 * 2018年1月16日下午9:31:41
 */
public class ServerListImpl implements IServerList {
	
	private Map<String,IServer> isMap=null;
	
	/**
	 * 给每个Server添加提供者，方便负载均衡时使用
	 * **/
	public void addServerProvider(ServerProvider iprovider){
		if (isMap != null) {
			Iterator<Entry<String, IServer>> its = isMap.entrySet().iterator();
			// iaMap=new HashMap<String,IApp>();
			while (its.hasNext()) {
				Entry<String, IServer> entry = its.next();
				((ServerBeanImpl)entry.getValue()).getiProvider().add(iprovider);
			}
		}
	}
	
	//根据服务名得到Server
	public IServer getServer(String requestUrl){
		if(requestUrl!=null){
			IServer ia=null;
			String[] appStr=requestUrl.split("/");
			if(appStr!=null&&appStr.length!=0){
				ia=isMap.get(appStr[2]);
			}
			return ia;
		}
		return null;
	}
	
	public void addServer(String serverName,IServer is){
		if(isMap!=null){
			//防止为空或者重复，IServer中应当重写equals方法
			if(serverName!=null&&is!=null&&!isMap.containsKey(serverName)&&isMap.containsValue(is)){
				this.isMap.put(serverName, is);
			}
		}
		else{
			isMap=new HashMap<String,IServer>();
			//防止为空或者重复，IServer中应当重写equals方法
			if(serverName!=null&&is!=null&&!isMap.containsKey(serverName)&&isMap.containsValue(is)){
				this.isMap.put(serverName, is);
			}
		}
	}
	
}
