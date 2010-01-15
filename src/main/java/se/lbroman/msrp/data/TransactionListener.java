/**
 * 
 */
package se.lbroman.msrp.data;

/**
 * @author Leonard Broman
 * 
 */
public interface TransactionListener {

	public void transactionTimeout(Transaction transaction);

	public void transactionReplied(Transaction transaction);

}
