/**
 * 
 */
package se.lbroman.msrp.impl.data.header;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.AuthenticationInfoHeader;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;
import se.lbroman.msrp.impl.parser.HeaderVisitor;


/**
 * UNIMPLEMENTED!!!
 * 
 * <pre>
 * Authentication-Info =  &quot;Authentication-Info:&quot; SP ainfo *(&quot;,&quot; ainfo)
 * ainfo               = nextnonce / message-qop / response-auth / cnonce / nonce-count
 * nextnonce           =  &quot;nextnonce=&quot; nonce-value
 * message-qop         = &quot;qop=&quot; qop-value
 * qop-value           = &quot;auth&quot; / token
 * cnonce              = &quot;cnonce=&quot; cnonce-value
 * cnonce-value        = nonce-value
 * nonce-count         = &quot;nc=&quot; nc-value
 * nonce-value         = quoted-string
 * response-auth       =  &quot;rspauth=&quot; response-digest
 * response-digest     =  DQUOTE *LHEX DQUOTE
 * </pre>
 * 
 * @author Leonard Broman
 * @deprecated unimplemented
 */
@Deprecated
public class AuthenticationInfoHeaderImpl extends MsrpHeaderImpl implements
		AuthenticationInfoHeader {

	private static Log logger = LogFactory
			.getLog(AuthenticationInfoHeaderImpl.class);

	/**
	 * 
	 */
	public AuthenticationInfoHeaderImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String encode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parse(String data) throws HeaderParseErrorException {
		throw new HeaderParseErrorException(
				"Authentication-Info not implemented");
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		return null;
	}

    @Override
    public void accept(HeaderVisitor v) throws HeaderParseErrorException {
        v.visit(this);
    }

}
