package net.sf.jacclog.api.domain.http;

/**
 * Defines the root interface of a Hypertext Transfer Protocol (HTTP) response header field consisting of a type and
 * value.<br>
 * <br>
 * The implementation of this interface may be mutable or immutable. This interface only gives access to retrieve data,
 * never to change it.<br>
 * <br>
 * HTTP header fields are components of the message header of requests and responses in the Hypertext Transfer Protocol
 * (HTTP). They define the operating parameters of an HTTP transaction.<br>
 * <br>
 * The response-header fields allow the server to pass additional information about the response which cannot be placed
 * in the Status- Line. These header fields give information about the server and about further access to the resource
 * identified by the Request-URI.<br>
 * <br>
 * After receiving and interpreting a request message, a server responds with an HTTP response message. <br>
 * <br>
 * <strong>Example HTTP response (header fields are italic)</strong>
 * 
 * <pre>
 * HTTP/1.1 200 OK
 * <i>Server: Apache/1.3.29 (Unix) PHP/4.3.4
 * Content-Length: 1234
 * Content-Language: de
 * Connection: close
 * Content-Type: text/html
 * 
 * (content of a resource)</i>
 * </pre>
 * 
 * @author André Rouél
 */
public interface ReadableHttpResponseHeaderField {

	/**
	 * Gets the type of the response header which is never <code>null</code>.
	 * 
	 * @return the type of the response header
	 */
	public ReadableHttpResponseHeader getType();

	/**
	 * Gets the value of the response header which is never <code>null</code>.
	 * 
	 * @return the value of the response header
	 */
	public String getValue();

}
