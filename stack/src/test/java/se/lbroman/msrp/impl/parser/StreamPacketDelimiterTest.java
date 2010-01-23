package se.lbroman.msrp.impl.parser;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

import se.lbroman.msrp.impl.data.ByteArrays;
import se.lbroman.msrp.impl.data.ByteUtils;
import se.lbroman.msrp.impl.exception.NoPacketFoundException;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public class StreamPacketDelimiterTest {
    
    StreamPacketDelimiter delimiter = new StreamPacketDelimiter();
    
    ByteUtils byteUtils = new ByteArrays();
    
    
    @Test
    public void NoPacketFoundInEmptyBuffer() throws IOException, ParseErrorException {
        try {
            delimiter.extractPacket();
            fail();
        } catch (NoPacketFoundException e) {
            
        }
    }
    
    @Test
    public void NoPacketFoundInMSRPOnlyBuffer() throws ParseErrorException {
        charge("MSRP");
        try {
            delimiter.extractPacket();
            fail();
        } catch (NoPacketFoundException e) {
            
        }
    }
    
    @Test
    public void NoPacketFoundInMSRPSpaceOnlyBuffer() throws ParseErrorException {
        charge("MSRP ");
        try {
            delimiter.extractPacket();
            fail();
        } catch (NoPacketFoundException e) {
            
        }
    }
    
    @Test
    public void ParseErrorOnLongIds() throws NoPacketFoundException {
        charge("MSRP 123456789012345678901234567890123 ");
        try {
            delimiter.extractPacket();
            fail();
        } catch (ParseErrorException e) {
            
        }
    }

    private void charge(String data) {
        delimiter.size = data.length();
        byteUtils.copySubRange(data.getBytes(), 0, delimiter.buffer, 0, data.length());
    }

}
