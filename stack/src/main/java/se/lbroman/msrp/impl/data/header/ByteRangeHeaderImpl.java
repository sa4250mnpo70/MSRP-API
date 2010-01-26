package se.lbroman.msrp.impl.data.header;

import se.lbroman.msrp.data.header.ByteRangeHeader;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;
import se.lbroman.msrp.impl.parser.HeaderVisitor;


/**
 * This is a Byte-Range header.
 * 
 * Is is copyable and placeable in an Immutable container.
 * 
 * <pre>
 * Byte-Range = &quot;Byte-Range:&quot; SP range-start &quot;-&quot; range-end &quot;/&quot; total
 * </pre>
 * 
 * @author Leonard Broman
 * 
 */
public class ByteRangeHeaderImpl extends MsrpHeaderImpl implements
		ByteRangeHeader {
	
	private long start;

	private long end;

	private long total;

	public ByteRangeHeaderImpl() {

	}

	public ByteRangeHeaderImpl(long start, long end, long total) {
		this.start = start;
		this.end = end;
		this.total = total;
	}

	/**
	 * Copy constructor
	 * 
	 * @param orig
	 */
	public ByteRangeHeaderImpl(ByteRangeHeaderImpl orig) {
		start = orig.start;
		end = orig.end;
		total = orig.total;
	}

	@Override
	public ByteRangeHeaderImpl clone() {
		return new ByteRangeHeaderImpl(this);
	}

	/**
	 * @return the start
	 */
	public long getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public long getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(long end) {
		this.end = end;
	}

	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}
	
	public void setTotal(long total) {
	    this.total = total;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		return start + "-" + 
		        (end == -1 ? "*" : end) + "/" + 
		        (total == -1 ? "*" : total);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (end ^ (end >>> 32));
		result = prime * result + (int) (start ^ (start >>> 32));
		result = prime * result + (int) (total ^ (total >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ByteRangeHeaderImpl other = (ByteRangeHeaderImpl) obj;
		if (end != other.end)
			return false;
		if (start != other.start)
			return false;
		if (total != other.total)
			return false;
		return true;
	}

	@Override
    public void accept(HeaderVisitor v) throws HeaderParseErrorException {
        v.visit(this);
    }

}
