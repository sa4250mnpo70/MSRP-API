package se.lbroman.msrp.data;

import java.io.Serializable;


/**
 * From RFC 4975
 * 
 * Parameters not supported.
 * 
 * <pre>
 * MSRP-URI = msrp-scheme &quot;://&quot; authority [&quot;/&quot; session-id] &quot;;&quot; transport *( &quot;;&quot; URI-parameter)
 * msrp-scheme = &quot;msrp&quot; / &quot;msrps&quot;
 * authority     = [ userinfo &quot;@&quot; ] host [ &quot;:&quot; port ]
 * session-id = 1*( unreserved / &quot;+&quot; / &quot;=&quot; / &quot;/&quot; )
 * unreserved    = ALPHA / DIGIT / &quot;-&quot; / &quot;.&quot; / &quot;_&quot; / &quot;&tilde;&quot;
 * transport = &quot;tcp&quot; / 1*ALPHANUM
 * URI-parameter = token [&quot;=&quot; token]
 * token = 1*(%x21 / %x23-27 / %x2A-2B / %x2D-2E / %x30-39 / %x41-5A / %x5E-7E)
 * </pre>
 * 
 * @author Leonard Broman
 * @see <a href="http://tools.ietf.org/html/rfc4975#section-9">RFC 4975</a>
 */
public interface MsrpURI extends Serializable {

	public static final int MSRP_PORT = 2855;

	/**
	 * 
	 * @return a copy
	 */
	public MsrpURI clone();

	/**
	 * 
	 * @return the host part of the URI
	 */
	public String getHost();

	/**
	 * 
	 * @return the port part of the URI
	 */
	public int getPort();

	public String getScheme();

	public String getTransport();

	public String getUserInfo();

	/**
	 * 
	 * @see Object#hashCode()
	 */
	public int hashCode();

	/**
	 * 
	 * @see Object#toString()
	 */
	public String toString();

	/**
	 * Set the resource of this URI
	 * 
	 * @param resource
	 *            the resource identifier to set
	 */
	public void setResource(String resource);

	/**
	 * 
	 * @return the resource identifier
	 */
	public String getResource();

}