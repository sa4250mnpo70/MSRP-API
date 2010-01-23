/**
 * 
 */
package se.lbroman.msrp.impl.parser;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.impl.data.Pair;
import se.lbroman.msrp.impl.data.Parameter;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;


/**
 * Keep instances of this class static to avoid map reloading.
 * 
 * @author Leonard Broman
 * 
 */
public class DefaultParamsParser<E extends Enum<E>> implements ParamsParser<E> {

	private static Log logger = LogFactory.getLog(ParamsParser.class);

	public static final String qdtextChar = "[ \\t\\x21\\x23-\\x5B\\x5D-\\x7E]";

	public static final String qdescChar = "(?:\\\\\\\\|\\\\\")";

	public static final String tokenChar = "[\\x21\\x23-\\x27\\x2A-\\x2B\\x2D-\\x2E\\x30-\\x39\\x41-\\x5A\\x5E-\\x7E]";

	public static final String token = "(?:" + tokenChar + ")*";

	public static final String quotedString = "(?:\"(?:" + qdtextChar + "|"
			+ qdescChar + ")*\")";

	public static final String quotedStringParam = "^" + token + "="
			+ quotedString + "$";

	public static final String tokenParam = "^" + token + "=" + token + "$";

	public static final String regex = token + "=" + "(?:" + quotedString + "|"
			+ token + ")" + "(?:, |)";

	private HashMap<String, E> map = new HashMap<String, E>();

	private static final Pattern pattern = Pattern.compile(regex);

	private static final Pattern quotedStringPattern = Pattern
			.compile(quotedStringParam);

	private static final Pattern tokenParamPattern = Pattern
			.compile(tokenParam);

	private Class<E> type;

	public DefaultParamsParser(Class<E> enumType) {
		type = enumType;
		logger.debug("Searching with regex: " + pattern.pattern());
		for (E e : type.getEnumConstants()) {
			// logger.trace("Loaded parameter type: " + e.toString() + " for "
			// + enumType.toString());
			logger.trace("Loaded parameter type: " + e.toString());
			map.put(e.toString(), e);
		}
	}

	private E resolve(String name) throws IllegalArgumentException {
		if (map.containsKey(name)) {
			return map.get(name);
		}
		throw new IllegalArgumentException("The name " + name
				+ " does not exist for " + type);
	}

	public Pair<EnumMap<E, String>, List<Parameter>> parse(
			String data) throws HeaderParseErrorException {
		assert data != null;
		EnumMap<E, String> params = new EnumMap<E, String>(type);
		LinkedList<Parameter> extendedParams = new LinkedList<Parameter>();
		Pair<EnumMap<E, String>, List<Parameter>> pair = new Pair<EnumMap<E, String>, List<Parameter>>(
				params, extendedParams);
		Matcher m = pattern.matcher(data);
		while (m.find()) {
			String one = m.group();
			if (one.endsWith(", ")) {
				one = one.substring(0, one.length() - 2);
			}
			logger.trace("Found parameter: " + one);
			String[] vals = one.split("=");
			if (vals.length != 2) {
				throw new HeaderParseErrorException(
						"Flag parameters not allowed");
			}
			String key = vals[0];
			String value = vals[1];
			try {
				E en = resolve(key);
				if (params.containsKey(en))
					throw new HeaderParseErrorException("Duplicate parameter: "
							+ en.toString());
				params.put(en, value);
			} catch (IllegalArgumentException e) {
				extendedParams.add(new Parameter(key, value));
			}
		}
		return pair;
	}

	public boolean checkTokenParam(E param, String data) {
		if (tokenParamPattern.matcher(data).matches()) {
			String[] vals = data.split("=");
			if (vals[0].equals(param.toString())) {
				return true;
			}
		}
		return false;
	}

	public boolean checkQuotedParam(E param, String data) {
		if (quotedStringPattern.matcher(data).matches()) {
			String[] vals = data.split("=");
			if (vals[0].equals(param.toString())) {
				return true;
			}
		}
		return false;
	}

}
