package net.sf.jacclog.api.domain.http;

/**
 * Defines the root interface of a Hypertext Transfer Protocol (HTTP) cookie.<br>
 * <br>
 * The implementation of this interface may be mutable or immutable. This interface only gives access to retrieve data,
 * never to change it.
 * 
 * @author André Rouél
 */
public interface ReadableHttpCookie {

	/**
	 * Gets the value of the Comment attribute that allows an origin server to document how it intends to use the
	 * cookie.<br>
	 * <br>
	 * The user can inspect the information to decide whether to initiate or continue a session with this cookie.
	 * Characters in value MUST be in UTF-8 encoding. [<a href="http://tools.ietf.org/html/rfc2279">RFC2279</a>]<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 * 
	 * @return the comment or <code>null</code>
	 */
	String getComment();

	/**
	 * Gets the CommentURL attribute that allows an origin server to document how it intends to use the cookie.<br>
	 * <br>
	 * The user can inspect the information identified by the URL to decide whether to initiate or continue a session
	 * with this cookie.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 * 
	 * @return the comment URL or <code>null</code>
	 */
	String getCommentUrl();

	/**
	 * Gets the state of the Discard attribute.<br>
	 * <br>
	 * It instructs the user agent to discard the cookie unconditionally when the user agent terminates.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 */
	boolean isDiscard();

	/**
	 * Gets the value of the Domain attribute that specifies the domain for which the cookie is valid. If an explicitly
	 * specified value does not start with a dot, the user agent supplies a leading dot.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 * 
	 * @return the domain or <code>null</code>
	 */
	String getDomain();

	/**
	 * Gets the value of the Max-Age attribute.<br>
	 * <br>
	 * It defines the delta-seconds, specifically the lifetime of the cookie in seconds, and is a decimal non-negative
	 * integer.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 * 
	 * @return the maximum age or <code>null</code>
	 */
	Long getMaxAge();

	/**
	 * Gets the value of the Path attribute.<br>
	 * <br>
	 * It specifies the subset of URLs on the origin server to which this cookie applies.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 * 
	 * @return the path or <code>null</code>
	 */
	String getPath();

	/**
	 * Gets the value of the Port attribute that restricts the port to which a cookie may be returned in a Cookie
	 * request header.<br>
	 * <br>
	 * Note that it is possible to specify multiple ports and that a comma-separated string of ports can be returned.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 * 
	 * @return the specified port number(s) or <code>null</code>
	 */
	String getPort();

	/**
	 * Gets the state of the Secure attribute.<br>
	 * <br>
	 * It directs the user agent to use only (unspecified) secure means to contact the origin server whenever it sends
	 * back this cookie, to protect the confidentially and authenticity of the information in the cookie.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 */
	boolean isSecure();

	/**
	 * Gets the value of the Version attribute.<br>
	 * <br>
	 * It is a decimal integer that identifies the version of the state management specification to which the cookie
	 * conforms. For the RFC2965 specification Version=1 applies.<br>
	 * <br>
	 * This attribute is required according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 * 
	 * @return the version number, but never <code>null</code>
	 */
	Integer getVersion();

}
