package se.lbroman.msrp.impl.data.packet;

import se.lbroman.msrp.data.packet.NotImplemented;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;


/**
 * 
 * @author Leonard Broman
 * 
 */
public class NotImplementedImpl extends ResponseImpl implements NotImplemented {

	// private Log logger = LogFactory.getLog(this.getClass());

	// @Override
	// public byte[] encode() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public void setHeader(MsrpHeaderImpl h) {
		super.setHeader(h);
	}

	@Override
	public PACKET_TYPE getType() {
		return PACKET_TYPE.R501;
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
		super.parse(rp);
	}

}
