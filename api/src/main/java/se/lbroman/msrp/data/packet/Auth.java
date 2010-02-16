/**
 * 
 */
package se.lbroman.msrp.data.packet;

/**
 * @author Leonard Broman
 * 
 */
public interface Auth extends Request {

	public static final String type = "AUTH";

	public boolean verify(Unauthorized auth, String string);

}
