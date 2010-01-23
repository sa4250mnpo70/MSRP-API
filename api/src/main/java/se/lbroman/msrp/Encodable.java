package se.lbroman.msrp;

public interface Encodable<T> {
    
    /**
     * Line Feed, The Unix Way
     */
    final static public String LF = "\n";
    /**
     * Carriage Return - Line Feed, The Micro$oft and ARPAnet way
     */
    final static public String CRLF = "\r\n";
    /**
     * Carriage Return, the old Mac OS way
     */
    final static public String CR = "\r";
    
    public T encode();

}
