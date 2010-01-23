package se.lbroman.msrp.impl.parser;

import java.util.EnumMap;
import java.util.List;

import se.lbroman.msrp.impl.data.Pair;
import se.lbroman.msrp.impl.data.Parameter;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;

/**
 * A parameter parser capable parsing http header parameters.
 * 
 * @author Leonard Broman
 *
 * @param <E> enumeration of the recognized parameters
 */
public interface ParamsParser<E extends Enum<E>> {

    Pair<EnumMap<E, String>, List<Parameter>> parse(String data) throws HeaderParseErrorException;

    boolean checkQuotedParam(E param, String data);

    boolean checkTokenParam(E param, String data);

}
