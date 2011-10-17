package net.sf.jacclog.api.domain.http;

/**
 * Defines a Hypertext Transfer Protocol (HTTP) response header field consisting of a type and value that can be queried
 * and modified.<br>
 * <br>
 * This interface gives access to change data.<br>
 * <br>
 * The implementation of this interface will be mutable. It may provide more advanced methods than those in this
 * interface.
 * 
 * @author André Rouél
 */
public interface ReadWritableHttpResponseHeaderField extends ReadableHttpResponseHeaderField {

	/**
	 * Sets the type of the response header.
	 * 
	 * @param type
	 *            the type of the response header
	 */
	public void setType(final ReadableHttpResponseHeader type);

	/**
	 * Sets the value of the response header.
	 * 
	 * @param value
	 *            the value of the response header
	 */
	public void setValue(final String value);

}
