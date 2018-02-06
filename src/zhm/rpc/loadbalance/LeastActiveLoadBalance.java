/**
 * leastActiveLoadBalance.java
 * zhm.rpc.loadbalance
 * 2018年1月21日下午2:48:42
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
 * 得到活跃数最小的一个或几个server，然后使用加权随机算法。
 * @author zhuheming
 * leastActiveLoadBalance
 * 2018年1月21日下午2:48:42
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
		//如果所有活跃时间都一致，直接转为权重随机算法。
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
