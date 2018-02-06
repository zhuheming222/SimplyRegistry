/**
 * XmlDocumentHolder.java
 * zhm.rpc.base
 * 2017年12月19日下午9:07:42
 *
 */
package zhm.rpc.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;

/**
 * @author zhuheming
 * XmlDocumentHolder xml配置文件加载
 * 2017年12月19日下午9:07:42
 */
public class XmlDocumentHolder implements DocumentHolder {

	//存放所有xml文件和路径的对应关系
	private Map<String,Document> documentList=new ConcurrentHashMap<String,Document>();
	
	/* (non-Javadoc)
	 * @see zhm.rpc.base.DocumentHolder#getDocument(java.lang.String)
	 */
	@Override
	public Document getDocument(String filePath) {
		// TODO Auto-generated method stub
		Document document=this.documentList.get(filePath);
		if(document==null){
			this.documentList.put(filePath, readDocument(filePath));
		}
		return this.documentList.get(filePath);
	}
	
	private Document readDocument(String filePath){
		Document doc=null;
		
		SAXReader saxRead=new SAXReader(true);
		saxRead.setEntityResolver(new MyRPCEntityResolver());
		
		File xmlFile=new File(filePath);
		System.out.println(xmlFile.getAbsolutePath());
		try {
			//InputStream in=new FileInputStream(xmlFile);
			doc=saxRead.read(xmlFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

}
