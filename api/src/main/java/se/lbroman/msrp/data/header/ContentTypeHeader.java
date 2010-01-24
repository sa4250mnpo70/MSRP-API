/**
 * 
 */
package se.lbroman.msrp.data.header;

/**
 * @author Leonard Broman
 * 
 */
public interface ContentTypeHeader extends MsrpHeader {

	public static final String key = "Content-Type";

	public String getContentType();

	public String getContentSubType();

	public String getParameter(String param);

}
