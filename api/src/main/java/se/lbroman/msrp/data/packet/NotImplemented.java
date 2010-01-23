/**
 * 
 */
package se.lbroman.msrp.data.packet;

/**
 * @author Leonard Broman
 * 
 */
public interface NotImplemented extends Response {

	public static final int code = 501;
	public static final PACKET_TYPE type = PACKET_TYPE.R501;
	public static final String message = "Not implemented";

}
