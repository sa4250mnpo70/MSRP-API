package se.lbroman.msrp.data.header;

import se.lbroman.msrp.data.MsrpURI;


public interface FromPathHeader extends PathHeader {

	public static final String key = MsrpHeader.HEADER_TYPE.FromPath.getKey();

	public MsrpURI getLastHop();

	public MsrpURI getOrigin();

	public void addHop(MsrpURI uri);
}
