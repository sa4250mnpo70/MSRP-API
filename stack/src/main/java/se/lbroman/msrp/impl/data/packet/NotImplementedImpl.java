package se.lbroman.msrp.impl.data.packet;

import se.lbroman.msrp.data.packet.NotImplemented;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;
import se.lbroman.msrp.impl.parser.PacketVisitor;

/**
 * 
 * @author Leonard Broman
 * 
 */
public class NotImplementedImpl extends ResponseImpl implements NotImplemented {

    // private Log logger = LoggerFactory.getLogger(this.getClass());

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

    @Override
    public void accept(PacketVisitor visitor) throws ParseErrorException {
        visitor.visit(this);
    }

}
