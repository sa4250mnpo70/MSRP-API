/**
 * 
 */
package se.lbroman.msrp.data.packet;

/**
 * @author Leonard Broman
 * @deprecated Not standardized (yet?)
 * 
 */
@Deprecated
public interface NicknameUsageFailed extends Response {

	public static final int code = 423;
	public static final String type = "423";
	public static final String message = "Nickname usage failed";

}
