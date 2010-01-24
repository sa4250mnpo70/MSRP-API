package se.lbroman.msrp.impl.parser;

import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
import se.lbroman.msrp.impl.exception.ParseErrorException;

/**
 * 
 * @author Leonard Broman
 *
 */
public interface HeaderParser {
   
    /**
     * 
     * @param line
     * @return
     * @throws ParseErrorException
     */
    RawMsrpHeader<?> createRawHeader(String line) throws ParseErrorException;
    
    MsrpHeaderImpl createHeader(String line) throws ParseErrorException;

}
