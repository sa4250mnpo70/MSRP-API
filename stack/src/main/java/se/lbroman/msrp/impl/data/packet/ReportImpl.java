package se.lbroman.msrp.impl.data.packet;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.lbroman.msrp.data.packet.Report;
import se.lbroman.msrp.impl.MsrpParser;
import se.lbroman.msrp.impl.data.ByteArrayBuilder;
import se.lbroman.msrp.impl.data.header.FromPathHeaderImpl;
import se.lbroman.msrp.impl.data.header.MessageIDHeaderImpl;
import se.lbroman.msrp.impl.data.header.MsrpHeaderImpl;
import se.lbroman.msrp.impl.data.header.RawMsrpHeader;
import se.lbroman.msrp.impl.data.header.StatusHeaderImpl;
import se.lbroman.msrp.impl.data.header.ToPathHeaderImpl;
import se.lbroman.msrp.impl.exception.HeaderParseErrorException;


/**
 * Report packet, has the Status: header for reporting transmission results.
 * 
 * @author Leonard Broman
 * 
 */
public class ReportImpl extends RequestImpl implements Report {
	/** Attributes */
	protected StatusHeaderImpl status;

	private static Log logger = LogFactory.getLog(ReportImpl.class);

	public ReportImpl(FromPathHeaderImpl from, ToPathHeaderImpl to,
			StatusHeaderImpl status, MessageIDHeaderImpl messageID) {
		setHeader(from);
		setHeader(to);
		setHeader(messageID);
		this.status = status;
	}

	@Override
	public boolean isProper() {
		if (status != null)
			return super.isProper();
		return false;
	}

	@Override
	public byte[] encode() {
		ByteArrayBuilder packet = new ByteArrayBuilder();
		packet.append(("MSRP " + id + " REPORT" + CRLF).getBytes());
		packet.append(encodeFromTo());
		packet.append((messageID.encode() + CRLF).getBytes());
		if (status != null) {
			packet.append((status.encode() + CRLF).getBytes());
		}
		packet.append(("-------" + id + "$" + CRLF).getBytes());
		return packet.getBytes();
	}

	@Override
	public void setHeader(MsrpHeaderImpl h) {
		if (h instanceof StatusHeaderImpl) {
			status = (StatusHeaderImpl) h;
			logger.trace(h.getKey() + "header set");
		} else {
			super.setHeader(h);
		}
	}

	@Override
	public PACKET_TYPE getType() {
		return PACKET_TYPE.REPORT;
	}

	@Override
	public void parse(RawMsrpPacket rp) {
		for (RawMsrpHeader h : rp.getHeaders()) {
			try {
				// If you get a java.lang.InstantiationException
				// it is because the header doesnt have a nullary constructor
				MsrpHeaderImpl mh = MsrpParser.headerTypes.get(h.getKey())
						.newInstance();
			//	mh.parse(h.getKey() + h.getContent());
				setHeader(mh);
			} catch (InstantiationException e) {
				e.printStackTrace();
				logger.error("The header " + h.getKey()
						+ " has no nullary constructor defined!");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
