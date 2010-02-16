package se.lbroman.msrp.impl.parser;

import se.lbroman.msrp.impl.data.packet.AuthImpl;
import se.lbroman.msrp.impl.data.packet.BadRequestImpl;
import se.lbroman.msrp.impl.data.packet.NotFoundImpl;
import se.lbroman.msrp.impl.data.packet.NotImplementedImpl;
import se.lbroman.msrp.impl.data.packet.OKImpl;
import se.lbroman.msrp.impl.data.packet.ReportImpl;
import se.lbroman.msrp.impl.data.packet.SendImpl;
import se.lbroman.msrp.impl.data.packet.UnauthorizedImpl;
import se.lbroman.msrp.impl.data.packet.VisitImpl;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public interface PacketVisitor {

    void visit(AuthImpl authImpl) throws ParseErrorException;

    void visit(BadRequestImpl badRequestImpl) throws ParseErrorException;

    void visit(NotFoundImpl notFoundImpl) throws ParseErrorException;

    void visit(NotImplementedImpl notImplementedImpl) throws ParseErrorException;

    void visit(OKImpl okImpl) throws ParseErrorException;

    void visit(ReportImpl reportImpl) throws ParseErrorException;

    void visit(SendImpl sendImpl) throws ParseErrorException;

    void visit(UnauthorizedImpl unauthorizedImpl) throws ParseErrorException;

    void visit(VisitImpl visitImpl) throws ParseErrorException;

}
