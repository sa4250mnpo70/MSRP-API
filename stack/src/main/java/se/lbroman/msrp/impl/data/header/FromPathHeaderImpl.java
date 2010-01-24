package se.lbroman.msrp.impl.data.header;

import java.util.LinkedList;

import se.lbroman.msrp.data.MsrpURI;
import se.lbroman.msrp.data.header.FromPathHeader;
import se.lbroman.msrp.impl.data.MsrpURIImpl;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;
import se.lbroman.msrp.impl.parser.HeaderVisitor;



/**
 * From-Path = "From-Path:" SP MSRP-URI *( SP MSRP-URI )
 * 
 * @author Leonard Broman
 * 
 */
public class FromPathHeaderImpl extends PathHeaderImpl implements
		FromPathHeader {

	public FromPathHeaderImpl() {
		super();
	}

	public FromPathHeaderImpl(LinkedList<MsrpURIImpl> list) {
		super(list);
	}

	public FromPathHeaderImpl(MsrpURIImpl uri) {
		super(uri);
	}

	public FromPathHeaderImpl(FromPathHeaderImpl orig) {
		super(orig);
	}

	// @Override
	// public String encode() {
	// return key + getValue();
	// }

	@Override
	public FromPathHeaderImpl clone() {
		return new FromPathHeaderImpl(this);
	}

	// @Override
	// public void parse(String data) throws HeaderParseErrorException {
	// String[] set = data.split(key);
	// if (set.length != 2) {
	// throw new HeaderParseErrorException("Malformed From-Path header");
	// }
	// URIList = parseUriList(set[1]);
	// }

	/**
	 * 
	 * @return the URI of the originating host
	 */
	public MsrpURIImpl getOrigin() {
		return URIList.getLast();
	}

	public MsrpURIImpl getLastHop() {
		return URIList.getFirst();
	}

	public void addHop(MsrpURIImpl uri) {
		URIList.addFirst(uri);
	}

	public void addHop(MsrpURI uri) {
		addHop((MsrpURIImpl) uri);
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
