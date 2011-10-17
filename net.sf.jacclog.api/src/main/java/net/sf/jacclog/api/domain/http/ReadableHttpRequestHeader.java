package net.sf.jacclog.api.domain.http;

/**
 * Defines the root interface of a Hypertext Transfer Protocol request header.
 * 
 * @author André Rouél
 */
public interface ReadableHttpRequestHeader {

	/**
	 * Gets the name of the request header which can not be <code>null</code>.<br>
	 * <br>
	 * For example: <code>Set-Cookie</code>
	 * 
	 * @return the name of the request header
	 */
	String getName();

}
