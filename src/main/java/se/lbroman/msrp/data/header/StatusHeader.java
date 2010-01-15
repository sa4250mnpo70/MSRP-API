/**
 * 
 */
package se.lbroman.msrp.data.header;

/**
 * @author Leonard Broman
 * 
 */
public interface StatusHeader extends MsrpHeader {

	public static final String key = MsrpHeader.HEADER_TYPE.Status.getKey();

	public int getCode();

	public int getNameSpace();

	public String getComment();

}
