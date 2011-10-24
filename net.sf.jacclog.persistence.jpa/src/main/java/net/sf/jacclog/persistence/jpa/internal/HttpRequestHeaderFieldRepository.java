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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import net.sf.jacclog.api.domain.http.ReadableHttpRequestHeader;
import net.sf.jacclog.api.domain.http.ReadableHttpRequestHeaderField;
import net.sf.jacclog.persistence.jpa.entity.HttpRequestHeaderField;
import net.sf.jacclog.persistence.jpa.entity.HttpRequestHeaderType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestHeaderFieldRepository {

	private static final Logger LOG = LoggerFactory.getLogger(HttpRequestHeaderFieldRepository.class);

	private static final String PERSISTENCE_UNIT_NAME = "jacclogPU";

	private final EntityManagerFactory entityManagerFactory;

	private final HttpRequestHeaderTypeRepository requestHeaderTypeRepository;

	public HttpRequestHeaderFieldRepository() {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		requestHeaderTypeRepository = new HttpRequestHeaderTypeRepository(entityManagerFactory);
	}

	public HttpRequestHeaderFieldRepository(final EntityManagerFactory entityManagerFactory) {
		if (entityManagerFactory == null) {
			throw new IllegalArgumentException("Argument 'entityManagerFactory' can not be null.");
		}

		this.entityManagerFactory = entityManagerFactory;
		requestHeaderTypeRepository = new HttpRequestHeaderTypeRepository(entityManagerFactory);
	}

	public HttpRequestHeaderFieldRepository(final Map<String, String> properties) {
		if (properties == null) {
			throw new IllegalArgumentException("Argument 'properties' can not be null.");
		}

		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
		requestHeaderTypeRepository = new HttpRequestHeaderTypeRepository(entityManagerFactory);
	}

	/**
	 * Counts all HTTP request headers of a specific type.
	 * 
	 * @param type
	 *            the type of a HTTP request header field
	 * @return number of HTTP request headers
	 */
	public long count(final ReadableHttpRequestHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Long count = entityManager
				.createQuery("SELECT count(o) FROM HttpRequestHeaderField o LEFT JOIN o.type t WHERE t.name = :type",
						Long.class).setParameter("type", type.getName()).getSingleResult();
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
	 * Finds a HTTP request header field by primary key.
	 * 
	 * @param id
	 *            the primary key (ID) of a HTTP request header field
	 * @return the found request header field or <code>null</code> if the field does not exist
	 */
	public HttpRequestHeaderField find(final Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Argument 'id' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final HttpRequestHeaderField field = entityManager.find(HttpRequestHeaderField.class, id);
		entityManager.close();
		return field;
	}

	/**
	 * Find all HTTP request header fields within a start and end date.
	 * 
	 * @param type
	 *            the type of a HTTP request header field
	 * @return a list of HTTP request header fields
	 */
	public List<HttpRequestHeaderField> find(final ReadableHttpRequestHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpRequestHeaderField> entries = entityManager
				.createQuery("SELECT o FROM HttpRequestHeaderField o LEFT JOIN o.type t WHERE t.name = :type",
						HttpRequestHeaderField.class).setParameter("type", type.getName()).getResultList();
		entityManager.close();
		return entries;
	}

	/**
	 * Finds all HTTP request headers of a specific type ordered by ID. In addition by specifying a starting position
	 * and maximum number of results it restricts the size of the result set.
	 * 
	 * @param type
	 *            the type of a HTTP request header field
	 * @param startPosition
	 *            Position of the first result, numbered from 0
	 * @param maxResults
	 *            Maximum number of results to retrieve
	 * @throws IllegalArgumentException
	 *             if the given type is <code>null</code>
	 * @return a list of HTTP request header fields
	 */
	public List<HttpRequestHeaderField> find(final ReadableHttpRequestHeader type, final int startPosition,
			final int maxResults) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpRequestHeaderField> fields = entityManager
				.createQuery(
						"SELECT o FROM HttpRequestHeaderField o LEFT JOIN o.type t WHERE t.name = :type ORDER BY o.id",
						HttpRequestHeaderField.class).setParameter("type", type.getName())
				.setFirstResult(startPosition).setMaxResults(maxResults).getResultList();
		entityManager.close();
		return fields;
	}

	/**
	 * Finds a HTTP request header field by type and value.
	 * 
	 * @param type
	 *            the type of a HTTP request header field
	 * @param value
	 *            the value of a HTTP request header field
	 * @throws IllegalArgumentException
	 *             if the given arguments are <code>null</code>
	 * @return the HTTP request header field or <code>null</code>, if nothing is found
	 */
	public HttpRequestHeaderField find(final ReadableHttpRequestHeader type, final String value) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		if (value == null) {
			throw new IllegalArgumentException("Argument 'value' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		HttpRequestHeaderField entry = null;
		try {
			entry = entityManager
					.createQuery(
							"SELECT o FROM HttpRequestHeaderField o LEFT JOIN o.type t WHERE t.name = :type AND o.value = :value",
							HttpRequestHeaderField.class).setParameter("type", type.getName())
					.setParameter("value", value).getSingleResult();
		} catch (final NoResultException e) {
			LOG.debug("The HTTP request header field with the type '" + type.getName() + "' and value '" + value
					+ "' does not exist. (" + e.getLocalizedMessage() + ")");
		} finally {
			entityManager.close();
		}
		return entry;
	}

	/**
	 * Finds a HTTP request header field by the type and value of the given (readable) field.
	 * 
	 * @param field
	 *            the readable HTTP request header field
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return the HTTP request header field or <code>null</code>, if nothing is found
	 */
	public HttpRequestHeaderField find(final ReadableHttpRequestHeaderField field) {
		if (field == null) {
			throw new IllegalArgumentException("Argument 'field' can not be null.");
		}

		return find(field.getType(), field.getValue());
	}

	/**
	 * Reads all HTTP request header fields within the repository.
	 * 
	 * @return a list of all HTTP request header fields
	 */
	public List<HttpRequestHeaderField> findAll() {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpRequestHeaderField> fields = entityManager.createQuery("SELECT o FROM HttpRequestHeaderField o",
				HttpRequestHeaderField.class).getResultList();
		entityManager.close();
		return fields;
	}

	/**
	 * Updates the given field within the persisted one in the repository.
	 * 
	 * @param field
	 *            the HTTP request header field to be updated
	 * @return the updated HTTP request header field or <code>null</code> if the merge process failed
	 */
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
				persistType(field);
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
			persistType(field);
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

	private void persistType(final HttpRequestHeaderField field) {
		final HttpRequestHeaderType attached = requestHeaderTypeRepository.find(field.getType());
		if (attached == null) {
			final HttpRequestHeaderType entity = field.getHttpRequestHeaderType();
			try {
				requestHeaderTypeRepository.persist(entity);
			} catch (final RuntimeException e) {
				LOG.info(e.getLocalizedMessage() + ": " + entity, e);
			}
		} else {
			field.setHttpRequestHeaderType(attached);
		}
	}

	public void remove(final HttpRequestHeaderField entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' can not be null.");
		}

		if (entry.getId() == null) {
			throw new IllegalArgumentException("The ID for an log entry can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final HttpRequestHeaderField attached = entityManager.find(HttpRequestHeaderField.class, entry.getId());
		entityManager.getTransaction().begin();
		try {
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

	public void remove(final List<HttpRequestHeaderField> types) {
		if (types == null || types.isEmpty()) {
			throw new IllegalArgumentException("Argument 'entries' can not be null or empty.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpRequestHeaderField> attached = new ArrayList<HttpRequestHeaderField>(types.size());
		for (final HttpRequestHeaderField type : types) {
			attached.add(entityManager.find(HttpRequestHeaderField.class, type.getId()));
		}
		entityManager.getTransaction().begin();
		try {
			for (final HttpRequestHeaderField type : attached) {
				entityManager.remove(type);
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
