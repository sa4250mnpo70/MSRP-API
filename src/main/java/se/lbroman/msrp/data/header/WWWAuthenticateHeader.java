/**
 * 
 */
package se.lbroman.msrp.data.header;

/**
 * @author LBM
 * 
 */
public interface WWWAuthenticateHeader extends MsrpHeader {

	public String key =MsrpHeader.HEADER_TYPE.WWWAuthenticate.getKey();

	/**
	 * Sets the realm parameter
	 * @param realm the realm value, or null to unset
	 * @throws IllegalArgumentException if realm is not a proper quoted string
	 */
	public void setRealm(String realm) throws IllegalArgumentException;
	
	/**
	 * Sets the opaque parameter
	 * @param opaque the opaque value, or null to unset
	 * @throws IllegalArgumentException if opaque is not a proper quoted string
	 */
	public void setOpaque(String opaque) throws IllegalArgumentException;
	
	/**
	 * Sets the nonce parameter
	 * @param nonce the nonce value, or null to unset
	 * @throws IllegalArgumentException if nonce is not a proper quoted string
	 */
	public void setNonce(String nonce)  throws IllegalArgumentException;
	
	/**
	 * Sets the algorithm parameter
	 * @param alg the algorithm to set, or null to unset
	 * @throws IllegalArgumentException if algorithm is not a proper token
	 */
	public void setAlgorithm(String alg) throws IllegalArgumentException;
	
	/**
	 * Sets the qop paramater
	 * @param qop the qop options list, or null to unset
	 * @throws IllegalArgumentException if qop is not a properly formatted qop options list
	 */
	public void setQop(String qop) throws IllegalArgumentException;
	
	/**
	 * 
	 * @return the realm value (without quotes)
	 */
	public String getRealm();

	/**
	 * 
	 * @return the opaque value (without quotes)
	 */
	public String getOpaque();

	public boolean getStale();

	public String getNonce();
	
	public String getAlgorithm();

}
