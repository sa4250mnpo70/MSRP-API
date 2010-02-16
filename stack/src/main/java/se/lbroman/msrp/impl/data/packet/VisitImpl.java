package se.lbroman.msrp.impl.data.packet;

import se.lbroman.msrp.data.packet.Visit;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;
import se.lbroman.msrp.impl.parser.PacketVisitor;

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
    public void accept(PacketVisitor visitor) throws ParseErrorException {
        visitor.visit(this);
    }

    @Override
    protected String getCode() {
        return type;
    }

}
