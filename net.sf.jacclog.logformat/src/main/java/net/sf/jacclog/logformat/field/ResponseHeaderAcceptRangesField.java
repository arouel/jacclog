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

import net.sf.jacclog.api.domain.http.HttpResponseHeader;

/**
 * An instance of this class represents a <code>Accept-Ranges</code> field in a HTTP header of a response sent to a
 * client.<br>
 * <br>
 * <strong>Field:</strong> %{Accept-Ranges}o
 */
public final class ResponseHeaderAcceptRangesField extends AbstractParameterizedLogFormatField<HttpResponseHeader> {

	/**
	 * <code>FieldHolder</code> is loaded on the first execution of <code>getInstance()</code> or the first access to
	 * <code>FieldHolder.INSTANCE</code>, not before.
	 */
	private static class FieldHolder {
		public static final ResponseHeaderAcceptRangesField INSTANCE = new ResponseHeaderAcceptRangesField(
				FieldType.RESPONSE_HEADER_LINE, HttpResponseHeader.ACCEPT_RANGES);
	}

	public static ResponseHeaderAcceptRangesField getInstance() {
		return FieldHolder.INSTANCE;
	}

	/**
	 * Can not be instantiated from other classes.
	 * 
	 * @param type
	 * @param parameter
	 */
	private ResponseHeaderAcceptRangesField(final FieldType type, final HttpResponseHeader parameter) {
		super(type, parameter);
	}

}
