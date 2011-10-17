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
package net.sf.jacclog.api.domain.http;

/**
 * Defines the type of a Hypertext Transfer Protocol (HTTP) request header field.
 * 
 * @author André Rouél
 */
public enum HttpRequestHeader implements ReadableHttpRequestHeader {

	/**
	 * Content-Types that are acceptable by the client<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Accept: text/plain</code>
	 */
	ACCEPT("Accept"),

	/**
	 * Character sets that are acceptable by the client<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Accept-Charset: utf-8</code>
	 */
	ACCEPT_CHARSET("Accept-Charset"),

	/**
	 * Acceptable encodings by the client<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Accept-Encoding: <compress | gzip | deflate | sdch | identity></code>
	 */
	ACCEPT_ENCODING("Accept-Encoding"),

	/**
	 * Acceptable languages for response by the client<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Accept-Language: en-US</code>
	 */
	ACCEPT_LANGUAGE("Accept-Language"),

	/**
	 * Authentication credentials for HTTP authentication<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==</code>
	 */
	AUTHORIZATION("Authorization"),

	/**
	 * The Cache-Control general-header field specifies directives that MUST be obeyed by all caching mechanisms along
	 * the request/response chain. The directives specify behavior intended to prevent caches from adversely interfering
	 * with the request or response.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Cache-Control: no-cache</code>
	 */
	CACHE_CONTROL("Cache-Control"),

	/**
	 * The Connection general-header field allows the sender to specify options that are desired for that particular
	 * connection and MUST NOT be communicated by proxies over further connections.<br>
	 * <br>
	 * In other words, it specifies the type of connection the user-agent would prefer.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Connection: close</code>
	 */
	CONNECTION("Connection"),

	/**
	 * Cookie<br>
	 * <br>
	 * An HTTP cookie previously sent by the server with Set-Cookie.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Cookie: $Version=1; Skin=new;</code>
	 */
	COOKIE("Cookie"),

	/**
	 * Content-Length<br>
	 * <br>
	 * The length of the request body in octets (8-bit bytes)<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Content-Length: 348</code>
	 */
	CONTENT_LENGTH("Content-Length"),

	/**
	 * A Base64-encoded binary MD5 sum of the content of the request body<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Content-MD5: Q2hlY2sgSW50ZWdyaXR5IQ==</code>
	 */
	CONTENT_MD5("Content-MD5"),

	/**
	 * The mime type of the body of the request (used with POST and PUT requests)<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Content-Type: application/x-www-form-urlencoded</code>
	 */
	CONTENT_TYPE("Content-Type"),

	/**
	 * The date and time that the message was sent<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Date: Tue, 15 Nov 1994 08:12:31 GMT</code>
	 */
	DATE("Date"),

	/**
	 * Indicates that particular server behaviors are required by the client<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Expect: 100-continue</code>
	 */
	EXPECT("Expect"),

	/**
	 * The email address of the user making the request<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>From: user@example.com</code>
	 */
	FROM("From"),

	/**
	 * The domain name of the server (for virtual hosting), mandatory since HTTP/1.1<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Host: en.wikipedia.org</code>
	 */
	HOST("Host"),

	/**
	 * Only perform the action if the client supplied entity matches the same entity on the server. This is mainly for
	 * methods like PUT to only update a resource if it has not been modified since the user last updated it.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>If-Match: "737060cd8c284d8af7ad3082f209582d"</code>
	 */
	IF_MATCH("If-Match"),

	/**
	 * Allows a 304 Not Modified to be returned if content is unchanged<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>If-Modified-Since: Sat, 29 Oct 1994 19:43:31 GMT</code>
	 */
	IF_MODIFIED_SINCE("If-Modified-Since"),

	/**
	 * Allows a 304 Not Modified to be returned if content is unchanged, see HTTP ETag<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>If-None-Match: "737060cd8c284d8af7ad3082f209582d"</code>
	 */
	IF_NONE_MATCH("If-None-Match"),

	/**
	 * If the entity is unchanged, send me the part(s) that I am missing; otherwise, send me the entire new entity<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>If-Range: "737060cd8c284d8af7ad3082f209582d"</code>
	 */
	IF_RANGE("If-Range"),

	/**
	 * Only send the response if the entity has not been modified since a specific time.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>If-Unmodified-Since: Sat, 29 Oct 1994 19:43:31 GMT</code>
	 */
	IF_UNMODIFIED_SINCE("If-Unmodified-Since"),

	/**
	 * Max-Forwards<br>
	 * <br>
	 * Limit the number of times the message can be forwarded through proxies or gateways.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Max-Forwards: 10</code>
	 */
	MAX_FORWARDS("Max-Forwards"),

	/**
	 * Implementation-specific headers that may have various effects anywhere along the request-response chain.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Pragma: no-cache</code>
	 */
	PRAGMA("Pragma"),

	/**
	 * Authorization credentials for connecting to a proxy.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Proxy-Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==</code>
	 */
	PROXY_AUTHORIZATION("Proxy-Authorization"),

	/**
	 * Request only part of an entity. Bytes are numbered from <code>0</code>.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Range: bytes=500-999</code>
	 */
	RANGE("Range"),

	/**
	 * This is the address of the previous web page from which a link to the currently requested page was followed. (The
	 * word “referrer” is misspelled in the RFC as well as in most implementations.)<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Referer: http://en.wikipedia.org/wiki/Main_Page</code>
	 */
	REFERER("Referer"),

	/**
	 * The transfer encodings the user agent is willing to accept: the same values as for the response header
	 * Transfer-Encoding can be used, plus the "trailers" value (related to the "chunked" transfer method) to notify the
	 * server it accepts to receive additional headers (the trailers) after the last, zero-sized, chunk.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>TE: trailers, deflate</code>
	 */
	TE("TE"),

	/**
	 * Ask the server to upgrade to another protocol.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Upgrade: HTTP/2.0, SHTTP/1.3, IRC/6.9, RTA/x11</code>
	 */
	UPGRADE("Upgrade"),

	/**
	 * The user agent reported by the client. Typically, this is a string describing the type and version of browser
	 * software being used.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>User-Agent: Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)</code>
	 */
	USER_AGENT("User-Agent"),

	/**
	 * Informs the server of proxies through which the request was sent.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Via: 1.0 fred, 1.1 nowhere.com (Apache/1.1)</code>
	 */
	VIA("Via"),

	/**
	 * A general warning about possible problems with the entity body.<br>
	 * <br>
	 * <strong>Example:</strong><br>
	 * <code>Warning: 199 Miscellaneous warning</code>
	 */
	WARNING("Warning");

	/**
	 * Evaluates the given string against the available enumeration constants. If a field matches against the input
	 * string, the constant will be returned otherwise an <code>UnknownHttpRequestHeader</code> will be created and
	 * returned.<br>
	 * <br>
	 * This function checks the equality by ignoring the case of the given string.
	 * 
	 * @param headerName
	 *            the header field name
	 * @return the matching field of <code>HttpRequestHeader</code> or an instance of
	 *         <code>UnknownHttpRequestHeader</code>, but never <code>null</code>
	 */
	public static ReadableHttpRequestHeader evaluate(final String headerName) {
		ReadableHttpRequestHeader result = null;
		for (final HttpRequestHeader name : values()) {
			if (name.getName().equalsIgnoreCase(headerName)) {
				result = name;
				break;
			}
		}
		if (result == null) {
			result = new UnknownHttpRequestHeader(headerName);
		}
		return result;
	}

	/**
	 * The name of the HTTP request header field
	 */
	private final String field;

	private HttpRequestHeader(final String field) {
		this.field = field;
	}

	/**
	 * Gets the name (as textual value) of the HTTP request header which can never be <code>null</code>.
	 * 
	 * @return the name of the request header
	 */
	@Override
	public String getName() {
		return field;
	}

}
