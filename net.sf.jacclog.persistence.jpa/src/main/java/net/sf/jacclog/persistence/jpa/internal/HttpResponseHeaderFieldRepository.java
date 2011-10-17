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

import net.sf.jacclog.api.domain.http.ReadableHttpResponseHeader;
import net.sf.jacclog.persistence.jpa.entity.HttpResponseHeaderField;
import net.sf.jacclog.persistence.jpa.entity.HttpResponseHeaderFieldPK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponseHeaderFieldRepository {

	private static final Logger LOG = LoggerFactory.getLogger(HttpResponseHeaderFieldRepository.class);

	private static final String PERSISTENCE_UNIT_NAME = "jacclogPU";

	private final EntityManagerFactory entityManagerFactory;

	public HttpResponseHeaderFieldRepository() {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public HttpResponseHeaderFieldRepository(final EntityManagerFactory entityManagerFactory) {
		if (entityManagerFactory == null) {
			throw new IllegalArgumentException("Argument 'entityManagerFactory' can not be null.");
		}

		this.entityManagerFactory = entityManagerFactory;
	}

	public HttpResponseHeaderFieldRepository(final Map<String, String> properties) {
		if (properties == null) {
			throw new IllegalArgumentException("Argument 'properties' can not be null.");
		}

		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
	}

	/**
	 * Counts all HTTP response headers of a specific type.
	 * 
	 * @param type
	 *            the type of HTTP response header field
	 * @return number of HTTP response headers
	 */
	public long count(final ReadableHttpResponseHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Long count = entityManager
				.createQuery("SELECT count(o) FROM HttpResponseHeaderField o WHERE o.type = :type", Long.class)
				.setParameter("type", type.getName()).getSingleResult();
		entityManager.close();
		return count;
	}

	public long countAll() {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Long count = entityManager.createQuery("SELECT count(o) FROM HttpResponseHeaderField o", Long.class)
				.getSingleResult();
		entityManager.close();
		return count;
	}

	public HttpResponseHeaderField find(final HttpResponseHeaderField field) {
		if (field == null) {
			throw new IllegalArgumentException("Argument 'field' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final HttpResponseHeaderFieldPK primaryKey = new HttpResponseHeaderFieldPK(field.getType().getName(),
				field.getValue());
		final HttpResponseHeaderField entry = entityManager.find(HttpResponseHeaderField.class, primaryKey);
		entityManager.close();
		return entry;
	}

	/**
	 * Finds all HTTP response headers by specifying a starting position and a maximum number of results.
	 * 
	 * @param startPosition
	 *            Position of the first result, numbered from 0
	 * @param maxResults
	 *            Maximum number of results to retrieve
	 * @return a list of HTTP response header fields
	 */
	public List<HttpResponseHeaderField> find(final int startPosition, final int maxResults) {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpResponseHeaderField> entries = entityManager
				.createQuery("SELECT o FROM HttpResponseHeaderField o", HttpResponseHeaderField.class)
				.setFirstResult(startPosition).setMaxResults(maxResults).getResultList();
		entityManager.close();
		return entries;
	}

	/**
	 * Find all HTTP response header fields within a start and end date.
	 * 
	 * @param type
	 *            the type of HTTP response header field
	 * @return
	 */
	public List<HttpResponseHeaderField> find(final ReadableHttpResponseHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpResponseHeaderField> entries = entityManager
				.createQuery("SELECT o FROM HttpResponseHeaderField o WHERE o.type = :type",
						HttpResponseHeaderField.class).setParameter("type", type.getName()).getResultList();
		entityManager.close();
		return entries;
	}

	/**
	 * Finds all HTTP response headers of a specific type ordered by ID. In addition by specifying a starting position
	 * and maximum number of results it restricts the size of the result set.
	 * 
	 * @param type
	 *            the type of HTTP response header field
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
	 * @return a list of HTTP response header fields
	 */
	public List<HttpResponseHeaderField> find(final ReadableHttpResponseHeader type, final int startPosition,
			final int maxResults) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpResponseHeaderField> fields = entityManager
				.createQuery("SELECT o FROM HttpResponseHeaderField o WHERE o.type = :type ORDER BY o.id",
						HttpResponseHeaderField.class).setParameter("type", type.getName())
				.setFirstResult(startPosition).setMaxResults(maxResults).getResultList();
		entityManager.close();
		return fields;
	}

	public List<HttpResponseHeaderField> findAll() {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpResponseHeaderField> fields = entityManager.createQuery(
				"SELECT o FROM HttpResponseHeaderField o", HttpResponseHeaderField.class).getResultList();
		entityManager.close();
		return fields;
	}

	public HttpResponseHeaderField merge(final HttpResponseHeaderField field) {
		if (field == null) {
			throw new IllegalArgumentException("Argument 'field' must be set.");
		}

		HttpResponseHeaderField merged = null;
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

	public void persist(final Collection<HttpResponseHeaderField> fields) {
		if (fields == null) {
			throw new IllegalArgumentException("Argument 'fields' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			for (final HttpResponseHeaderField field : fields) {
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

	public void persist(final HttpResponseHeaderField field) {
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

	public void remove(final HttpResponseHeaderField field) {
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
			HttpResponseHeaderField attached = field;
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

	public void remove(final List<HttpResponseHeaderField> fields) {
		if (fields == null || fields.isEmpty()) {
			throw new IllegalArgumentException("Argument 'fields' can not be null or empty.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			for (final HttpResponseHeaderField field : fields) {
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
		LOG.debug("Starting HttpResponseHeaderFieldRepository...");
	}

	public void stop() {
		LOG.debug("Closing EntityManagerFactory of HttpResponseHeaderFieldRepository...");

		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}

}
