/**
 * ConsistentHashLoadBalance.java
 * zhm.rpc.loadbalance
 * 2018��1��22������8:49:26
 *
 */
package zhm.rpc.loadbalance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import zhm.rpc.core.ServerProvider;

/**
 * hashһ���Ը��ؾ���
 * @author zhuheming
 * ConsistentHashLoadBalance
 * 2018��1��22������8:49:26
 */
public class ConsistentHashLoadBalance implements ILoadBalance {

	//ÿ�����ǽڵ��Ӧ������ڵ�ĸ���
	private static final int VIRTUAL_NODES_PER_REAL=5;
	
	private  List<String> realNodeList=null;
	
	private  Map<String,ServerProvider> msi=new HashMap<String,ServerProvider>();
	
	private  SortedMap<Integer,String> virtualNodes=new TreeMap<Integer,String>();
	

	public void CreateConsistentHash(List<ServerProvider> lis){
		realNodeList=new LinkedList<String>();

		//���ַ������뵽realNodeList�У����Ҽ��뵽msi�У�������λ����Ӧ ��IServer		
		//�ȸ���IserverList�õ�ÿ��server���ض��ַ�����Ψһ��ʶ���񣬴˴�ʹ��name+ip+port
		//List<IServer> lis=isl.getServerList();
		for(int i=0;i<lis.size();i++){
			String serverHashString=lis.get(i).getServerIP()+lis.get(i).getServerPort();
			realNodeList.add(serverHashString);
			msi.put(serverHashString, lis.get(i));
		}
		
		//������ڵ���뵽����ĺ������
		for(String realStr: realNodeList){
			for(int i=0;i<VIRTUAL_NODES_PER_REAL;i++){
				String virStr=realStr+"VN"+i;
				int virHash=getHash(virStr);
				virtualNodes.put(virHash, virStr);
			}
		}
	}
	
	/* 	
	 * //��������Ĳ�����������ĸ�����ڵ������Ȼ������������ڵ��Ӧ����ʵ�ڵ�
	 * @see zhm.rpc.loadbalance.ILoadBalance#selectServer(zhm.rpc.core.IServerList)
	 */
	@Override
	public ServerProvider selectServer(List<ServerProvider> isl,String requestMess) {
		if(realNodeList==null){
			CreateConsistentHash(isl);
		}
		int requestHash=getHash(requestMess);
		SortedMap<Integer,String> getResult=virtualNodes.tailMap(requestHash);
		Integer resultInt=getResult.firstKey();
		String virNodeStr=virtualNodes.get(resultInt);
		
		String realNodeSt=virNodeStr.substring(0, virNodeStr.indexOf("VN"));
		
		return msi.get(realNodeSt);
	}
	
	//FNV1_32_HSAH�㷨
	private int getHash(String str){
		final int p=16777619;
		int hash=(int)2166136261L;
		for(int i=0;i<str.length();i++){
			hash=(hash^str.charAt(i)*p);
		}
		hash +=hash<<13;
		hash ^=hash>>7;
		hash +=hash<<3;
		hash ^=hash>>17;
		hash +=hash<<5;
		
		if(hash<0)hash=Math.abs(hash);
		return hash;
	}

}
