package se.lbroman.msrp.impl.data.packet;

import se.lbroman.msrp.data.header.MsrpHeader.HEADER_TYPE;
import se.lbroman.msrp.data.packet.OK;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;

/**
 * 
 * @author Leonard Broman
 * 
 */
public class OKImpl extends ResponseImpl implements OK {

	/**
	 * 
	 */
	public OKImpl(FromPathHeaderImpl from, ToPathHeaderImpl to, String id) {
		setHeader(from);
		setHeader(to);
		this.id = id;
	}

	public OKImpl() {

	}

	@Override
	public PACKET_TYPE getType() {
		return PACKET_TYPE.R200;
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
	public OKImpl clone() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

    @Override
    public MsrpHeaderImpl getHeader(HEADER_TYPE header) {
        // TODO Auto-generated method stub
        return null;
    }
}
