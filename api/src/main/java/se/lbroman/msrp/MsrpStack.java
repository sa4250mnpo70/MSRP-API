package se.lbroman.msrp;

import java.util.Properties;

import se.lbroman.msrp.data.header.FromPathHeader;


/**
 * This interface is to be redone. Server listening is to be removed. A
 * particular interface may be added for proxy support, but since the proxy
 * parts are not implemented it is now on ice.
 * 
 * The stack, by configuration, knows of its proxies, routes and listening
 * interfaces and ports. This information is passed to the layer above when
 * requests are made.
 * 
 * Connections can be inititated in two ways
 * 
 * 
 * <ul>
 * By sending
 * <li>The client sends a packet. The FromPath header is set by the stack.
 * <li>The stack choses the transmission method.
 * <ul>
 * <li>A present connection is bound to the destination host:port.
 * <li>A new connection is made, possibly via a proxy.
 * </ul>
 * <li>The stack returns the MsrpURI to identify this listening point.
 * </ul>
 * 
 * Please ignore the above, it's a stupid concept.
 * 
 * The stack has the levels of transmission control. Connections, Transmissions
 * and Transactions. A connection must be established either by generating an
 * offer or from an offer.
 * 
 * The Stack generates Connections, which generates Transmissions, which
 * generates Transactions.
 * 
 * 
 * 
 * @author Leonard Broman
 * 
 */
public interface MsrpStack {

	/** Configuration constants */
	public final static String PROP_STACK_URI = "javax.msrp.STACK_URI";

	/**
	 * Port to which the stack binds for incoming connections. This is also the
	 * port anounces in the path for outgoing connections, even if it does not
	 * have any meaning for that case.
	 */
	public final static String PROP_STACK_PORT = "javax.msrp.STACK_PORT";

	/**
	 * The interval in milliseconds for performing a heart beat transmission
	 * over sockets.
	 */
	public final static String PROP_CHANNEL_HEARTBEAT = "javax.msrp.CHANNEL_HEARTBEAT";

	/**
	 * The timeout when channels are establishing new connections. Lost
	 * connections are detected after about heartbeat + timeout + 5 seconds.
	 */
	public final static String PROP_CHANNEL_TIMEOUT = "javax.msrp.CHANNEL_TIMEOUT";

	/**
	 * @deprecated this will be renamed later when it's proper meaning has been determined.
	 */
	@Deprecated
	public final static String PROP_STACK_MAX_PACKETS_PER_FLUSH = "javax.msrp.maxPacketsParsedPerFlush";

	/**
	 * @deprecated this will be renamed later when it's proper meaning has been determined.
	 */
	@Deprecated
	public final static String PROP_STACK_MAX_RESPONSE_QUEUE_LENGTH = "javax.msrp.maxPacketsOnResponseQueue";

	// public final static int CONNECTION_DIRECTION_INCOMING = 1;
	// public final static int CONNECTION_DIRECTION_OUTGOING = 2;

	/** Temporary default values */
	public final static int SENDBUFFERSIZE = 2048 * 2 * 2 * 2 * 2 * 2 * 2; // 128k

	public final static int RECVBUFFERSIZE = 2048 * 2 * 2 * 2 * 2; // 32k

	/**
	 * Name of the execution thread in the stack, this can be set to simplify debugging.
	 */
	public static final String PROP_STACK_NAME = "javax.msrp.STACK_NAME";

	public void start();

	public void stop();

	public void setProperties(Properties conf);

	public Properties getProperties();

	public HeaderFactory getHeaderFactory();

	// @Deprecated
	// public PacketFactory getPacketFactory();

	/**
	 * This is the From-Path for the stack. Only one uri, the one representing
	 * the listening point. No resource is set. This is usually either the local
	 * IP or a FQDN of the local IP. It has also the protocol (msrp/msrps) and
	 * port information.
	 * 
	 * @return The uri representing the local listening point.
	 */
	public FromPathHeader getFromPath();

	/**
	 * Connection creation generation 2. One method, use variable to determine
	 * direction.
	 * 
	 * @return a new connection
	 */
	public Connection createConnection(Connection.DIRECTION direction);

	/**
	 * Sets the listener
	 * 
	 * @param listener
	 */
	public void setListener(MsrpStackListener listener);

}