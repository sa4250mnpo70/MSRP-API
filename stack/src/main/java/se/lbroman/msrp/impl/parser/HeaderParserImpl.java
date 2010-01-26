/**
 * 
 */
package se.lbroman.msrp.impl.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.AuthenticationInfoHeader;
import se.lbroman.msrp.data.header.AuthorizationHeader;
import se.lbroman.msrp.data.header.ByteRangeHeader;
import se.lbroman.msrp.data.header.ContentTypeHeader;
import se.lbroman.msrp.data.header.FailureReportHeader;
import se.lbroman.msrp.data.header.FromPathHeader;
import se.lbroman.msrp.data.header.MessageIDHeader;
import se.lbroman.msrp.data.header.StatusHeader;
import se.lbroman.msrp.data.header.SuccessReportHeader;
import se.lbroman.msrp.data.header.ToPathHeader;
import se.lbroman.msrp.data.header.UsePathHeader;
import se.lbroman.msrp.data.header.WWWAuthenticateHeader;
import se.lbroman.msrp.impl.data.Parameter;
import se.lbroman.msrp.impl.data.header.AuthenticationInfoHeaderImpl;
import se.lbroman.msrp.impl.data.header.AuthorizationHeaderImpl;
import se.lbroman.msrp.impl.data.header.ByteRangeHeaderImpl;
import se.lbroman.msrp.impl.data.header.ContentTypeHeaderImpl;
import se.lbroman.msrp.impl.data.header.FailureReportHeaderImpl;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.MessageIDHeaderImpl;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
import se.lbroman.msrp.impl.data.header.StatusHeaderImpl;
import se.lbroman.msrp.impl.data.header.SuccessReportHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.UsePathHeaderImpl;
import se.lbroman.msrp.impl.data.header.WWWAuthenticateHeaderImpl;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;
import se.lbroman.msrp.impl.exception.ParseErrorException;

/**
 * @author leonard
 * 
 */
public class HeaderParserImpl implements HeaderParser, HeaderVisitor {

    private static Log logger = LogFactory.getLog(HeaderParserImpl.class);

    private Map<String, Class<?>> keys = new TreeMap<String, Class<?>>();

    {
        keys.put(AuthenticationInfoHeader.key, AuthenticationInfoHeaderImpl.class);
        keys.put(AuthorizationHeader.key, AuthorizationHeaderImpl.class);
        keys.put(ByteRangeHeader.key, ByteRangeHeaderImpl.class);
        keys.put(ContentTypeHeader.key, ContentTypeHeaderImpl.class);
        keys.put(FailureReportHeader.key, FailureReportHeaderImpl.class);
        keys.put(FromPathHeader.key, FromPathHeaderImpl.class);
        keys.put(MessageIDHeader.key, MessageIDHeaderImpl.class);
        keys.put(StatusHeader.key, StatusHeaderImpl.class);
        keys.put(SuccessReportHeader.key, SuccessReportHeaderImpl.class);
        keys.put(ToPathHeader.key, ToPathHeaderImpl.class);
        keys.put(UsePathHeader.key, UsePathHeaderImpl.class);
        keys.put(WWWAuthenticateHeader.key, WWWAuthenticateHeaderImpl.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * se.lbroman.msrp.impl.parser.HeaderParser#createRawHeader(java.lang.String
     * )
     */

    @Override
    public RawMsrpHeader<?> createRawHeader(String line) throws ParseErrorException {
        try {
            // logger.trace("Parsing a raw msrp header line");
            String[] s = line.split(": ");
            String key = s[0];
            Class<?> type = getImplementationType(key);
            if (type == null) {
                throw new ParseErrorException("Unknown header type");
            }
            String content = s[1];
            if (content.contains("\r") || content.contains("\n")) {
                throw new ParseErrorException("Headers may not containt linebreaks");
            }
            if (logger.isTraceEnabled()) {
                logger.trace("Successfully parsed raw header: " + key + ": " + content);
            }
            @SuppressWarnings("unchecked")
            RawMsrpHeader<?> result = new RawMsrpHeader(type, key, content);
            return result;
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.debug(e);
            if (logger.isTraceEnabled()) {
                e.printStackTrace();
            }
            logger.debug("Tried to parse: " + line);
            throw new ParseErrorException(line);
        }
    }

    private Class<?> getImplementationType(String key) throws ParseErrorException {
        if (keys.containsKey(key)) {
            return keys.get(key);
        } else {
            throw new ParseErrorException("Unsupported header type : " + key);
        }
    }

    public MsrpHeaderImpl createHeader(String line) throws ParseErrorException {
        RawMsrpHeader<?> raw = createRawHeader(line);
        MsrpHeaderImpl header = instanceiate(raw);

        header.accept(this);
        return header;
    }

    private MsrpHeaderImpl instanceiate(RawMsrpHeader<?> raw) {
        MsrpHeaderImpl header = null;
        try {
            header = raw.getType().newInstance();
            header.setRawHeader(raw);
        } catch (InstantiationException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return header;
    }

    @Override
    public void visit(WWWAuthenticateHeaderImpl wwwAuthenticateHeaderImpl) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(UsePathHeaderImpl usePathHeaderImpl) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ToPathHeaderImpl toPathHeaderImpl) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(SuccessReportHeaderImpl successReportHeaderImpl) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(StatusHeaderImpl header) {
        String value = header.getRawHeader().getContent();
        String[] set = value.split(" ", 3);
        int code = Integer.parseInt(set[1]);
        String comment;
        if (set.length > 2) {
            comment = set[2];
            comment = comment.trim();
        } else {
            comment = "";
        }
        header.setCode(code);
        header.setComment(comment);
        if (logger.isTraceEnabled()) {
            logger.trace("Parsed \"" + value + "\" > \"" + header.encode() + "\"");
        }
    }

    @Override
    public void visit(MessageIDHeaderImpl header) {
        header.setIdent(header.getRawHeader().getContent());
    }

    @Override
    public void visit(FromPathHeaderImpl fromPathHeaderImpl) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(FailureReportHeaderImpl failureReportHeaderImpl) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ContentTypeHeaderImpl header) {
        String data = header.getRawHeader().getContent();
        String[] set = data.split("/");
        String type = set[0];
        List<Parameter> params = new ArrayList<Parameter>();
        String subType;
        if (set[1].contains(";")) {
            set = set[1].split(";");
            subType = set[0];
            for (int i = 1; i < set.length; i++) {
                String[] set2 = set[i].split("=");
                if (set2.length == 1) {
                    params.add(new Parameter(set2[0]));
                } else {
                    params.add(new Parameter(set2[0], set2[1]));
                }
            }
        } else {
            subType = set[1];
        }
        header.setType(type);
        header.setSubType(subType);
        header.setParameters(params);
        if (logger.isTraceEnabled()) {
            logger.trace("Parsed: \"" + data + "\" > \"" + header.encode() + "\"");
        }
    }

    @Override
    public void visit(ByteRangeHeaderImpl header) throws HeaderParseErrorException {
        String value = header.getRawHeader().getContent();
        String[] set = value.split("-");
        if (set.length != 2)
            throw new HeaderParseErrorException("Malformed ByteRangeHeader");
        try {
            header.setStart(Integer.parseInt(set[0]));
        } catch (NumberFormatException e) {
            throw new HeaderParseErrorException("Malformed ByteRangeHeader, start value malformed");
        }
        set = set[1].split("/");
        if (set.length != 2)
            throw new HeaderParseErrorException("Malformed ByteRangeHeader");
        try {
            if (set[0].equals("*")) {
                header.setEnd(-1);
            } else {
                header.setEnd(Integer.parseInt(set[0]));
            }
        } catch (NumberFormatException e) {
            // logger.error(e);
            throw new HeaderParseErrorException("Malformed ByteRangeHeader. Expected <integer> or * got: " + set[0]);
        }
        try {
            if (set[1].equals("*")) {
                header.setTotal(-1);
            } else {
                header.setTotal(Integer.parseInt(set[1]));
            }
        } catch (NumberFormatException e) {
            throw new HeaderParseErrorException("Malformed ByteRangeHeader, total value malformed");
        }
        if (logger.isTraceEnabled()) {
            logger.trace("Parsed: \"" + value + "\" > \"" + header.encode() + "\"");
        }
    }

    @Override
    public void visit(AuthorizationHeaderImpl authorizationHeaderImpl) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(AuthenticationInfoHeaderImpl authenticationInfoHeaderImpl) {
        // TODO Auto-generated method stub

    }

}
