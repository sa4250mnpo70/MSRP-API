package se.lbroman.msrp.impl.data.packet;

import se.lbroman.msrp.data.packet.Visit;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;


/**
 * Implementation of the VISIT packet
 * 
 * @author Leonard Broman
 * 
 */
public class VisitImpl extends RequestImpl implements Visit {

	public VisitImpl() {

	}

	public VisitImpl(FromPathHeaderImpl from, ToPathHeaderImpl to) {
		setHeader(from);
		setHeader(to);
	}

	@Override
	public PACKET_TYPE getType() {
		return PACKET_TYPE.VISIT;
	}

}
