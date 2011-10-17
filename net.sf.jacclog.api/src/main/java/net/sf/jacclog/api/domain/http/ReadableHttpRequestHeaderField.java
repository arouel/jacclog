package net.sf.jacclog.api.domain.http;

/**
 * Defines the root interface of a Hypertext Transfer Protocol (HTTP) request header field consisting of a type and
 * value.<br>
 * <br>
 * The implementation of this interface may be mutable or immutable. This interface only gives access to retrieve data,
 * never to change it.<br>
 * <br>
 * HTTP header fields are components of the message header of requests and responses in the Hypertext Transfer Protocol
 * (HTTP). They define the operating parameters of an HTTP transaction.<br>
 * <br>
 * The request-header fields allow the client to pass additional information about the request, and about the client
 * itself, to the server. These fields act as request modifiers, with semantics equivalent to the parameters on a
 * programming language method invocation. <br>
 * <br>
 * <strong>Example HTTP request (header fields are italic)</strong>
 * 
 * <pre>
 * GET /infotext.html HTTP/1.1
 * <i>Host: jacclog.sf.net
 * Cookie: name1=value1; name2=value2</i>
 * </pre>
 * 
 * @author André Rouél
 */
public interface ReadableHttpRequestHeaderField {

	/**
	 * Gets the type of the request header which is never <code>null</code>.
	 * 
	 * @return the type of the request header
	 */
	public ReadableHttpRequestHeader getType();

	/**
	 * Gets the value of the request header which is never <code>null</code>.
	 * 
	 * @return the value of the request header
	 */
	public String getValue();

}
