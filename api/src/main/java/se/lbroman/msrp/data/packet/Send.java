/**
 * 
 */
package se.lbroman.msrp.data.packet;

import java.io.UnsupportedEncodingException;

import se.lbroman.msrp.data.header.ByteRangeHeader;


/**
 * @author Leonard Broman
 * 
 */
public interface Send extends Request {

	public static final PACKET_TYPE type = PACKET_TYPE.SEND;

	/**
	 * Get the raw packet content
	 * 
	 * @return the content as-is
	 */
	public byte[] getContent();

	/**
	 * Get the content as string.
	 * 
	 * @param encoding
	 *            the encoding to use
	 * @return the content
	 * @throws UnsupportedEncodingException
	 *             if the encoding is not supported
	 */
	public String getContentString(String encoding)
			throws UnsupportedEncodingException;

	/**
	 * Returns the encoded content type.
	 * 
	 * @return The content type encoded as a string
	 * @see javax.msrp.data.header.ContentTypeHeader
	 */
	public String getContentType();

	/**
	 * 
	 * @return the Byte-Range header for this send packet
	 */
	public ByteRangeHeader getByteRange();

}
