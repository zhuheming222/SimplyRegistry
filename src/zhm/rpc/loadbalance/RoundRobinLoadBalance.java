/**
 * RoundRobinLoadBalance.java
 * zhm.rpc.loadbalance
 * 2018��1��20������4:45:02
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
 * 2018��1��20������4:45:02
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
		//�ж�Ȩ���Ƿ�һ�£�������Ȩ��
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
		
		//��һ�£��ü����ж������ı��������ĸ����䣬�����ַ�ʽ�ж���ѯλ��
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
			//���Ȩ����ֱͬ��ȡ����
			randomResult=loadInt.get()%lis.size();			
		}
		//���loadInt������Ȩ�أ�˵���Ѿ�����Ȩ����ѯ��һ�飬���¿�ʼ������ֻ��1
		if(loadInt.get()>=totalWeight){
			loadInt.set(0);
		}else{
			loadInt.getAndAdd(1);			
		}
			
		return lis.get(randomResult);
	}

}
