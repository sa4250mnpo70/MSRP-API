package se.lbroman.msrp.impl.data.packet;

import java.io.UnsupportedEncodingException;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.MsrpHeader.HEADER_TYPE;
import se.lbroman.msrp.data.packet.Send;
import se.lbroman.msrp.impl.MsrpParser;
import se.lbroman.msrp.impl.data.ByteArrayBuilder;
import se.lbroman.msrp.impl.data.header.ByteRangeHeaderImpl;
import se.lbroman.msrp.impl.data.header.ContentTypeHeaderImpl;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;


/**
 * A send packet.
 * 
 * @author Leonard Broman
 * 
 */
public class SendImpl extends RequestImpl implements Send {
	/* Attributes */
	// public static final int maxSize = 2048;
	private static Log logger = LogFactory.getLog(SendImpl.class);

	/**
	 * Package content. This is an array, this have first index -1 !!! (???)
	 */
	private byte[] content;

	private ContentTypeHeaderImpl contentType;

	private ByteRangeHeaderImpl byteRange;

	private boolean lastPacket;

	/**
	 * 
	 * @param fromH
	 *            the from header
	 * @param toH
	 *            the to header
	 * @param contentTypeH
	 *            the content type
	 * @param start
	 *            the first byte (where 1 is the very first)
	 * @param end
	 *            the last byte (not the one after the last)
	 * @param total
	 *            total number of bytes for this transmission
	 * @param content
	 *            the content
	 */
	public SendImpl(FromPathHeaderImpl fromH, ToPathHeaderImpl toH,
			ContentTypeHeaderImpl contentTypeH, long start, long end,
			long total, byte[] content) {
		int size = (int) (end - start + 1);
		this.content = new byte[size];
		for (int i = 0; i < this.content.length; i++) {
			this.content[i] = content[i];
		}
		setHeader(fromH);
		setHeader(toH);
		setHeader(contentTypeH);
		byteRange = new ByteRangeHeaderImpl(start, end, total);
		if (end == total)
			lastPacket = true;
	}

	/**
	 * Standard constructor. This sets the variables by reference! Use for small
	 * sends.
	 * 
	 * @param from
	 * @param to
	 * @param type
	 * @param content
	 */
	public SendImpl(FromPathHeaderImpl from, ToPathHeaderImpl to,
			ContentTypeHeaderImpl type, byte[] content) {
		this.from = from;
		this.to = to;
		contentType = type;
		this.content = content;
		byteRange = new ByteRangeHeaderImpl(1, -1, content.length);

	}

	/**
	 * Copy constructor.
	 * 
	 * @param s
	 */
	public SendImpl(SendImpl s) {
		to = s.getTo().clone();
		from = s.getFrom().clone();
		content = new byte[s.content.length];
		System.arraycopy(s.content, 0, content, 0, content.length);
		contentType = s.contentType.clone();
		byteRange = s.byteRange.clone();
		lastPacket = s.lastPacket;
	}

	/**
	 * Nullary constructor to provide type-dynamic instantiation.
	 */
	public SendImpl() {

	}

	private byte[] encodeHeader() {
		ByteArrayBuilder packet = new ByteArrayBuilder();
		packet.append(("MSRP " + id + " SEND" + CRLF).getBytes());
		packet.append(encodeFromTo());
		packet.append((messageID.encode() + CRLF).getBytes());
		packet.append(encodeExtraHeaders());
		return packet.getBytes();
	}

	/**
	 * 
	 */
	@Override
	protected byte[] encodeEndLine() {
		if (lastPacket)
			return ("-------" + id + "$" + CRLF).getBytes();
		return ("-------" + id + "+" + CRLF).getBytes();
	}

	/**
	 * Simply encodes the content part of the message. Theoretically this may
	 * contain the "Content stuff" such as MIME headers n' shiit. But we don't
	 * care about that for now.
	 */
	@Override
	public byte[] encode() {
		ByteArrayBuilder packet = new ByteArrayBuilder();
		packet.append(encodeHeader());
		packet.append((byteRange.encode() + CRLF).getBytes());
		packet.append((contentType.encode() + CRLF + CRLF).getBytes());
		packet.append(content);
		packet.append(CRLF.getBytes());
		packet.append(encodeEndLine());
		// byteRange.setStart(end + 1);
		return packet.getBytes();
	}

	/**
	 * Sets a ContentTypeHeader or a ByteRangeHeader if suitable. If not this
	 * class or any superclass can set the header, it is added as an
	 * extraheader.
	 * 
	 * @param h
	 *            the header to set
	 */
	@Override
	public void setHeader(MsrpHeaderImpl h) {
		if (h instanceof ContentTypeHeaderImpl) {
			contentType = (ContentTypeHeaderImpl) h;
			logger.trace(h.getKey() + "header set");
		} else if (h instanceof ByteRangeHeaderImpl) {
			byteRange = (ByteRangeHeaderImpl) h;
			logger.trace(h.getKey() + "header set");
		} else {
			super.setHeader(h);
		}
	}

	/**
	 * 
	 * @return the ByteRangeHeader
	 * @see Send#getByteRange()
	 */
	public ByteRangeHeaderImpl getByteRange() {
		return byteRange;

	}

	@Override
	public SendImpl clone() {
		return new SendImpl(this);
	}

	/**
	 * @see Send#getContentString(String)
	 */
	public String getContentString(String encoding)
			throws UnsupportedEncodingException {
		return new String(content, encoding);
	}

	/**
	 * Use with care! May ruin the content of the package! Use setContent to
	 * change content!
	 * 
	 * @return the content
	 * @see Send#getContent()
	 */
	public byte[] getContent() {
		return content;
	}

	public boolean isLastPacket() {
		return lastPacket;
	}

	/**
	 * Returns true if this packet contains all the required fields of an MSRP
	 * packet. Just a sanity check.
	 * 
	 * @return true if the packet is proper
	 */
	@Override
	public boolean isProper() {
		if (getTo() == null)
			return false;
		if (getFrom() == null)
			return false;
		if (contentType == null)
			return false;
		if (byteRange == null)
			return false;
		return true;
	}

	/**
	 * Returns the content type string. NOT THE HEADER
	 */
	public String getContentType() {
		return contentType.getValue();
	}

	@Override
	public MsrpHeaderImpl getHeader(HEADER_TYPE header) {
		if (header == HEADER_TYPE.ContentType)
			return contentType;
		else if (header == HEADER_TYPE.ByteRange)
			return byteRange;
		else
			return super.getHeader(header);
	}

	@Override
	public PACKET_TYPE getType() {
		return PACKET_TYPE.SEND;
	}

	@Override
	public void parse(RawMsrpPacket rp) {
		for (RawMsrpHeader<?> h : rp.getHeaders()) {
			try {
				// If you get a java.lang.InstantiationException
				// it is because the header doesnt have a nullary constructor
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
		content = rp.getContent();
		id = rp.getTransactionID();
		if (rp.getContFlag() == '$') {
			lastPacket = true;
		}
	}

}
