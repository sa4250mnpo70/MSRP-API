/**
 * 
 */
package se.lbroman.msrp.data.header;

/**
 * @author Leonard Broman
 * 
 */
public interface PathHeader extends MsrpHeader {

	public ToPathHeader makeTo();

	public FromPathHeader makeFrom();

	// /**
	// * This should be avoided, since To/From-Path contains destinations and
	// * origins.
	// *
	// * @return a UsePathHeader
	// * @deprecated
	// */
	// @Deprecated
	// public UsePathHeader makeUse();

}
