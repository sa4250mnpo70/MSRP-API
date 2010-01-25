package se.lbroman.msrp.impl.data;

import java.util.LinkedList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.Encodable;
import se.lbroman.msrp.data.MsrpURI;
import se.lbroman.msrp.exception.ParseErrorException;


/**
 * 
 * <pre>
 * MSRP-URI = msrp-scheme &quot;://&quot; authority [&quot;/&quot; session-id] &quot;;&quot; transport *( &quot;;&quot; URI-parameter)
 * msrp-scheme = &quot;msrp&quot; / &quot;msrps&quot;
 * authority     = [ userinfo &quot;@&quot; ] host [ &quot;:&quot; port ]
 * session-id = 1*( unreserved / &quot;+&quot; / &quot;=&quot; / &quot;/&quot; )
 * unreserved    = ALPHA / DIGIT / &quot;-&quot; / &quot;.&quot; / &quot;_&quot; / &quot;&tilde;&quot;
 * transport = &quot;tcp&quot; / 1*ALPHANUM
 * URI-parameter = token [&quot;=&quot; token]
 * token = 1*(%x21 / %x23-27 / %x2A-2B / %x2D-2E / %x30-39 / %x41-5A / %x5E-7E)
 * </pre>
 * 
 * @author Leonard Broman
 * 
 */
public class MsrpURIImpl implements Encodable<String>, MsrpURI {

	private static Log logger = LogFactory.getLog(MsrpURIImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 5901216428962799493L;
	/** Attributes */
	private String scheme = "msrp";
	private String userinfo = null;
	private String host = null;
	private int port = 2855;
	private String resource = null;
	private String transport = "tcp";
	private List<Parameter> parameters = new LinkedList<Parameter>();

	/**
	 * Operation
	 * 
	 * @param URI
	 * @throws ParseErrorException
	 * @deprecated Use the parser instead!
     */
    @Deprecated
	public MsrpURIImpl(String URI) throws ParseErrorException {
		parse(URI);
	}

	/**
	 * Copy constructor. Reduced to reference copy strings, since they are
	 * immutable.
	 * 
	 * @param orig
	 */
	public MsrpURIImpl(MsrpURIImpl orig) {
		if (orig.scheme != null)
			this.scheme = orig.scheme;
		if (orig.userinfo != null)
			this.userinfo = orig.userinfo;
		if (orig.host != null)
			this.host = orig.host;
		this.port = orig.port;
		if (orig.resource != null)
			this.resource = orig.resource;
		if (orig.transport != null)
			this.transport = orig.transport;
		parameters = new LinkedList<Parameter>();
		for (Parameter param : orig.parameters) {
			parameters.add(new Parameter(param));
		}
	}

	/**
	 * Interface copy constructor. This only copies attributes which can be
	 * accessed via getters in the interface.
	 * 
	 * @param orig
	 *            The original MSRP URI.
	 * @see javax.msrp.data.MsrpURI
	 */
	public MsrpURIImpl(MsrpURI orig) {
		this.host = orig.getHost();
		this.port = orig.getPort();
		this.resource = orig.getResource();
	}

	/**
	 * Direct attribute constructor. For precise URI creation.
	 * 
	 * @param scheme
	 * @param userinfo
	 * @param host
	 * @param port
	 * @param sessionID
	 * @param transport
	 * @param parameters
	 */
	public MsrpURIImpl(String scheme, String userinfo, String host, int port,
			String resource, String transport, List<Parameter> parameters) {
		this.scheme = scheme;
		this.userinfo = userinfo;
		this.host = host;
		this.port = port;
		this.resource = resource;
		this.transport = transport;
		this.parameters = parameters;
	}

	/**
	 * Encodes the URI into a String
	 */
	public String encode() {
		String s = scheme + "://";
		if (userinfo != null) {
			s += userinfo + "@";
		}
		if (port == MSRP_PORT) {
			s += host;
		} else {
			s += host + ":" + port;
		}
		s += "/";
		if (resource != null) {
			s += resource;
		}
		s += ";" + transport;
		for (Parameter p : parameters) {
			s += ";" + p.encode();
		}
		return s;
	}

	@Override
	public MsrpURIImpl clone() {
		return new MsrpURIImpl(this);
	}

	/**
	 * *
	 * 
	 * <pre>
	 * MSRP-URI = msrp-scheme &quot;://&quot; authority [&quot;/&quot; session-id] &quot;;&quot; transport *( &quot;;&quot; URI-parameter)
	 * msrp-scheme = &quot;msrp&quot; / &quot;msrps&quot;
	 * authority     = [ userinfo &quot;@&quot; ] host [ &quot;:&quot; port ]
	 * session-id = 1*( unreserved / &quot;+&quot; / &quot;=&quot; / &quot;/&quot; )
	 * unreserved    = ALPHA / DIGIT / &quot;-&quot; / &quot;.&quot; / &quot;_&quot; / &quot;&tilde;&quot;
	 * transport = &quot;tcp&quot; / 1*ALPHANUM
	 * URI-parameter = token [&quot;=&quot; token]
	 * token = 1*(%x21 / %x23-27 / %x2A-2B / %x2D-2E / %x30-39 / %x41-5A / %x5E-7E)
	 * </pre>
	 * 
	 * @param s
	 * @deprecated Use the parser instead!
	 */
	@Deprecated
	public void parse(String s) throws ParseErrorException {
		logger.trace("Parsing uri: " + s);
		String[] set = s.split("://");
		if (set.length != 2) {
			throw new ParseErrorException("Incorrect uri <scheme>://<uri> : "
					+ s);
		}
		scheme = set[0];
		set = set[1].split(";");
		// Parse everything before the parameters
		String[] set2;
		set2 = set[0].split("@");
		userinfo = null;
		if (set2.length > 1) {
			userinfo = set2[0];
			set2 = set2[1].split(":");
		} else {
			set2 = set2[0].split(":");
		}
		host = "ParseErrorHost.NoWhere";
		port = 2855;
		resource = null;
		if (set2.length == 2) {
			// host port/session
			host = set2[0];
			set2 = set2[1].split("/");
			port = Integer.parseInt(set2[0]);
			if (set2.length == 2) {
				// port session
				resource = set2[1];
			}
		} else if (set2.length == 1) {
			// host/session
			set2 = set2[0].split("/");
			host = set2[0];
			if (set2.length == 2) {
				resource = set2[1];
			}
		}

		// Parse transport and parameters
		transport = set[1];
		parameters = new LinkedList<Parameter>();
		for (int i = 2; i < set.length; i++) {
			parameters.add(new Parameter(set[i]));
		}
		// return new MsrpURI(scheme, userinfo, host, port, sessionID,
		// transport, p);
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getHostPort() {
		return host + ":" + port;
	}

	public String getHostPortResource() {
		return getHostPort() + ":" + resource;
	}

	@Override
	public int hashCode() {
		return this.encode().hashCode();
	}

	@Override
	public String toString() {
		return encode();
	}

	public void setResource(String id) {
		resource = id;
	}

	public String getResource() {
		return resource;
	}

	public String getScheme() {
		return scheme;
	}

	public String getUserInfo() {
		return userinfo;
	}

	public String getTransport() {
		return transport;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MsrpURIImpl))
			return false;
		final MsrpURIImpl other = (MsrpURIImpl) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (port != other.port)
			return false;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		if (scheme == null) {
			if (other.scheme != null)
				return false;
		} else if (!scheme.equals(other.scheme))
			return false;
		if (transport == null) {
			if (other.transport != null)
				return false;
		} else if (!transport.equals(other.transport))
			return false;
		return true;
	}

}
