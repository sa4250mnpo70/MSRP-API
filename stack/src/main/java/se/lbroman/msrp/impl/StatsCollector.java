/**
 * 
 */
package se.lbroman.msrp.impl;

/**
 * @author Leonard Broman
 *
 */
public class StatsCollector {
	
	private static StatsCollector instance;
	
	private int received = 0;

	private int sent = 0;
	
	private int parsed = 0;
	
	public static StatsCollector instance() {
		if (instance == null) {
			instance = new StatsCollector();
		}
		return instance;
	}
	
	public int PPRecv() {
		return ++received;
	}
	
	public int received() {
		return received;
	}
	
	public int PPSent() {
		return ++sent;
	}
	
	public int sent() {
		return sent;
	}

	public int PPParsed() {
		return ++parsed;
	}

}
