/**
 * 
 */
package se.lbroman.msrp.impl.data.packet;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.ContentTypeHeader;
import se.lbroman.msrp.data.packet.MsrpPacket.PACKET_TYPE;
import se.lbroman.msrp.impl.data.ByteArrays;
import se.lbroman.msrp.impl.data.ByteUtils;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
import se.lbroman.msrp.impl.exception.ParseErrorException;
import se.lbroman.msrp.impl.parser.HeaderParser;
import se.lbroman.msrp.impl.parser.HeaderParserImpl;

/**
 * This packet parses a raw byte array containing one transaction into an MSRP
 * packet with headers and content separated for future treatment.
 * 
 * @author Leonard Broman
 * 
 */
public class RawMsrpPacket {

	private Log logger = LogFactory.getLog(RawMsrpPacket.class);
	private PACKET_TYPE type;
	private String transactionID;
	private byte contFlag;

	private LinkedList<RawMsrpHeader<?>> headers = new LinkedList<RawMsrpHeader<?>>();
	private byte[] content;
	private byte[] packet;
	private int pos = 0;
	ByteUtils byteUtils = new ByteArrays();
	HeaderParser headerParser = new HeaderParserImpl();

	public RawMsrpPacket(byte[] packet) throws ParseErrorException {
		this.packet = packet;
		// First line: MSRP id type [comment]
		try {
			String firstLine = new String(getLine(), "UTF-8");
			// isolate id and type
			String[] set = firstLine.split(" ");
			transactionID = set[1];
			type = PACKET_TYPE.byString(set[2]);
			if (type == null) {
				throw new ParseErrorException("Illegal packet type: " + set[2]);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			e.printStackTrace();
		}
		// We ignore the comment
		if (type == PACKET_TYPE.SEND) {
			// Here be content
			RawMsrpHeader<?> h;
			do {
				try {
					h = headerParser.createRawHeader(new String(getLine()));
				} catch (ParseErrorException e) {
					e.printStackTrace();
					throw new ParseErrorException(e);
				}
				headers.add(h);
				// The last header must be Content-Type:
			} while (!(h.getKey().equals(ContentTypeHeader.key)));
			// We can now calculate where the content is
			// CRLF data CRLF ------- id ? CRLF
			// TODO MIME header treatment
			content = new byte[packet.length - pos - 3
					- transactionID.getBytes().length - 7 - 2 - 2];
			byteUtils
					.copySubRange(packet, pos + 2, content, 0, content.length);
		} else {
			RawMsrpHeader<?> h;
			do {
				h = headerParser.createRawHeader(new String(getLine()));
				headers.add(h);
			} while (packet[pos] != '-');
		}
		contFlag = packet[packet.length - 3];
	}

	/**
	 * @return the type
	 */
	public PACKET_TYPE getType() {
		return type;
	}

	/**
	 * @return the transactionID
	 */
	public String getTransactionID() {
		return transactionID;
	}

	/**
	 * @return the contFlag
	 */
	public byte getContFlag() {
		return contFlag;
	}

	/**
	 * @return the headers
	 */
	public LinkedList<RawMsrpHeader<?>> getHeaders() {
		return headers;
	}

	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	private byte[] getLine() {
		int s = pos;
		while (packet[pos] != '\n') {
			pos++;
		}
		pos++;
		byte[] neu = new byte[pos - s - 2];
		byteUtils.copySubRange(packet, s, neu, 0, neu.length);
		return neu;
	}

	@Override
	public String toString() {
		return new String(packet);
	}

}
