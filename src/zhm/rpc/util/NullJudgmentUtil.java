/**
 * NullJudgmentUtil.java
 * zhm.rpc.util
 * 2018年2月23日上午10:38:47
 *
 */
package zhm.rpc.util;

/**
 * @author zhuheming
 * NullJudgmentUtil
 * 2018年2月23日上午10:38:47
 */
public class NullJudgmentUtil {

	/*
	 * 判断对象是否为空
	 * */
	public boolean objectNullJudgment(Object obj){
		if(obj!=null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 判断对象的方法返回是否为空
	 * 方法调用应该不影响原来对象
	 * 必须对对象进行深拷贝
	 * **/
//	public boolean methodReturnNullJudgment(Object obj,String methodName){
//		//得到对象的类型，new 一个对象
//		//将参数对象深拷贝到new 的对象
//		//使用new 对象执行方法进行判断
//	}
}
