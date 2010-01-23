package se.lbroman.msrp.impl.data.header;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.MsrpHeader;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;


/**
 * An MSRP header as defined in RFC 4975 and RFC 4976
 * 
 * @author Leonard Broman
 * @see <a href="http://tools.ietf.org/html/rfc4975">RFC 4975</a>
 * @see <a href="http://tools.ietf.org/html/rfc4976">RFC 4976</a>
 * 
 */
public abstract class MsrpHeaderImpl implements MsrpHeader {

	private static Log logger = LogFactory.getLog(MsrpHeaderImpl.class);

	protected String value;

	@Override
	public MsrpHeaderImpl clone() throws UnsupportedOperationException {
	    throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param data
	 * @throws HeaderParseErrorException
	 */
	public void parse(String data) throws HeaderParseErrorException {
		// String value;
		String[] set = data.split(getKey());
		if (set.length != 2) {
			throw new HeaderParseErrorException("Malformed " + getKey()
					+ " header");
		}
		value = set[1];
		if (logger.isTraceEnabled()) {
			logger.trace("Parsed: \"" + data + "\" > \"" + encode() + "\"");
		}
	}

	/**
	 * Get the key of this header
	 * 
	 * @return this headers key
	 * @see MsrpHeader#getKey()
	 */
	public abstract String getKey();

	/**
	 * Get the value of this header
	 * 
	 * @return this headers value
	 * @see MsrpHeader#getValue()
	 */
	public abstract String getValue();

	/**
	 * Encode the Header without the CRLF at the end!
	 * 
	 * @return the header encoded as a String
	 * @see MsrpHeader#encode()
	 */
	public String encode() {
		return getKey() + getValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MsrpHeaderImpl other = (MsrpHeaderImpl) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	public abstract HEADER_TYPE getType();

}
