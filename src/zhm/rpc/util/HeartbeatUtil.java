/**
 * HeartbeatUtil.java
 * zhm.rpc.util
 * 2018��1��18������9:24:33
 *
 */
package zhm.rpc.util;

import zhm.rpc.core.IApp;
import zhm.rpc.ioc.BeanContext;

/**
 * @author zhuheming
 * HeartbeatUtil
 * 2018��1��18������9:24:33
 */
public class HeartbeatUtil implements Runnable{

	private BeanContext bl=null;
	
	public HeartbeatUtil(BeanContext ia){
		this.bl=ia;
	} 
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			//ѭ���жϸ��������ṩ���Ƿ����
			IApp ia= this.bl.getCycleApp();
			if(ServerManagerUtil.judgmentAvailable(ia)){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				//ֱ�ӽ����Ӧ������
				bl.offLineApp(ia);
			}
		}
	}

}
