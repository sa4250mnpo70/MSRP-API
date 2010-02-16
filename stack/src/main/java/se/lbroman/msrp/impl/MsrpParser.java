package se.lbroman.msrp.impl;

import java.util.EnumMap;
import java.util.LinkedHashMap;

import se.lbroman.msrp.data.header.*;
import se.lbroman.msrp.data.packet.*;
import se.lbroman.msrp.data.packet.MsrpPacket.PACKET_TYPE;
import se.lbroman.msrp.impl.data.header.AuthorizationHeaderImpl;
import se.lbroman.msrp.impl.data.header.ByteRangeHeaderImpl;
import se.lbroman.msrp.impl.data.header.ContentTypeHeaderImpl;
import se.lbroman.msrp.impl.data.header.FailureReportHeaderImpl;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.MessageIDHeaderImpl;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.StatusHeaderImpl;
import se.lbroman.msrp.impl.data.header.SuccessReportHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.UsePathHeaderImpl;
import se.lbroman.msrp.impl.data.header.WWWAuthenticateHeaderImpl;
import se.lbroman.msrp.impl.data.packet.AuthImpl;
import se.lbroman.msrp.impl.data.packet.BadRequestImpl;
import se.lbroman.msrp.impl.data.packet.MsrpPacketImpl;
import se.lbroman.msrp.impl.data.packet.NotImplementedImpl;
import se.lbroman.msrp.impl.data.packet.OKImpl;
import se.lbroman.msrp.impl.data.packet.RawMsrpPacket;
import se.lbroman.msrp.impl.data.packet.SendImpl;
import se.lbroman.msrp.impl.data.packet.UnauthorizedImpl;
import se.lbroman.msrp.impl.data.packet.VisitImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public class MsrpParser {
    
    private static final StatsCollector stats = StatsCollector.instance();
    @Deprecated
    public final static LinkedHashMap<String, Class<? extends MsrpHeaderImpl>> headerTypes = new LinkedHashMap<String, Class<? extends MsrpHeaderImpl>>();
    @Deprecated
    public final static EnumMap<PACKET_TYPE, Class<? extends MsrpPacketImpl>> packetTypes = new EnumMap<PACKET_TYPE, Class<? extends MsrpPacketImpl>>(
            PACKET_TYPE.class);

    static {
        headerTypes.put(AuthorizationHeader.key,
                AuthorizationHeaderImpl.class);
        headerTypes.put(ByteRangeHeader.key,
                ByteRangeHeaderImpl.class);
        headerTypes.put(ContentTypeHeader.key,
                ContentTypeHeaderImpl.class);
        headerTypes.put(FailureReportHeader.key,
                FailureReportHeaderImpl.class);
        headerTypes.put(FromPathHeader.key,
                FromPathHeaderImpl.class);
        headerTypes.put(MessageIDHeader.key,
                MessageIDHeaderImpl.class);
        headerTypes.put(StatusHeader.key,
                StatusHeaderImpl.class);
        headerTypes.put(SuccessReportHeader.key,
                SuccessReportHeaderImpl.class);
        headerTypes.put(ToPathHeader.key,
                ToPathHeaderImpl.class);
        headerTypes.put(UsePathHeader.key,
                UsePathHeaderImpl.class);
        headerTypes
                .put(
                        WWWAuthenticateHeader.key,
                        WWWAuthenticateHeaderImpl.class);
        packetTypes.put(OK.type,
                OKImpl.class);
        packetTypes.put(BadRequest.type,
                BadRequestImpl.class);
        packetTypes.put(NotImplemented.type,
                NotImplementedImpl.class);
        packetTypes.put(Unauthorized.type,
                UnauthorizedImpl.class);
    }

    public static MsrpPacketImpl parse(RawMsrpPacket rp)
            throws ParseErrorException {
        if (packetTypes.containsKey(rp.getType())) {
            try {
                MsrpPacketImpl p = packetTypes.get(rp.getType()).newInstance();
                p.parse(rp);
                return p;
            } catch (InstantiationException e) {
                throw new ParseErrorException("Something went extremely wrong",
                        e);
            } catch (IllegalAccessException e) {
                throw new ParseErrorException("Something went extremely wrong",
                        e);
            }
        } else {
            throw new ParseErrorException("Unimplemented packet");
        }
    }
    

}
