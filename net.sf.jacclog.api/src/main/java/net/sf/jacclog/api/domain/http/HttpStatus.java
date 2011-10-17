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
 * Defines status codes for the Hypertext Transfer Protocol (HTTP).<br>
 * <br>
 * All status codes defined in RFC1945 (HTTP/1.0), RFC2616 (HTTP/1.1), and RFC2518 (WebDAV) are listed.
 */
public enum HttpStatus {

	/** <tt>100 Continue</tt> (HTTP/1.1 - RFC 2616) */
	CONTINUE(100),
	/** <tt>101 Switching Protocols</tt> (HTTP/1.1 - RFC 2616) */
	SWITCHING_PROTOCOLS(101),
	/** <tt>102 Processing</tt> (WebDAV - RFC 2518) */
	PROCESSING(102),

	/** <tt>200 OK</tt> (HTTP/1.0 - RFC 1945) */
	OK(200),
	/** <tt>201 Created</tt> (HTTP/1.0 - RFC 1945) */
	CREATED(201),
	/** <tt>202 Accepted</tt> (HTTP/1.0 - RFC 1945) */
	ACCEPTED(202),
	/** <tt>203 Non Authoritative Information</tt> (HTTP/1.1 - RFC 2616) */
	NON_AUTHORITATIVE_INFORMATION(203),
	/** <tt>204 No Content</tt> (HTTP/1.0 - RFC 1945) */
	NO_CONTENT(204),
	/** <tt>205 Reset Content</tt> (HTTP/1.1 - RFC 2616) */
	RESET_CONTENT(205),
	/** <tt>206 Partial Content</tt> (HTTP/1.1 - RFC 2616) */
	PARTIAL_CONTENT(206),
	/**
	 * <tt>207 Multi-Status</tt> (WebDAV - RFC 2518) or <tt>207 Partial Update
	 * OK</tt> (HTTP/1.1 - draft-ietf-http-v11-spec-rev-01?)
	 */
	MULTI_STATUS(207),

	/** <tt>300 Mutliple Choices</tt> (HTTP/1.1 - RFC 2616) */
	MULTIPLE_CHOICES(300),
	/** <tt>301 Moved Permanently</tt> (HTTP/1.0 - RFC 1945) */
	MOVED_PERMANENTLY(301),
	/** <tt>302 Moved Temporarily</tt> (Sometimes <tt>Found</tt>) (HTTP/1.0 - RFC 1945) */
	MOVED_TEMPORARILY(302),
	/** <tt>303 See Other</tt> (HTTP/1.1 - RFC 2616) */
	SEE_OTHER(303),
	/** <tt>304 Not Modified</tt> (HTTP/1.0 - RFC 1945) */
	NOT_MODIFIED(304),
	/** <tt>305 Use Proxy</tt> (HTTP/1.1 - RFC 2616) */
	USE_PROXY(305),
	/** <tt>307 Temporary Redirect</tt> (HTTP/1.1 - RFC 2616) */
	TEMPORARY_REDIRECT(307),

	/** <tt>400 Bad Request</tt> (HTTP/1.1 - RFC 2616) */
	BAD_REQUEST(400),
	/** <tt>401 Unauthorized</tt> (HTTP/1.0 - RFC 1945) */
	UNAUTHORIZED(401),
	/** <tt>402 Payment Required</tt> (HTTP/1.1 - RFC 2616) */
	PAYMENT_REQUIRED(402),
	/** <tt>403 Forbidden</tt> (HTTP/1.0 - RFC 1945) */
	FORBIDDEN(403),
	/** <tt>404 Not Found</tt> (HTTP/1.0 - RFC 1945) */
	NOT_FOUND(404),
	/** <tt>405 Method Not Allowed</tt> (HTTP/1.1 - RFC 2616) */
	METHOD_NOT_ALLOWED(405),
	/** <tt>406 Not Acceptable</tt> (HTTP/1.1 - RFC 2616) */
	NOT_ACCEPTABLE(406),
	/** <tt>407 Proxy Authentication Required</tt> (HTTP/1.1 - RFC 2616) */
	PROXY_AUTHENTICATION_REQUIRED(407),
	/** <tt>408 Request Timeout</tt> (HTTP/1.1 - RFC 2616) */
	REQUEST_TIMEOUT(408),
	/** <tt>409 Conflict</tt> (HTTP/1.1 - RFC 2616) */
	CONFLICT(409),
	/** <tt>410 Gone</tt> (HTTP/1.1 - RFC 2616) */
	GONE(410),
	/** <tt>411 Length Required</tt> (HTTP/1.1 - RFC 2616) */
	LENGTH_REQUIRED(411),
	/** <tt>412 Precondition Failed</tt> (HTTP/1.1 - RFC 2616) */
	PRECONDITION_FAILED(412),
	/** <tt>413 Request Entity Too Large</tt> (HTTP/1.1 - RFC 2616) */
	REQUEST_TOO_LONG(413),
	/** <tt>414 Request-URI Too Long</tt> (HTTP/1.1 - RFC 2616) */
	REQUEST_URI_TOO_LONG(414),
	/** <tt>415 Unsupported Media Type</tt> (HTTP/1.1 - RFC 2616) */
	UNSUPPORTED_MEDIA_TYPE(415),
	/** <tt>416 Requested Range Not Satisfiable</tt> (HTTP/1.1 - RFC 2616) */
	REQUESTED_RANGE_NOT_SATISFIABLE(416),
	/** <tt>417 Expectation Failed</tt> (HTTP/1.1 - RFC 2616) */
	EXPECTATION_FAILED(417),
	/**
	 * Static constant for a 419 error. <tt>419 Insufficient Space on Resource</tt> (WebDAV -
	 * draft-ietf-webdav-protocol-05?) or <tt>419 Proxy Reauthentication Required</tt> (HTTP/1.1 drafts?)
	 */
	INSUFFICIENT_SPACE_ON_RESOURCE(419),
	/**
	 * Static constant for a 420 error. <tt>420 Method Failure</tt> (WebDAV - draft-ietf-webdav-protocol-05?)
	 */
	METHOD_FAILURE(420),
	/** <tt>422 Unprocessable Entity</tt> (WebDAV - RFC 2518) */
	UNPROCESSABLE_ENTITY(422),
	/** <tt>423 Locked</tt> (WebDAV - RFC 2518) */
	LOCKED(423),
	/** <tt>424 Failed Dependency</tt> (WebDAV - RFC 2518) */
	FAILED_DEPENDENCY(424),

	/** <tt>500 Server Error</tt> (HTTP/1.0 - RFC 1945) */
	INTERNAL_SERVER_ERROR(500),
	/** <tt>501 Not Implemented</tt> (HTTP/1.0 - RFC 1945) */
	NOT_IMPLEMENTED(501),
	/** <tt>502 Bad Gateway</tt> (HTTP/1.0 - RFC 1945) */
	BAD_GATEWAY(502),
	/** <tt>503 Service Unavailable</tt> (HTTP/1.0 - RFC 1945) */
	SERVICE_UNAVAILABLE(503),
	/** <tt>504 Gateway Timeout</tt> (HTTP/1.1 - RFC 2616) */
	GATEWAY_TIMEOUT(504),
	/** <tt>505 HTTP Version Not Supported</tt> (HTTP/1.1 - RFC 2616) */
	HTTP_VERSION_NOT_SUPPORTED(505),

	/** <tt>507 Insufficient Storage</tt> (WebDAV - RFC 2518) */
	INSUFFICIENT_STORAGE(507),

	/**
	 * Unknown HTTP status code<br>
	 * <br>
	 * This field is not a valid HTTP status code. It represents an invalid or unknown state.<br>
	 * <br>
	 * If the HTTP status has not been logged, this field should be used.
	 */
	UNKNOWN(0);

	/**
	 * Evaluates the given numeric value against the defined HTTP status codes.<br>
	 * <br>
	 * If the input value not matches against defined fields the <code>HttpStatus.UNKNOWN</code> will be returned.
	 * 
	 * @param statusCode
	 *            the numeric value of a HTTP status
	 * @return the matching HTTP status code field or <code>UNKNOWN</code>
	 */
	public static HttpStatus evaluate(final int statusCode) {
		HttpStatus result = HttpStatus.UNKNOWN;
		for (final HttpStatus status : values()) {
			if (status.value() == statusCode) {
				result = status;
				break;
			}
		}
		return result;
	}

	/**
	 * Checks a passed numeric value if it is a valid status code.
	 * 
	 * @param statusCode
	 *            A numeric value, possibly a status code
	 * @return <code>true</code> if it is a valid code, otherwise <code>false</code>
	 */
	public static boolean isValid(final int statusCode) {
		boolean result = false;
		if (HttpStatus.UNKNOWN.value() != statusCode) {
			for (final HttpStatus status : values()) {
				if (status.value() == statusCode) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * The numeric value of a HTTP status code
	 */
	private final int status;

	HttpStatus(final int status) {
		this.status = status;
	}

	HttpStatus(final String status) {
		this(Integer.parseInt(status));
	}

	/**
	 * Gets the HTTP status code as numeric value.
	 * 
	 * @return A HTTP status code
	 */
	public int value() {
		return status;
	}

}
