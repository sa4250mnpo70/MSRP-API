package se.lbroman.msrp.impl.parser;

import se.lbroman.msrp.exception.ParseErrorException;
import se.lbroman.msrp.impl.data.MsrpURIImpl;

public interface UriParser {
    
   /**
    * <pre>
    * MSRP-URI = msrp-scheme &quot;://&quot; authority [&quot;/&quot; session-id] &quot;;&quot; transport *( &quot;;&quot; URI-parameter)
    * msrp-scheme = &quot;msrp&quot; / &quot;msrps&quot;
    * authority     = [ userinfo &quot;@&quot; ] host [ &quot;:&quot; port ]
    * session-id = 1*( unreserved / &quot;+&quot; / &quot;=&quot; / &quot;/&quot; )
    * unreserved    = ALPHA / DIGIT / &quot;-&quot; / &quot;.&quot; / &quot;_&quot; / &quot;&tilde;&quot;
    * transport = &quot;tcp&quot; / 1*ALPHANUM
    * URI-parameter = token [&quot;=&quot; token]
    * token = 1*(%x21 / %x23-27 / %x2A-2B / %x2D-2E / %x30-39 / %x41-5A / %x5E-7E)
    * </pre>
    */
    MsrpURIImpl createMsrpUri(String data) throws ParseErrorException;

}
