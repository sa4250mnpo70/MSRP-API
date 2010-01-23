package se.lbroman.msrp.impl.data.packet;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.packet.Auth;
import se.lbroman.msrp.data.packet.Unauthorized;
import se.lbroman.msrp.impl.data.ByteArrayBuilder;
import se.lbroman.msrp.impl.data.header.AuthorizationHeaderImpl;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;


/**
 * The initial AUTH request contains no identification information. The 401
 * response MUST contain a WWW-Authenticate header with realm,qop and nonce
 * parameters.
 * 
 * The second, authenticating, AUTH request contains an Authorization header
 * with the parameters username, realm, nonce, qop, cnonce and response
 * 
 * 
 * 
 * <pre>
 * (Alice opens a TLS connection to intra.example.com and sends an AUTH
 *  request to initiate the authentication process.)
 * 
 *  MSRP 49fh AUTH
 *  To-Path: msrps://alice@intra.example.com;tcp
 *  From-Path: msrps://alice.example.com:9892/98cjs;tcp
 *  -------49fh$
 * 
 *  (Alice's relay challenges the AUTH request.)
 * 
 *  MSRP 49fh 401 Unauthorized
 *  To-Path: msrps://alice.example.com:9892/98cjs;tcp
 *  From-Path: msrps://alice@intra.example.com;tcp
 *  WWW-Authenticate: Digest realm=&quot;intra.example.com&quot;, qop=&quot;auth&quot;, \
 *  nonce=&quot;dcd98b7102dd2f0e8b11d0f600bfb0c093&quot;
 *  -------49fh$
 * 
 *  (Alice responds to the challenge.)
 * 
 *  MSRP 49fi AUTH
 *  To-Path: msrps://alice@intra.example.com;tcp
 *  From-Path: msrps://alice.example.com:9892/98cjs;tcp
 *  Authorization: Digest username=&quot;Alice&quot;,
 *  realm=&quot;intra.example.com&quot;, \
 *  nonce=&quot;dcd98b7102dd2f0e8b11d0f600bfb0c093&quot;, \
 *  qop=auth, nc=00000001, cnonce=&quot;0a4f113b&quot;, \
 *  response=&quot;6629fae49393a05397450978507c4ef1&quot;
 *  -------49fi$
 * </pre>
 * 
 * @author Leonard Broman
 * @see <a href="http://tools.ietf.org/html/rfc4976#section-7>RFC 4976</a>
 */
public class AuthImpl extends RequestImpl implements Auth {
	/** Attributes */

	private static Log logger = LogFactory.getLog(AuthImpl.class);

	protected AuthorizationHeaderImpl auth;

	public AuthImpl() {

	}

	public AuthImpl(UnauthorizedImpl unauth, String user, String secret) {
		logger.debug("Created AUTH digested challenge response");
		setHeader(unauth.getFrom().makeTo());
		setHeader(unauth.getTo().makeFrom());
		auth = unauth.getAuthenticateHeader().doAuth(user, secret);
		setHeader(auth);
	}

	public AuthImpl(FromPathHeaderImpl from, ToPathHeaderImpl to) {
		setHeader(from);
		setHeader(to);
	}

	public AuthImpl(FromPathHeaderImpl from, ToPathHeaderImpl to,
			AuthorizationHeaderImpl auth) {
		setHeader(from);
		setHeader(to);
		setHeader(auth);
	}

	public AuthImpl(AuthImpl orig) throws UnsupportedOperationException {
		super(orig);
		if (orig.auth != null) {
			this.auth = orig.auth.clone();
		}
	}

	public boolean verify(Unauthorized unauth, String secret) {
		return auth.verify(((UnauthorizedImpl) unauth).getAuthenticateHeader(),
				secret);
	}

	/**
	 * 
	 */
	@Override
	public byte[] encode() {
		ByteArrayBuilder packet = new ByteArrayBuilder();
		packet.append(("MSRP " + id + " AUTH" + CRLF).getBytes());
		packet.append(encodeFromTo());
		if (auth != null) {
			packet.append((auth.encode() + CRLF).getBytes());
		}
		// packet.append(CRLF.getBytes());
		packet.append(encodeExtraHeaders());
		packet.append(encodeEndLine());
		return packet.getBytes();
	}

	@Override
	public void setHeader(MsrpHeaderImpl h) {
		if (h instanceof AuthorizationHeaderImpl) {
			auth = (AuthorizationHeaderImpl) h;
			logger.trace(h.getKey() + "header set");
		} else {
			super.setHeader(h);
		}
	}

	@Override
	public PACKET_TYPE getType() {
		return PACKET_TYPE.AUTH;
	}

	@Override
	public AuthImpl clone() throws UnsupportedOperationException   {
		return new AuthImpl(this);
	}
}
