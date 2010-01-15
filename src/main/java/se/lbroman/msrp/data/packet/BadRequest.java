/**
 * 
 */
package se.lbroman.msrp.data.packet;

/** 
 * @author Leonard Broman
 * 
 */
public interface BadRequest extends Response {

	public static final int code = 400;
	public static final PACKET_TYPE type = PACKET_TYPE.R400;
	public static final String message = type.getComment();

}
