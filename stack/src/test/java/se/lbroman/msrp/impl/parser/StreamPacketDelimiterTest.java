package se.lbroman.msrp.impl.parser;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

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

}
