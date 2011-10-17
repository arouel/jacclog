package net.sf.jacclog.api.domain.http;

/**
 * Defines the root interface of a Hypertext Transfer Protocol response header.
 * 
 * @author André Rouél
 */
public interface ReadableHttpResponseHeader {

	/**
	 * Gets the name of the response header which can not be <code>null</code>.<br>
	 * <br>
	 * For example: <code>Set-Cookie</code>
	 * 
	 * @return the name of the response header
	 */
	String getName();

}
