/**
 * 
 */
package se.lbroman.msrp.impl.data.header;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.PathHeader;
import se.lbroman.msrp.impl.data.MsrpURIImpl;

/**
 * @author Leonard Broman
 * 
 */
public abstract class PathHeaderImpl extends MsrpHeaderImpl implements
		PathHeader {

	private static Log logger = LogFactory.getLog(PathHeaderImpl.class);

	protected LinkedList<MsrpURIImpl> URIList = new LinkedList<MsrpURIImpl>();;

	protected PathHeaderImpl(List<MsrpURIImpl> list) {
		for (MsrpURIImpl u : list) {
			URIList.add(u.clone());
		}
	}

	protected PathHeaderImpl(MsrpURIImpl uri) {
		URIList.add(uri.clone());
	}

	/**
	 * Copy constructor
	 * 
	 * @param orig
	 */
	protected PathHeaderImpl(PathHeaderImpl orig) {
		for (MsrpURIImpl uri : orig.URIList) {
			URIList.add(uri.clone());
		}
	}

	public PathHeaderImpl() {
	}

	public List<MsrpURIImpl> getURIList() {
		return URIList;
	}
	
	public void setURIList(LinkedList<MsrpURIImpl> uriList) {
	    this.URIList = uriList;
	}

	/**
	 * Creates a ToPath from this path and reverses its order.
	 * 
	 * @return To-Path
	 */
	public ToPathHeaderImpl makeTo() {
		ToPathHeaderImpl to = new ToPathHeaderImpl(URIList);
		// to.reversePath(); //See javadoc on makeFrom()
		return to;
	}

	/**
	 * RFC 4976 page 5 states that during relay traversal the from-header is
	 * already reversed and thus should not be reversed when responding.
	 * 
	 * @return a From-Path
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc4976">RFC 4976</a>
	 */
	public FromPathHeaderImpl makeFrom() {
		FromPathHeaderImpl from = new FromPathHeaderImpl(URIList);
		return from;
	}

	@Override
	public String getValue() {
		String code = new String();
		for (MsrpURIImpl uri : URIList) {
			code += " " + uri.encode();
		}
		code = code.trim();
		return code;
	}

	@Override
	public abstract String getKey();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PathHeaderImpl))
			return false;
		final PathHeaderImpl other = (PathHeaderImpl) obj;
		if (URIList == null) {
			if (other.URIList != null)
				return false;
		} else if (!URIList.equals(other.URIList))
			return false;
		return true;
	}

}
