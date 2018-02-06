/**
 * RegistryUtil.java
 * zhm.rpc.util
 * 2018年1月18日下午6:58:06
 *
 */
package zhm.rpc.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import zhm.rpc.core.AppBeanImpl;
import zhm.rpc.core.IApp;

/**
 * @author zhuheming
 * RegistryUtil
 * 2018年1月18日下午6:58:06
 */
public class ServerManagerUtil {

	public static boolean judgmentAvailable(IApp ia){
		Socket socket=new Socket();
		if(ia!=null){
			AppBeanImpl abi=(AppBeanImpl)ia;
			try {
				socket.connect(new InetSocketAddress(abi.getAppIp(),abi.getAppPort()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}finally{
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}
		return false;
	}
}
