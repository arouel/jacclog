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
	 * Remote IP-address<br>
	 * <br>
	 * Placeholder: <code>%a</code>
	 */
	REMOTE_IP_ADDRESS("a", false),

	/**
	 * Local IP-address <br>
	 * <br>
	 * Placeholder: <code>%A</code>
	 */
	LOCAL_IP_ADDRESS("A", false),

	/**
	 * Size of response in bytes, excluding HTTP headers. <br>
	 * <br>
	 * Placeholder: <code>%B</code>
	 */
	RESPONSE_IN_BYTES("B", false),

	/**
	 * Size of response in bytes, excluding HTTP headers. In CLF format, i.e. a '-' rather than a 0 when no bytes are
	 * sent.<br>
	 * <br>
	 * Placeholder: <code>%b</code>
	 */
	RESPONSE_IN_BYTES_CLF("b", false),

	/**
	 * The contents of cookie Foobar in the request sent to the server.<br>
	 * <br>
	 * Placeholder: <code>%{Foobar}C</code>
	 */
	COOKIE("C", true),

	/**
	 * The time taken to serve the request, in microseconds.<br>
	 * <br>
	 * Placeholder: <code>%D</code>
	 */
	REQUEST_IN_MILLIS("D", false),

	/**
	 * The contents of the environment variable FOOBAR<br>
	 * <br>
	 * Placeholder: <code>%{FOOBAR}e</code>
	 */
	ENV_VAR("e", true),

	/**
	 * Filename<br>
	 * <br>
	 * Placeholder: <code>%f</code>
	 */
	FILENAME("f", false),

	/**
	 * Remote host<br>
	 * <br>
	 * Placeholder: <code>%h</code>
	 */
	REMOTE_HOST("h", false),

	/**
	 * The request protocol<br>
	 * <br>
	 * Placeholder: <code>%H</code>
	 */
	REQUEST_PROTOCOL("H", false),

	/**
	 * The contents of Foobar: header line(s) in the request sent to the server.<br>
	 * <br>
	 * Placeholder: <code>%{Foobar}i</code>
	 */
	REQUEST_HEADER_LINE("i", true),

	/**
	 * Remote logname (from identd, if supplied). This will return a dash unless IdentityCheck is set On.<br>
	 * <br>
	 * Placeholder: <code>%l</code>
	 */
	REMOTE_LOGNAME("l", false),

	/**
	 * The request method<br>
	 * <br>
	 * Placeholder: <code>%m</code>
	 */
	REQUEST_METHOD("m", false),

	/**
	 * The contents of note Foobar from another module.<br>
	 * <br>
	 * Placeholder: <code>%{Foobar}n</code>
	 */
	MOD_CONTENTS("n", true),

	/**
	 * The contents of Foobar: header line(s) in the reply.<br>
	 * <br>
	 * Placeholder: <code>%{Foobar}o</code>
	 */
	RESPONSE_HEADER_LINE("o", true),

	/**
	 * The canonical port of the server serving the request<br>
	 * <br>
	 * Placeholder: <code>%p</code>
	 */
	SERVER_PORT("p", false),

	/**
	 * The process ID of the child that serviced the request.<br>
	 * <br>
	 * Placeholder: <code>%P</code>
	 */
	PROCESS_ID("P", false),

	/**
	 * The process ID or thread id of the child that serviced the request. Valid formats are pid and tid. (Apache 2.0.46
	 * and later)<br>
	 * <br>
	 * Placeholder: <code>%{format}P</code>
	 */
	PID_OR_TID("P", true),

	/**
	 * The query string (prepended with a <code>?</code> if a query string exists, otherwise an empty string)<br>
	 * <br>
	 * Placeholder: <code>%q</code>
	 */
	QUERY_STRING("q", false),

	/**
	 * First line of request<br>
	 * <br>
	 * Placeholder: <code>%r</code>
	 */
	REQUEST_FIRST_LINE("r", false),

	/**
	 * Status. For requests that got internally redirected, this is the status of the *original* request --- %...>s for
	 * the last.<br>
	 * <br>
	 * Placeholder: <code>%s</code>
	 */
	STATUS_CODE("s", false),

	/**
	 * Status for the last request<br>
	 * <br>
	 * Will be interpreted as field <code>STATUS_CODE</code>.<br>
	 * <br>
	 * Placeholder: <code>%s</code>
	 */
	LAST_STATUS_CODE(">s", false),

	/**
	 * Time the request was received (standard english format)<br>
	 * <br>
	 * Placeholder: <code>%t</code>
	 */
	REQUEST_TIME("t", false),

	/**
	 * The time, in the form given by format, which should be in strftime(3) format. (potentially localized)<br>
	 * <br>
	 * Placeholder: <code>%{format}t</code>
	 */
	REQUEST_TIME_CUSTOM("t", true),

	/**
	 * The time taken to serve the request, in seconds.<br>
	 * <br>
	 * Placeholder: <code>%T</code>
	 */
	REQUEST_IN_SECONDS("T", false),

	/**
	 * Remote user (from auth; may be bogus if return status (%s) is 401)<br>
	 * <br>
	 * Placeholder: <code>%u</code>
	 */
	REMOTE_USER("u", false),

	/**
	 * The URL path requested, not including any query string.<br>
	 * <br>
	 * Placeholder: <code>%U</code>
	 */
	URL_PATH("U", false),

	/**
	 * The canonical ServerName of the server serving the request.<br>
	 * <br>
	 * Placeholder: <code>%v</code>
	 */
	SERVERNAME_CANONICAL("v", false),

	/**
	 * The server name according to the UseCanonicalName setting.<br>
	 * <br>
	 * Placeholder: <code>%V</code>
	 */
	SERVERNAME("V", false),

	/**
	 * Connection status when response is completed:
	 * <ul>
	 * <li>X = connection aborted before the response completed.</li>
	 * <li>+ = connection may be kept alive after the response is sent.</li>
	 * <li>- = connection will be closed after the response is sent.</li>
	 * </ul>
	 * <br>
	 * <br>
	 * Placeholder: <code>%X</code>
	 */
	CONNECTION_STATUS("X", false),

	/**
	 * Bytes received, including request and headers, cannot be zero. You need to enable <i>mod_logio</i> to use this.<br>
	 * <br>
	 * Placeholder: <code>%I</code>
	 */
	BYTES_RECEIVED("I", false),

	/**
	 * Bytes sent, including headers, cannot be zero. You need to enable <i>mod_logio</i> to use this.<br>
	 * <br>
	 * Placeholder: <code>%O</code>
	 */
	BYTES_SEND("O", false),

	/**
	 * A common slot for ignorable fields, can be applied multiple times <br>
	 * <br>
	 * Placeholder: <code>%0</code>
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
