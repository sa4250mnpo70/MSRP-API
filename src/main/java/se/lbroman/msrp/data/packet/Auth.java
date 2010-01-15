/**
 * 
 */
package se.lbroman.msrp.data.packet;

/**
 * @author Leonard Broman
 * 
 */
public interface Auth extends Request {

	public static final PACKET_TYPE type = PACKET_TYPE.AUTH;

	public boolean verify(Unauthorized auth, String string);

}
