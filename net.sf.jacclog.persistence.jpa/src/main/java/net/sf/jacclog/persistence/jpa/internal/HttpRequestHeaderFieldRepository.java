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
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.sf.jacclog.api.domain.http.ReadableHttpRequestHeader;
import net.sf.jacclog.persistence.jpa.entity.HttpRequestHeaderField;
import net.sf.jacclog.persistence.jpa.entity.HttpRequestHeaderFieldPK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestHeaderFieldRepository {

	private static final Logger LOG = LoggerFactory.getLogger(HttpRequestHeaderFieldRepository.class);

	private static final String PERSISTENCE_UNIT_NAME = "jacclogPU";

	private final EntityManagerFactory entityManagerFactory;

	public HttpRequestHeaderFieldRepository() {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public HttpRequestHeaderFieldRepository(final EntityManagerFactory entityManagerFactory) {
		if (entityManagerFactory == null) {
			throw new IllegalArgumentException("Argument 'entityManagerFactory' can not be null.");
		}

		this.entityManagerFactory = entityManagerFactory;
	}

	public HttpRequestHeaderFieldRepository(final Map<String, String> properties) {
		if (properties == null) {
			throw new IllegalArgumentException("Argument 'properties' can not be null.");
		}

		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
	}

	/**
	 * Counts all HTTP request headers of a specific type.
	 * 
	 * @param type
	 *            the type of HTTP request header field
	 * @return number of HTTP request headers
	 */
	public long count(final ReadableHttpRequestHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Long count = entityManager
				.createQuery("SELECT count(o) FROM HttpRequestHeaderField o WHERE o.type = :type", Long.class)
				.setParameter("type", type.getName()).getSingleResult();
		entityManager.close();
		return count;
	}

	public long countAll() {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Long count = entityManager.createQuery("SELECT count(o) FROM HttpRequestHeaderField o", Long.class)
				.getSingleResult();
		entityManager.close();
		return count;
	}

	public HttpRequestHeaderField find(final HttpRequestHeaderField field) {
		if (field == null) {
			throw new IllegalArgumentException("Argument 'field' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final HttpRequestHeaderFieldPK primaryKey = new HttpRequestHeaderFieldPK(field.getType().getName(),
				field.getValue());
		final HttpRequestHeaderField entry = entityManager.find(HttpRequestHeaderField.class, primaryKey);
		entityManager.close();
		return entry;
	}

	/**
	 * Finds all HTTP request headers by specifying a starting position and a maximum number of results.
	 * 
	 * @param startPosition
	 *            Position of the first result, numbered from 0
	 * @param maxResults
	 *            Maximum number of results to retrieve
	 * @return a list of HTTP request header fields
	 */
	public List<HttpRequestHeaderField> find(final int startPosition, final int maxResults) {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpRequestHeaderField> entries = entityManager
				.createQuery("SELECT o FROM HttpRequestHeaderField o", HttpRequestHeaderField.class)
				.setFirstResult(startPosition).setMaxResults(maxResults).getResultList();
		entityManager.close();
		return entries;
	}

	/**
	 * Find all HTTP request header fields within a start and end date.
	 * 
	 * @param type
	 *            the type of HTTP request header field
	 * @return
	 */
	public List<HttpRequestHeaderField> find(final ReadableHttpRequestHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpRequestHeaderField> entries = entityManager
				.createQuery("SELECT o FROM HttpRequestHeaderField o WHERE o.type = :type",
						HttpRequestHeaderField.class).setParameter("type", type.getName()).getResultList();
		entityManager.close();
		return entries;
	}

	/**
	 * Finds all HTTP request headers of a specific type ordered by ID. In addition by specifying a starting position
	 * and maximum number of results it restricts the size of the result set.
	 * 
	 * @param type
	 *            the type of HTTP request header field
	 * @param startPosition
	 *            Position of the first result, numbered from 0
	 * @param maxResults
	 *            Maximum number of results to retrieve
	 * @throws IllegalArgumentException
	 *             If the start date is null
	 * @throws IllegalArgumentException
	 *             If the end date is null
	 * @throws IllegalArgumentException
	 *             If the start is greater than the end date is null
	 * @return a list of HTTP request header fields
	 */
	public List<HttpRequestHeaderField> find(final ReadableHttpRequestHeader type, final int startPosition,
			final int maxResults) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpRequestHeaderField> fields = entityManager
				.createQuery("SELECT o FROM HttpRequestHeaderField o WHERE o.type = :type ORDER BY o.id",
						HttpRequestHeaderField.class).setParameter("type", type.getName())
				.setFirstResult(startPosition).setMaxResults(maxResults).getResultList();
		entityManager.close();
		return fields;
	}

	public List<HttpRequestHeaderField> findAll() {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpRequestHeaderField> fields = entityManager.createQuery("SELECT o FROM HttpRequestHeaderField o",
				HttpRequestHeaderField.class).getResultList();
		entityManager.close();
		return fields;
	}

	public HttpRequestHeaderField merge(final HttpRequestHeaderField field) {
		if (field == null) {
			throw new IllegalArgumentException("Argument 'field' must be set.");
		}

		HttpRequestHeaderField merged = null;
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			merged = entityManager.merge(field);
			entityManager.getTransaction().commit();
		} catch (final RuntimeException e1) {
			if (entityManager.getTransaction().isActive()) {
				try {
					entityManager.getTransaction().rollback();
				} catch (final RuntimeException e2) {
					// Log rollback failure or something
					throw e2;
				}
			}
			throw e1;
		} finally {
			if (entityManager != null) {
				entityManager.clear();
				entityManager.close();
			}
		}

		return merged;
	}

	public void persist(final Collection<HttpRequestHeaderField> fields) {
		if (fields == null) {
			throw new IllegalArgumentException("Argument 'fields' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			for (final HttpRequestHeaderField field : fields) {
				entityManager.persist(field);
			}
			entityManager.getTransaction().commit();
		} catch (final RuntimeException e1) {
			if (entityManager.getTransaction().isActive()) {
				try {
					entityManager.getTransaction().rollback();
				} catch (final RuntimeException e2) {
					// Log rollback failure or something
					throw e2;
				}
			}
			throw e1;
		} finally {
			if (entityManager != null) {
				entityManager.clear();
				entityManager.close();
			}
		}
	}

	public void persist(final HttpRequestHeaderField field) {
		if (field == null) {
			throw new IllegalArgumentException("Argument 'field' must be set.");
		}

		if (field.getType() == null) {
			throw new IllegalArgumentException("The type of an header field can not be null.");
		}

		if (field.getValue() == null) {
			throw new IllegalArgumentException("The value of an header field can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(field);
			entityManager.getTransaction().commit();
		} catch (final RuntimeException e1) {
			if (entityManager.getTransaction().isActive()) {
				try {
					entityManager.getTransaction().rollback();
				} catch (final RuntimeException e2) {
					// Log rollback failure or something
					throw e2;
				}
			}
			throw e1;
		} finally {
			if (entityManager != null) {
				entityManager.clear();
				entityManager.close();
			}
		}
	}

	public void remove(final HttpRequestHeaderField field) {
		if (field == null) {
			throw new IllegalArgumentException("Argument 'field' can not be null.");
		}

		if (field.getType() == null) {
			throw new IllegalArgumentException("The type of an header field can not be null.");
		}

		if (field.getValue() == null) {
			throw new IllegalArgumentException("The value of an header field can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			HttpRequestHeaderField attached = field;
			if (!entityManager.contains(attached)) {
				attached = entityManager.merge(attached);
			}
			entityManager.remove(attached);
			entityManager.getTransaction().commit();
		} catch (final RuntimeException e1) {
			if (entityManager.getTransaction().isActive()) {
				try {
					entityManager.getTransaction().rollback();
				} catch (final RuntimeException e2) {
					// Log rollback failure or something
					throw e2;
				}
			}
			throw e1;
		} finally {
			if (entityManager != null) {
				entityManager.clear();
				entityManager.close();
			}
		}
	}

	public void remove(final List<HttpRequestHeaderField> fields) {
		if (fields == null || fields.isEmpty()) {
			throw new IllegalArgumentException("Argument 'fields' can not be null or empty.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			for (final HttpRequestHeaderField field : fields) {
				if (!entityManager.contains(field)) {
					entityManager.remove(entityManager.merge(field));
				} else {
					entityManager.remove(field);
				}
			}
			entityManager.getTransaction().commit();
		} catch (final RuntimeException e1) {
			if (entityManager.getTransaction().isActive()) {
				try {
					entityManager.getTransaction().rollback();
				} catch (final RuntimeException e2) {
					// Log rollback failure or something
					throw e2;
				}
			}
			throw e1;
		} finally {
			if (entityManager != null) {
				entityManager.clear();
				entityManager.close();
			}
		}
	}

	public void start() {
		LOG.debug("Starting HttpRequestHeaderFieldRepository...");
	}

	public void stop() {
		LOG.debug("Closing EntityManagerFactory of HttpRequestHeaderFieldRepository...");

		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}

}
