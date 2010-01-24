package se.lbroman.msrp.impl.parser;

import se.lbroman.msrp.impl.data.header.AuthenticationInfoHeaderImpl;
import se.lbroman.msrp.impl.data.header.AuthorizationHeaderImpl;
import se.lbroman.msrp.impl.data.header.ByteRangeHeaderImpl;
import se.lbroman.msrp.impl.data.header.ContentTypeHeaderImpl;
import se.lbroman.msrp.impl.data.header.FailureReportHeaderImpl;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.MessageIDHeaderImpl;
import se.lbroman.msrp.impl.data.header.StatusHeaderImpl;
import se.lbroman.msrp.impl.data.header.SuccessReportHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.UsePathHeaderImpl;
import se.lbroman.msrp.impl.data.header.WWWAuthenticateHeaderImpl;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;

public interface HeaderVisitor {

    void visit(WWWAuthenticateHeaderImpl wwwAuthenticateHeaderImpl) throws HeaderParseErrorException;

    void visit(UsePathHeaderImpl usePathHeaderImpl) throws HeaderParseErrorException;

    void visit(ToPathHeaderImpl toPathHeaderImpl) throws HeaderParseErrorException;

    void visit(SuccessReportHeaderImpl successReportHeaderImpl) throws HeaderParseErrorException;

    void visit(StatusHeaderImpl statusHeaderImpl) throws HeaderParseErrorException;

    void visit(MessageIDHeaderImpl messageIDHeaderImpl) throws HeaderParseErrorException;

    void visit(FromPathHeaderImpl fromPathHeaderImpl) throws HeaderParseErrorException;

    void visit(FailureReportHeaderImpl failureReportHeaderImpl) throws HeaderParseErrorException;

    void visit(ContentTypeHeaderImpl contentTypeHeaderImpl) throws HeaderParseErrorException;

    void visit(ByteRangeHeaderImpl byteRangeHeaderImpl) throws HeaderParseErrorException;

    void visit(AuthorizationHeaderImpl authorizationHeaderImpl) throws HeaderParseErrorException;

    void visit(AuthenticationInfoHeaderImpl authenticationInfoHeaderImpl) throws HeaderParseErrorException;

}
