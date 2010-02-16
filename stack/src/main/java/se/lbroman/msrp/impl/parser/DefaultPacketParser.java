package se.lbroman.msrp.impl.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.lbroman.msrp.data.packet.MsrpPacket;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
import se.lbroman.msrp.impl.data.packet.AuthImpl;
import se.lbroman.msrp.impl.data.packet.BadRequestImpl;
import se.lbroman.msrp.impl.data.packet.MsrpPacketImpl;
import se.lbroman.msrp.impl.data.packet.NotFoundImpl;
import se.lbroman.msrp.impl.data.packet.NotImplementedImpl;
import se.lbroman.msrp.impl.data.packet.OKImpl;
import se.lbroman.msrp.impl.data.packet.RawMsrpPacket;
import se.lbroman.msrp.impl.data.packet.ReportImpl;
import se.lbroman.msrp.impl.data.packet.SendImpl;
import se.lbroman.msrp.impl.data.packet.UnauthorizedImpl;
import se.lbroman.msrp.impl.data.packet.VisitImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public class DefaultPacketParser implements PacketParser, PacketVisitor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultPacketParser.class);

    public final static Map<Class<? extends MsrpPacket>, Class<? extends MsrpPacketImpl>> packetTypes = new HashMap<Class<? extends MsrpPacket>, Class<? extends MsrpPacketImpl>>();

    static {
        // MsrpParser.headerTypes = new LinkedHashMap<String, Class<? extends
        // MsrpHeaderImpl>>();
        // headerTypes.put("Authentication-Info: ",
        // com.atosorigin.jse.ims.msrp.data.header.AuthenticationInfoHeader.class);
        packetTypes.put(se.lbroman.msrp.data.packet.OK.class, se.lbroman.msrp.impl.data.packet.OKImpl.class);
        packetTypes.put(se.lbroman.msrp.data.packet.Send.class, se.lbroman.msrp.impl.data.packet.SendImpl.class);
        packetTypes.put(se.lbroman.msrp.data.packet.Visit.class, se.lbroman.msrp.impl.data.packet.VisitImpl.class);
        packetTypes.put(se.lbroman.msrp.data.packet.BadRequest.class,
                se.lbroman.msrp.impl.data.packet.BadRequestImpl.class);
        packetTypes.put(se.lbroman.msrp.data.packet.NotImplemented.class,
                se.lbroman.msrp.impl.data.packet.NotImplementedImpl.class);
        packetTypes.put(se.lbroman.msrp.data.packet.Auth.class, se.lbroman.msrp.impl.data.packet.AuthImpl.class);
        packetTypes.put(se.lbroman.msrp.data.packet.Unauthorized.class,
                se.lbroman.msrp.impl.data.packet.UnauthorizedImpl.class);
    }

    private HeaderParser headerParser = new HeaderParserImpl();
    
    private RawMsrpPacket source;
    
    public DefaultPacketParser() {
        
    }
    
    private DefaultPacketParser(RawMsrpPacket src) {
        this.source = src;
    }

    @Override
    public MsrpPacketImpl parsePacket(RawMsrpPacket rawPacket) throws ParseErrorException {
        if (packetTypes.containsKey(rawPacket.getType())) {
            try {
                MsrpPacketImpl p = packetTypes.get(rawPacket.getType()).newInstance();
                DefaultPacketParser parser = new DefaultPacketParser(rawPacket);
                p.accept(parser);
                return p;
            } catch (InstantiationException e) {
                throw new ParseErrorException("Something went extremely wrong", e);
            } catch (IllegalAccessException e) {
                throw new ParseErrorException("Something went extremely wrong", e);
            }
        } else {
            throw new ParseErrorException("Unimplemented packet");
        }
    }

    @Override
    public void visit(AuthImpl packet) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void visit(BadRequestImpl packet) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void visit(NotFoundImpl packet) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void visit(NotImplementedImpl packet) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void visit(OKImpl packet) throws ParseErrorException {
        addHeaders(packet);
    }

    @Override
    public void visit(ReportImpl packet) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void visit(SendImpl packet) throws ParseErrorException {
        if (logger.isDebugEnabled()) {
            logger.debug("Parsing SEND packet");
        }
        addHeaders(packet);
        packet.setContent(source.getContent());
        packet.setId(source.getTransactionID());
        if (source.getContFlag() == '$') {
            packet.setLastPacket(true);
        }
    }

    @Override
    public void visit(UnauthorizedImpl unauthorizedImpl) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void visit(VisitImpl visitImpl) {
        // TODO Auto-generated method stub
        
    }
    
    private void addHeaders(MsrpPacketImpl packet) throws ParseErrorException {
        for (RawMsrpHeader<?> h : source.getHeaders()) {
            MsrpHeaderImpl header = headerParser.createHeader(h);
            packet.setHeader(header);
        }
    }

}
