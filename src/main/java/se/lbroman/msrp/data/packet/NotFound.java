/**
 * 
 */
package se.lbroman.msrp.data.packet;

/**
 * @author Leonard Broman
 * 
 */
public interface NotFound extends Response {

	public static final int code = 404;
	public static final String type = "404";
	public static final String message = "Not found";

}
