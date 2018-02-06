/**
 * RandomLoadBalance.java
 * zhm.rpc.loadbalance
 * 2018年1月20日下午12:14:34
 *
 */
package zhm.rpc.loadbalance;

import java.util.List;
import java.util.Random;

import zhm.rpc.core.AppBeanImpl;
import zhm.rpc.core.IServer;
import zhm.rpc.core.IServerList;
import zhm.rpc.core.ServerBeanImpl;
import zhm.rpc.core.ServerProvider;

/**
 * @author zhuheming
 * RandomLoadBalance
 * 2018年1月20日下午12:14:34
 */
public class RandomLoadBalance implements ILoadBalance {

	/* (non-Javadoc)
	 * @see zhm.rpc.loadbalance.ILoadBalance#selectServer(zhm.rpc.core.IServerList)
	 * 	判断所有的权重是否相同，相同的话直接随机到某个server
	 *	不同的话在0到总权重直接随机一个数，然后判断数落在哪个server的区间
	 */
	@Override
	public ServerProvider selectServer(List<ServerProvider> lis,String requestMess) {
		// TODO Auto-generated method stub
		boolean sameWeight=true;
		int totalWeight=0;
		int curWeight=0;
		//List<IServer> lis=isl.getServerList();

		//ServerBeanImpl sbi=null;
		//AppBeanImpl abi=null;
		Random random=new Random();
		//判断权重是否都一致，计算总权重
		for(int i=0;i<lis.size();i++){
			// sbi=(ServerBeanImpl)lis.get(i);
			// abi=(ServerProvider)lis.getIa();
			
			if(curWeight!=0){
				if(lis.get(i).getWeight()!=curWeight){
					sameWeight=false;
				}
			}
			curWeight=lis.get(i).getWeight();
			//if(lis.get(i).getIa())
			totalWeight+=lis.get(i).getWeight();
		}
		
		//不一致，用减法判断随机数落在哪个区间。
		int randomResult=0;
		if(!sameWeight&&totalWeight>0){
			int offset=random.nextInt(totalWeight);
			for(int i=0;i<lis.size();i++){
				//sbi=(ServerBeanImpl)lis.get(i);
				//abi=(AppBeanImpl)sbi.getIa();
				offset-=lis.get(i).getWeight();
				if(offset<0){
					randomResult=i;
					break;
				}
			}
		}else{
			//一致直接返回随机 
			randomResult=random.nextInt(lis.size());
		}
			
		return lis.get(randomResult);
	}

}
