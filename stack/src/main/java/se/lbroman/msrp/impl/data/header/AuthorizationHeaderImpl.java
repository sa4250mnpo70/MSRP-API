package se.lbroman.msrp.impl.data.header;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.AuthorizationHeader;
import se.lbroman.msrp.impl.data.ByteArrayBuilder;
import se.lbroman.msrp.impl.data.Pair;
import se.lbroman.msrp.impl.data.Parameter;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;

/**
 * 
 * <pre>
 * Authorization = &quot;Authorization:&quot; SP credentials
 * credentials = &quot;Digest&quot; SP digest-response *( &quot;,&quot; SP digest-response)
 * digest-response     = ( username / realm / nonce / response / 
 * [algorithm ] / cnonce / [opaque] / message-qop / [nonce-count]  / [auth-param] )
 * username            = &quot;username=&quot; username-value
 * username-value      = quoted-string
 * realm               = &quot;realm=&quot; realm-value
 * realm-value         = quoted-string
 * nonce               = &quot;nonce=&quot; nonce-value
 * nonce-value         = quoted-string
 * message-qop         = &quot;qop=&quot; qop-value
 * cnonce              = &quot;cnonce=&quot; cnonce-value
 * cnonce-value        = nonce-value
 * nonce-count         = &quot;nc=&quot; nc-value
 * nc-value            = 8LHEX
 * response            = &quot;response=&quot; request-digest
 * request-digest      = DQUOTE 32LHEX DQUOTE
 * LHEX                = DIGIT / %x61-66 ;lowercase a-f
 * opaque              = &quot;opaque=&quot; quoted-string
 * auth-param          = token &quot;=&quot; ( token / quoted-string )
 * token = 1*(%x21 / %x23-27 / %x2A-2B / %x2D-2E / %x30-39 / %x41-5A / %x5E-7E)
 * </pre>
 * 
 * @author Leonard Broman
 */
public class AuthorizationHeaderImpl extends MsrpHeaderImpl implements
		AuthorizationHeader {

	public enum PARAMETER {
		username, realm, nonce, response, algorithm, cnonce, opaque, qop, nc;
	}

	private static Log logger = LogFactory
			.getLog(AuthorizationHeaderImpl.class);

	/** Attributes */

	private final String method = "Digest";

	private List<Parameter> extraParams = new LinkedList<Parameter>();
	private EnumMap<PARAMETER, String> params = new EnumMap<PARAMETER, String>(
			PARAMETER.class);
	private static ParamsParser<PARAMETER> parser = new ParamsParser<PARAMETER>(
			PARAMETER.class);
	private static Random r = new Random(System.currentTimeMillis());

	public AuthorizationHeaderImpl() {

	}

	public AuthorizationHeaderImpl(AuthorizationHeaderImpl orig) {
		for (Entry<PARAMETER, String> e : orig.params.entrySet()) {
			params.put(e.getKey(), e.getValue());
		}
		for (Parameter p : orig.extraParams) {
			extraParams.add(p.clone());
		}
	}

	public AuthorizationHeaderImpl(WWWAuthenticateHeaderImpl h, String user,
			String secret) {
		String realm = h.getRealm();
		params.put(PARAMETER.realm, "\"" + realm + "\"");
		String nonce = h.getNonce();
		params.put(PARAMETER.nonce, "\"" + nonce + "\"");
		// Generate CNONCE
		String cnonce = r.nextLong() + "";
		params.put(PARAMETER.cnonce, "\"" + cnonce + "\"");
		String qop = "auth"; // Only auth supported!
		params.put(PARAMETER.qop, "\"" + qop + "\"");
		params.put(PARAMETER.username, "\"" + user + "\"");
		setNc(1);
		// // Construct H(A2)
		// // Since MSRP has no digest-uri we skip this for now.
		byte[] responseB = digest(realm, user, secret, nonce, cnonce, qop,
				getNc(), "", "");
		String response = new String(Hex.encodeHex(responseB));
		params.put(PARAMETER.algorithm, "MD5");
		params.put(PARAMETER.response, "\"" + response + "\"");
	}

	public boolean verify(WWWAuthenticateHeaderImpl h, String secret) {
		logger.debug("Verifying credentials for: " + getUsername());
		byte[] responseB = digest(h.getRealm(), getUsername(), secret, h
				.getNonce(), getCnonce(), getQop(), getNc(), "", "");
		String response = new String(Hex.encodeHex(responseB));
		String thisResp = getResponse();
		boolean result = thisResp.equals(response);
		if (result) {

		} else {
			logger.debug("Authentication failed for: " + getUsername());
			logger.debug("Expected: " + response);
			logger.debug("Received: " + thisResp);
		}
		return (result);
	}

	private String getResponse() {
		String val = params.get(PARAMETER.response);
		return val.substring(1, val.length() - 1);
	}

	private String getQop() {
		String val = params.get(PARAMETER.qop);
		return val.substring(1, val.length() - 1);
	}

	private void setNc(int i) {
		String nc = Integer.toString(i, 16);
		while (nc.length() < 8) {
			nc = "0" + nc;
		}
		params.put(PARAMETER.nc, nc);
	}

	private String getNc() {
		String val = params.get(PARAMETER.nc);
		return val;
	}

	private String getCnonce() {
		String val = params.get(PARAMETER.cnonce);
		return val.substring(1, val.length() - 1);
	}

	private String getUsername() {
		String val = params.get(PARAMETER.username);
		return val.substring(1, val.length() - 1);
	}

	public static byte[] digest(String realm, String user, String secret,
			String nonce, String cnonce, String qop, String nc, String method,
			String uri) {
		// Construct H(A1)
		logger.trace("Calculating MD5 response for: ");
		logger.trace("Realm: \"" + realm + "\"");
		logger.trace("User: \"" + user + "\"");
		// logger.trace("Secret: \"" + secret + "\"");
		logger.trace("Nonce: \"" + nonce + "\"");
		logger.trace("Cnonce: \"" + cnonce + "\"");
		logger.trace("Qop: \"" + qop + "\"");
		logger.trace("Nc: \"" + nc + "\"");
		logger.trace("Method: \"" + method + "\"");
		logger.trace("Uri: \"" + uri + "\"");
		byte[] ha1 = DigestUtils.md5((user + ":" + realm + ":" + secret)
				.getBytes());
		ByteArrayBuilder builder = new ByteArrayBuilder();
		builder.append(ha1);
		builder.append(":".getBytes());
		builder.append((nonce + ":").getBytes());
		builder.append((nc + ":").getBytes());
		// Generate CNONCE
		builder.append(cnonce.getBytes());
		builder.append(qop.getBytes());
		// Construct H(A2)
		// Since MSRP has no digest-uri we skip this for now.
		return DigestUtils.md5(builder.getBytes());
	}

	// /**
	// * Generates an Auth packet from an Unauthorized challenge response.
	// *
	// * UNIMPLEMENTED!!!
	// *
	// * @param packet
	// * @param username
	// * @param secret
	// * @return a digested header
	// *
	// * @deprecated
	// */
	// @Deprecated
	// public static AuthImpl digest(UnauthorizedImpl packet, String username,
	// String secret) {
	// return null;
	// }

	@Override
	public void parse(String data) throws HeaderParseErrorException {
		String[] vals = data.split(getKey(), 2);
		if (vals.length != 2) {
			throw new HeaderParseErrorException("Malformed header key for: "
					+ getKey() + " in " + data);
		}
		// Remove Digest, the only allowed method
		if (!vals[1].startsWith("Digest ")) {
			throw new HeaderParseErrorException("Malformed method for: "
					+ getKey() + " in " + data);
		}
		vals = vals[1].split("Digest ", 2);
		// Now there are only the params left, one mandatory
		// String left = vals[1];
		Pair<EnumMap<PARAMETER, String>, List<Parameter>> p = parser
				.parse(vals[1]);
		params = p.getFirst();
		extraParams = p.getSecond();
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public AuthorizationHeaderImpl clone() {
		return new AuthorizationHeaderImpl(this);
	}

	@Override
	public String getValue() {
		StringBuilder b = new StringBuilder();
		b.append(method + " ");
		boolean first = true;
		for (Entry<PARAMETER, String> e : params.entrySet()) {
			if (!first) {
				b.append(", ");
			} else {
				first = false;
			}
			b.append(e.getKey().toString());
			b.append("=");
			b.append(e.getValue());
		}
		for (Parameter p : extraParams) {
			if (!first) {
				b.append(", ");
			} else {
				first = false;
			}
			b.append(p.encode());
		}
		return b.toString();
	}

	@Override
	public HEADER_TYPE getType() {
		return HEADER_TYPE.Authorization;
	}

}
