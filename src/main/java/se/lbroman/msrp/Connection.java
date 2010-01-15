package se.lbroman.msrp;

import java.io.File;

import se.lbroman.msrp.data.OfferPath;
import se.lbroman.msrp.data.Transmission;
import se.lbroman.msrp.data.header.FromPathHeader;
import se.lbroman.msrp.data.header.ToPathHeader;

/**
 * A connection. It is the channel between two MSRP peers.
 * 
 * @author Leonard Broman
 * 
 */
public interface Connection {
	
	public enum STATE {
		INITIAL,PROPOSAL,READY,CONNECTED,CLOSING,CLOSED;
	}
	
	public enum DIRECTION {
		INCOMING,OUTGOING;
	}

	/**
	 * The OfferPath is the path which should be offered to the peer.
	 * 
	 * @return the path to offer
	 */
	public OfferPath getOfferPath();

	/**
	 * The FromPath is the last hop of the OfferPath. This is set as the
	 * From-Path in messages sent and is the reception uri unique to this
	 * connection.
	 * 
	 * @return the From-Path
	 */
	public FromPathHeader getFromPath();

	/**
	 * The ToPath is where messages are sent by this connection. The first hop
	 * is the first outgoing proxy (set by the stack). The last hop is the uri
	 * which uniquely defines the session at the peer.
	 * 
	 * @return the To-Path
	 */
	public ToPathHeader getToPath();

	// /**
	// * Deliver a transmission to the connection. The To-Path and From-Path is
	// * set to the ones for this transmission.
	// *
	// * @param t
	// * the transmission to perfom
	// * @throws IllegalStateException
	// * if the connection is not in STATE_READY or STATE_CONNECTED
	// * @Deprecated create transmissions and send them instead.
	// */
	// @Deprecated
	// public Transmission send(Send s) throws IllegalStateException;

	/**
	 * Create a transmission to transmit a text message over this connection.
	 * UTF-8 encoding is used.
	 * 
	 * TODO Encoding?
	 * 
	 * @param text
	 * @return a new Transmission
	 */
	public Transmission createTransmission(String text);

	/**
	 * Create a file transmission.
	 * 
	 * TODO Receiver accept or deny transmission?
	 * 
	 * TODO Sending (large) files to conferences?
	 * 
	 * @param file
	 * @return a new Transmission
	 */
	public Transmission createTransmission(File file);

	// /**
	// * When invited, this puts everything together and launches the stack to
	// * await a connection.
	// *
	// * @deprecated can only go into prepared when we have an offered path
	// */
	// @Deprecated
	// public void prepare();

	/**
	 * When the invited and sending the SIP ACK or when inviting and receiving
	 * the SIP ACK this launches the connection and triggers the stack to start
	 * awaiting packets to the URI of the connection.
	 * 
	 * @param peerPath
	 * 
	 */
	public void prepare(OfferPath peerPath) throws IllegalStateException;

	/**
	 * Get the state of this connection.
	 * 
	 * @return the state of the connection
	 */
	public STATE getState();

	/**
	 * Set a listener for this connection.
	 * 
	 * @param listener
	 *            a listener to listen, or null to unset.
	 */
	public void setListener(ConnectionListener listener);

	/**
	 * 
	 */
	public void close();

	public String getId();

	public void visit();

}
