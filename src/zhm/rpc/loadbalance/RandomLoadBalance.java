/**
 * RandomLoadBalance.java
 * zhm.rpc.loadbalance
 * 2018��1��20������12:14:34
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
 * 2018��1��20������12:14:34
 */
public class RandomLoadBalance implements ILoadBalance {

	/* (non-Javadoc)
	 * @see zhm.rpc.loadbalance.ILoadBalance#selectServer(zhm.rpc.core.IServerList)
	 * 	�ж����е�Ȩ���Ƿ���ͬ����ͬ�Ļ�ֱ�������ĳ��server
	 *	��ͬ�Ļ���0����Ȩ��ֱ�����һ������Ȼ���ж��������ĸ�server������
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
		//�ж�Ȩ���Ƿ�һ�£�������Ȩ��
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
		
		//��һ�£��ü����ж�����������ĸ����䡣
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
			//һ��ֱ�ӷ������ 
			randomResult=random.nextInt(lis.size());
		}
			
		return lis.get(randomResult);
	}

}
