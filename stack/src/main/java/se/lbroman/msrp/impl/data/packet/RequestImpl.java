package se.lbroman.msrp.impl.data.packet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.lbroman.msrp.data.header.MessageIDHeader;
import se.lbroman.msrp.data.header.MsrpHeader;
import se.lbroman.msrp.data.packet.Request;
import se.lbroman.msrp.impl.MsrpParser;
import se.lbroman.msrp.impl.data.ByteArrayBuilder;
import se.lbroman.msrp.impl.data.header.MessageIDHeaderImpl;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;


/**
 * 
 * @author Leonard Broman
 * 
 */
public abstract class RequestImpl extends MsrpPacketImpl implements Request {

	protected MessageIDHeaderImpl messageID;

	private static Logger logger = LoggerFactory.getLogger(RequestImpl.class);

	{
		generateMessageID();
	}

	protected RequestImpl() {

	}

	protected RequestImpl(AuthImpl orig) throws UnsupportedOperationException {
		super(orig);
		if (orig.messageID != null) {
			messageID = orig.messageID.clone();
		}
	}

	protected void generateMessageID() {
		setMessageID(Long.toString(r.nextLong(), 36) + "-"
				+ System.currentTimeMillis());
	}

	/**
	 * Generates an OK-response to this Request.
	 * 
	 * @return a 200 OK
	 * @deprecated use createResponse instead
	 * @see #createResponse(javax.msrp.data.packet.MsrpPacket.PACKET_TYPE)
	 */
	@Deprecated
	public OKImpl createOK() {
		OKImpl o = new OKImpl(to.makeFrom(), from.makeTo(), id);
		return o;
	}

	public String getMessageID() {
		return messageID.getValue();
	}

	/**
	 * Sets the message ID
	 * 
	 * @param id
	 *            the message id to set.
	 */
	public void setMessageID(String id) {
		messageID = new MessageIDHeaderImpl();
		messageID.setIdent(id);
	}

	/**
	 * If the header is a MessageIDHeader, it is set, otherwise
	 * MsrpPacketImpl.setHeader() is called.
	 * 
	 * @param h
	 *            the header to set
	 * @see MsrpPacketImpl#setHeader(MsrpHeaderImpl)
	 */
	@Override
	public void setHeader(MsrpHeaderImpl h) {
		if (h instanceof MessageIDHeaderImpl) {
			messageID = (MessageIDHeaderImpl) h;
			logger.trace(h.getKey() + "header set");
		} else {
			super.setHeader(h);
		}
	}

	@SuppressWarnings("unchecked")
    @Override
	public <T extends MsrpHeader> T getHeader(Class<T> header) {
		if (MessageIDHeader.class.isAssignableFrom(header)) {
			return (T) messageID;
		} else {
			return (T) super.getHeader(header);
		}
	}

	@Override
	public byte[] encode() {
		ByteArrayBuilder packet = new ByteArrayBuilder();
		packet.append(("MSRP " + id + " " + getCode() + CRLF).getBytes());
		packet.append(encodeFromTo());
		packet.append((messageID.encode() + CRLF).getBytes());
		packet.append(encodeEndLine());
		return packet.getBytes();
	}

	protected abstract String getCode();

    /**
	 * Parses a RawMsrpPacket. Subclasses must override this to set specific
	 * headers and information.
	 * 
	 * The Type, Id and Message are already set in the instance type.
	 * 
	 * TODO: Add response message interpretation.
	 */
	@Override
	public void parse(RawMsrpPacket rp) {
		for (RawMsrpHeader<?> h : rp.getHeaders()) {
			try {
				MsrpHeaderImpl mh = MsrpParser.headerTypes.get(h.getKey())
						.newInstance();
				//mh.parse(h.getKey() + h.getContent());
				setHeader(mh);
			} catch (InstantiationException e) {
				e.printStackTrace();
				logger.error("The header " + h.getKey()
						+ " has no nullary constructor defined!");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		id = rp.getTransactionID();
	}

	@Override
	public boolean isProper() {
		return super.isProper();
	}

	public ResponseImpl createResponse(PACKET_TYPE type) {
		switch (type) {
		case R200:
			return new OKImpl(to.makeFrom(), from.makeTo(), id);
		case R400:
			return new BadRequestImpl(to.makeFrom(), from.makeTo(), id);
		default:
			// TODO add more responses!
			logger.error("There will be an error!");
			return null;
		}
	}

}
