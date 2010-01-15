/**
 * 
 */
package se.lbroman.msrp.data;

import se.lbroman.msrp.data.header.ContentTypeHeader;
import se.lbroman.msrp.data.packet.MsrpPacket.PACKET_TYPE;


/**
 * An MSRP transmission.
 * 
 * @author Leonard Broman
 * 
 */
public interface Transmission {

	public enum STATE {
		INCOMPLETE, COMPLETE, FAILED;
	}

	public void send();

	public void stop() throws IllegalStateException;

	public void pause() throws IllegalStateException;

	public void resume() throws IllegalStateException;

	public float percentTransmitted();

	public long size();

	/**
	 * 
	 * @return the current state
	 */
	public STATE getState();

	public void setListener(TransmissionListener listener);

	/**
	 * @return the content type in the form of a header
	 */
	public ContentTypeHeader getContentType();

	public String getEncoding();

	public byte[] getContent();

	public String getId();

	public PACKET_TYPE getType();

}
