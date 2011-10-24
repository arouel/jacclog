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

import net.sf.jacclog.api.domain.http.ReadableHttpResponseHeader;
import net.sf.jacclog.api.domain.http.ReadableHttpResponseHeaderField;
import net.sf.jacclog.persistence.jpa.entity.HttpResponseHeaderField;
import net.sf.jacclog.persistence.jpa.entity.HttpResponseHeaderType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponseHeaderFieldRepository {

	private static final Logger LOG = LoggerFactory.getLogger(HttpResponseHeaderFieldRepository.class);

	private static final String PERSISTENCE_UNIT_NAME = "jacclogPU";

	private final EntityManagerFactory entityManagerFactory;

	private final HttpResponseHeaderTypeRepository responseHeaderTypeRepository;

	public HttpResponseHeaderFieldRepository() {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		responseHeaderTypeRepository = new HttpResponseHeaderTypeRepository(entityManagerFactory);
	}

	public HttpResponseHeaderFieldRepository(final EntityManagerFactory entityManagerFactory) {
		if (entityManagerFactory == null) {
			throw new IllegalArgumentException("Argument 'entityManagerFactory' can not be null.");
		}

		this.entityManagerFactory = entityManagerFactory;
		responseHeaderTypeRepository = new HttpResponseHeaderTypeRepository(entityManagerFactory);
	}

	public HttpResponseHeaderFieldRepository(final Map<String, String> properties) {
		if (properties == null) {
			throw new IllegalArgumentException("Argument 'properties' can not be null.");
		}

		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
		responseHeaderTypeRepository = new HttpResponseHeaderTypeRepository(entityManagerFactory);
	}

	/**
	 * Counts all HTTP response headers of a specific type.
	 * 
	 * @param type
	 *            the type of a HTTP response header field
	 * @return number of HTTP response headers
	 */
	public long count(final ReadableHttpResponseHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Long count = entityManager
				.createQuery("SELECT count(o) FROM HttpResponseHeaderField o LEFT JOIN o.type t WHERE t.name = :type",
						Long.class).setParameter("type", type.getName()).getSingleResult();
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
	 * Finds a HTTP response header field by primary key.
	 * 
	 * @param id
	 *            the primary key (ID) of a HTTP response header field
	 * @return the found response header field or <code>null</code> if the field does not exist
	 */
	public HttpResponseHeaderField find(final Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Argument 'id' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final HttpResponseHeaderField field = entityManager.find(HttpResponseHeaderField.class, id);
		entityManager.close();
		return field;
	}

	/**
	 * Find all HTTP response header fields within a start and end date.
	 * 
	 * @param type
	 *            the type of a HTTP response header field
	 * @return a list of HTTP response header fields
	 */
	public List<HttpResponseHeaderField> find(final ReadableHttpResponseHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpResponseHeaderField> entries = entityManager
				.createQuery("SELECT o FROM HttpResponseHeaderField o LEFT JOIN o.type t WHERE t.name = :type",
						HttpResponseHeaderField.class).setParameter("type", type.getName()).getResultList();
		entityManager.close();
		return entries;
	}

	/**
	 * Finds all HTTP response headers of a specific type ordered by ID. In addition by specifying a starting position
	 * and maximum number of results it restricts the size of the result set.
	 * 
	 * @param type
	 *            the type of a HTTP response header field
	 * @param startPosition
	 *            Position of the first result, numbered from 0
	 * @param maxResults
	 *            Maximum number of results to retrieve
	 * @throws IllegalArgumentException
	 *             if the given type is <code>null</code>
	 * @return a list of HTTP response header fields
	 */
	public List<HttpResponseHeaderField> find(final ReadableHttpResponseHeader type, final int startPosition,
			final int maxResults) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpResponseHeaderField> fields = entityManager
				.createQuery(
						"SELECT o FROM HttpResponseHeaderField o LEFT JOIN o.type t WHERE t.name = :type ORDER BY o.id",
						HttpResponseHeaderField.class).setParameter("type", type.getName())
				.setFirstResult(startPosition).setMaxResults(maxResults).getResultList();
		entityManager.close();
		return fields;
	}

	/**
	 * Finds a HTTP response header field by type and value.
	 * 
	 * @param type
	 *            the type of a HTTP response header field
	 * @param value
	 *            the value of a HTTP response header field
	 * @throws IllegalArgumentException
	 *             if the given arguments are <code>null</code>
	 * @return the HTTP response header field or <code>null</code>, if nothing is found
	 */
	public HttpResponseHeaderField find(final ReadableHttpResponseHeader type, final String value) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		if (value == null) {
			throw new IllegalArgumentException("Argument 'value' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		HttpResponseHeaderField entry = null;
		try {
			entry = entityManager
					.createQuery(
							"SELECT o FROM HttpResponseHeaderField o LEFT JOIN o.type t WHERE t.name = :type AND o.value = :value",
							HttpResponseHeaderField.class).setParameter("type", type.getName())
					.setParameter("value", value).getSingleResult();
		} catch (final NoResultException e) {
			LOG.debug("The HTTP response header field with the type '" + type.getName() + "' and value '" + value
					+ "' does not exist. (" + e.getLocalizedMessage() + ")");
		} finally {
			entityManager.close();
		}
		return entry;
	}

	/**
	 * Finds a HTTP response header field by the type and value of the given (readable) field.
	 * 
	 * @param field
	 *            the readable HTTP response header field
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return the HTTP response header field or <code>null</code>, if nothing is found
	 */
	public HttpResponseHeaderField find(final ReadableHttpResponseHeaderField field) {
		if (field == null) {
			throw new IllegalArgumentException("Argument 'field' can not be null.");
		}

		return find(field.getType(), field.getValue());
	}

	/**
	 * Reads all HTTP response header fields within the repository.
	 * 
	 * @return a list of all HTTP response header fields
	 */
	public List<HttpResponseHeaderField> findAll() {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpResponseHeaderField> fields = entityManager.createQuery(
				"SELECT o FROM HttpResponseHeaderField o", HttpResponseHeaderField.class).getResultList();
		entityManager.close();
		return fields;
	}

	/**
	 * Updates the given field within the persisted one in the repository.
	 * 
	 * @param field
	 *            the HTTP response header field to be updated
	 * @return the updated HTTP response header field or <code>null</code> if the merge process failed
	 */
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

	private void persistType(final HttpResponseHeaderField field) {
		final HttpResponseHeaderType attached = responseHeaderTypeRepository.find(field.getType());
		if (attached == null) {
			final HttpResponseHeaderType entity = field.getHttpResponseHeaderType();
			try {
				responseHeaderTypeRepository.persist(entity);
			} catch (final RuntimeException e) {
				LOG.info(e.getLocalizedMessage() + ": " + entity, e);
			}
		} else {
			field.setHttpResponseHeaderType(attached);
		}
	}

	public void remove(final HttpResponseHeaderField entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' can not be null.");
		}

		if (entry.getId() == null) {
			throw new IllegalArgumentException("The ID for an log entry can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final HttpResponseHeaderField attached = entityManager.find(HttpResponseHeaderField.class, entry.getId());
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

	public void remove(final List<HttpResponseHeaderField> types) {
		if (types == null || types.isEmpty()) {
			throw new IllegalArgumentException("Argument 'entries' can not be null or empty.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpResponseHeaderField> attached = new ArrayList<HttpResponseHeaderField>(types.size());
		for (final HttpResponseHeaderField type : types) {
			attached.add(entityManager.find(HttpResponseHeaderField.class, type.getId()));
		}
		entityManager.getTransaction().begin();
		try {
			for (final HttpResponseHeaderField type : attached) {
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
		LOG.debug("Starting HttpResponseHeaderFieldRepository...");
	}

	public void stop() {
		LOG.debug("Closing EntityManagerFactory of HttpResponseHeaderFieldRepository...");

		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}

}
