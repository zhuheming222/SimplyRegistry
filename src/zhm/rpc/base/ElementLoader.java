/**
 * ElementLoader.java
 * zhm.rpc.base
 * 2017��12��21������9:37:53
 *
 */
package zhm.rpc.base;

import java.util.Collection;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author zhuheming
 * ElementLoader �����ļ�Ԫ�ؼ���
 * 
 * 2017��12��21������9:37:53
 */
public interface ElementLoader {
	
	/*
	 * �õ�Document�µ�����Ԫ��
	 * */
	public Map<String,Element> getDocument(Document doc);
	
	/*
	 * �õ������ļ�ĳ��Ԫ��
	 * */
	public Element getElement(String elId);
	
	/*
	 * �õ������ļ�����Ԫ��
	 * */
	public Collection<Element> getAllElement();
}
