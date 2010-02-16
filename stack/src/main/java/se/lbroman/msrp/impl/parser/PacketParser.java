package se.lbroman.msrp.impl.parser;

import se.lbroman.msrp.impl.data.packet.MsrpPacketImpl;
import se.lbroman.msrp.impl.data.packet.RawMsrpPacket;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public interface PacketParser {
    
    MsrpPacketImpl parsePacket(RawMsrpPacket rawPacket) throws ParseErrorException;

}
