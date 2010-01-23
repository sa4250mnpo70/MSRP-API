/**
 * 
 */
package se.lbroman.msrp;

import se.lbroman.msrp.data.MsrpURI;
import se.lbroman.msrp.data.OfferPath;
import se.lbroman.msrp.exception.ParseErrorException;


/**
 * @author Leonard Broman
 * 
 */
public interface HeaderFactory {

	/**
	 * 
	 * @param uri
	 * @return a new MsrpURI
	 * @throws ParseErrorException
	 */
	public MsrpURI createMsrpURI(String uri) throws ParseErrorException;

	public OfferPath createOfferPath(String path) throws ParseErrorException;

}
