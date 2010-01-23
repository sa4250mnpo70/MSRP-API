/**
 * 
 */
package se.lbroman.msrp.data.packet;

/**
 * @author Leonard Broman
 * 
 */
public interface Response extends MsrpPacket {

	public int getCode();

	public String getMessage();

}
