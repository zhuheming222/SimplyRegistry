/**
 * RoundRobinLoadBalance.java
 * zhm.rpc.loadbalance
 * 2018年1月20日下午4:45:02
 *
 */
package zhm.rpc.loadbalance;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import zhm.rpc.core.AppBeanImpl;
import zhm.rpc.core.IServer;
import zhm.rpc.core.IServerList;
import zhm.rpc.core.ServerBeanImpl;
import zhm.rpc.core.ServerProvider;

/**
 * @author zhuheming
 * RoundRobinLoadBalance
 * 2018年1月20日下午4:45:02
 */
public class RoundRobinLoadBalance implements ILoadBalance {
	
	private AtomicInteger loadInt=new AtomicInteger(0);

	/* (non-Javadoc)
	 * @see zhm.rpc.loadbalance.ILoadBalance#selectServer(zhm.rpc.core.IServerList)
	 */
	@Override
	public ServerProvider selectServer(List<ServerProvider> lis,String requestMess) {
		// TODO Auto-generated method stub
		boolean sameWeight=true;
		int totalWeight=0;
		int curWeight=0;
		// List<IServer> lis=isl.getServerList();

//		ServerBeanImpl sbi=null;
//		AppBeanImpl abi=null;
		Random random=new Random();
		//判断权重是否都一致，计算总权重
		for(int i=0;i<lis.size();i++){
			//sbi=(ServerBeanImpl)lis.get(i);
			//abi=(AppBeanImpl)sbi.getIa();
			if(curWeight!=0){
				if(lis.get(i).getWeight()!=curWeight){
					sameWeight=false;
				}
			}
			curWeight=lis.get(i).getWeight();
			//if(lis.get(i).getIa())
			totalWeight+=lis.get(i).getWeight();
		}
		
		//不一致，用减法判断自增的变量落在哪个区间，用这种方式判断轮询位置
		int randomResult=0;
		if(!sameWeight&&totalWeight>0){
			int offset=loadInt.get();
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
			//如果权重相同直接取余数
			randomResult=loadInt.get()%lis.size();			
		}
		//如果loadInt大于总权重，说明已经按照权重轮询过一遍，重新开始，否则只加1
		if(loadInt.get()>=totalWeight){
			loadInt.set(0);
		}else{
			loadInt.getAndAdd(1);			
		}
			
		return lis.get(randomResult);
	}

}
