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
package net.sf.jacclog.service.importer.internal.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jacclog.logformat.field.Field;

public final class TokenToFieldMapper {

	public static Map<Field, String> map(final List<Field> fields, final List<String> tokens) {
		if (fields == null || fields.isEmpty()) {
			throw new IllegalArgumentException("Argument 'fields' can not be null or empty.");
		}

		if (tokens == null) {
			throw new IllegalArgumentException("Argument 'tokens' can not be null.");
		}

		if (tokens.size() != fields.size()) {
			throw new IllegalStateException(
					"The length of the tokens will not fit together with the length of the fields.");
		}

		final Map<Field, String> map = new HashMap<Field, String>(fields.size());
		for (int i = 0; i < fields.size(); i++) {
			final Field field = fields.get(i);
			if (field != null) {
				map.put(field, tokens.get(i));
			}
		}
		return map;
	}

	private TokenToFieldMapper() {
	}

}
