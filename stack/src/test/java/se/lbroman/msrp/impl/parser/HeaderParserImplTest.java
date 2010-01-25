package se.lbroman.msrp.impl.parser;

import org.junit.Test;
import static org.junit.Assert.*;

import se.lbroman.msrp.data.header.ByteRangeHeader;
import se.lbroman.msrp.data.header.FromPathHeader;
import se.lbroman.msrp.data.header.StatusHeader;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
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
    public void parseStatusHeader() throws ParseErrorException {
        MsrpHeaderImpl header = parser.createHeader("Status: 000 200");
        assertTrue(header instanceof StatusHeader);
        StatusHeader h = (StatusHeader) header;
        assertEquals(0, h.getNameSpace());
        assertEquals(200, h.getCode());
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
