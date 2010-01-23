package se.lbroman.msrp.impl.data;

/**
 * This is a much lighter implementation of the Joined "Pair". It only provides
 * a basic building block for returning objects as pairs instead of arrays and
 * provides improved type safety.
 * 
 * @author Leonard Broman
 * 
 */
public class Pair<F, S> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4152698677129409491L;

	private F first;
	private S second;

	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Copy constructor
	 * 
	 * @param orig
	 */
	public Pair(Pair<F, S> orig) {
		first = orig.first;
		second = orig.second;
	}

	/**
	 * @return the first
	 */
	public F getFirst() {
		return first;
	}

	/**
	 * @return the second
	 */
	public S getSecond() {
		return second;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Pair<F, S> other = (Pair<F, S>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

}
