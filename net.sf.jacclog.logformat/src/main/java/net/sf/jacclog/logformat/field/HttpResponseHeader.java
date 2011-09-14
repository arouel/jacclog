/*******************************************************************************
 * Copyright 2011 André Rouél
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.jacclog.logformat.field;

/**
 * HTTP header fields are components of the message header of requests and responses in the Hypertext Transfer Protocol
 * (HTTP). They define the operating parameters of an HTTP transaction.
 * 
 * @author André Rouél
 */
public enum HttpResponseHeader implements Parameter<HttpResponseHeader> {

	/**
	 * What partial content range types this server supports<br>
	 * <code>Accept-Ranges: bytes</code>
	 */
	ACCEPT_RANGES("Accept-Ranges"),
	/**
	 * The age the object has been in a proxy cache in seconds<br>
	 * <code>Age: 12</code>
	 */
	AGE("Age"),

	/**
	 * Valid actions for a specified resource. To be used for a 405 Method not allowed<br>
	 * <code>Allow: GET, HEAD</code>
	 */
	ALLOW("Allow"),

	/**
	 * Tells all caching mechanisms from server to client whether they may cache this object<br>
	 * <code>Cache-Control: max-age=3600</code>
	 */
	CACHE_CONTROL("Cache-Control"),

	/**
	 * Options that are desired for the connection[4]<br>
	 * <code>Connection: close</code>
	 */
	CONNECTION("Connection"),

	/**
	 * The type of encoding used on the data<br>
	 * <code>Content-Encoding: gzip</code>
	 */
	CONTENT_ENCODING("Content-Encoding"),

	/**
	 * The language the content is in<br>
	 * <code>Content-Language: da</code>
	 */
	CONTENT_LANGUAGE("Content-Language"),

	/**
	 * The length of the response body in octets (8-bit bytes)<br>
	 * <code>Content-Length: 348</code>
	 */
	CONTENT_LENGTH("Content-Length"),

	/**
	 * An alternate location for the returned data<br>
	 * <code>Content-Location: /index.htm</code>
	 */
	CONTENT_LOCATION("Content-Location"),

	/**
	 * A Base64-encoded binary MD5 sum of the content of the response<br>
	 * <code>Content-MD5: Q2hlY2sgSW50ZWdyaXR5IQ==</code>
	 */
	CONTENT_MD5("Content-MD5"),

	/**
	 * An opportunity to raise a "File Download" dialogue box for a known MIME type<br>
	 * <code>Content-Disposition: attachment; filename=fname.ext</code>
	 */
	CONTENT_DISPOSITION("Content-Disposition"),

	/**
	 * Where in a full body message this partial message belongs<br>
	 * <code>Content-Range: bytes 21010-47021/47022</code>
	 */
	CONTENT_RANGE("Content-Range"),

	/**
	 * The mime type of this content<br>
	 * <code>Content-Type: text/html; charset=utf-8</code>
	 */
	CONTENT_TYPE("Content-Type"),

	/**
	 * The date and time that the message was sent<br>
	 * <code>Date: Tue, 15 Nov 1994 08:12:31 GMT</code>
	 */
	DATE("Date"),

	/**
	 * An identifier for a specific version of a resource, often a Message Digest, see ETag<br>
	 * <code>ETag: "737060cd8c284d8af7ad3082f209582d"</code>
	 */
	ETAG("ETag"),

	/**
	 * Gives the date/time after which the response is considered stale<br>
	 * <code>Expires: Thu, 01 Dec 1994 16:00:00 GMT</code>
	 */
	EXPIRES("Expires"),

	/**
	 * The last modified date for the requested object, in RFC 2822 format<br>
	 * <code>Last-Modified: Tue, 15 Nov 1994 12:45:26 GMT</code>
	 */
	LAST_MODIFIED("Last-Modified"),

	/**
	 * Used to express a typed relationship with another resource, where the relation type is defined by RFC 5988<br>
	 * <code>Link: </feed>; rel="alternate"</code>
	 */
	LINK("Link"),

	/**
	 * Used in redirection, or when a new resource has been created.<br>
	 * <code>Location: http://www.w3.org/pub/WWW/People.html</code>
	 */
	LOCATION("Location"),

	/**
	 * This header is supposed to set P3P policy, in the form of P3P:CP="your_compact_policy". However, P3P did not take
	 * off,[5] most browsers have never fully implemented it, a lot of websites set this header with fake policy text,
	 * that was enough to fool browsers the existence of P3P policy and grant permissions for third party cookies.<br>
	 * <code>P3P: CP="This is not a P3P policy! See http://www.google.com/support/accounts/bin/answer.py?hl=en&answer=151657 for more info."</code>
	 */
	P3P("P3P"),

	/**
	 * Implementation-specific headers that may have various effects anywhere along the request-response chain.<br>
	 * <code>Pragma: no-cache</code>
	 */
	PRAGMA("Pragma"),

	/**
	 * Request authentication to access the proxy.<br>
	 * <code>Proxy-Authenticate: Basic</code>
	 */
	PROXY_AUTHENTICATE("Proxy-Authenticate"),

	/**
	 * Used in redirection, or when a new resource has been created. This refresh redirects after 5 seconds.</code> <br>
	 * <br>
	 * (This is a proprietary/non-standard header extension introduced by Netscape and supported by most web browsers.)<br>
	 * <code>Refresh: 5; url=http://www.w3.org/pub/WWW/People.html</code>
	 */
	REFRESH("Refresh"),

	/**
	 * If an entity is temporarily unavailable, this instructs the client to try again after a specified period of time.<br>
	 * <code>Retry-After: 120</code>
	 */
	RETRY_AFTER("Retry-After"),

	/**
	 * A name for the server<br>
	 * <code>Server: Apache/1.3.27 (Unix) (Red-Hat/Linux)</code>
	 */
	SERVER("Server"),

	/**
	 * An HTTP cookie<br>
	 * <code>Set-Cookie: UserID=JohnDoe; Max-Age=3600; Version=1</code>
	 */
	SET_COOKIE("Set-Cookie"),

	/**
	 * A HSTS Policy informing the HTTP client how long to cache the HTTPS only policy and whether this applies to
	 * subdomains.<br>
	 * <code>Strict-Transport-Security: max-age=16070400; includeSubDomains</code>
	 */
	STRICT_TRANSPORT_SECURITY("Strict-Transport-Security"),

	/**
	 * The Trailer general field value indicates that the given set of header fields is present in the trailer of a
	 * message encoded with chunked transfer-coding.<br>
	 * <code>Trailer: Max-Forwards</code>
	 */
	TRAILER("Trailer"),

	/**
	 * Transfer-Encoding<br>
	 * <code>The form of encoding used to safely transfer the entity to the user. Currently defined methods are: chunked, compress, deflate, gzip, identity.<br><code>Transfer-Encoding: chunked</code>
	 */
	TRANSFER_ENCODING("Transfer-Encoding"),

	/**
	 * Tells downstream proxies how to match future request headers to decide whether the cached response can be used
	 * rather than requesting a fresh one from the origin server.<br>
	 * <code>Vary: *</code>
	 */
	VARY("Vary"),

	/**
	 * Informs the client of proxies through which the response was sent.<br>
	 * <code>Via: 1.0 fred, 1.1 nowhere.com (Apache/1.1)</code>
	 */
	VIA("Via"),

	/**
	 * A general warning about possible problems with the entity body.<br>
	 * <code>Warning: 199 Miscellaneous warning</code>
	 */
	WARNING("Warning"),

	/**
	 * Indicates the authentication scheme that should be used to access the requested entity.<br>
	 * <code>WWW-Authenticate: Basic</code>
	 */
	WWW_AUTHENTICATE("WWW-Authenticate");

	/**
	 * Evaluates the field name against all available Enumeration-Constants and return the appropriate field.
	 * 
	 * @param field
	 * @return <code>HttpResponseHeader</code> if valid, otherwise <code>null</code>
	 */
	public static HttpResponseHeader evaluate(final String field) {
		HttpResponseHeader result = null;
		for (final HttpResponseHeader name : values()) {
			if (name.value().equals(field)) {
				result = name;
				break;
			}
		}
		return result;
	}

	/**
	 * Name of HTTP response header field
	 */
	private final String field;

	private HttpResponseHeader(final String field) {
		this.field = field;
	}

	/**
	 * Gives itself back.
	 * 
	 * @return itself
	 */
	@Override
	public HttpResponseHeader getParameter() {
		return this;
	}

	/**
	 * Returns the value of this Enumeration-Constant.
	 * 
	 * @return value of constant
	 */
	public String value() {
		return field;
	}

}