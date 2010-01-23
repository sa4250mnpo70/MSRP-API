package se.lbroman.msrp.exception;

public class ParseErrorException extends Exception {


    private static final long serialVersionUID = 1L;
    
    public ParseErrorException(String arg0) {
        super(arg0);
    }

    public ParseErrorException(ParseErrorException e) {
        super(e);
    }


}
