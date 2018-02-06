/**
 * ConsistentHashLoadBalance.java
 * zhm.rpc.loadbalance
 * 2018年1月22日下午8:49:26
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
 * hash一致性负载均衡
 * @author zhuheming
 * ConsistentHashLoadBalance
 * 2018年1月22日下午8:49:26
 */
public class ConsistentHashLoadBalance implements ILoadBalance {

	//每个真是节点对应的虚拟节点的个数
	private static final int VIRTUAL_NODES_PER_REAL=5;
	
	private  List<String> realNodeList=null;
	
	private  Map<String,ServerProvider> msi=new HashMap<String,ServerProvider>();
	
	private  SortedMap<Integer,String> virtualNodes=new TreeMap<Integer,String>();
	

	public void CreateConsistentHash(List<ServerProvider> lis){
		realNodeList=new LinkedList<String>();

		//将字符串加入到realNodeList中，并且加入到msi中，用来定位到对应 的IServer		
		//先根据IserverList得到每个server的特定字符串，唯一标识服务，此处使用name+ip+port
		//List<IServer> lis=isl.getServerList();
		for(int i=0;i<lis.size();i++){
			String serverHashString=lis.get(i).getServerIP()+lis.get(i).getServerPort();
			realNodeList.add(serverHashString);
			msi.put(serverHashString, lis.get(i));
		}
		
		//将虚拟节点加入到虚拟的红黑树中
		for(String realStr: realNodeList){
			for(int i=0;i<VIRTUAL_NODES_PER_REAL;i++){
				String virStr=realStr+"VN"+i;
				int virHash=getHash(virStr);
				virtualNodes.put(virHash, virStr);
			}
		}
	}
	
	/* 	
	 * //根据请求的参数算出距离哪个虚拟节点最近，然后计算出改虚拟节点对应的真实节点
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
	
	//FNV1_32_HSAH算法
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
