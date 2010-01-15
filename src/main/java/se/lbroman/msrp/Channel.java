/**
 * 
 */
package se.lbroman.msrp;

import se.lbroman.msrp.data.packet.MsrpPacket;

/**
 * 
 * @author Leonard Broman
 * 
 */
public interface Channel {

    public enum STATE {
        UNDETERMINED, DETERMINED, ASSOCIATED, CLOSED, STATELESS;
    }

    public enum DIRECTION {
        INBOUND, OUTBOUND;
    }

    public STATE getState();

    public void visit() throws IllegalStateException;

    public void setListener(ChannelListener listener);

    public void send(MsrpPacket packet) throws IllegalStateException;

}
