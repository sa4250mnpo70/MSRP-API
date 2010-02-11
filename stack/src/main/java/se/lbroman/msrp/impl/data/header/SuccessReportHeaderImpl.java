package se.lbroman.msrp.impl.data.header;

import se.lbroman.msrp.data.header.SuccessReportHeader;
import se.lbroman.msrp.impl.exception.ParseErrorException;
import se.lbroman.msrp.impl.parser.HeaderVisitor;


/**
 * 
 * @author Leonard Broman
 * 
 */
public class SuccessReportHeaderImpl extends MsrpHeaderImpl implements
		SuccessReportHeader {
	
    private Success result;

	public SuccessReportHeaderImpl() {
		super();
	}

	public SuccessReportHeaderImpl(SuccessReportHeaderImpl orig) {
		this.result = orig.result;
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
	    switch (result) {
	        case Yes: return "yes";
	        case No: return "no";
	        default: return "";
	    }
	}

	@Override
    public void accept(HeaderVisitor v) throws ParseErrorException {
        v.visit(this);
    }

    @Override
    public Success getResult() {
        return result;
    }
    
    public void setResult(Success result) {
        this.result = result;
    }
}
