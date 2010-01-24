/**
 * 
 */
package se.lbroman.msrp.data.header;

import se.lbroman.msrp.data.MsrpURI;


/**
 * @author Leonard Broman
 * 
 */
public interface ToPathHeader extends PathHeader {

	public final static String key = "To-Path";

	public MsrpURI getDestination();

	public MsrpURI getNextHop();

	public MsrpURI removeHop();

}
