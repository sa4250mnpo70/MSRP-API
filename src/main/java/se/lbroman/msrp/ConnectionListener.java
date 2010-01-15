/**
 * 
 */
package se.lbroman.msrp;

import se.lbroman.msrp.data.Transmission;


/**
 * @author Leonard Broman
 * 
 */
public interface ConnectionListener {

	/**
	 * Called when the connection transits to STATE_READY
	 */
	public void connectionReady(Connection conn);

	public void connectionConnected(Connection conn);

	public void connectionClosed(Connection conn);

	public void receive(Connection conn, Transmission t);

}
