/**
 * 
 */
package se.lbroman.msrp.impl.parser;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.lbroman.msrp.impl.data.ByteArrays;
import se.lbroman.msrp.impl.data.ByteUtils;
import se.lbroman.msrp.impl.data.packet.RawMsrpPacket;
import se.lbroman.msrp.impl.exception.NoPacketFoundException;
import se.lbroman.msrp.impl.exception.ParseErrorException;


/**
 * This class provides the first step in parsing incoming datastreams. It splits
 * up the datastream into arrays each containing one msrp transaction. This is
 * the point where the stack extract the data from the socket.
 * 
 * TODO: Move the buffer logic out of here
 * 
 * @author Leonard Broman
 */
public class StreamPacketDelimiter implements PacketDelimiter {

    private static Logger logger = LoggerFactory.getLogger(StreamPacketDelimiter.class);

	private int maxSize = 1024 * 2 * 2;
	byte[] buffer;
	int start;
	int size;
	private InputStream is;
	
	ByteUtils byteUtils = new ByteArrays();

	{
	    init();
	}
	
	StreamPacketDelimiter() {
    }
	
	/**
	 * 
	 * @param is
	 */
	public StreamPacketDelimiter(InputStream is) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("Creating packet delimiter");
	    }
		this.is = is;
	}

	/**
	 * 
	 * @return an isolated msrp packet
	 * @throws NoPacketFoundException
	 *             If there are no packets available
	 * @throws IOException
	 *             If the InputStream is no longer valid.
	 * @throws ParseErrorException
	 *             If there is invalid data on the stream. This means the
	 *             connection will be cut.
	 */
	@Override
	public RawMsrpPacket get() throws NoPacketFoundException, IOException,
			ParseErrorException {
		logger.trace("Trying to get a raw packet from the delimiter");
		try {
			if (!reBuffer()) {
				throw new NoPacketFoundException();
			}
		} catch (IOException e1) {
			throw e1;
		}

		try {
			return new RawMsrpPacket(extractPacket());
		} catch (NoPacketFoundException e) {
			logger.debug("No packet found",e);
			if (size == maxSize) {
			    logger.warn(String.format("Full buffer detected for size %1$s starting at %2$s",size,start));
				throw new ParseErrorException(
						"Buffer blocked by too large packet");
			}
			throw e;
		} catch (ParseErrorException e) {
			logger.debug("Parse error",e);
			throw e;
		}

	}

	/**
	 * Refills the internal buffer with data from the stream. This is the point
	 * in the stack where data is read from the socket.
	 * 
	 * @return True if the internal buffer has data, false otherwise
	 * @throws IOException
	 */
	boolean reBuffer() throws IOException {
		// logger.trace("reBuffer()");
		// logger.trace("Rebuffering, buffer state:\n" + dumpBuffer());
		if (start > 0) {
			// Move everything to start of stream (should we have circular
			// buffer
			// instead?
			for (int i = 0; i < size; i++) {
				buffer[i] = buffer[i + start];
			}
			start = 0;
		}
		if (is.available() > 0) {
			int take;
			if (is.available() > (maxSize - size)) {
				take = maxSize - size;
			} else {
				take = is.available();
			}
			is.read(buffer, size, take);
			// byte[] data = ByteArrays.subRange(buffer, size, take);
			size += take;
			// logger.trace("Fillup complete, buffer state:\n" + dumpBuffer());
		} else {
			if (size == 0)
				return false;
			return true;
		}
		return true;
	}

	/**
	 * Extract ONE packet from the stream, if there is one to get.
	 * 
	 * @throws NoPacketFoundException
	 *             If a packet could not be extracted
	 * @throws ParseErrorException
	 * @throws exception.InvalidDataException
	 *             If the data in the buffer does not contain a proper MSRP
	 *             packet
	 * 
	 * @see http://tools.ietf.org/html/rfc4975#section-9
	 */
	byte[] extractPacket() throws NoPacketFoundException,
			ParseErrorException {
		/*
		 * The start should contain: "MSRP" SP id SP The end should contain: CR
		 * LF "-------" id ("$" / "+" / "#") CR LF
		 */
	    if (size < 4) {
	        throw new NoPacketFoundException();
	    }
		byte[] msrp = "MSRP".getBytes();
		// byte[] fin = "-------".getBytes();
		int pos = start;
		// Check that we are at start of packet
		if (byteUtils.equalsSubRange(buffer, pos, msrp, 0, 4)) {
			pos += 4;
		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("Invalid buffer data, dumping: \n" + dumpBuffer());
			}
			throw new ParseErrorException();
		}
		if (size < 5) {
		    throw new NoPacketFoundException();
		}
		// After MSRP a space
		if (buffer[pos] == ' ') {
			pos++;
		} else
			throw new ParseErrorException();
		// Extract the transaction-id
        // ident = ALPHANUM  3*31ident-char
        // ident-char = ALPHANUM / "." / "-" / "+" / "%" / "="
		if (size < 6) {
		    throw new NoPacketFoundException();
		}
		int recordStart = pos;
		
		for (int count = 0;; pos++,count++) {
			if (buffer[pos] == ' ')
				break;
			if (count == 32) {
			    // Character 33 not a space
			    throw new ParseErrorException("The ID field exceeded 32 characters");
			}
		}
		byte[] id = byteUtils.subRange(buffer, recordStart, pos - recordStart);

		boolean found = false;
		while (!found) {
			if (pos > start + size) {
				throw new NoPacketFoundException();
			}
			// Locate "-" in chunks of 7
			if (buffer[pos] == '-') {
				// Back up to find CR LF
				int s = pos;
				boolean ok = true;
				for (; buffer[s] == '-'; s--)
					;
				// If the end byte would fall outside the buffer from this point
				// we cant get a complete packet anyway
				if (s + 7 + id.length + 1 > start + size)
					throw new NoPacketFoundException();
				// Now we must have the CRLF
				if (buffer[s] == '\n' && buffer[s - 1] == '\r') {
					// This is a good start
					s--; // Move to CR
					int dashes = pos - s - 1;
					// We need 7 dashes
					while (dashes < 7) {
						pos++;
						if (buffer[pos] == '-') {
							dashes++;
						} else {
							ok = false;
							break;
						}
					}
					if (ok) {
						pos++;
						// We're good! Now we should have the id
						if (byteUtils.equalsSubRange(buffer, pos, id, 0,
								id.length)) {
							// We got the ID!
							pos += id.length;
						} else {
							ok = false;
						}
					}
					if (ok) {
						// Now there should be the last sign "$","+","#"
						if (buffer[pos] == '$' || buffer[pos] == '+'
								|| buffer[pos] == '#') {
							// YES! It is a packet!!!
							byte[] packet = byteUtils.subRange(buffer, start,
									pos - start + 3);
							start += packet.length;
							size -= packet.length;
							// Return the extracted packet
							// packets.addLast(packet);
							logger.trace("Extracted packet");
							if (logger.isTraceEnabled()) {
								logger.trace("\n" + new String(packet));
								// logger.trace("Buffer dump: \n" +
								// dumpBuffer());
							}
							return packet;
						} else
							ok = false;
					}
				} else
					// rn-rn-------
					// Not a CRLF, advance to ^ -> ^
					pos = s + 10;
			} else
				pos += 7;
		}
		throw new NoPacketFoundException();
	}

	public String dumpBuffer() {
		return new String("Buffer start: " + start + "\nBuffer size: " + size
				+ "\n" + new String(buffer));
	}

	public boolean hasWork() throws IOException {
		if (size > 0 || is.available() > 0) {
			logger.trace("Work available (size: " + size
					+ " , is.available(): " + is.available() + ")");
			return true;
		} else
			return false;
	}
	
	void setMaxSize(int maxSize) {
	    this.maxSize = maxSize;
	    init();
	}
	
	private void init() {
	    buffer = new byte[maxSize];
	    size = 0;
	    start = 0;
	}

}
