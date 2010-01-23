/**
 * 
 */
package se.lbroman.msrp.impl.data;

import java.util.Random;

/**
 * This provides static methods to manipulate byte[] data
 * 
 * @author Leonard Broman
 * 
 */
public class ByteArrays {

	/**
	 * 
	 * @param a
	 * @param aStart
	 * @param b
	 * @param bStart
	 * @param len
	 * @return if the subrange i identical, otherwise false
	 */
	public static boolean equalsSubRange(byte[] a, int aStart, byte[] b,
			int bStart, int len) {
		for (int i = 0; i < len; i++) {
			if (a[i + aStart] != b[i + bStart]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param from
	 * @param fromStart
	 * @param to
	 * @param toStart
	 * @param len
	 */
	public static void copySubRange(byte[] from, int fromStart, byte[] to,
			int toStart, int len) {
		for (int i = 0; i < len; i++) {
			to[i + toStart] = from[i + fromStart];
		}
	}

	public static byte[] append(byte[] first, byte[] second) {
		byte[] neu = new byte[first.length + second.length];
		copySubRange(first, 0, neu, 0, first.length);
		copySubRange(second, 0, neu, first.length, second.length);
		return neu;
	}

	public static byte[] random(int length) {
		byte[] neu = new byte[length];
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		r.nextBytes(neu);
		return neu;
	}

	/**
	 * Extracts a subrange from a byte array.
	 * 
	 * @param source
	 *            byte array to extract from
	 * @param from
	 *            first byte
	 * @param size
	 *            number of bytes to read
	 * @return a byte[] containing the subrange
	 */
	public static byte[] subRange(byte[] source, int from, int size) {
		byte[] neu = new byte[size];
		for (int i = 0; i < neu.length; i++) {
			neu[i] = source[i + from];
		}
		return neu;
	}
}
