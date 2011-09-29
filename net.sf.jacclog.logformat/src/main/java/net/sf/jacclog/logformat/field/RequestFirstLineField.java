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
 * This class represents the first line of a HTTP request.<br>
 * <br>
 * For example:<br>
 * <code>POST /some/path.html HTTP/1.1</code><br>
 * <code>GET http://foo.bar/a.html HTTP/1.0</code><br>
 * <code>HEAD /xyz?a=b HTTP/1.1</code><br>
 * <br>
 * <strong>Field:</strong> %r
 */
public final class RequestFirstLineField extends AbstractLogFormatField {

	/**
	 * <code>FieldHolder</code> is loaded on the first execution of <code>getInstance()</code> or the first access to
	 * <code>FieldHolder.INSTANCE</code>, not before.
	 */
	private static class FieldHolder {
		public static final RequestFirstLineField INSTANCE = new RequestFirstLineField(FieldType.REQUEST_FIRST_LINE);
	}

	public static RequestFirstLineField getInstance() {
		return FieldHolder.INSTANCE;
	}

	/**
	 * Can not be instantiated from other classes.
	 * 
	 * @param type
	 * @param parameter
	 */
	private RequestFirstLineField(final FieldType type) {
		super(type);
	}

}
