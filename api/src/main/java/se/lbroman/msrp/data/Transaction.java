/**
 * 
 */
package se.lbroman.msrp.data;

import se.lbroman.msrp.data.packet.MsrpPacket;


/**
 * @author Leonard Broman
 * 
 */
public interface Transaction {

	public boolean isSent();

	public boolean isOk();

	public void setListener(TransactionListener listener);

	public String getId();

	public MsrpPacket getPacketToSend();

}
