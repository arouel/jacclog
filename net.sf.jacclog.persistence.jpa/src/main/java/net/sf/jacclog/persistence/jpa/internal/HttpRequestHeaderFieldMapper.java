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

import net.sf.jacclog.api.domain.http.ReadableHttpRequestHeaderField;
import net.sf.jacclog.persistence.jpa.entity.HttpRequestHeaderField;
import net.sf.jacclog.service.repository.domain.PersistableHttpRequestHeaderField;

/**
 * Utility class to map alien objects with correct interface to an entity.
 * 
 * @author André Rouél
 */
public final class HttpRequestHeaderFieldMapper {

	/**
	 * Maps values of an alien object that only implements <code>PersistableHttpRequestHeaderField</code> to a new
	 * <code>HttpRequestHeaderField</code> entity.<br>
	 * <br>
	 * If the given argument is already an entity, the input will be directly returned.
	 * 
	 * @param entry
	 *            the storable request header field
	 * @return the request header field entity
	 * 
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public static HttpRequestHeaderField map(final PersistableHttpRequestHeaderField field) {
		return map((ReadableHttpRequestHeaderField) field);
	}

	/**
	 * Maps values of an alien object that only implements <code>ReadableHttpRequestHeaderField</code> to a new
	 * <code>HttpRequestHeaderField</code> entity.<br>
	 * <br>
	 * If the given argument is already an entity, the input will be directly returned.
	 * 
	 * @param entry
	 *            the readable request header field
	 * @return the request header field entity
	 * 
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public static HttpRequestHeaderField map(final ReadableHttpRequestHeaderField field) {
		if (field == null) {
			throw new IllegalArgumentException("Argument 'field' must be set.");
		}

		final HttpRequestHeaderField f;
		if (field instanceof HttpRequestHeaderField) {
			f = (HttpRequestHeaderField) field;
		} else {
			f = new HttpRequestHeaderField(field.getType(), field.getValue());
		}
		return f;
	}

	/**
	 * Maps a collection of alien objects that only implements <code>PersistableHttpRequestHeaderField</code> to a new
	 * set of <code>HttpRequestHeaderField</code>.<br>
	 * <br>
	 * If the objects in the collection already an entities, they will be passed directly to the new set.
	 * 
	 * @param requestHeaders
	 *            the collection of storable request header fields
	 * @return the set of request header field entities
	 * 
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public static Set<HttpRequestHeaderField> mapPersistables(
			final Collection<PersistableHttpRequestHeaderField> requestHeaders) {
		if (requestHeaders == null) {
			throw new IllegalArgumentException("Argument 'requestHeaders' must be set.");
		}

		final Set<HttpRequestHeaderField> fields = new HashSet<HttpRequestHeaderField>(requestHeaders.size());
		for (final PersistableHttpRequestHeaderField field : requestHeaders) {
			fields.add(map(field));
		}
		return fields;
	}

	/**
	 * Maps a collection of alien objects that only implements <code>PersistableHttpRequestHeaderField</code> to a new
	 * set of <code>HttpRequestHeaderField</code>.<br>
	 * <br>
	 * If the objects in the collection already an entities, they will be passed directly to the new set.
	 * 
	 * @param requestHeaders
	 *            the collection of storable request header fields
	 * @return the set of request header field entities
	 * 
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public static Set<HttpRequestHeaderField> mapReadables(
			final Collection<ReadableHttpRequestHeaderField> requestHeaders) {
		if (requestHeaders == null) {
			throw new IllegalArgumentException("Argument 'requestHeaders' must be set.");
		}

		final Set<HttpRequestHeaderField> fields = new HashSet<HttpRequestHeaderField>(requestHeaders.size());
		for (final ReadableHttpRequestHeaderField field : requestHeaders) {
			fields.add(map(field));
		}
		return fields;
	}

	/**
	 * Casts a collection of entities to a list that only works with <code>PersistableHttpRequestHeaderField</code>.
	 * 
	 * @param collection
	 *            the collection of request header field entities
	 * @return the set with storable request header fields
	 * 
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public static Set<PersistableHttpRequestHeaderField> translate(final Collection<HttpRequestHeaderField> collection) {
		if (collection == null) {
			throw new IllegalArgumentException("Argument 'collection' must be set.");
		}

		final Set<PersistableHttpRequestHeaderField> fields = new HashSet<PersistableHttpRequestHeaderField>(collection);
		return fields;
	}

	private HttpRequestHeaderFieldMapper() {
		// stateless classes should not be implemented
	}

}
