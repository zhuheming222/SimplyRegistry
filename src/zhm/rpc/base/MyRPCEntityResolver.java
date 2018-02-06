/**
 * MyRPCEntityResolver.java
 * zhm.rpc.base
 * 2017年12月19日下午9:50:59
 *
 */
package zhm.rpc.base;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author zhuheming
 * MyRPCEntityResolver
 * 2017年12月19日下午9:50:59
 */
public class MyRPCEntityResolver implements EntityResolver {

	private static final String PUBLIC_ID = null;

	/* (non-Javadoc)
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
	 */
	  public InputSource resolveEntity (String publicId, String systemId) {
	    if (systemId.endsWith("log4j.dtd")  || PUBLIC_ID.equals(publicId)) {
	      Class clazz = getClass();
	      InputStream in = clazz.getResourceAsStream("MyRPC.dtd");
	      if (in == null) {
		    System.out.println("Could not find [MyRPC.dtd] using [" + clazz.getClassLoader()
			     + "] class loader, parsed without DTD.");
	        in = new ByteArrayInputStream(new byte[0]);
	      }
		  return new InputSource(in);
	    } else {
	      return null;
	    }
	  }


}
