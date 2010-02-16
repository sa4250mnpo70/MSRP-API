package se.lbroman.msrp.impl.data.packet;

import se.lbroman.msrp.data.packet.OK;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;
import se.lbroman.msrp.impl.parser.PacketVisitor;

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
    public void accept(PacketVisitor visitor) throws ParseErrorException {
        visitor.visit(this);
    }
}
