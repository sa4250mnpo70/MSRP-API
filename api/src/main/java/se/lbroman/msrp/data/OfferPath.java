/**
 * 
 */
package se.lbroman.msrp.data;

import java.util.List;

/**
 * To reduce confusion with To/Use/From-paths this is a representation of the
 * path list offered in an SDP offer/answer
 * 
 * @author Leonard Broman
 * 
 */
public interface OfferPath extends se.lbroman.msrp.Encodable<String> {

	public List<? extends MsrpURI> getURIList();

}
