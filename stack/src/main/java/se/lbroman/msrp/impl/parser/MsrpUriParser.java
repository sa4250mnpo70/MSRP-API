package se.lbroman.msrp.impl.parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.lbroman.msrp.impl.data.MsrpURIImpl;
import se.lbroman.msrp.impl.data.Parameter;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public class MsrpUriParser implements UriParser {

    private static final Logger logger = LoggerFactory.getLogger(MsrpUriParser.class);
    
    @Override
    public MsrpURIImpl createMsrpUri(String data) throws ParseErrorException {
        if (logger.isTraceEnabled()) {
            logger.trace("Parsing uri: " + data);
        }
        String[] set = data.split("://");
        if (set.length != 2) {
            throw new ParseErrorException("Incorrect uri <scheme>://<uri> : "
                    + data);
        }
        String scheme = set[0];
        set = set[1].split(";");
        // Parse everything before the parameters
        String[] set2;
        set2 = set[0].split("@");
        String userinfo = null;
        if (set2.length > 1) {
            userinfo = set2[0];
            set2 = set2[1].split(":");
        } else {
            set2 = set2[0].split(":");
        }
        String host = "ParseErrorHost.NoWhere";
        int port = 2855;
        String resource = null;
        if (set2.length == 2) {
            // host port/session
            host = set2[0];
            set2 = set2[1].split("/");
            port = Integer.parseInt(set2[0]);
            if (set2.length == 2) {
                // port session
                resource = set2[1];
            }
        } else if (set2.length == 1) {
            // host/session
            set2 = set2[0].split("/");
            host = set2[0];
            if (set2.length == 2) {
                resource = set2[1];
            }
        }
        if (set.length < 2) {
            throw new ParseErrorException("Missing transport");
        }
        // Parse transport and parameters
        String transport = set[1];
        List<Parameter> parameters = new LinkedList<Parameter>();
        for (int i = 2; i < set.length; i++) {
            parameters.add(new Parameter(set[i]));
        }
        // return new MsrpURI(scheme, userinfo, host, port, sessionID,
        // transport, p);
        MsrpURIImpl result = new MsrpURIImpl(scheme, userinfo, host, port, resource, transport, parameters);
        return result;
    }
    
    @Override
    public List<MsrpURIImpl> createMsrpUriList(String data) throws ParseErrorException {
        LinkedList<MsrpURIImpl> URIList = new LinkedList<MsrpURIImpl>();
        String[] set = data.split(" ");
        Vector<String> v = new Vector<String>(Arrays.asList(set));
        for (String s : v) {
            try {
                URIList.add(createMsrpUri(s));
            } catch (ParseErrorException e) {
                logger.error("Parser error",e);
                throw new ParseErrorException(e.getMessage());
            }
        }
        return URIList;
    }

}
