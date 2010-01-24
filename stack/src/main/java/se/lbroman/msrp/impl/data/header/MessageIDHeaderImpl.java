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
	/** Attributes */
	// private String value;
	public MessageIDHeaderImpl() {
		super();
	}


	public MessageIDHeaderImpl(String ident) {
		this.value = ident;
	}

	/**
	 * Copy constructor
	 */
	public MessageIDHeaderImpl(MessageIDHeaderImpl orig) {
		this.value = orig.value;
	}

	@Override
	public MessageIDHeaderImpl clone() {
		return new MessageIDHeaderImpl(this);
	}


	@Override
	public String getValue() {
		return value;
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
