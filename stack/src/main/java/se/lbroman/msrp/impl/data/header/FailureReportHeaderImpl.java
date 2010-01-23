package se.lbroman.msrp.impl.data.header;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.FailureReportHeader;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;


/**
 * Failure-Report = "Failure-Report:" SP ("yes" / "no" / "partial" )
 * 
 * @author Leonard Broman
 * 
 */

public class FailureReportHeaderImpl extends MsrpHeaderImpl implements
		FailureReportHeader {

	private static Log logger = LogFactory
			.getLog(FailureReportHeaderImpl.class);

	public FailureReportHeaderImpl() {
		super();
	}

	/** Attributes */
	public FailureReportHeaderImpl(String value) {
		this.value = value;
	}

	public FailureReportHeaderImpl(FailureReportHeaderImpl orig) {
		this.value = orig.value;
	}

	// @Override
	// public String encode() {
	// return key + getValue();
	// }

	@Override
	public FailureReportHeaderImpl clone() {
		return new FailureReportHeaderImpl(this);
	}

	@Override
	public void parse(String data) throws HeaderParseErrorException {
		// String value;
		// String[] set = data.split(key);
		// if (set.length != 2) {
		// throw new HeaderParseErrorException("Malformed FailureReportHeader");
		// }
		super.parse(data);
		if (value.equals("yes") || value.equals("no")
				|| value.equals("partial")) {
			// value = set[1];
			logger.trace("Parsed: " + getKey() + getValue());
		} else
			throw new HeaderParseErrorException("Malformed FailureReportHeader");

		// return new FailureReportHeader(value);
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
	public HEADER_TYPE getType() {
		return HEADER_TYPE.FailureReport;
	}
}
