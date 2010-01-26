package se.lbroman.msrp.impl.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;


/**
 * 
 * @author Leonard Broman
 *
 */
public class ParsePathHeadersTest {
    
    HeaderParserImpl parser = new HeaderParserImpl(); 
    
    @Test
    public void parseFromHeaderOneLeg() throws ParseErrorException {
        String leg1 = "msrp://example.com/;tcp";
        String text = "From-Path: " + leg1;
        FromPathHeaderImpl h = (FromPathHeaderImpl) parser.createHeader(text);
        assertEquals(text, h.encode());
        assertEquals(leg1, h.getOrigin().encode());
        assertEquals(leg1, h.getLastHop().encode());
    }

    @Test
    public void parseFromHeaderTwoLegs() throws ParseErrorException {
        String leg1 = "msrp://example.com/;tcp";
        String leg2 = "msrp://host.com/897648;tcp";
        String text = "From-Path: " + leg1 + " " + leg2;
        FromPathHeaderImpl h = (FromPathHeaderImpl) parser.createHeader(text);
        assertEquals(text, h.encode());
        assertEquals(leg2, h.getOrigin().encode());
        assertEquals(leg1, h.getLastHop().encode());
    }

    @Test(expected = ParseErrorException.class)
    public void parseFromHeaderError() throws ParseErrorException {
        String leg1 = "msrp://example.com/;tcp";
        String leg2 = "msrp://host.com/897648;tcp";
        String text = "From-Path: " + leg1 + "\r\n" + leg2;
        parser.createHeader(text);
    }
    
    @Test
    public void parseOneLeg() throws ParseErrorException {
        String leg1 = "msrp://example.com/;tcp";
        // String leg2 = "msrp://host.com/897648;tcp";
        String text = "To-Path: " + leg1;
        ToPathHeaderImpl h = (ToPathHeaderImpl) parser.createHeader(text);
        assertEquals(text, h.encode());
        assertEquals(leg1, h.getDestination().encode());
        assertEquals(leg1, h.getNextHop().encode());
    }

    @Test
    public void parseTwoLegs() throws ParseErrorException {
        String leg1 = "msrp://example.com/;tcp";
        String leg2 = "msrp://host.com/897648;tcp";
        String text = "To-Path: " + leg1 + " " + leg2;
        ToPathHeaderImpl h = (ToPathHeaderImpl) parser.createHeader(text);
        assertEquals(text, h.encode());
        assertEquals(leg2, h.getDestination().encode());
        assertEquals(leg1, h.getNextHop().encode());
    }
    
    
    
}
