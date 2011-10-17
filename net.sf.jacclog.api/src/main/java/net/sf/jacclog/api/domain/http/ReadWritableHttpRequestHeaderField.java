package net.sf.jacclog.api.domain.http;

/**
 * Defines a Hypertext Transfer Protocol (HTTP) request header field consisting of a type and value that can be queried
 * and modified.<br>
 * <br>
 * This interface gives access to change data.<br>
 * <br>
 * The implementation of this interface will be mutable. It may provide more advanced methods than those in this
 * interface.
 * 
 * @author André Rouél
 */
public interface ReadWritableHttpRequestHeaderField extends ReadableHttpRequestHeaderField {

	/**
	 * Sets the type of the request header.
	 * 
	 * @param type
	 *            the type of the request header
	 */
	public void setType(final ReadableHttpRequestHeader type);

	/**
	 * Sets the value of the request header.
	 * 
	 * @param value
	 *            the value of the request header
	 */
	public void setValue(final String value);

}
