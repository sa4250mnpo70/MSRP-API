package se.lbroman.msrp.impl.data.header;

import java.util.LinkedList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.header.ContentTypeHeader;
import se.lbroman.msrp.impl.data.Parameter;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;
import se.lbroman.msrp.impl.parser.HeaderVisitor;


/**
 * Content type descriptor
 * 
 * <pre>
 * Content-Type = &quot;Content-Type:&quot; SP media-type 
 * media-type = type &quot;/&quot; subtype *( &quot;;&quot; gen-param ) 
 * type = token 
 * subtype = token 
 * gen-param = pname [ &quot;=&quot; pval ]
 * pname = token 
 * pval = token / quoted-string
 * </pre>
 * 
 * @author Leonard Broman
 * 
 */
public class ContentTypeHeaderImpl extends MsrpHeaderImpl implements
		ContentTypeHeader {
	/* Attributes */
	private static Log logger = LogFactory.getLog(ContentTypeHeaderImpl.class);

	private String type = null;
	private String subType = null;
	private List<Parameter> params = new LinkedList<Parameter>();

	public ContentTypeHeaderImpl() {

	}

	public ContentTypeHeaderImpl(String type, String subType) {
		this.type = type;
		this.subType = subType;
	}

	public ContentTypeHeaderImpl(String type, String subType,
			List<Parameter> params) {
		this.type = type;
		this.subType = subType;
		this.params = params;
	}

	public ContentTypeHeaderImpl(ContentTypeHeaderImpl orig) {
		this.type = orig.type;
		this.subType = orig.subType;
		for (Parameter p : orig.params) {
			params.add(p.clone());
		}
	}

	@Override
	public ContentTypeHeaderImpl clone() {
		return new ContentTypeHeaderImpl(this);
	}

	@Override
	public String getValue() {
		String code = new String();
		code += type + "/" + subType;
		for (Parameter p : params) {
			code += ";" + p.encode();
		}
		return code;
	}

	@Override
	public String getKey() {
		return key;
	}

	public String getContentSubType() {
		return subType;
	}

	public String getContentType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		result = prime * result + ((subType == null) ? 0 : subType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ContentTypeHeaderImpl other = (ContentTypeHeaderImpl) obj;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		if (subType == null) {
			if (other.subType != null)
				return false;
		} else if (!subType.equals(other.subType))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String getParameter(String param) {
		for (Parameter p : params) {
			if (p.getFirst().equals(param)) {
				return p.getSecond();
			}
		}
		return null;
	}
	
	@Override
    public void accept(HeaderVisitor v) throws HeaderParseErrorException {
        v.visit(this);
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public void setSubType(String subType2) {
        this.subType = subType2;
    }

    public void setParameters(List<Parameter> params2) {
        this.params = params2;
    }

}
