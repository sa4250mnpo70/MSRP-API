/**
 * 
 */
package se.lbroman.msrp;

import se.lbroman.msrp.data.packet.MsrpPacket;

/**
 * @author Leonard Broman
 * 
 */
public interface ChannelListener {

	public void receive(MsrpPacket p);

	public void channelClosedInactive(Channel channel);

	public void channelClosedFailure(Channel channel);

	public void channelConnected(Channel channel);

}
