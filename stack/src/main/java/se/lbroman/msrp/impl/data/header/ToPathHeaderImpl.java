package se.lbroman.msrp.impl.data.header;

import java.util.List;

import se.lbroman.msrp.data.header.ToPathHeader;
import se.lbroman.msrp.impl.data.MsrpURIImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;
import se.lbroman.msrp.impl.parser.HeaderVisitor;


/**
 * 
 * @author Leonard Broman
 * @see FromPathHeaderImpl
 */

public class ToPathHeaderImpl extends PathHeaderImpl implements ToPathHeader {

	public ToPathHeaderImpl() {
		super();
	}

	public ToPathHeaderImpl(List<MsrpURIImpl> list) {
		super(list);
	}

	public ToPathHeaderImpl(MsrpURIImpl uri) {
		super(uri);
	}

	public ToPathHeaderImpl(ToPathHeaderImpl orig) {
		super(orig);
	}

	@Override
	public ToPathHeaderImpl clone() {
		return new ToPathHeaderImpl(this);
	}

	public MsrpURIImpl getDestination() {
		return URIList.getLast();
	}

	public MsrpURIImpl getNextHop() {
		return URIList.getFirst();
	}

	public MsrpURIImpl removeHop() {
		return URIList.removeFirst();
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
    public void accept(HeaderVisitor v) throws ParseErrorException   {
        v.visit(this);
    }
	
}
