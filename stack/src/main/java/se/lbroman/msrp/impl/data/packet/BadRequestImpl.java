package se.lbroman.msrp.impl.data.packet;

import se.lbroman.msrp.data.packet.BadRequest;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;



/**
 * 
 * @author Leonard Broman
 * 
 */
public class BadRequestImpl extends ResponseImpl implements BadRequest {
	/* Attributes */

	// private static Log logger = LogFactory.getLog(BadRequestImpl.class);
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
	public PACKET_TYPE getType() {
		return PACKET_TYPE.R400;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
}
