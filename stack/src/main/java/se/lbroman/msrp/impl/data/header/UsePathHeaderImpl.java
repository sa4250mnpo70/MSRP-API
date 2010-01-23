/**
 * 
 */
package se.lbroman.msrp.impl.data.header;

import java.util.LinkedList;

import se.lbroman.msrp.data.MsrpURI;
import se.lbroman.msrp.data.header.UsePathHeader;
import se.lbroman.msrp.impl.data.MsrpURIImpl;


/**
 * 
 * 
 * <pre>
 * Use-Path = &quot;Use-Path:&quot; SP MSRP-URI *(SP MSRP-URI)
 * MSRP-URI = msrp-scheme &quot;://&quot; authority [&quot;/&quot; session-id] &quot;;&quot; transport *( &quot;;&quot; URI-parameter)
 * msrp-scheme = &quot;msrp&quot; / &quot;msrps&quot;
 * authority = [ userinfo &quot;@&quot; ] host [ &quot;:&quot; port ]
 * session-id = 1*( unreserved / &quot;+&quot; / &quot;=&quot; / &quot;/&quot; )
 * unreserved = ALPHA / DIGIT / &quot;-&quot; / &quot;.&quot; / &quot;_&quot; / &quot;&tilde;&quot;
 * transport = &quot;tcp&quot; / 1*ALPHANUM
 * URI-parameter = token [&quot;=&quot; token]
 * token = 1*(%x21 / %x23-27 / %x2A-2B / %x2D-2E / %x30-39 / %x41-5A / %x5E-7E)
 * </pre>
 * 
 * @author Leonard Broman
 * 
 * @see FromPathHeaderImpl
 * 
 */

public class UsePathHeaderImpl extends PathHeaderImpl implements UsePathHeader {

	public UsePathHeaderImpl() {
		super();
	}

	public UsePathHeaderImpl(LinkedList<MsrpURIImpl> list) {
		super(list);
	}

	public UsePathHeaderImpl(MsrpURIImpl uri) {
		super(uri);
	}

	public UsePathHeaderImpl(UsePathHeaderImpl orig) {
		super(orig);
	}

	// @Override
	// public String encode() {
	// String code = new String();
	// code += key;
	// code = code.trim();
	// for (MsrpURIImpl uri : URIList) {
	// code += " " + uri.encode();
	// }
	// return code;
	// }

	@Override
	public UsePathHeaderImpl clone() {
		return new UsePathHeaderImpl(this);
	}

	// @Override
	// public void parse(String data) throws HeaderParseErrorException {
	// String[] set = data.split(key);
	// if (set.length != 2) {
	// throw new HeaderParseErrorException("Malformed Use-Path header");
	// }
	// URIList = parseUriList(set[1]);
	// }

	// /**
	// * @see UsePathHeader#getDestination()
	// * @deprecated
	// */
	// @Deprecated
	// public MsrpURI getDestination() {
	// return URIList.getLast();
	// }

	/**
	 * @see UsePathHeader#getEntryPoint()
	 */
	public MsrpURI getEntryPoint() {
		return URIList.getFirst();
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public HEADER_TYPE getType() {
		return HEADER_TYPE.UsePath;
	}
}
