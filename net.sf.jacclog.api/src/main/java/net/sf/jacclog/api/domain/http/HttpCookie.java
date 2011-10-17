package net.sf.jacclog.api.domain.http;

/**
 * HttpCookie is the standard implementation of an immutable Hypertext Transfer Protocol (HTTP) cookie.
 * 
 * @author André Rouél
 */
public class HttpCookie {

	/**
	 * The value of the Comment attribute allows an origin server to document how it intends to use the cookie. The user
	 * can inspect the information to decide whether to initiate or continue a session with this cookie. Characters in
	 * value MUST be in UTF-8 encoding. [<a href="http://tools.ietf.org/html/rfc2279">RFC2279</a>]<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 */
	private String comment;

	/**
	 * The CommentURL attribute allows an origin server to document how it intends to use the cookie. The user can
	 * inspect the information identified by the URL to decide whether to initiate or continue a session with this
	 * cookie.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 */
	private String commentUrl;

	/**
	 * The Discard attribute instructs the user agent to discard the cookie unconditionally when the user agent
	 * terminates.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 */
	private boolean discard;

	/**
	 * The value of the Domain attribute specifies the domain for which the cookie is valid. If an explicitly specified
	 * value does not start with a dot, the user agent supplies a leading dot.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 */
	private String domain;

	/**
	 * The value of the Max-Age attribute is delta-seconds, the lifetime of the cookie in seconds, a decimal
	 * non-negative integer. To handle cached cookies correctly, a client SHOULD calculate the age of the cookie
	 * according to the age calculation rules in the HTTP/1.1 specification [<a
	 * href="http://tools.ietf.org/html/rfc2616">RFC2616</a>].<br>
	 * When the age is greater than delta-seconds seconds, the client SHOULD discard the cookie. A value of zero means
	 * the cookie SHOULD be discarded immediately.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 */
	private long maxAge;

	/**
	 * The value of the Path attribute specifies the subset of URLs on the origin server to which this cookie applies.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 */
	private String path;

	/**
	 * The Port attribute restricts the port to which a cookie may be returned in a Cookie request header. Note that the
	 * syntax REQUIREs quotes around the OPTIONAL portlist even if there is only one port number in portlist.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 */
	private String port;

	/**
	 * The Secure attribute (with no value) directs the user agent to use only (unspecified) secure means to contact the
	 * origin server whenever it sends back this cookie, to protect the confidentially and authenticity of the
	 * information in the cookie.
	 * 
	 * The user agent (possibly with user interaction) MAY determine what level of security it considers appropriate for
	 * "secure" cookies. The Secure attribute should be considered security advice from the server to the user agent,
	 * indicating that it is in the session's interest to protect the cookie contents. When it sends a "secure" cookie
	 * back to a server, the user agent SHOULD use no less than the same level of security as was used when it received
	 * the cookie from the server.<br>
	 * <br>
	 * This attribute is optional according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 */
	private boolean secure;

	/**
	 * The value of the Version attribute, a decimal integer, identifies the version of the state management
	 * specification to which the cookie conforms. For this specification, Version=1 applies.<br>
	 * <br>
	 * This attribute is required according to <a href="http://tools.ietf.org/html/rfc2965">RFC2965</a>.
	 */
	private int version;

	public String getComment() {
		return comment;
	}

	public String getCommentUrl() {
		return commentUrl;
	}

	public String getDomain() {
		return domain;
	}

	public long getMaxAge() {
		return maxAge;
	}

	public String getPath() {
		return path;
	}

	public String getPort() {
		return port;
	}

	public int getVersion() {
		return version;
	}

	public boolean isDiscard() {
		return discard;
	}

	public boolean isSecure() {
		return secure;
	}

}
