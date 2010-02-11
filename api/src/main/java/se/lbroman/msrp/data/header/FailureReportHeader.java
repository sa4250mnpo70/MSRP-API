/**
 * 
 */
package se.lbroman.msrp.data.header;


/**
 * @author Leonard Broman
 * 
 */
public interface FailureReportHeader extends MsrpHeader {

	public static final String key = "Failure-Report";
	
	enum Failure {
	    Yes,No,Partial
	}
	
	public Failure getResult();

}
