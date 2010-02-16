package se.lbroman.msrp.impl.data.packet;

import se.lbroman.msrp.data.packet.BadRequest;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;
import se.lbroman.msrp.impl.parser.PacketVisitor;



/**
 * 
 * @author Leonard Broman
 * 
 */
public class BadRequestImpl extends ResponseImpl implements BadRequest {
	/* Attributes */

	// private static Logger logger = LoggerFactory.getLogger(BadRequestImpl.class);
	public BadRequestImpl() {

	}

	public BadRequestImpl(FromPathHeaderImpl from, ToPathHeaderImpl to,
			String id) {
		super();
		setHeader(from);
		setHeader(to);
		this.id = id;
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
    public void accept(PacketVisitor visitor) throws ParseErrorException {
        visitor.visit(this);
    }
	
}
