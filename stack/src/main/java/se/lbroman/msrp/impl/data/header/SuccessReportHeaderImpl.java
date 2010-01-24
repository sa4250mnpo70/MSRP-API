package se.lbroman.msrp.impl.data.header;

import se.lbroman.msrp.data.header.SuccessReportHeader;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;
import se.lbroman.msrp.impl.parser.HeaderVisitor;


/**
 * 
 * @author Leonard Broman
 * 
 */
public class SuccessReportHeaderImpl extends MsrpHeaderImpl implements
		SuccessReportHeader {
	/** Attributes */

	public SuccessReportHeaderImpl() {
		super();
	}

	// private String value;
	public SuccessReportHeaderImpl(String value) {
		this.value = value;
	}

	public SuccessReportHeaderImpl(SuccessReportHeaderImpl orig) {
		this.value = orig.value;
	}

	@Override
	public SuccessReportHeaderImpl clone() {
		return new SuccessReportHeaderImpl(this);
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
    public void accept(HeaderVisitor v) throws HeaderParseErrorException {
        v.visit(this);
    }
}
