package se.lbroman.msrp.impl.parser;

import java.io.IOException;

import java.io.InputStream;

import org.easymock.classextension.EasyMock;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.classextension.EasyMock.*;

import se.lbroman.msrp.impl.data.ByteArrays;
import se.lbroman.msrp.impl.data.ByteUtils;
import se.lbroman.msrp.impl.exception.NoPacketFoundException;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public class StreamPacketDelimiterTest {
    
    StreamPacketDelimiter delimiter;
    
    ByteUtils byteUtils = new ByteArrays();
    
    InputStream is = EasyMock.createMock(InputStream.class);
    
    @Before
    public void setUp() {
        delimiter = new StreamPacketDelimiter(is);
    }
    
    @Test
    public void noPacketFoundInEmptyBuffer() throws IOException, ParseErrorException {
        try {
            delimiter.extractPacket();
            fail();
        } catch (NoPacketFoundException e) {
            
        }
    }
    
    @Test
    public void noPacketFoundInMSRPOnlyBuffer() throws ParseErrorException {
        charge("MSRP");
        try {
            delimiter.extractPacket();
            fail();
        } catch (NoPacketFoundException e) {
            
        }
    }
    
    @Test
    public void noPacketFoundInMSRPSpaceOnlyBuffer() throws ParseErrorException {
        charge("MSRP ");
        try {
            delimiter.extractPacket();
            fail();
        } catch (NoPacketFoundException e) {
            
        }
    }
    
    @Test
    public void parseErrorOnLongIds() throws NoPacketFoundException {
        charge("MSRP 123456789012345678901234567890123 ");
        try {
            delimiter.extractPacket();
            fail();
        } catch (ParseErrorException e) {
            
        }
    }
    
    @Test
    public void moveToFrontOnRebuffer() throws IOException {
        String test = " Test String";
        delimiter.start = 1;
        charge(test);
        expect(is.available()).andReturn(0);
        replay(is);
        delimiter.reBuffer();
        verify(is);
        assertTrue(byteUtils.equalsSubRange(delimiter.buffer, 0, test.getBytes(), 1, test.length() - 1));
    }

    private void charge(String data) {
        delimiter.size = data.length();
        byteUtils.copySubRange(data.getBytes(), 0, delimiter.buffer, 0, data.length());
    }

}
