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
package net.sf.jacclog.persistence.jpa;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sf.jacclog.api.domain.http.HttpRequestHeader;
import net.sf.jacclog.persistence.jpa.entity.HttpRequestHeaderType;
import net.sf.jacclog.persistence.jpa.internal.HttpRequestHeaderTypeRepository;

public class HttpRequestHeaderTypeDataOnDemand {

	/**
	 * Test request header types
	 */
	private List<HttpRequestHeaderType> entries;

	private final HttpRequestHeaderTypeRepository repository;

	private final Random rnd = new SecureRandom();

	public HttpRequestHeaderTypeDataOnDemand(final HttpRequestHeaderTypeRepository repository) {
		if (repository == null) {
			throw new IllegalArgumentException("Argument 'repository' can not be null.");
		}

		this.repository = repository;
	}

	public HttpRequestHeaderType getNewTransientHttpRequestHeaderType(final int index) {
		return new HttpRequestHeaderType(HttpRequestHeader.evaluate("header_type_" + index));
	}

	public HttpRequestHeaderType getRandomHttpRequestHeaderType() {
		initialize();
		final HttpRequestHeaderType type = entries.get(rnd.nextInt(entries.size()));
		return repository.find(type.getId());
	}

	public HttpRequestHeaderType getSpecificHttpRequestHeaderType(int index) {
		initialize();
		if (index < 0) {
			index = 0;
		}
		if (index > (entries.size() - 1)) {
			index = entries.size() - 1;
		}
		final HttpRequestHeaderType type = entries.get(index);
		return repository.find(type.getId());
	}

	public void initialize() {
		initialize(20);
	}

	/**
	 * Initialize the repository with self created log entries.
	 * 
	 * @param amount
	 *            Number of log entries to be created
	 */
	public void initialize(final int amount) {
		entries = repository.find(0, amount);
		if (entries == null) {
			throw new IllegalStateException(
					"Find entries implementation for 'HttpRequestHeaderTypeRepository' illegally returned null.");
		}
		if (!entries.isEmpty()) {
			return;
		}

		entries = new ArrayList<HttpRequestHeaderType>();
		for (int i = 0; i < amount; i++) {
			final HttpRequestHeaderType entity = getNewTransientHttpRequestHeaderType(i);
			repository.persist(entity);
			entries.add(entity);
		}
	}

	public boolean modifyHttpRequestHeaderType(final HttpRequestHeaderType obj) {
		return false;
	}

}
