package se.lbroman.msrp.data.header;


public interface SuccessReportHeader extends MsrpHeader {

	final String key = "Success-Report";
	
	enum Success {
	    Yes,No;
	}
	
	Success getResult();

}
