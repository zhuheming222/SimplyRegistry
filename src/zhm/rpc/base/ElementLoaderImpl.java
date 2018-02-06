/**
 * ElementLoaderImpl.java
 * zhm.rpc.base
 * 2017年12月21日下午9:44:18
 *
 */
package zhm.rpc.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author zhuheming
 * ElementLoaderImpl
 * 2017年12月21日下午9:44:18
 */
public class ElementLoaderImpl implements ElementLoader {

	private Map<String,Element> docMap=new HashMap<String,Element>();
	
	@SuppressWarnings("unchecked")
	public Map<String,Element> getDocument(Document doc){
		List<Element> liel=doc.getRootElement().elements();
		
		for(Element el:liel){
			if(el.elements()!=null){
				List<Element> lielst=el.elements();
				for(Element elst:lielst){
					String elName=elst.getName();
					docMap.put(elName, elst);
				}
			}
			String idStr=el.getName();
			//String idcode=el.attributeValue("code");
			//String idsrc=el.attributeValue("src");
			docMap.put(idStr, el);
		}
		return docMap;
	}
	
	/* (non-Javadoc)
	 * @see zhm.rpc.base.ElementLoader#getElement(java.lang.String)
	 */
	@Override
	public Element getElement(String elId) {
		// TODO Auto-generated method stub
		return docMap.get(elId);
	}

	/* (non-Javadoc)
	 * @see zhm.rpc.base.ElementLoader#getAllElement()
	 */
	@Override
	public Collection<Element> getAllElement() {
		// TODO Auto-generated method stub
		return docMap.values();
	}

}
