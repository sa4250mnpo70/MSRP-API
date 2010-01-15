/**
 * 
 */
package se.lbroman.msrp.data.header;

/**
 * @author Leonard Broman
 * 
 */
public interface ContentTypeHeader extends MsrpHeader {

	public static final String key = MsrpHeader.HEADER_TYPE.ContentType.getKey();

	public String getContentType();

	public String getContentSubType();

	public String getParameter(String param);

}
