/**
 * 
 */
package se.lbroman.msrp.impl.data.header;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Leonard Broman
 * 
 * @see se.lbroman.msrp.impl.parser.HeaderParser
 */
public class RawMsrpHeader<T extends MsrpHeaderImpl> implements Cloneable {

	private static final Logger logger = LoggerFactory.getLogger(RawMsrpHeader.class);
	private Class<T> type;
	private String key;
	private String content;

	/*
	 * 
	 * @param line
	 * @throws ParseErrorException
	 * @deprecated use the parser instead
	 * @see se.lbroman.msrp.impl.parser.HeaderParser
	 */
	/*
	@Deprecated
	public RawMsrpHeader(String line) throws ParseErrorException {
		// logger.trace("RawMsrpHeader(String line)");
		try {
			// logger.trace("Parsing a raw msrp header line");
			String[] s = line.split(": ");
			key = s[0] + ": ";
			type = HEADER_TYPE.byString(key);
			if (type == null) {
				throw new ParseErrorException("Unknown header type");
			}
			content = s[1];
			if (content.contains("\r") || content.contains("\n")) {
				throw new ParseErrorException(
						"Headers may not containt linebreaks");
			}
			if (logger.isTraceEnabled()) {
				logger.trace("Successfully parsed raw header: " + getKey() + ""
						+ getContent());
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.debug(e);
			if (logger.isTraceEnabled()) {
				e.printStackTrace();
			}
			logger.debug("Tried to parse: " + line);
			throw new ParseErrorException(line);
		}
	} */

	public RawMsrpHeader(Class<T> type2, String key2, String content2) {
        this.type = type2;
        this.key = key2;
        this.content = content2;
    }

    RawMsrpHeader(RawMsrpHeader<T> orig) {
        if (logger.isDebugEnabled()) {
            logger.debug("copy");
        }
        this.type = orig.type;
        this.key = orig.key;
        this.content = orig.content;
    }

    /**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	public Class<T> getType() {
		return type;
	}
	
	@SuppressWarnings("unchecked")
    public RawMsrpHeader<T> clone() {
	    return new RawMsrpHeader(this);
	}

}
