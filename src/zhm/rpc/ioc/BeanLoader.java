/**
 * BeanLoader.java
 * zhm.rpc.ioc
 * 2017��12��21������10:04:48
 *
 */
package zhm.rpc.ioc;

import java.util.Map;
import zhm.rpc.core.IApp;

/**
 * @author zhuheming
 * BeanLoader ��xml�н����ü��ز�ʵ�������ڴ��У��Ա����ʹ��
 * 2017��12��21������10:04:48
 */
public interface BeanLoader {

	/*
	 * ����xml�����ļ�ʵ������Ӧ�����л��࣬������ʵ��
	 * 
	 * */
	public Map<String,IApp> createBean();

}
