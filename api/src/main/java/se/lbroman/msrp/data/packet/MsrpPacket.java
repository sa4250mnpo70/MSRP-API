/**
 * 
 */
package se.lbroman.msrp.data.packet;

import se.lbroman.msrp.data.header.FromPathHeader;
import se.lbroman.msrp.data.header.MsrpHeader;
import se.lbroman.msrp.data.header.ToPathHeader;


/**
 * Top level MSRP packet. It contains all information that all MSRP packets
 * contains.
 * 
 * @author Leonard Broman
 * 
 */
public interface MsrpPacket {

    @Deprecated
	public enum PACKET_TYPE {
		SEND("SEND"), VISIT("VISIT"), REPORT("REPORT"), AUTH("AUTH"), R200(
				"200", "OK"), R400("400", "Bad Request"), R401("401",
				"Unauthorized"), R501("501", "Not implemented"), R404("404",
				"Not found");

		private String string;

		private String comment;

		private PACKET_TYPE(String string) {
			this.string = string;
		}

		private PACKET_TYPE(String string, String comment) {
			this.string = string;
			this.comment = comment;
		}

		public String getComment() {
			return comment;
		}

		@Override
		public String toString() {
			if (comment == null) {
				return string;
			} else {
				return string + " " + comment;
			}
		}

		@Deprecated
		public static PACKET_TYPE byString(String s) {
			for (PACKET_TYPE t : PACKET_TYPE.values()) {
				if (s.equals(t.string)) {
					return t;
				}
			}
			return null;
		}

	}

	/**
	 * Get the header with this key
	 * 
	 * @param header
	 *            the key
	 * @return the header
	 */
	public <T extends MsrpHeader> T getHeader(Class<T> headerType);

	/**
	 * Get the From-Path
	 * 
	 * @return the From-Path header
	 */
	public FromPathHeader getFrom();

	/**
	 * Get the To-Path
	 * 
	 * @return the To-Path header
	 */
	public ToPathHeader getTo();

	/**
	 * Get the transaction id.
	 * 
	 * @return the transaction id
	 */
	public String getId();

	/**
	 * 
	 * @return a copy
	 * @throws UnsupportedOperationException
	 */
	public MsrpPacket clone() throws UnsupportedOperationException;

}
