package se.lbroman.msrp.impl.parser;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

import se.lbroman.msrp.impl.data.ByteArrays;
import se.lbroman.msrp.impl.exception.NoPacketFoundException;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public class StreamPacketDelimiterTest {
    
    StreamPacketDelimiter delimiter = new StreamPacketDelimiter();
    
    
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
        delimiter.size = 4;
        ByteArrays.copySubRange("MSRP".getBytes(), 0, delimiter.buffer, 0, 4);
        try {
            delimiter.extractPacket();
            fail();
        } catch (NoPacketFoundException e) {
            
        }
    }

}
