/**
 * 
 */
package se.lbroman.msrp.impl.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.MsrpHeader.HEADER_TYPE;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
import se.lbroman.msrp.impl.exception.ParseErrorException;

/**
 * @author leonard
 *
 */
public class HeaderParserImpl implements HeaderParser {

    private static Log logger = LogFactory.getLog(HeaderParserImpl.class);
    
    /* (non-Javadoc)
     * @see se.lbroman.msrp.impl.parser.HeaderParser#createRawHeader(java.lang.String)
     */
    @Override
    public RawMsrpHeader createRawHeader(String line) throws ParseErrorException {
        try {
            // logger.trace("Parsing a raw msrp header line");
            String[] s = line.split(": ");
            String key = s[0] + ": ";
            HEADER_TYPE type = HEADER_TYPE.byString(key);
            if (type == null) {
                throw new ParseErrorException("Unknown header type");
            }
            String content = s[1];
            if (content.contains("\r") || content.contains("\n")) {
                throw new ParseErrorException(
                        "Headers may not containt linebreaks");
            }
            if (logger.isTraceEnabled()) {
                logger.trace("Successfully parsed raw header: " + key + ""
                        + content);
            }
            return new RawMsrpHeader(type,key,content);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.debug(e);
            if (logger.isTraceEnabled()) {
                e.printStackTrace();
            }
            logger.debug("Tried to parse: " + line);
            throw new ParseErrorException(line);
        }
    }

}
