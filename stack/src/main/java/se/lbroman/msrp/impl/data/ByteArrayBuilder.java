/**
 * 
 */
package se.lbroman.msrp.impl.data;

import java.util.LinkedList;

/**
 * Yet another extremely simple byte array data structure. It appends data in
 * constant time, but returns data in linear time, amortized constant time.
 * Useful for building arrays and extracting them.
 * 
 * 
 * @author Leonard Broman
 * 
 */
public class ByteArrayBuilder {

	private LinkedList<byte[]> chunks = new LinkedList<byte[]>();;
	private int length = 0;

	public void append(byte[] a) {
		chunks.add(a);
		length += a.length;
	}

	/**
	 * Adds a subrange of an array to the builder.
	 * 
	 * @param a
	 *            the array
	 * @param start
	 *            first byte to copy
	 * @param end
	 *            last byte to copy
	 */
	public void append(byte[] a, int start, int end) {
		int size = (end-start+1);
		byte[] b = new byte[size];
		for (int i = start; i <= end; i++) {
			b[i - start] = a[i];
		}
		chunks.add(b);
		length += end - start + 1;
	}

	public byte[] getBytes() {
		byte[] b = new byte[length];
		int pos = 0;
		for (byte[] c : chunks) {
			for (int i = 0; i < c.length; i++) {
				b[pos] = c[i];
				pos++;
			}
		}
		chunks.clear();
		chunks.add(b);
		return b;
	}

}
