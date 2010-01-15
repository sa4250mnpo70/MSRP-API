/**
 * 
 */
package se.lbroman.msrp.data.header;

import se.lbroman.msrp.data.MsrpURI;

/**
 * The use path is given by a relay to inform a relay using client of the path
 * to WAN. They are in this order,if two relays as follows
 * 
 * <pre>
 * UE --- Relay A --- Relay B --- WAN
 * </pre>
 * 
 * Then Relay A sends
 * 
 * <pre>
 * Use-Path: RelayA:2855/resa RelayB:2855/resb
 * </pre>
 * 
 * This is the beginning of the To-Path of outgoing requests!
 * 
 * The Use-Path does not contain the From-Path local uri!
 * 
 * @author Leonard Broman
 * @see com.atosorigin.msrp.ConnectionImpl
 */
public interface UsePathHeader extends PathHeader {

	public final static String key = MsrpHeader.HEADER_TYPE.UsePath.getKey();

	/**
	 * Get the point where the UE should send its requests.
	 * 
	 * @return The entry point to the relay chain
	 */
	public MsrpURI getEntryPoint();

	// /**
	// * Get the final destination URI. This should be the identifier that the
	// * local stack is using for the tcp connection connected to the relay
	// chain
	// * where this use-path is used.
	// *
	// * @return The destination URI for the transmission
	// * @deprecated since it is not correct
	// */
	// @Deprecated
	// public MsrpURI getDestination();

}
