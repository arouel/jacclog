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
package net.sf.jacclog.persistence.jpa.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.sf.jacclog.api.domain.http.ReadableHttpResponseHeaderField;
import net.sf.jacclog.persistence.jpa.entity.HttpResponseHeaderField;
import net.sf.jacclog.service.repository.domain.PersistableHttpResponseHeaderField;

/**
 * Utility class to map alien objects with correct interface to an entity.
 * 
 * @author André Rouél
 */
public final class HttpResponseHeaderFieldMapper {

	/**
	 * Maps values of an alien object that only implements <code>PersistableHttpResponseHeaderField</code> to a new
	 * <code>HttpResponseHeaderField</code> entity.<br>
	 * <br>
	 * If the given argument is already an entity, the input will be directly returned.
	 * 
	 * @param entry
	 *            the storable response header field
	 * @return the response header field entity
	 * 
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public static HttpResponseHeaderField map(final PersistableHttpResponseHeaderField field) {
		return map((ReadableHttpResponseHeaderField) field);
	}

	/**
	 * Maps values of an alien object that only implements <code>ReadableHttpResponseHeaderField</code> to a new
	 * <code>HttpResponseHeaderField</code> entity.<br>
	 * <br>
	 * If the given argument is already an entity, the input will be directly returned.
	 * 
	 * @param entry
	 *            the readable response header field
	 * @return the response header field entity
	 * 
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public static HttpResponseHeaderField map(final ReadableHttpResponseHeaderField field) {
		if (field == null) {
			throw new IllegalArgumentException("Argument 'field' must be set.");
		}

		final HttpResponseHeaderField f;
		if (field instanceof HttpResponseHeaderField) {
			f = (HttpResponseHeaderField) field;
		} else {
			f = new HttpResponseHeaderField(field.getType(), field.getValue());
		}
		return f;
	}

	/**
	 * Maps a collection of alien objects that only implements <code>PersistableHttpResponseHeaderField</code> to a new
	 * set of <code>HttpResponseHeaderField</code>.<br>
	 * <br>
	 * If the objects in the collection already an entities, they will be passed directly to the new set.
	 * 
	 * @param responseHeaders
	 *            the collection of storable response header fields
	 * @return the set of response header field entities
	 * 
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public static Set<HttpResponseHeaderField> mapPersistables(
			final Collection<PersistableHttpResponseHeaderField> responseHeaders) {
		if (responseHeaders == null) {
			throw new IllegalArgumentException("Argument 'responseHeaders' must be set.");
		}

		final Set<HttpResponseHeaderField> fields = new HashSet<HttpResponseHeaderField>(responseHeaders.size());
		for (final PersistableHttpResponseHeaderField field : responseHeaders) {
			fields.add(map(field));
		}
		return fields;
	}

	/**
	 * Maps a collection of alien objects that only implements <code>PersistableHttpResponseHeaderField</code> to a new
	 * set of <code>HttpResponseHeaderField</code>.<br>
	 * <br>
	 * If the objects in the collection already an entities, they will be passed directly to the new set.
	 * 
	 * @param responseHeaders
	 *            the collection of storable response header fields
	 * @return the set of response header field entities
	 * 
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public static Set<HttpResponseHeaderField> mapReadables(
			final Collection<ReadableHttpResponseHeaderField> responseHeaders) {
		if (responseHeaders == null) {
			throw new IllegalArgumentException("Argument 'responseHeaders' must be set.");
		}

		final Set<HttpResponseHeaderField> fields = new HashSet<HttpResponseHeaderField>(responseHeaders.size());
		for (final ReadableHttpResponseHeaderField field : responseHeaders) {
			fields.add(map(field));
		}
		return fields;
	}

	/**
	 * Casts a collection of entities to a list that only works with <code>PersistableHttpResponseHeaderField</code>.
	 * 
	 * @param collection
	 *            the collection of response header field entities
	 * @return the set with storable response header fields
	 * 
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public static Set<PersistableHttpResponseHeaderField> translate(final Collection<HttpResponseHeaderField> collection) {
		if (collection == null) {
			throw new IllegalArgumentException("Argument 'collection' must be set.");
		}

		final Set<PersistableHttpResponseHeaderField> fields = new HashSet<PersistableHttpResponseHeaderField>(
				collection);
		return fields;
	}

	private HttpResponseHeaderFieldMapper() {
		// stateless classes should not be implemented
	}

}
