package se.lbroman.msrp.data.packet;

/**
 * 
 * @author Leonard Broman
 * 
 */
public interface Request extends MsrpPacket {

	public String getMessageID();

	public Response createResponse(PACKET_TYPE type);

}
