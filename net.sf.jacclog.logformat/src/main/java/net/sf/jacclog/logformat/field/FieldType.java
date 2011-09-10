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

public enum FieldType {

	/**
	 * %...a Remote IP-address
	 */
	REMOTE_IP_ADDRESS("a", false),

	/**
	 * %...A Local IP-address
	 */
	LOCAL_IP_ADDRESS("A", false),

	/**
	 * %...B Size of response in bytes, excluding HTTP headers.
	 */
	RESPONSE_IN_BYTES("B", false),

	/**
	 * %...b Size of response in bytes, excluding HTTP headers. In CLF format, i.e. a '-' rather than a 0 when no bytes
	 * are sent.
	 */
	RESPONSE_IN_BYTES_CLF("b", false),

	/**
	 * %...{Foobar}C The contents of cookie Foobar in the request sent to the server.
	 */
	COOKIE("C", true),

	/**
	 * %...D The time taken to serve the request, in microseconds.
	 */
	REQUEST_IN_MILLIS("D", false),

	/**
	 * %...{FOOBAR}e The contents of the environment variable FOOBAR
	 */
	ENV_VAR("e", true),

	/**
	 * %...f Filename
	 */
	FILENAME("f", false),

	/**
	 * %...h Remote host
	 */
	REMOTE_HOST("h", false),

	/**
	 * %...H The request protocol
	 */
	REQUEST_PROTOCOL("H", false),

	/**
	 * %...{Foobar}i The contents of Foobar: header line(s) in the request sent to the server.
	 */
	REQUEST_HEADER_LINE("i", true),

	/**
	 * %...l Remote logname (from identd, if supplied). This will return a dash unless IdentityCheck is set On.
	 */
	REMOTE_LOGNAME("l", false),

	/**
	 * %...m The request method
	 */
	REQUEST_METHOD("m", false),

	/**
	 * %...{Foobar}n The contents of note Foobar from another module.
	 */
	MOD_CONTENTS("n", true),

	/**
	 * %...{Foobar}o The contents of Foobar: header line(s) in the reply.
	 */
	RESPONSE_HEADER_LINE("o", true),

	/**
	 * %...p The canonical port of the server serving the request
	 */
	SERVER_PORT("p", false),

	/**
	 * %...P The process ID of the child that serviced the request.
	 */
	PROCESS_ID("P", false),

	/**
	 * %...{format}P The process ID or thread id of the child that serviced the request. Valid formats are pid and tid.
	 * (Apache 2.0.46 and later)
	 */
	PID_OR_TID("P", true),

	/**
	 * %...q The query string (prepended with a ? if a query string exists, otherwise an empty string)
	 */
	QUERY_STRING("q", false),

	/**
	 * %...r First line of request
	 */
	REQUEST_FIRST_LINE("r", false),

	/**
	 * %...s Status. For requests that got internally redirected, this is the status of the *original* request ---
	 * %...>s for the last.
	 */
	STATUS_CODE("s", false),

	/**
	 * %...s Status for the last request
	 */
	LAST_STATUS_CODE(">s", false),

	/**
	 * %...t Time the request was received (standard english format)
	 */
	REQUEST_TIME("t", false),

	/**
	 * %...{format}t The time, in the form given by format, which should be in strftime(3) format. (potentially
	 * localized)
	 */
	REQUEST_TIME_CUSTOM("t", true),

	/**
	 * %...T The time taken to serve the request, in seconds.
	 */
	REQUEST_IN_SECONDS("T", false),

	/**
	 * %...u Remote user (from auth; may be bogus if return status (%s) is 401)
	 */
	REMOTE_USER("u", false),

	/**
	 * %...U The URL path requested, not including any query string.
	 */
	URL_PATH("U", false),

	/**
	 * %...v The canonical ServerName of the server serving the request.
	 */
	SERVERNAME_CANONICAL("v", false),

	/**
	 * %...V The server name according to the UseCanonicalName setting.
	 */
	SERVERNAME("V", false),

	/**
	 * %...X Connection status when response is completed:
	 * <ul>
	 * <li>X = connection aborted before the response completed.</li>
	 * <li>+ = connection may be kept alive after the response is sent.</li>
	 * <li>- = connection will be closed after the response is sent.</li>
	 * </ul>
	 */
	CONNECTION_STATUS("X", false),

	/**
	 * %...I Bytes received, including request and headers, cannot be zero. You need to enable mod_logio to use this.
	 */
	BYTES_RECEIVED("I", false),

	/**
	 * %...O Bytes sent, including headers, cannot be zero. You need to enable mod_logio to use this.
	 */
	BYTES_SEND("O", false),

	/**
	 * A common slot for ignorable fields, can be applied multiple times
	 */
	IGNORE("0", false);

	/**
	 * Evaluates the field name against all available Enumeration-Constants and return the appropriate field.
	 * 
	 * @param keyword
	 * @return <code>HttpRequestHeader</code> if valid, otherwise <code>null</code>
	 */
	public static FieldType evaluate(final String keyword, final boolean parameterized) {
		FieldType result = null;
		for (final FieldType name : values()) {
			if (name.getKeyword().equals(keyword) && name.parameterized == parameterized) {
				result = name;
				break;
			}
		}
		return result;
	}

	private final String keyword;

	/**
	 * Flag to indicate if a field is parameterized or not.
	 */
	private final boolean parameterized;

	FieldType(final String keyword, final boolean parameterized) {
		this.keyword = keyword;
		this.parameterized = parameterized;
	}

	/**
	 * Gets the keyword of this field.
	 * 
	 * @return
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * Indicates whether there is a parameterized field.
	 * 
	 * @return <code>true</code> if it is parameterized, otherwise <code>false</code>
	 */
	public boolean isParameterized() {
		return parameterized;
	}

}
