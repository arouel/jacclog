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
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import net.sf.jacclog.api.domain.http.ReadableHttpRequestHeader;
import net.sf.jacclog.persistence.jpa.entity.HttpRequestHeaderType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestHeaderTypeRepository {

	private static final Logger LOG = LoggerFactory.getLogger(HttpRequestHeaderTypeRepository.class);

	private static final String PERSISTENCE_UNIT_NAME = "jacclogPU";

	private final EntityManagerFactory entityManagerFactory;

	public HttpRequestHeaderTypeRepository() {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public HttpRequestHeaderTypeRepository(final EntityManagerFactory entityManagerFactory) {
		if (entityManagerFactory == null) {
			throw new IllegalArgumentException("Argument 'entityManagerFactory' can not be null.");
		}

		this.entityManagerFactory = entityManagerFactory;
	}

	public HttpRequestHeaderTypeRepository(final Map<String, String> properties) {
		if (properties == null) {
			throw new IllegalArgumentException("Argument 'properties' can not be null.");
		}

		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
	}

	/**
	 * Counts all HTTP request headers of a specific type.
	 * 
	 * @param type
	 *            the type of a HTTP request header type
	 * @return number of HTTP request headers
	 */
	public long count(final ReadableHttpRequestHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Long count = entityManager
				.createQuery("SELECT count(o) FROM HttpRequestHeaderType o WHERE o.name = :name", Long.class)
				.setParameter("name", type.getName()).getSingleResult();
		entityManager.close();
		return count;
	}

	public long countAll() {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Long count = entityManager.createQuery("SELECT count(o) FROM HttpRequestHeaderType o", Long.class)
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
	 * @return a list of HTTP request header types
	 */
	public List<HttpRequestHeaderType> find(final int startPosition, final int maxResults) {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpRequestHeaderType> entries = entityManager
				.createQuery("SELECT o FROM HttpRequestHeaderType o", HttpRequestHeaderType.class)
				.setFirstResult(startPosition).setMaxResults(maxResults).getResultList();
		entityManager.close();
		return entries;
	}

	/**
	 * Finds a HTTP request header type by primary key.
	 * 
	 * @param id
	 *            the primary key (ID) of a HTTP request header type
	 * @return the found request header type or <code>null</code> if the type does not exist
	 */
	public HttpRequestHeaderType find(final Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Argument 'id' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final HttpRequestHeaderType type = entityManager.find(HttpRequestHeaderType.class, id);
		entityManager.close();
		return type;
	}

	/**
	 * Finds a HTTP request header type by the name of the given <code>ReadableHttpRequestHeader</code>.
	 * 
	 * @param type
	 *            the readable HTTP request header type
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return the HTTP request header type or <code>null</code>, if nothing is found
	 */
	public HttpRequestHeaderType find(final ReadableHttpRequestHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		return find(type.getName());
	}

	/**
	 * Finds all HTTP request headers of a specific type ordered by ID. In addition by specifying a starting position
	 * and maximum number of results it restricts the size of the result set.
	 * 
	 * @param type
	 *            the type of a HTTP request header type
	 * @param startPosition
	 *            Position of the first result, numbered from 0
	 * @param maxResults
	 *            Maximum number of results to retrieve
	 * @throws IllegalArgumentException
	 *             if the given type is <code>null</code>
	 * @return a list of HTTP request header types
	 */
	public List<HttpRequestHeaderType> find(final ReadableHttpRequestHeader type, final int startPosition,
			final int maxResults) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpRequestHeaderType> types = entityManager
				.createQuery("SELECT o FROM HttpRequestHeaderType o WHERE o.name = :name ORDER BY o.id",
						HttpRequestHeaderType.class).setParameter("name", type.getName()).setFirstResult(startPosition)
				.setMaxResults(maxResults).getResultList();
		entityManager.close();
		return types;
	}

	/**
	 * Finds a HTTP request header type by name.
	 * 
	 * @param type
	 *            the type of a HTTP request header type
	 * @param value
	 *            the value of a HTTP request header type
	 * @throws IllegalArgumentException
	 *             if the given arguments are <code>null</code>
	 * @return the HTTP request header type or <code>null</code>, if nothing is found
	 */
	public HttpRequestHeaderType find(final String name) {
		if (name == null) {
			throw new IllegalArgumentException("Argument 'name' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		HttpRequestHeaderType entry = null;
		try {
			entry = entityManager
					.createQuery("SELECT o FROM HttpRequestHeaderType o WHERE o.name = :name",
							HttpRequestHeaderType.class).setParameter("name", name).getSingleResult();
		} catch (final NoResultException e) {
			LOG.debug("The HTTP request header type with the name '" + name + "' does not exist. ("
					+ e.getLocalizedMessage() + ")");
		} finally {
			entityManager.close();
		}
		return entry;
	}

	/**
	 * Reads all HTTP request header types within the repository.
	 * 
	 * @return a list of all HTTP request header types
	 */
	public List<HttpRequestHeaderType> findAll() {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<HttpRequestHeaderType> types = entityManager.createQuery("SELECT o FROM HttpRequestHeaderType o",
				HttpRequestHeaderType.class).getResultList();
		entityManager.close();
		return types;
	}

	/**
	 * Updates the given type within the persisted one in the repository.
	 * 
	 * @param type
	 *            the HTTP request header type to be updated
	 * @return the updated HTTP request header type or <code>null</code> if the merge process failed
	 */
	public HttpRequestHeaderType merge(final HttpRequestHeaderType type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' must be set.");
		}

		HttpRequestHeaderType merged = null;
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			merged = entityManager.merge(type);
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

	public void persist(final Collection<HttpRequestHeaderType> types) {
		if (types == null) {
			throw new IllegalArgumentException("Argument 'types' can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			for (final HttpRequestHeaderType type : types) {
				entityManager.persist(type);
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

	public void persist(final HttpRequestHeaderType type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' must be set.");
		}

		if (type.getType() == null) {
			throw new IllegalArgumentException("The name of an header type can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(type);
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

	public void remove(final HttpRequestHeaderType type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		if (type.getId() == null) {
			throw new IllegalArgumentException("The ID for an type can not be null.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			entityManager.remove(entityManager.find(HttpRequestHeaderType.class, type.getId()));
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

	public void remove(final List<HttpRequestHeaderType> types) {
		if (types == null || types.isEmpty()) {
			throw new IllegalArgumentException("Argument 'entries' can not be null or empty.");
		}

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			for (final HttpRequestHeaderType type : types) {
				if (type.getId() == null) {
					throw new IllegalArgumentException("The ID for an type can not be null.");
				}

				entityManager.remove(entityManager.find(HttpRequestHeaderType.class, type.getId()));
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
		LOG.debug("Starting HttpRequestHeaderTypeRepository...");
	}

	public void stop() {
		LOG.debug("Closing EntityManagerFactory of HttpRequestHeaderTypeRepository...");

		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}

}
