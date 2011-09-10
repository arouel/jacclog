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
package net.sf.jacclog.uasparser.internal.util;

public final class StringUtil {

	/**
	 * Filters a quotation mark at the beginning and end of string.
	 * 
	 * @param value
	 *            A string
	 * @return A filtered string
	 */
	public static String filterQuotationMarks(final String value) {
		if (value == null) {
			throw new IllegalArgumentException("Argument 'value' can not be null.");
		}

		final StringBuilder builder = new StringBuilder(value);
		if (value.matches("^\".*\"$")) {
			builder.delete(value.length() - 1, value.length());
			builder.delete(0, 1);
		}
		return builder.toString();
	}

	/**
	 * Filters a quotation mark at the beginning and end for all string in the array.
	 * 
	 * @param values
	 *            An array of strings
	 * @return An filtered array of strings
	 */
	public static String[] filterQuotationMarks(final String[] values) {
		if (values == null) {
			throw new IllegalArgumentException("Argument 'values' can not be null.");
		}

		final String[] result = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			result[i] = filterQuotationMarks(values[i]);
		}
		return result;
	}

	/**
	 * Filters an opening and closing square brackets at the end of the string.
	 * 
	 * @param value
	 * @return filtered value
	 */
	public static String filterSquareBrackets(final String value) {
		if (value == null) {
			throw new IllegalArgumentException("Argument 'value' can not be null.");
		}

		final StringBuilder builder = new StringBuilder(value);
		if (value.matches(".*\\[\\]$")) {
			builder.delete(value.length() - 2, value.length());
		}
		return builder.toString();
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private StringUtil() {
		// This class is not intended to create objects from it.
	}

}
