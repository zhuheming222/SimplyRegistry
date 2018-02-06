/**
 * LoadBalanceManager.java
 * zhm.rpc.loadbalance
 * 2018年1月25日下午6:57:32
 *
 */
package zhm.rpc.loadbalance;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuheming
 * LoadBalanceManager
 * 2018年1月25日下午6:57:32
 */
public class LoadBalanceManager {

	private Map<String,ILoadBalance> loadBalanceMap=new HashMap<String,ILoadBalance>();
	
	public Map<String,ILoadBalance>  getLoadBalanceManager(){
		return loadBalanceMap;
	}
	
	/**
	 * 添加负载均衡map，启动时调用
	 * **/
	public void addLoadBalanceMap(String loadBalanceId,ILoadBalance iObject){
		if(loadBalanceId!=null&&iObject!=null){
			if(!this.loadBalanceMap.containsKey(loadBalanceId)){
				this.loadBalanceMap.put(loadBalanceId, iObject);
			}
		}
	}
	
	public ILoadBalance getLoadBalance(String loadBalanceName){
		if(loadBalanceName!=null){
			return this.loadBalanceMap.get(loadBalanceName);
		}else{
			return null;
		}
	}
}
