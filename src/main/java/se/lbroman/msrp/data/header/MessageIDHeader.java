/**
 * 
 */
package se.lbroman.msrp.data.header;


/**
 * @author Leonard Broman
 * 
 */
public interface MessageIDHeader extends MsrpHeader {

	public static final String key = MsrpHeader.HEADER_TYPE.MessageID.getKey();
}
