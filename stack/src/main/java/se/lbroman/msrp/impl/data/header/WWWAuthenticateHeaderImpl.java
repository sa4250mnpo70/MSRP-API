package se.lbroman.msrp.impl.data.header;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.WWWAuthenticateHeader;
import se.lbroman.msrp.impl.data.Pair;
import se.lbroman.msrp.impl.data.Parameter;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;
import se.lbroman.msrp.impl.parser.DefaultParamsParser;
import se.lbroman.msrp.impl.parser.HeaderVisitor;
import se.lbroman.msrp.impl.parser.ParamsParser;


/**
 * <pre>
 * WWW-Authenticate    = &quot;WWW-Authenticate:&quot; SP &quot;Digest&quot; SP digest-param *(&quot;,&quot; SP digest-param)
 * 
 *  digest-param        = ( realm / nonce / [ opaque ] / [ stale ] / [
 *  algorithm ] / qop-options  / [auth-param] )
 * 
 *  realm               = &quot;realm=&quot; realm-value
 *  realm-value         = quoted-string
 * 
 *  auth-param          = token &quot;=&quot; ( token / quoted-string )
 * 
 *  nonce               = &quot;nonce=&quot; nonce-value
 *  nonce-value         = quoted-string
 *  opaque              = &quot;opaque=&quot; quoted-string
 *  stale               = &quot;stale=&quot; ( &quot;true&quot; / &quot;false&quot; )
 *  algorithm           = &quot;algorithm=&quot; ( &quot;MD5&quot; / token )
 *  qop-options         = &quot;qop=&quot; DQUOTE qop-list DQUOTE
 *  qop-list            = qop-value *( &quot;,&quot; qop-value )
 *  qop-value           = &quot;auth&quot; / token
 * 
 * DQUOTE         =  %x22
 *  ; &quot; (Double Quote)
 * </pre>
 * 
 * @see <a href="http://tools.ietf.org/html/rfc2617>RFC 2617 - HTTP
 *      Authentication: Basic and Digest Access Authentication</a>
 * @see <a href="http://tools.ietf.org/html/rfc4976>RFC 4976 - Relay Extensions
 *      for MSRP</a>
 * @author Leonard Broman
 * 
 */
public class WWWAuthenticateHeaderImpl extends MsrpHeaderImpl implements
		WWWAuthenticateHeader {

	private static Log logger = LogFactory
			.getLog(WWWAuthenticateHeaderImpl.class);

	public enum PARAMETER {
		realm, nonce, opaque, stale, algorithm, qop;
	}

	/** Attributes */
	private final String method = "Digest";
	private List<Parameter> extraParams = new LinkedList<Parameter>();
	private EnumMap<PARAMETER, String> params = new EnumMap<PARAMETER, String>(
			PARAMETER.class);
	private static ParamsParser<PARAMETER> parser = new DefaultParamsParser<PARAMETER>(
			PARAMETER.class);
	private static Random r = new Random(System.currentTimeMillis());

	public WWWAuthenticateHeaderImpl() {
		params.put(PARAMETER.nonce, "\"" + r.nextLong() + "\"");
		params.put(PARAMETER.qop, "\"auth\"");
		params.put(PARAMETER.algorithm, "MD5");
	}

	public void setRealm(String value) {
		if (value == null) {
			params.remove(PARAMETER.realm);
		} else {
			String value2;
			if (value.startsWith("\""))
				value2 = value;
			else
				value2 = "\"" + value + "\"";
			if (parser.checkQuotedParam(PARAMETER.realm, PARAMETER.realm
					.toString()
					+ "=" + value2)) {
				params.put(PARAMETER.realm, value2);
			} else {
				throw new IllegalArgumentException(
						"The parameter failed quoted string check");
			}
		}
	}

	public void setNonce(String value) {
		if (value == null) {
			params.remove(PARAMETER.nonce);
		} else {
			String value2;
			if (value.startsWith("\""))
				value2 = value;
			else
				value2 = "\"" + value + "\"";
			if (parser.checkQuotedParam(PARAMETER.nonce, PARAMETER.nonce
					.toString()
					+ "=" + value2)) {
				params.put(PARAMETER.nonce, value2);
			} else {
				throw new IllegalArgumentException(
						"The parameter failed quoted string check");
			}
		}
	}

	public void setOpaque(String value) {
		if (value == null) {
			params.remove(PARAMETER.opaque);
		} else {
			String value2;
			if (value.startsWith("\""))
				value2 = value;
			else
				value2 = "\"" + value + "\"";
			if (parser.checkQuotedParam(PARAMETER.opaque, PARAMETER.opaque
					.toString()
					+ "=" + value2)) {
				params.put(PARAMETER.opaque, value2);
			} else {
				throw new IllegalArgumentException(
						"The parameter failed quoted string check");
			}
		}
	}

	public void setAlgorithm(String value) {
		if (value == null) {
			params.remove(PARAMETER.algorithm);
		} else {
			if (parser.checkTokenParam(PARAMETER.algorithm, PARAMETER.algorithm
					.toString()
					+ "=" + value)) {
				params.put(PARAMETER.algorithm, value);
			} else {
				throw new IllegalArgumentException(
						"The parameter failed quoted string check");
			}
		}
	}

	public void setQop(String qop) throws IllegalArgumentException {
		if (value == null) {
			params.remove(PARAMETER.qop);
		} else {
			String value2;
			if (value.startsWith("\""))
				value2 = value;
			else
				value2 = "\"" + value + "\"";
			if (parser.checkQuotedParam(PARAMETER.qop, PARAMETER.qop.toString()
					+ "=" + value2)) {
				params.put(PARAMETER.qop, value2);
			} else {
				throw new IllegalArgumentException(
						"The parameter failed quoted string check");
			}
		}
	}

	@Override
	public String encode() {
		return getKey() + getValue();
	}

	@Deprecated
	public void parse(String data) throws HeaderParseErrorException {
		// Remove and check the key
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
		// Check that everything is here
		if (!params.containsKey(PARAMETER.realm))
			throw new HeaderParseErrorException(
					"WWW-Authenticate header must have the realm parameter");
		if (!params.containsKey(PARAMETER.nonce))
			throw new HeaderParseErrorException(
					"WWW-Authenticate header must have the nonce parameter");
		if (!params.containsKey(PARAMETER.qop))
			throw new HeaderParseErrorException(
					"WWW-Authenticate header must have the qop parameter");
		logger.trace("Sucessfully parsed the WWW-Authenticate header");
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		String code = method;
		boolean first = true;
		for (Entry<PARAMETER, String> e : params.entrySet()) {
			if (first) {
				first = false;
				code += " ";
			} else {
				code += ", ";
			}
			if (e.getValue().length() == 0) {
				code += e.getKey().toString();
			} else {
				code += e.getKey().toString() + "=" + e.getValue();
			}
		}
		for (Parameter p : extraParams) {
			if (first) {
				first = false;
				code += " ";
			} else {
				code += ", ";
			}
			code += p.encode();
		}
		return code;
	}


	public AuthorizationHeaderImpl doAuth(String user, String secret) {
		return new AuthorizationHeaderImpl(this, user, secret);
	}

	public String getNonce() {
		String val = params.get(PARAMETER.nonce);
		return val.substring(1, val.length() - 1);
	}

	public String getOpaque() {
		String val = params.get(PARAMETER.opaque);
		return val.substring(1, val.length() - 1);
	}

	public String getRealm() {
		String val = params.get(PARAMETER.realm);
		return val.substring(1, val.length() - 1);
	}

	public boolean getStale() {
		return Boolean.parseBoolean(params.get(PARAMETER.stale));
	}

	public String getAlgorithm() {
		return params.get(PARAMETER.algorithm);
	}
	
	@Override
    public void accept(HeaderVisitor v) throws HeaderParseErrorException {
        v.visit(this);
    }

}
