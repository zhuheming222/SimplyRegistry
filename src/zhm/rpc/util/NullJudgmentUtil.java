/**
 * NullJudgmentUtil.java
 * zhm.rpc.util
 * 2018��2��23������10:38:47
 *
 */
package zhm.rpc.util;

/**
 * @author zhuheming
 * NullJudgmentUtil
 * 2018��2��23������10:38:47
 */
public class NullJudgmentUtil {

	/*
	 * �ж϶����Ƿ�Ϊ��
	 * */
	public boolean objectNullJudgment(Object obj){
		if(obj!=null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * �ж϶���ķ��������Ƿ�Ϊ��
	 * ��������Ӧ�ò�Ӱ��ԭ������
	 * ����Զ���������
	 * **/
//	public boolean methodReturnNullJudgment(Object obj,String methodName){
//		//�õ���������ͣ�new һ������
//		//���������������new �Ķ���
//		//ʹ��new ����ִ�з��������ж�
//	}
}
