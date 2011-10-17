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
 * The set of common methods for Hypertext Transfer Protocol version 1.1 (HTTP/1.1).<br>
 * <br>
 * More informations can be found under {@link http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html}.
 * 
 * @author André Rouél
 */
public enum HttpRequestMethod {

	/**
	 * The GET method means retrieve whatever information (in the form of an entity) is identified by the Request-URI.
	 */
	GET("GET"),

	/**
	 * The POST method is used to request that the origin server accept the entity enclosed in the request as a new
	 * subordinate of the resource identified by the Request-URI in the Request-Line.
	 */
	POST("POST"),

	/**
	 * The HEAD method is identical to GET except that the server MUST NOT return a message-body in the response. The
	 * metainformation contained in the HTTP headers in response to a HEAD request SHOULD be identical to the
	 * information sent in response to a GET request.
	 */
	HEAD("HEAD"),

	/**
	 * The PUT method requests that the enclosed entity be stored under the supplied Request-URI.
	 */
	PUT("PUT"),

	/**
	 * The DELETE method requests that the origin server delete the resource identified by the Request-URI. This method
	 * MAY be overridden by human intervention (or other means) on the origin server.
	 */
	DELETE("DELETE"),

	/**
	 * The TRACE method is used to invoke a remote, application-layer loop-back of the request message.
	 */
	TRACE("TRACE"),

	/**
	 * The OPTIONS method represents a request for information about the communication options available on the
	 * request/response chain identified by the Request-URI.
	 */
	OPTIONS("OPTIONS"),

	/**
	 * This specification reserves the method name CONNECT for use with a proxy that can dynamically switch to being a
	 * tunnel (e.g. SSL tunneling).
	 */
	CONNECT("CONNECT"),

	/**
	 * Unknown HTTP request method<br>
	 * <br>
	 * This field is not a valid HTTP request method. It represents an invalid or unknown state.<br>
	 * <br>
	 * If the HTTP request method not been logged, this field should be used.
	 */
	UNKNOWN("-");

	/**
	 * Evaluates the given string against the standardized HTTP request methods.<br>
	 * <br>
	 * If the input value not matches against defined fields the <code>HttpRequestMethod.UNKNOWN</code> will be
	 * returned.
	 * 
	 * @param requestMethod
	 *            the textual value of a HTTP request method
	 * @return the matching HTTP request method field or <code>UNKNOWN</code>
	 */
	public static HttpRequestMethod evaluate(final String requestMethod) {
		HttpRequestMethod result = HttpRequestMethod.UNKNOWN;
		for (final HttpRequestMethod method : values()) {
			if (method.getName().equals(requestMethod)) {
				result = method;
				break;
			}
		}
		return result;
	}

	/**
	 * Checks a passed numeric value if it is a valid HTTP request method.
	 * 
	 * @param requestMethod
	 *            A textual value, possibly a request method
	 * @return <code>true</code> if it is a valid method, otherwise <code>false</code>
	 */
	public static boolean isValid(final String requestMethod) {
		boolean result = false;
		if (requestMethod != null && HttpRequestMethod.UNKNOWN.getName() != requestMethod) {
			for (final HttpRequestMethod method : values()) {
				if (method.equals(requestMethod)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * The textual value of a HTTP request method
	 */
	private final String method;

	HttpRequestMethod(final String requestMethod) {
		method = requestMethod;
	}

	/**
	 * Gets the name (as textual value) of the HTTP request method which can never be <code>null</code>.
	 * 
	 * @return the name of the request method
	 */
	public String getName() {
		return method;
	}

}
