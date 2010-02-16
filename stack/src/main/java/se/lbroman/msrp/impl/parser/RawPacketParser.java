package se.lbroman.msrp.impl.parser;

import se.lbroman.msrp.impl.data.packet.RawMsrpPacket;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public interface RawPacketParser {
    
    public RawMsrpPacket parse(byte[] content) throws ParseErrorException;

}
