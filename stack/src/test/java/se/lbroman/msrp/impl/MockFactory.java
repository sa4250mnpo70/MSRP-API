/**
 * 
 */
package se.lbroman.msrp.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.lbroman.msrp.data.header.MsrpHeader;
import se.lbroman.msrp.data.header.ToPathHeader;
import se.lbroman.msrp.data.packet.MsrpPacket;
import se.lbroman.msrp.data.packet.Send;
import se.lbroman.msrp.impl.data.MsrpURIImpl;
import se.lbroman.msrp.impl.data.header.ContentTypeHeaderImpl;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.MessageIDHeaderImpl;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
import se.lbroman.msrp.impl.data.header.StatusHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;
import se.lbroman.msrp.impl.data.packet.AuthImpl;
import se.lbroman.msrp.impl.data.packet.RawMsrpPacket;
import se.lbroman.msrp.impl.data.packet.ReportImpl;
import se.lbroman.msrp.impl.data.packet.SendImpl;
import se.lbroman.msrp.impl.data.packet.VisitImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;

/**
 * @author Leonard Broman
 * 
 */
public class MockFactory {

    private static Logger logger = LoggerFactory.getLogger(MockFactory.class);

    private static int count = 1;

    public static MsrpURIImpl getMsrpURI() {
        MsrpURIImpl result = new MsrpURIImpl();
        result.setResource(Integer.toString(count++));
        result.setScheme("msrp");
        result.setHost("host" + count++ + ".example.com");
        return result;
    }

    public static ToPathHeaderImpl getToPathHeader() {
        return new ToPathHeaderImpl(getMsrpURI());
    }

    public static FromPathHeaderImpl getFromPathHeader() {
        return new FromPathHeaderImpl(getMsrpURI());
    }

    public static ContentTypeHeaderImpl getContentTypeHeader() {
        return new ContentTypeHeaderImpl("text", "plain");
    }

    private static MessageIDHeaderImpl getMessagIDHeader() {
        return new MessageIDHeaderImpl().setIdent("x" + count++);
    }

    private static StatusHeaderImpl getStatusHeader() {
        return new StatusHeaderImpl(123, "Special status");
    }

    public static SendImpl getSend(byte[] content) {
        return new SendImpl(getFromPathHeader(), getToPathHeader(), getContentTypeHeader(), content);
    }

    public static VisitImpl getVisit() {
        return new VisitImpl(getFromPathHeader(), getToPathHeader());
    }

    public static ReportImpl getReport() {
        return new ReportImpl(getFromPathHeader(), getToPathHeader(), getStatusHeader(), getMessagIDHeader());
    }

    public static AuthImpl getAuth() {
        return new AuthImpl(getFromPathHeader(), getToPathHeader());
    }
    
    public static <T extends MsrpPacket> RawMsrpPacket getRaw(Class<T> type) {
        RawMsrpPacket rp = new RawMsrpPacket();
        rp.setType(type);
        rp.setID("trans-" + count++);
        rp.setContent("\u00e5\u00e4\u00f6".getBytes());
        rp.addHeader(getRawTo());
        return rp;
    }

    private static RawMsrpHeader<?> getRawTo() {
        return new RawMsrpHeader<ToPathHeaderImpl>(ToPathHeaderImpl.class,"To-Path",getMsrpURI().encode());
    }

}
