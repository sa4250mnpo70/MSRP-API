package se.lbroman.msrp.impl.parser;

import java.io.IOException;

import se.lbroman.msrp.impl.data.packet.RawMsrpPacket;
import se.lbroman.msrp.impl.exception.NoPacketFoundException;
import se.lbroman.msrp.impl.exception.ParseErrorException;

/**
 * Parser that splits larger data block into raw msrp packets
 * 
 * @author Leonard Broman
 *
 */
public interface PacketDelimiter {

    RawMsrpPacket get() throws NoPacketFoundException, IOException, ParseErrorException;

}
