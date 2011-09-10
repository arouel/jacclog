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
 * For requests that got internally redirected, this is the status of the *original* request. Look at
 * <code>HttpLastStatusField</code> for the last status of the request.<br>
 * <br>
 * <strong>Field:</strong> %s
 */
public final class HttpStatusField extends AbstractLogFormatField {

	/**
	 * <code>FieldHolder</code> is loaded on the first execution of <code>getInstance()</code> or the first access to
	 * <code>FieldHolder.INSTANCE</code>, not before.
	 */
	private static class FieldHolder {
		public static final HttpStatusField INSTANCE = new HttpStatusField(FieldType.STATUS_CODE);
	}

	public static HttpStatusField getInstance() {
		return FieldHolder.INSTANCE;
	}

	/**
	 * Can not be instantiated from other classes.
	 * 
	 * @param type
	 * @param parameter
	 */
	private HttpStatusField(final FieldType type) {
		super(type);
	}

}
