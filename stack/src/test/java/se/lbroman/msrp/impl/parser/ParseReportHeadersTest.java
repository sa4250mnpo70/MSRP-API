package se.lbroman.msrp.impl.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import se.lbroman.msrp.data.header.FailureReportHeader;
import se.lbroman.msrp.data.header.MsrpHeader;
import se.lbroman.msrp.data.header.SuccessReportHeader;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public class ParseReportHeadersTest {
    
    HeaderParser parser = new HeaderParserImpl();
    
    @Test
    public void successYes() throws ParseErrorException {
        SuccessReportHeader header = (SuccessReportHeader) parser.createHeader(SuccessReportHeader.key + ": yes");
        assertEquals(SuccessReportHeader.Success.Yes,header.getResult());
    }
    
    @Test
    public void successNo() throws ParseErrorException {
        SuccessReportHeader header = (SuccessReportHeader) parser.createHeader(SuccessReportHeader.key + ": no");
        assertEquals(SuccessReportHeader.Success.No,header.getResult());
    }
    
    @Test
    public void failureYes() throws ParseErrorException {
        FailureReportHeader header = (FailureReportHeader) parser.createHeader(FailureReportHeader.key + ": yes");
        assertEquals(FailureReportHeader.Failure.Yes,header.getResult());
    }

    @Test
    public void failureNo() throws ParseErrorException {
        FailureReportHeader header = (FailureReportHeader) parser.createHeader(FailureReportHeader.key + ": no");
        assertEquals(FailureReportHeader.Failure.No,header.getResult());
    }
    
    @Test
    public void failurePartial() throws ParseErrorException {
        FailureReportHeader header = (FailureReportHeader) parser.createHeader(FailureReportHeader.key + ": partial");
        assertEquals(FailureReportHeader.Failure.Partial,header.getResult());
    }
    
    @Test
    public void incorrectSuccess() {
        try {
            parser.createHeader(SuccessReportHeader.key + ": crap");
            fail();
        } catch (Exception e) {
            
        }
    }
    
    @Test(expected=ParseErrorException.class)
    public void incorrectFailure() throws ParseErrorException {
        parser.createHeader(FailureReportHeader.key + ": crap");
    }

}
