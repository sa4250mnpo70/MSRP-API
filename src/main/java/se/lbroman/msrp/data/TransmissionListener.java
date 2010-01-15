/**
 * 
 */
package se.lbroman.msrp.data;

/**
 * @author Leonard Broman
 * 
 */
public interface TransmissionListener {

	/**
	 * When all sent packages for a transmission has been OK:ed. This method is
	 * called.
	 * 
	 * @param t
	 *            the transmission
	 */
	public void transmissionComplete(Transmission t);

	/**
	 * If the transmission times out, this is called.
	 * 
	 * @param t
	 *            the transmission
	 */
	public void transmissionTimeOut(Transmission t);

	/**
	 * If the transmission failes for some reason.
	 * 
	 * @param t
	 *            the transmission
	 */
	public void transmissionFailed(Transmission t);

}
