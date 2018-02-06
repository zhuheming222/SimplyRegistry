/**
 * leastActiveLoadBalance.java
 * zhm.rpc.loadbalance
 * 2018��1��21������2:48:42
 *
 */
package zhm.rpc.loadbalance;

import java.util.ArrayList;
import java.util.List;

import zhm.rpc.core.IServer;
import zhm.rpc.core.IServerList;
import zhm.rpc.core.ServerBeanImpl;
import zhm.rpc.core.ServerListImpl;
import zhm.rpc.core.ServerProvider;

/**
 * �õ���Ծ����С��һ���򼸸�server��Ȼ��ʹ�ü�Ȩ����㷨��
 * @author zhuheming
 * leastActiveLoadBalance
 * 2018��1��21������2:48:42
 */
public class LeastActiveLoadBalance implements ILoadBalance {
	
	RandomLoadBalance rlb=new RandomLoadBalance();

	/* (non-Javadoc)
	 * @see zhm.rpc.loadbalance.ILoadBalance#selectServer(zhm.rpc.core.IServerList)
	 */
	@Override
	public ServerProvider selectServer(List<ServerProvider> lis,String requestMess) {
		// TODO Auto-generated method stub
		List<ServerProvider> newlist=new ArrayList<ServerProvider>();
		ServerProvider sbi=null;
		int minActive=Integer.MAX_VALUE;
		//������л�Ծʱ�䶼һ�£�ֱ��תΪȨ������㷨��
		boolean sameActiveTime=true;
		//List<IServer> lis=isl.getServerList();
		for(int i=0;i<lis.size();i++){
			sbi=(ServerProvider)lis.get(i);
			if(i>0&&minActive!=sbi.getLastLiveTime()){
				sameActiveTime=false;
			}
			if(minActive>sbi.getLastLiveTime()){
				minActive=sbi.getLastLiveTime();				
			}			
		}
		if(!sameActiveTime){
			for(int i=0;i<lis.size();i++){
				sbi=(ServerProvider)lis.get(i);
				if(minActive==sbi.getLastLiveTime()){
					newlist.add(sbi);				
				}
			}
		}else{
			newlist=lis;
		}
		return rlb.selectServer(newlist,"");
	}
}
