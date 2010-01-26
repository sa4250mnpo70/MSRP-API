package se.lbroman.msrp.impl.parser;

import org.junit.Test;
import static org.junit.Assert.*;

import se.lbroman.msrp.impl.data.MsrpURIImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;

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
    
    @Test
    public void parseUSER() throws ParseErrorException {
        MsrpURIImpl uri = parser.createMsrpUri("msrp://user@test.com/;tcp");
        assertEquals("msrp", uri.getScheme());
        assertEquals("test.com", uri.getHost());
        assertEquals(2855, uri.getPort());
        assertEquals("tcp", uri.getTransport());
        assertEquals("user", uri.getUserInfo());
        assertEquals(null, uri.getResource());
        assertEquals("msrp://user@test.com/;tcp", uri.toString());
    }

    @Test
    public void parseRESOURCE() throws ParseErrorException {
        MsrpURIImpl uri = parser.createMsrpUri("msrp://user@test.com/1234;tcp");
        assertEquals("msrp", uri.getScheme());
        assertEquals("test.com", uri.getHost());
        assertEquals(2855, uri.getPort());
        assertEquals("tcp", uri.getTransport());
        assertEquals("user", uri.getUserInfo());
        assertEquals("1234", uri.getResource());
        assertEquals("msrp://user@test.com/1234;tcp", uri.toString());
    }

    @Test
    public void parseMSRPS() throws ParseErrorException {
        String text = "msrps://user@test.com/1234;tcp";
        MsrpURIImpl uri = parser.createMsrpUri(text);
        assertEquals("msrps", uri.getScheme());
        assertEquals("test.com", uri.getHost());
        assertEquals(2855, uri.getPort());
        assertEquals("tcp", uri.getTransport());
        assertEquals("user", uri.getUserInfo());
        assertEquals("1234", uri.getResource());
        assertEquals(text, uri.toString());
    }

    @Test
    public void parsePORT() throws ParseErrorException {
        String text = "msrp://user@test.com:1234/1234;tcp";
        MsrpURIImpl uri = parser.createMsrpUri(text);
        assertEquals("msrp", uri.getScheme());
        assertEquals("test.com", uri.getHost());
        assertEquals(1234, uri.getPort());
        assertEquals("tcp", uri.getTransport());
        assertEquals("user", uri.getUserInfo());
        assertEquals("1234", uri.getResource());
        assertEquals(text, uri.toString());
    }
    
    
}
