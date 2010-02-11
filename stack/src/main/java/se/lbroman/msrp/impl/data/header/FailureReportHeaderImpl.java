package se.lbroman.msrp.impl.data.header;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.FailureReportHeader;
import se.lbroman.msrp.impl.exception.ParseErrorException;
import se.lbroman.msrp.impl.parser.HeaderVisitor;

/**
 * Failure-Report = "Failure-Report:" SP ("yes" / "no" / "partial" )
 * 
 * @author Leonard Broman
 * 
 */

public class FailureReportHeaderImpl extends MsrpHeaderImpl implements FailureReportHeader {

    private static Log logger = LogFactory.getLog(FailureReportHeaderImpl.class);

    private Failure result;

    public FailureReportHeaderImpl() {
        super();
    }

    public FailureReportHeaderImpl(FailureReportHeaderImpl orig) {
        this.result = orig.result;
    }

    // @Override
    // public String encode() {
    // return key + getValue();
    // }

    @Override
    public FailureReportHeaderImpl clone() {
        if (logger.isDebugEnabled()) {
            logger.debug("Cloning failure report header");
        }
        return new FailureReportHeaderImpl(this);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        switch (result) {
        case Yes:
            return "yes";
        case No:
            return "no";
        case Partial:
            return "partial";
        default:
            return "";
        }
    }

    @Override
    public void accept(HeaderVisitor v) throws ParseErrorException {
        v.visit(this);
    }

    public void setResult(Failure yes) {
        this.result = yes;
    }

    @Override
    public Failure getResult() {
        return result;
    }
}
