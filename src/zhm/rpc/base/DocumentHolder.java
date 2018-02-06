/**
 * DocumentHolder.java
 * zhm.rpc.base
 * 2017年12月19日下午8:57:39
 *
 */
package zhm.rpc.base;

import org.dom4j.Document;

/**
 * @author zhuheming
 * DocumentHolder 配置文件加载接口
 * 2017年12月19日下午8:57:39
 */
public interface DocumentHolder {
	public Document getDocument(String filePath);
}
