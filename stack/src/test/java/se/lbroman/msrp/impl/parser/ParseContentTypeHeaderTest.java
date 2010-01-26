package se.lbroman.msrp.impl.parser;

import static org.junit.Assert.*;
import org.junit.Test;

import se.lbroman.msrp.data.header.ContentTypeHeader;
import se.lbroman.msrp.impl.data.header.ContentTypeHeaderImpl;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;


public class ParseContentTypeHeaderTest {
    
    private static String simple = "Content-Type: text/plain";
    private static String plus = "Content-Type: text/plain+xml";
    private static String params = "Content-Type: text/plain+xml;transfer=fast;isfocus";
    
    HeaderParserImpl parser = new HeaderParserImpl();

    @Test
    public void testParseSimple() throws ParseErrorException {
        MsrpHeaderImpl h = parser.createHeader(simple);
        assertTrue(h instanceof ContentTypeHeader);
        ContentTypeHeaderImpl simpleHeader = (ContentTypeHeaderImpl) h;
        assertEquals("plain", simpleHeader.getContentSubType());
        assertEquals("text", simpleHeader.getContentType());
            
    }

    @Test
    public void encodeSimpleHeader() throws ParseErrorException {
        MsrpHeaderImpl h = parser.createHeader(simple);
        assertEquals(simple, h.encode());
    }
    
    @Test
    public void parsePlus() throws ParseErrorException {
        MsrpHeaderImpl h = parser.createHeader(plus);
        assertTrue(h instanceof ContentTypeHeader);
        ContentTypeHeaderImpl plusHeader = (ContentTypeHeaderImpl) h;
        
        assertEquals("plain+xml", plusHeader.getContentSubType());
        assertEquals("text", plusHeader.getContentType());
        assertEquals(null, plusHeader.getParameter("xml"));
    }

    @Test
    public void encodePlus() throws ParseErrorException {
        MsrpHeaderImpl h = parser.createHeader(plus);
        assertEquals(plus, h.encode());
    }
    
    @Test
    public void parseParams() throws ParseErrorException {
        MsrpHeaderImpl h = parser.createHeader(params);
        assertTrue(h instanceof ContentTypeHeader);
        ContentTypeHeaderImpl paramsHeader = (ContentTypeHeaderImpl) h;

        assertEquals("plain+xml", paramsHeader.getContentSubType());
        assertEquals("text", paramsHeader.getContentType());
        assertEquals("fast", paramsHeader.getParameter("transfer"));
        assertEquals("", paramsHeader.getParameter("isfocus"));
        assertNull(paramsHeader.getParameter("someOtherParam"));
    }
    
    @Test
    public void encodeParams() throws ParseErrorException {
        MsrpHeaderImpl h = parser.createHeader(params);
        assertEquals(params, h.encode());
    }

    @Test
    public void copy() throws ParseErrorException {
        MsrpHeaderImpl parsed = parser.createHeader(simple);
        ContentTypeHeaderImpl simpleHeader = (ContentTypeHeaderImpl) parsed;
        
        parsed = parser.createHeader(plus);
        ContentTypeHeaderImpl plusHeader = (ContentTypeHeaderImpl) parsed;
        
        parsed = parser.createHeader(params);
        ContentTypeHeaderImpl paramsHeader = (ContentTypeHeaderImpl) parsed;
        
        ContentTypeHeaderImpl h = simpleHeader.clone();
        assertEquals(simpleHeader, h);
        assertEquals(simpleHeader.hashCode(), h.hashCode());
        h = plusHeader.clone();
        assertEquals(plusHeader, h);
        assertEquals(plusHeader.hashCode(), h.hashCode());
        h = paramsHeader.clone();
        assertEquals(paramsHeader, h);
        assertEquals(paramsHeader.encode(), h.encode());
        assertEquals(paramsHeader.hashCode(), h.hashCode());
    }

}
