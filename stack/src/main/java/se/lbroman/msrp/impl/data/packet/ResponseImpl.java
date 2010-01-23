package se.lbroman.msrp.impl.data.packet;

import se.lbroman.msrp.data.packet.Response;
import se.lbroman.msrp.impl.data.ByteArrayBuilder;


/**
 * 
 * @author Leonard Broman
 * 
 */
public abstract class ResponseImpl extends MsrpPacketImpl implements Response {

	// private static Log logger = LogFactory.getLog(ResponseImpl.class);

	public abstract int getCode();

	public abstract String getMessage();

	/**
	 * @return A byte array with the packet
	 */
	@Override
	public byte[] encode() {
		ByteArrayBuilder b = new ByteArrayBuilder();
		b.append(("MSRP " + id + " " + getCode() + " " + getMessage() + CRLF)
				.getBytes());
		b.append(encodeFromTo());
		b.append(encodeEndLine());
		// b.append(CRLF.getBytes());
		return b.getBytes();
	}

}
