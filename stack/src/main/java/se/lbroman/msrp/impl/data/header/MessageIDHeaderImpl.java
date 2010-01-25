package se.lbroman.msrp.impl.data.header;

import se.lbroman.msrp.data.header.MessageIDHeader;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;
import se.lbroman.msrp.impl.parser.HeaderVisitor;

/**
 * <pre>
 * Message-ID = &quot;Message-ID:&quot; SP ident
 * ident = ALPHANUM  3*31ident-char
 * ident-char = ALPHANUM / &quot;.&quot; / &quot;-&quot; / &quot;+&quot; / &quot;%&quot; / &quot;=&quot;
 * </pre>
 * 
 * @author Leonard Broman
 * 
 */
public class MessageIDHeaderImpl extends MsrpHeaderImpl implements
		MessageIDHeader {
	
    private String ident = "";
    
    public MessageIDHeaderImpl() {
		super();
	}

	/**
	 * Copy constructor
	 */
	public MessageIDHeaderImpl(MessageIDHeaderImpl orig) {
	    if (rawHeader != null) {
	        this.rawHeader = orig.rawHeader.clone();
	    }
	    ident = orig.ident;
	}

	@Override
	public MessageIDHeaderImpl clone() {
		return new MessageIDHeaderImpl(this);
	}


	@Override
	public String getValue() {
		return ident;
	}
	
	public void setIdent(String ident) {
	    this.ident = ident;
	}

	@Override
	public String getKey() {
		return key;
	}
	@Override
    public void accept(HeaderVisitor v) throws HeaderParseErrorException {
        v.visit(this);
    }

}
