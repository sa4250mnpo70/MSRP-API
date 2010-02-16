package se.lbroman.msrp.impl.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import se.lbroman.msrp.data.packet.OK;
import se.lbroman.msrp.data.packet.Send;
import se.lbroman.msrp.impl.MockFactory;
import se.lbroman.msrp.impl.data.packet.OKImpl;
import se.lbroman.msrp.impl.data.packet.RawMsrpPacket;
import se.lbroman.msrp.impl.data.packet.SendImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public class PacketParserTest {

    DefaultPacketParser parser = new DefaultPacketParser();
    
    @Test
    public void sendPacket() throws ParseErrorException {
        RawMsrpPacket packet = MockFactory.getRaw(Send.class);
        SendImpl result = (SendImpl) parser.parsePacket(packet);
        assertFalse(result.isLastPacket());
        assertNotNull(result.getTo());
    }
    
    @Test
    public void lastSendPacket() throws ParseErrorException {
        RawMsrpPacket packet = MockFactory.getRaw(Send.class);
        packet.setContFlag("$".getBytes()[0]);
        SendImpl result = (SendImpl) parser.parsePacket(packet);
        assertTrue(result.isLastPacket());
    }
    
    @Test
    public void content() throws ParseErrorException {
        RawMsrpPacket packet = MockFactory.getRaw(Send.class);
        String content = "This is a nice å ä ö packet é";
        packet.setContent(content.getBytes());
        SendImpl result = (SendImpl) parser.parsePacket(packet);
        assertEquals(content,new String(result.getContent()));
    }
    
    @Test
    public void test200Ok() throws ParseErrorException {
        RawMsrpPacket packet = MockFactory.getRaw(OK.class);
        OKImpl result = (OKImpl) parser.parsePacket(packet);
        assertNotNull(result.getTo());
    }
    
}
