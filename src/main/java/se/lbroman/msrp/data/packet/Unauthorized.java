/**
 * 
 */
package se.lbroman.msrp.data.packet;


/**
 * @author Leonard Broman
 * 
 */
public interface Unauthorized extends Response {

	public static final int code = 401;
	public static final PACKET_TYPE type = PACKET_TYPE.R401;
	public static final String message = "Unauthorized";

	/**
	 * Creates an AUTH challenge request from this response.
	 * 
	 * @param username
	 * @param secret
	 * @return a digested and ready-to-send auth request
	 */
	public Auth authorize(String username, String secret);

}
