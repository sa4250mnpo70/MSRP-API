package se.lbroman.msrp.impl.data;

public interface ByteUtils {

    /**
     * 
     * @param a
     * @param aStart
     * @param b
     * @param bStart
     * @param length
     * @return true if the subrange i identical, otherwise false
     */
    boolean equalsSubRange(byte[] a, int aStart, byte[] b, int bStart, int length);

    /**
     * Extracts a subrange from a byte array.
     * 
     * @param source
     *            byte array to extract from
     * @param from
     *            first byte
     * @param length
     *            number of bytes to read
     * @return a byte[] containing the subrange
     */
    byte[] subRange(byte[] source, int from, int length);

    /**
     * 
     * @param from
     * @param fromStart
     * @param to
     * @param toStart
     * @param len
     */
    void copySubRange(byte[] from, int fromStart, byte[] to,
            int toStart, int len);

}
