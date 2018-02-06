/**
 * ElementLoader.java
 * zhm.rpc.base
 * 2017年12月21日下午9:37:53
 *
 */
package zhm.rpc.base;

import java.util.Collection;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author zhuheming
 * ElementLoader 配置文件元素加载
 * 
 * 2017年12月21日下午9:37:53
 */
public interface ElementLoader {
	
	/*
	 * 得到Document下的所有元素
	 * */
	public Map<String,Element> getDocument(Document doc);
	
	/*
	 * 得到配置文件某个元素
	 * */
	public Element getElement(String elId);
	
	/*
	 * 得到配置文件所有元素
	 * */
	public Collection<Element> getAllElement();
}
