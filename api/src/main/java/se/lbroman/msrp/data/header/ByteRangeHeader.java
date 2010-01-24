package se.lbroman.msrp.data.header;


/**
 * 
 * @author Leonard Broman
 * 
 */
public interface ByteRangeHeader extends MsrpHeader {

	public static final String key = "Byte-Range";

	public long getStart();

	public long getEnd();

	public long getTotal();
}
