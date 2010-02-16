package se.lbroman.msrp.impl.data.packet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.lbroman.msrp.data.packet.NotFound;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;
import se.lbroman.msrp.impl.parser.PacketVisitor;


/**
 * 
 * @author Leonard Broman
 * 
 */
@Deprecated
public class NotFoundImpl extends ResponseImpl implements NotFound {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

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
	
	@Override
    public void accept(PacketVisitor visitor) throws ParseErrorException {
        visitor.visit(this);
    }

}
