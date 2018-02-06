/**
 * RegistryBoot.java
 * zhm.rpc.main
 * 2018��1��18������8:00:51
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
 * 2018��1��18������8:00:51
 */
public class RegistryBoot {
	
	private static final int REGISTRY_PORT=8989;

	public static void main(String[] args){
		//���ط����ṩ�ߵ����ã�������Ƿ����
		BeanContext bl=new BeanContext();
		bl.createDefaultBean();
		
		//����һ�������̣߳�����Ƿ���ã������þ�֪ͨ�����Ӧ�����ߡ�
		//�ٽ���һ�������̣߳�����Ѿ������õ�Ӧ���Ƿ����¿��ã����þͽ�������
		//֪ͨ���app���ߣ���Ҫ����ʹ�ö��߳�����ģ�������app
//		Thread heartBeat=new Thread(new HeartbeatUtil(bl));
//		heartBeat.start();
//		try {
//			heartBeat.join();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		
		// ��������		
		ServerSocket sock=null;
		Socket socket=null;
		ObjectInputStream ois=null;
		try {
			sock=new ServerSocket(REGISTRY_PORT);
			socket = sock.accept();

			ois= new ObjectInputStream(socket.getInputStream());
			
			ServerBeanImpl curServer=null;
			//ѭ���ȴ�����������������Ϣ
			while(true){
				String requestUrl = ois.readUTF();
				//���ȴ�url�õ���Ӧ��app
				AppBeanImpl abil=(AppBeanImpl)bl.getApp(requestUrl);
				//��url�õ���Ӧ��server
				ServerBeanImpl sbil=(ServerBeanImpl)abil.getiServerList().getServer(requestUrl);
				//���ݸ��ؾ����㷨�õ�����ĳ���ṩ��
				ServerProvider ipro=(ServerProvider)sbil.getServerProvider(bl,requestUrl);
				//���õ��ķ����ṩ��ip�͵�ַ���ظ�����������
				
				ObjectOutputStream ops=new ObjectOutputStream(socket.getOutputStream());
				ops.writeUTF(ipro.getServerIP()+":"+ipro.getServerPort());				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
