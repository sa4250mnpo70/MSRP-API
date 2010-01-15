/**
 * 
 */
package se.lbroman.msrp.data.packet;

/**
 * @author Leonard Broman
 * 
 */
public interface OK extends Response {

	public static final int code = 200;
	public static final PACKET_TYPE type = PACKET_TYPE.R200;
	public static final String message = type.getComment();

}
