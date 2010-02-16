package se.lbroman.msrp.impl.data.header;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.lbroman.msrp.data.header.MsrpHeader;
import se.lbroman.msrp.impl.exception.ParseErrorException;
import se.lbroman.msrp.impl.parser.HeaderVisitor;


/**
 * An MSRP header as defined in RFC 4975 and RFC 4976
 * 
 * @author Leonard Broman
 * @see <a href="http://tools.ietf.org/html/rfc4975">RFC 4975</a>
 * @see <a href="http://tools.ietf.org/html/rfc4976">RFC 4976</a>
 * 
 */
public abstract class MsrpHeaderImpl implements MsrpHeader {

	private static Logger logger = LoggerFactory.getLogger(MsrpHeaderImpl.class);

	/**
	 * @deprecated use the value in the raw header instead
	 */
	@Deprecated
	protected String value;
	
	protected RawMsrpHeader<?> rawHeader;


    @Override
	public MsrpHeaderImpl clone() throws UnsupportedOperationException {
	    throw new UnsupportedOperationException();
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
	

    public RawMsrpHeader<?> getRawHeader() {
        return rawHeader;
    }

    public void setRawHeader(RawMsrpHeader<?> rawHeader) {
        this.rawHeader = rawHeader;
    }

	/**
	 * Encode the Header without the CRLF at the end!
	 * 
	 * @return the header encoded as a String
	 * @see MsrpHeader#encode()
	 */
	public String encode() {
		return String.format("%1$s: %2$s",getKey(),getValue());
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
		return true;
	}
	
	public abstract void accept(HeaderVisitor v) throws ParseErrorException;
	
}
