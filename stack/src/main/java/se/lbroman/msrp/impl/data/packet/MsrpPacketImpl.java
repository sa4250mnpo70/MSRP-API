package se.lbroman.msrp.impl.data.packet;

import java.util.Random;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.Encodable;
import se.lbroman.msrp.data.header.MsrpHeader.HEADER_TYPE;
import se.lbroman.msrp.data.packet.MsrpPacket;
import se.lbroman.msrp.impl.MsrpParser;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;

/**
 * 
 * @author Leonard Broman
 * 
 */
public abstract class MsrpPacketImpl implements MsrpPacket,
		Encodable<byte[]> {

	private static Log logger = LogFactory.getLog(MsrpPacketImpl.class);

	public static Random r;
	static {
		r = new Random(System.currentTimeMillis());
	}

	{
		generateId();
	}

	/** Attributes */
	protected FromPathHeaderImpl from;
	protected ToPathHeaderImpl to;
	protected String id;
	protected final Vector<MsrpHeaderImpl> extraHeaders = new Vector<MsrpHeaderImpl>();

	protected MsrpPacketImpl() {

	}

	protected MsrpPacketImpl(AuthImpl orig) throws UnsupportedOperationException {
		setHeader(orig.from.clone());
		setHeader(orig.to.clone());
		this.id = orig.id;
		for (MsrpHeaderImpl h : orig.extraHeaders) {
			extraHeaders.add(h.clone());
		}
	}

	/**
	 * Checks if the header is a ToPathHeader or a FromPathHeader. If so they
	 * are added to their respective place, otherwise they are added the list of
	 * undefined headers.
	 * 
	 * @param h
	 *            The header to be added
	 */
	public void setHeader(MsrpHeaderImpl h) {
	/*	switch (h.getType()) {
		case FromPath:
			from = (FromPathHeaderImpl) h;
			break;
		case ToPath:
			to = (ToPathHeaderImpl) h;
			break;
		default:
			extraHeaders.add(h);
			if (logger.isTraceEnabled()) {
				logger.trace(h.getKey() + "header added as extraheader");
			}
			return;
		}
		if (logger.isTraceEnabled()) {
			logger.trace(h.getKey() + "header added");
		} */
	}

	@Override
	public MsrpPacketImpl clone() throws UnsupportedOperationException {
	    throw new UnsupportedOperationException();
	}

	public FromPathHeaderImpl getFrom() {
		return from;
	}

	public ToPathHeaderImpl getTo() {
		return to;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	protected void generateId() {
		setId(Long.toString(Math.abs(r.nextLong()), 36));
	}

	/**
	 * Operation
	 * 
	 * @return the encoded packet
	 */
	public abstract byte[] encode();

	/**
	 * This is for debugging purposes.
	 * 
	 * @return the packet encoded as a String
	 * @see Object#toString()
	 */
	@Override
	public final String toString() {
		return new String(encode());
	}

	protected byte[] encodeExtraHeaders() {
		String packet = new String();
		for (MsrpHeaderImpl h : extraHeaders) {
			packet += h.encode() + CRLF;
		}
		return packet.getBytes();
	}

	protected byte[] encodeFromTo() {
		return (from.encode() + CRLF + to.encode() + CRLF).getBytes();
	}

	protected byte[] encodeEndLine() {
		return ("-------" + id + "$" + CRLF).getBytes();
	}

	/**
	 * Gets a header identified by it's key.
	 * 
	 * @param header
	 *            the header key.
	 * @return a header if set, otherwise null.
	 */
	public MsrpHeaderImpl getHeader(HEADER_TYPE header) {
		if (header == HEADER_TYPE.FromPath) {
			return from;
		} else if (header == HEADER_TYPE.ToPath) {
			return to;
		} else {
			for (MsrpHeaderImpl h : extraHeaders) {
	/*			if (header == h.getType()) {
					return h;
				} */
			}
		}
		return null;
	}

	public abstract PACKET_TYPE getType();

	/**
	 * Parses a RawMsrpPacket. Subclasses must override this to set specific
	 * headers and information.
	 * 
	 * The Type, Id and Message are already set in the instance type.
	 * 
	 * TODO: Add response message interpretation.
	 * 
	 * @throws NotImplementedException
	 */
	public void parse(RawMsrpPacket rp) {
		logger.debug("Parsing " + rp.getType() + " packet");
		for (RawMsrpHeader<?> h : rp.getHeaders()) {
			try {
				MsrpHeaderImpl mh = MsrpParser.headerTypes.get(h.getKey())
						.newInstance();
				//mh.parse(h.getKey() + h.getContent());
				setHeader(mh);
			}  catch (InstantiationException e) {
				e.printStackTrace();
				logger.error("The header " + h.getKey()
						+ " has no nullary constructor defined!");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		id = rp.getTransactionID();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((extraHeaders == null) ? 0 : extraHeaders.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MsrpPacketImpl other = (MsrpPacketImpl) obj;
		if (extraHeaders == null) {
			if (other.extraHeaders != null)
				return false;
		} else if (!extraHeaders.equals(other.extraHeaders))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	public boolean isProper() {
		if (from != null)
			if (to != null)
				if (id != null)
					return true;
		return false;
	}

}
