/**
 * ServerListImpl.java
 * zhm.rpc.core
 * 2018��1��16������9:31:41
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
 * һ��app��·����һ��app
 * 	APPһ�� �� ����ṩ��
 * 	Appһ�� �� һ��ServerList
 * 	һ��ServerList �Զ�� Server����ͬ��ServerName��һ��ServerList��ͬname��Serverֻ��һ����
 * 	һ��Server·�� ��һ��Server
 * 	һ��Server�����Ŷ��IP��PORT�������Ӧ��App�ж���ṩ�ߵĻ���
 * 
 * @author zhuheming
 * ServerListImpl
 * 2018��1��16������9:31:41
 */
public class ServerListImpl implements IServerList {
	
	private Map<String,IServer> isMap=null;
	
	/**
	 * ��ÿ��Server����ṩ�ߣ����㸺�ؾ���ʱʹ��
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
	
	//���ݷ������õ�Server
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
			//��ֹΪ�ջ����ظ���IServer��Ӧ����дequals����
			if(serverName!=null&&is!=null&&!isMap.containsKey(serverName)&&isMap.containsValue(is)){
				this.isMap.put(serverName, is);
			}
		}
		else{
			isMap=new HashMap<String,IServer>();
			//��ֹΪ�ջ����ظ���IServer��Ӧ����дequals����
			if(serverName!=null&&is!=null&&!isMap.containsKey(serverName)&&isMap.containsValue(is)){
				this.isMap.put(serverName, is);
			}
		}
	}
	
}
