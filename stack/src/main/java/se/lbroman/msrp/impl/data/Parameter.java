package se.lbroman.msrp.impl.data;

import se.lbroman.msrp.Encodable;

/**
 * 
 * @author Leonard Broman
 * 
 */
public class Parameter extends Pair<String, String> implements
		Encodable<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7087837462731998942L;

	/**
	 * Key-only constructor
	 * 
	 * @param key
	 *            the key
	 */
	public Parameter(String key) {
		super(key, "");
	}

	/**
	 * Key=Value constructor
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public Parameter(String key, String value) {
		super(key, value);
	}

	/**
	 * Copy constructor
	 * 
	 * @param param
	 *            the original
	 */
	public Parameter(Parameter param) {
		super(param);
	}

	public String encode() {
		if (getSecond() == null || getSecond().equals("")) {
			return getFirst();
		} else {
			return getFirst() + "=" + getSecond();
		}
	}

	@Override
	public Parameter clone() {
		return new Parameter(this);
	}

	public static Parameter parse(String s) {
		String[] set = s.split("=");
		String key = set[0];
		String value = null;
		if (set.length == 2) {
			value = set[1];
		}
		if (value == null) {
			return new Parameter(key);
		} else {
			return new Parameter(key, value);
		}
	}
}
