/**
 * 
 */
package se.lbroman.msrp.impl.data.header;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.PathHeader;
import se.lbroman.msrp.exception.ParseErrorException;
import se.lbroman.msrp.impl.data.MsrpURIImpl;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;

/**
 * @author Leonard Broman
 * 
 */
public abstract class PathHeaderImpl extends MsrpHeaderImpl implements
		PathHeader {

	private static Log logger = LogFactory.getLog(PathHeaderImpl.class);

	protected LinkedList<MsrpURIImpl> URIList = new LinkedList<MsrpURIImpl>();;

	protected PathHeaderImpl(LinkedList<MsrpURIImpl> list) {
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

	protected static LinkedList<MsrpURIImpl> parseUriList(String data)
			throws HeaderParseErrorException {
		LinkedList<MsrpURIImpl> URIList = new LinkedList<MsrpURIImpl>();
		String[] set = data.split(" ");
		Vector<String> v = new Vector<String>(Arrays.asList(set));
		for (String s : v) {
			try {
				URIList.add(new MsrpURIImpl(s));
			} catch (ParseErrorException e) {
				logger.error(e);
				throw new HeaderParseErrorException(e.getMessage());
			}
		}
		return URIList;
	}

	public LinkedList<MsrpURIImpl> getURIList() {
		return URIList;
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
	public String encode() {
		return getKey() + getValue();
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
	public void parse(String data) throws HeaderParseErrorException {
		String[] set = data.split(getKey());
		if (set.length != 2)
			throw new HeaderParseErrorException("Malformed " + getKey()
					+ " header");
		URIList = parseUriList(set[1]);
		if (logger.isTraceEnabled()) {
			logger.trace("Parsed: \"" + data + "\" > \"" + encode() + "\"");
		}
	}

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
