package se.lbroman.msrp.impl.data.packet;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.packet.NotFound;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;


/**
 * 
 * @author Leonard Broman
 * 
 */
@Deprecated
public class NotFoundImpl extends ResponseImpl implements NotFound {

	private Log logger = LogFactory.getLog(this.getClass());

	@Override
	public byte[] encode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHeader(MsrpHeaderImpl h) {
		super.setHeader(h);
	}

	@Override
	public PACKET_TYPE getType() {
		return PACKET_TYPE.R404;
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
	public void parse(RawMsrpPacket rp) {
		// TODO Implement
		logger.error("Unimplemented");

	}

}
