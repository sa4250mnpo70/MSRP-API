package se.lbroman.msrp.data.header;


/**
 * 
 * @author Leonard Broman
 * 
 */
public interface ByteRangeHeader extends MsrpHeader {

	public static final String key = MsrpHeader.HEADER_TYPE.ByteRange.getKey();

	public long getStart();

	public long getEnd();

	public long getTotal();
}
