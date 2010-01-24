package se.lbroman.msrp.impl.data.packet;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.MsrpHeader.HEADER_TYPE;
import se.lbroman.msrp.data.packet.Unauthorized;
import se.lbroman.msrp.impl.data.ByteArrayBuilder;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.WWWAuthenticateHeaderImpl;


/**
 * 
 * @author Leonard Broman
 * 
 */
public class UnauthorizedImpl extends ResponseImpl implements Unauthorized {
	/** Attributes */

	private static Log logger = LogFactory.getLog(UnauthorizedImpl.class);
	private WWWAuthenticateHeaderImpl wwwAuth;

	public UnauthorizedImpl() {

	}

	public UnauthorizedImpl(FromPathHeaderImpl from, ToPathHeaderImpl to) {
		setHeader(from);
		setHeader(to);
		setHeader(new WWWAuthenticateHeaderImpl());
	}

	@Deprecated
	public UnauthorizedImpl(AuthImpl auth) {

	}

	public WWWAuthenticateHeaderImpl getAuthenticateHeader() {
		return wwwAuth;
	}

	@Override
	public byte[] encode() {
		ByteArrayBuilder b = new ByteArrayBuilder();
		b.append(("MSRP " + id + " " + getCode() + " " + getMessage() + CRLF)
				.getBytes());
		b.append(encodeFromTo());
		b.append((wwwAuth.encode() + CRLF).getBytes());
		b.append(encodeEndLine());
		return b.getBytes();
	}

	@Override
	public void setHeader(MsrpHeaderImpl h) {
	/*	if (h.getType() == HEADER_TYPE.WWWAuthenticate) {
			wwwAuth = (WWWAuthenticateHeaderImpl) h;
			logger.trace(h.getKey() + "header set");
		} else {
			super.setHeader(h);
		} */

	}

	@Override
	public MsrpHeaderImpl getHeader(HEADER_TYPE type) {
		if (type == HEADER_TYPE.WWWAuthenticate) {
			return wwwAuth;
		} else {
			return super.getHeader(type);
		}
	}

	@Override
	public PACKET_TYPE getType() {
		return PACKET_TYPE.R401;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public UnauthorizedImpl clone() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public AuthImpl authorize(String username, String secret) {
		return new AuthImpl(this, username, secret);
	}

}
