package se.lbroman.msrp.impl.data.header;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.StatusHeader;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;
import se.lbroman.msrp.impl.parser.HeaderVisitor;


/**
 * 
 * <pre>
 * Status = &quot;Status:&quot; SP namespace SP status-code [SP comment] 
 * namespace = 3(DIGIT); &quot;000&quot; for all codes defined in this document.
 * status-code = 3DIGIT
 * comment = utf8text
 * </pre>
 * 
 * @author Leonard Broman
 * 
 */
public class StatusHeaderImpl extends MsrpHeaderImpl implements StatusHeader {

	private static final Log logger = LogFactory.getLog(StatusHeaderImpl.class);

	private int namespace = 000;

	private int code;

	private String comment = "";

	public StatusHeaderImpl() {

	}

	public StatusHeaderImpl(int code) {
		this.code = code;
	}

	public StatusHeaderImpl(int code, String comment) {
		this.code = code;
		this.comment = comment;
	}

	public StatusHeaderImpl(StatusHeaderImpl orig) {
		this.namespace = orig.namespace;
		this.code = orig.code;
		this.comment = orig.comment;
		if (orig.rawHeader != null) {
		    this.rawHeader = orig.rawHeader;
		}
	}

	@Override
	public StatusHeaderImpl clone() {
	    if (logger.isTraceEnabled()) {
	        logger.trace("Cloning StatusHeader");
	    }
		return new StatusHeaderImpl(this);
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		String nspace = Integer.toString(namespace);
		while (nspace.length() < 3)
			nspace = "0" + nspace;
		String ncode = Integer.toString(code);
		while (ncode.length() < 3)
			ncode = "0" + ncode;
		String scode = nspace + " " + ncode;
		if (comment != null && !comment.equals("")) {
			scode += " " + comment;
		}
		return scode;
	}

	public int getCode() {
		return code;
	}

	public int getNameSpace() {
		return namespace;
	}

	public String getComment() {
		return comment;
	}
	@Override
    public void accept(HeaderVisitor v) throws HeaderParseErrorException {
        v.visit(this);
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public void setComment(String comment2) {
        this.comment = comment2;
    }

}
