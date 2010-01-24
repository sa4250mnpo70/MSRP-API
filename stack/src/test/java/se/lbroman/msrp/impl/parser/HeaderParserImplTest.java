package se.lbroman.msrp.impl.parser;

import org.junit.Test;
import static org.junit.Assert.*;

import se.lbroman.msrp.data.header.MsrpHeader;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public class HeaderParserImplTest {
    
    HeaderParserImpl parser = new HeaderParserImpl();
    
    @Test
    public void ParseRawHeader() throws ParseErrorException {
        RawMsrpHeader header = parser.createRawHeader("Byte-Range: 1-2");
        assertEquals(MsrpHeader.HEADER_TYPE.ByteRange,header.getType());
        assertEquals("Byte-Range: ",header.getKey());
        assertEquals("1-2",header.getContent());
    }
    
    

}
