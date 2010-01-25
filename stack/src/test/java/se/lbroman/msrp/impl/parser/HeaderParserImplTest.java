package se.lbroman.msrp.impl.parser;

import org.junit.Test;


import static org.junit.Assert.*;

import se.lbroman.msrp.data.header.ByteRangeHeader;
import se.lbroman.msrp.data.header.FromPathHeader;
import se.lbroman.msrp.data.header.MessageIDHeader;
import se.lbroman.msrp.data.header.StatusHeader;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
import se.lbroman.msrp.impl.data.header.StatusHeaderImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public class HeaderParserImplTest {
    
    HeaderParserImpl parser = new HeaderParserImpl();
    
    @Test
    public void parseRawHeader() throws ParseErrorException {
        RawMsrpHeader<?> header = parser.createRawHeader("Byte-Range: 1-2");
        assertTrue(implementsType(header.getType(),ByteRangeHeader.class));
        assertEquals(ByteRangeHeader.key,header.getKey());
        assertEquals("1-2",header.getContent());
    }
    
    @Test
    public void parseFromPathHeader() throws ParseErrorException {
        RawMsrpHeader<?> header = parser.createRawHeader("From-Path: bla;bla;");
        assertTrue(implementsType(header.getType(),FromPathHeader.class));
        assertEquals(FromPathHeader.key,header.getKey());
        assertEquals("bla;bla;",header.getContent());
    }
    
    @Test
    public void parseByteRangeHeader() throws ParseErrorException {
        MsrpHeaderImpl header = parser.createHeader("Byte-Range: 1-*/*");
        assertTrue(header instanceof ByteRangeHeader);
    }
    
    @Test
    public void parseByteRangeAgain() throws ParseErrorException {
        String data = "Byte-Range: 1-*/2";
        MsrpHeaderImpl header = parser.createHeader(data);
        assertEquals(data, header.encode());
        assertTrue(header instanceof ByteRangeHeader);
    }
    
    @Test
    public void parseStatusHeader() throws ParseErrorException {
        String text = "Status: 000 200";
        MsrpHeaderImpl header = parser.createHeader(text);
        assertTrue(header instanceof StatusHeader);
        StatusHeader h = (StatusHeader) header;
        assertEquals(0, h.getNameSpace());
        assertEquals(200, h.getCode());
        assertEquals(text,h.encode());
    }
    
    @Test
    public void parseWithCommentSpace() throws ParseErrorException {
        String text = "Status: 000 123 This is not so bad ";
        MsrpHeaderImpl header = parser.createHeader(text);
        assertTrue(header instanceof StatusHeader);
        StatusHeader h = (StatusHeader) header;
        assertEquals(0, h.getNameSpace());
        assertEquals(123, h.getCode());
        assertEquals(text,h.encode() + " ");
    }

    @Test
    public void constructorInt() {
        StatusHeaderImpl h = new StatusHeaderImpl();
        h.setCode(123);
        assertEquals(0, h.getNameSpace());
        assertEquals(123, h.getCode());
        assertEquals("Status: 000 123", h.encode());
    }

    @Test
    public void constructorIntString() {
        StatusHeaderImpl h = new StatusHeaderImpl(123, "This is not so bad");
        assertEquals(0, h.getNameSpace());
        assertEquals(123, h.getCode());
        assertEquals("This is not so bad", h.getComment());
        assertEquals("Status: 000 123 This is not so bad", h.encode());
    }

    @Test
    public void copyConstructor() {
        StatusHeaderImpl h = new StatusHeaderImpl(123, "This is not so bad");
        StatusHeaderImpl h2 = new StatusHeaderImpl(h);
        assertEquals(h, h2);
        assertEquals(h.encode(), h2.encode());
    }

    @Test
    public void copy() {
        StatusHeaderImpl h = new StatusHeaderImpl(123, "This is not so bad");
        StatusHeaderImpl h2 = h.clone();
        assertEquals(h, h2);
        assertEquals(h.encode(), h2.encode());
    }
    
    @Test
    public void parseStatusHeaderComment() throws ParseErrorException {
        MsrpHeaderImpl header = parser.createHeader("Status: 000 200 OK");
        assertTrue(header instanceof StatusHeader);
        StatusHeader h = (StatusHeader) header;
        assertEquals(0, h.getNameSpace());
        assertEquals(200, h.getCode());
        assertEquals("OK",h.getComment());
    }
    
    @Test
    public void parseMessageId() throws ParseErrorException{
        MsrpHeaderImpl header = parser.createHeader("Message-ID: asdf1234");
        assertTrue(header instanceof MessageIDHeader);
        assertEquals("asdf1234",header.getValue());
    }
    
    
    boolean implementsType(Class<?> type, Class<?> iface) {
        Class<?>[] classes = type.getInterfaces();
        for (Class<?> c : classes) {
            if (c.equals(iface)) {
                return true;
            }
        }
        return false;
    }
    
    

}
