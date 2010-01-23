/**
 * 
 */
package se.lbroman.msrp;

/**
 * Listener which listens to asynch events in the MSRP stack.
 * 
 * @author Leonard Broman
 * 
 */
public interface MsrpStackListener {

	public void msrpStackStarted(MsrpStack stack);

	public void msrpStackStartedFailed(MsrpStack stack);

	/**
	 * Called if a fatal or near-fatal exception arises during operation.
	 * 
	 * Two examples of this is when the dynamic socket listener fails to bind
	 * and when external application calls causes abnormal internal operation.
	 * 
	 * @param stack
	 *            the stack
	 * @param e
	 *            The fatal exception which caused the issue
	 */
	public void msrpStackInternalFailure(MsrpStack stack, Throwable e);

}
