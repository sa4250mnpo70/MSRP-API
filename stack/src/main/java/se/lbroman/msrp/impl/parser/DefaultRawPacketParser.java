package se.lbroman.msrp.impl.parser;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.lbroman.msrp.data.header.ContentTypeHeader;
import se.lbroman.msrp.data.packet.MsrpPacket;
import se.lbroman.msrp.data.packet.Send;
import se.lbroman.msrp.impl.data.ByteArrays;
import se.lbroman.msrp.impl.data.ByteUtils;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
import se.lbroman.msrp.impl.data.packet.RawMsrpPacket;
import se.lbroman.msrp.impl.exception.ParseErrorException;

public class DefaultRawPacketParser implements RawPacketParser {

    HeaderParser headerParser = new HeaderParserImpl();

    private static final Logger logger = LoggerFactory.getLogger(DefaultRawPacketParser.class);

    @Override
    public RawMsrpPacket parse(byte[] content) throws ParseErrorException {
        RawMsrpPacket packet = new RawMsrpPacket();
        PacketTraverser traverse = new PacketTraverser(content);
        String transactionID = null;
        Class<? extends MsrpPacket> type = null;
        // First line: MSRP id type [comment]
        try {
            String firstLine = new String(traverse.getLine(), "UTF-8");
            // isolate id and type
            String[] set = firstLine.split(" ");
            transactionID = set[1];
            type = getType(set[2]);
            if (type == null) {
                throw new ParseErrorException("Illegal packet type: " + set[2]);
            }
            packet.setType(type);
            packet.setID(transactionID);
        } catch (UnsupportedEncodingException e) {
            logger.error("Unsupported encoding", e);
            e.printStackTrace();
        }
        // We ignore the comment
        if (Send.class.equals(type)) {
            // Here be content
            RawMsrpHeader<?> h;
            do {
                try {
                    h = headerParser.createRawHeader(new String(traverse.getLine()));
                } catch (ParseErrorException e) {
                    e.printStackTrace();
                    throw new ParseErrorException(e);
                }
                packet.addHeader(h);
                // The last header must be Content-Type:
            } while (!(h.getKey().equals(ContentTypeHeader.key)));
            // We can now calculate where the content is
            // CRLF data CRLF ------- id ? CRLF
            // TODO MIME header treatment
            byte[] body = traverse.getContent(transactionID);
            packet.setContent(body);
        } else {
            RawMsrpHeader<?> h;
            do {
                h = headerParser.createRawHeader(new String(traverse.getLine()));
                packet.addHeader(h);
            } while (traverse.noMinus());
        }
        byte contFlag = content[content.length - 3];
        packet.setContFlag(contFlag);
        return packet;
    }

    Class<? extends MsrpPacket> getType(String code) {
        if ("SEND".equals(code)) {
            return Send.class;
        }
        return null;
    }

    private static class PacketTraverser {

        private byte[] packet;

        int pos = 0;

        private ByteUtils utils = new ByteArrays();

        private PacketTraverser(byte[] content) {
            this.packet = content;
        }

        private byte[] getLine() {
            int s = pos;
            while (packet[pos] != '\n') {
                pos++;
            }
            pos++;
            byte[] neu = new byte[pos - s - 2];
            utils.copySubRange(packet, s, neu, 0, neu.length);
            return neu;
        }
        
        private boolean noMinus() {
            return packet[pos] != '-';
        }

        private byte[] getContent(String transID) {
            byte[] content = new byte[packet.length - pos - 3 - transID.getBytes().length - 7 - 2 - 2];
            utils.copySubRange(packet, pos + 2, content, 0, content.length);
            return content;
        }

    }

}
