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
 * The contents of Referer: header line(s) in the request sent to the server.<br>
 * <br>
 * <strong>Field:</strong> %{Referer}i
 */
public final class HttpRefererField extends AbstractParameterizedLogFormatField<HttpRequestHeader> {

	/**
	 * <code>FieldHolder</code> is loaded on the first execution of <code>getInstance()</code> or the first access to
	 * <code>FieldHolder.INSTANCE</code>, not before.
	 */
	private static class FieldHolder {
		public static final HttpRefererField INSTANCE = new HttpRefererField(FieldType.REQUEST_HEADER_LINE,
				HttpRequestHeader.REFERER);
	}

	public static HttpRefererField getInstance() {
		return FieldHolder.INSTANCE;
	}

	/**
	 * Can not be instantiated from other classes.
	 * 
	 * @param type
	 * @param parameter
	 */
	private HttpRefererField(final FieldType type, final HttpRequestHeader parameter) {
		super(type, parameter);
	}

}
