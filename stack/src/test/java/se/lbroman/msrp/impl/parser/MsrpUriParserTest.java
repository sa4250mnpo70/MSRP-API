package se.lbroman.msrp.impl.parser;

import org.junit.Test;
import static org.junit.Assert.*;

import se.lbroman.msrp.exception.ParseErrorException;
import se.lbroman.msrp.impl.data.MsrpURIImpl;

public class MsrpUriParserTest {

    MsrpUriParser parser = new MsrpUriParser();
    
    
    @Test
    public void simpleUri() throws ParseErrorException {
        MsrpURIImpl uri = parser.createMsrpUri("msrp://host:1234/asdf;tcp");
        assertEquals("msrp", uri.getScheme());
        assertEquals(1234,uri.getPort());
        assertEquals("host",uri.getHost());
    }
    
    @Test
    public void missingTransport() {
        try {
            MsrpURIImpl uri = parser.createMsrpUri("msrp://host:1234/asdf;");
            fail();
        } catch (ParseErrorException e) {
            
        }
    }
    
    
}
