/**
 * 
 */
package se.lbroman.msrp.data.header;

import se.lbroman.msrp.Encodable;

/**
 * The header format is simply: KEY VALUE
 * 
 * where the key is given with a trailing COLON SPACE , the value is the rest
 * 
 * @author Leonard Broman
 * 
 */
public interface MsrpHeader extends Encodable<String> {

	public enum HEADER_TYPE {
		AuthenticationInfo("Authentication-Info: "), Authorization(
				"Authorization: "), ByteRange("Byte-Range: "), ContentType(
				"Content-Type: "), FailureReport("Failure-Report: "), FromPath(
				"From-Path: "), MessageID("Message-ID: "), Status("Status: "), SuccessReport(
				"Success-Report: "), ToPath("To-Path: "), UseNickName(
				"Use-Nickname: "), UsePath("Use-Path: "), WWWAuthenticate(
				"WWW-Authenticate: ");

		private String key;

		private HEADER_TYPE(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public static HEADER_TYPE byString(String s) {
			for (HEADER_TYPE t : HEADER_TYPE.values()) {
				if (s.equals(t.getKey())) {
					return t;
				}
			}
			return null;
		}
	}

	/**
	 * Get the key of this header
	 * 
	 * @return this headers key
	 */
	public String getKey();

	/**
	 * Get the value of this header
	 * 
	 * @return this headers value
	 */
	public String getValue();

	/**
	 * Encode the Header without the CRLF at the end!
	 * 
	 * @return String
	 */
	public String encode();

	/**
	 * Returns a copy of the header
	 * 
	 * @return a copy
	 */
	public MsrpHeader clone() throws CloneNotSupportedException;

	public HEADER_TYPE getType();

}
