/**
 * 
 */
package se.lbroman.msrp.data.header;

/**
 * @author Leonard Broman
 * 
 */
public interface StatusHeader extends MsrpHeader {

	public static final String key = "Status";

	public int getCode();

	public int getNameSpace();

	public String getComment();

}
