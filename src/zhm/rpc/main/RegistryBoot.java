/**
 * RegistryBoot.java
 * zhm.rpc.main
 * 2018年1月18日下午8:00:51
 *
 */
package zhm.rpc.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import zhm.rpc.core.AppBeanImpl;
import zhm.rpc.core.IProvider;
import zhm.rpc.core.ServerBeanImpl;
import zhm.rpc.core.ServerListImpl;
import zhm.rpc.core.ServerProvider;
import zhm.rpc.ioc.BeanContext;
import zhm.rpc.util.HeartbeatUtil;

/**
 * @author zhuheming
 * RegistryBoot
 * 2018年1月18日下午8:00:51
 */
public class RegistryBoot {
	
	private static final int REGISTRY_PORT=8989;

	public static void main(String[] args){
		//加载服务提供者的配置，并检测是否可用
		BeanContext bl=new BeanContext();
		bl.createDefaultBean();
		
		//建立一个心跳线程，检测是否可用，不可用就通知将相关应用下线。
		//再建立一个心跳线程，检测已经不可用的应用是否重新可用，可用就将其上线
		//通知相关app下线，需要考虑使用多线程数据模型来存放app
//		Thread heartBeat=new Thread(new HeartbeatUtil(bl));
//		heartBeat.start();
//		try {
//			heartBeat.join();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		
		// 建立输入		
		ServerSocket sock=null;
		Socket socket=null;
		ObjectInputStream ois=null;
		try {
			sock=new ServerSocket(REGISTRY_PORT);
			socket = sock.accept();

			ois= new ObjectInputStream(socket.getInputStream());
			
			ServerBeanImpl curServer=null;
			//循环等待服务消费者上送信息
			while(true){
				String requestUrl = ois.readUTF();
				//首先从url得到对应的app
				AppBeanImpl abil=(AppBeanImpl)bl.getApp(requestUrl);
				//从url得到对应的server
				ServerBeanImpl sbil=(ServerBeanImpl)abil.getiServerList().getServer(requestUrl);
				//根据负载均衡算法得到其中某个提供者
				ServerProvider ipro=(ServerProvider)sbil.getServerProvider(bl,requestUrl);
				//将得到的服务提供商ip和地址返回给服务消费者
				
				ObjectOutputStream ops=new ObjectOutputStream(socket.getOutputStream());
				ops.writeUTF(ipro.getServerIP()+":"+ipro.getServerPort());				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
