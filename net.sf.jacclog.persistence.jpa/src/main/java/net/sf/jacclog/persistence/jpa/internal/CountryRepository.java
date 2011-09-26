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

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;

import net.sf.jacclog.persistence.jpa.entity.Country;
import net.sf.jacclog.util.net.IpAddressTranslator;

import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryRepository {

	private static final Logger LOG = LoggerFactory.getLogger(CountryRepository.class);

	private static final String PERSISTENCE_UNIT_NAME = "jacclogPU";

	private final EntityManagerFactory entityManagerFactory;

	public CountryRepository() {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public CountryRepository(final Map<String, String> properties) {
		if (properties == null)
			throw new IllegalArgumentException("Argument 'properties' can not be null.");

		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
	}

	/**
	 * Count all countries within a start and end date.
	 * 
	 * @param period
	 * @return
	 */
	public long count(final Date start, final Date end) {
		if (start == null)
			throw new IllegalArgumentException("Argument 'start' can not be null.");

		if (end == null)
			throw new IllegalArgumentException("Argument 'end' can not be null.");

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Long count = entityManager
				.createQuery("SELECT count(o) FROM Country o WHERE o.finishedRequestAt BETWEEN :start AND :end",
						Long.class).setParameter("start", start, TemporalType.DATE)
				.setParameter("end", end, TemporalType.DATE).getSingleResult();
		entityManager.close();
		return count;
	}

	/**
	 * Count all countries within an interval
	 * 
	 * @param interval
	 * @return
	 */
	public long count(final Interval interval) {
		if (interval == null)
			throw new IllegalArgumentException("Argument 'interval' can not be null.");
		return count(interval.getStart().toDate(), interval.getEnd().toDate());
	}

	public long countAll() {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Long count = entityManager.createQuery("SELECT count(o) FROM Country o", Long.class).getSingleResult();
		entityManager.close();
		return count;
	}

	/**
	 * Finds all countries in the specific range by specifying a starting position and a maximum number of results.
	 * 
	 * @param startPosition
	 *            Position of the first result, numbered from 0
	 * @param maxResults
	 *            Maximum number of results to retrieve
	 * @return A list of countries
	 */
	public List<Country> find(final int startPosition, final int maxResults) {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<Country> entries = entityManager.createQuery("SELECT o FROM Country o", Country.class)
				.setFirstResult(startPosition).setMaxResults(maxResults).getResultList();
		entityManager.close();
		return entries;
	}

	public Country find(final Long id) {
		if (id == null)
			throw new IllegalArgumentException("Argument 'id' can not be null.");

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Country entry = entityManager.find(Country.class, id);
		entityManager.close();
		return entry;
	}

	/**
	 * Finds a country by an IP address. It is checked whether the given address lies between a defined IP address range
	 * of a record.
	 * 
	 * @param ipAddress
	 *            IP address (IPv4)
	 * @return A country
	 * @throws IllegalArgumentException
	 *             If the given argument is null
	 */
	public Country find(final Inet4Address ipAddress) {
		if (ipAddress == null) {
			throw new IllegalArgumentException("Argument 'ipAddress' can not be null.");
		}

		final long ipnum = IpAddressTranslator.toLong(ipAddress);

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Country country = entityManager
				.createQuery(
						"SELECT o FROM Country o WHERE o.beginIpAddressAsNumber < :address AND o.endIpAddressAsNumber > :address",
						Country.class).setParameter("address", ipnum).getSingleResult();
		return country;
	}

	public List<Country> findAll() {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<Country> entries = entityManager.createQuery("SELECT o FROM Country o", Country.class)
				.getResultList();
		entityManager.close();
		return entries;
	}

	public Country merge(final Country entry) {
		if (entry == null)
			throw new IllegalArgumentException("Argument 'entry' must be set.");

		Country merged = null;
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			merged = entityManager.merge(entry);
			entityManager.getTransaction().commit();
		} catch (final RuntimeException e1) {
			if (entityManager.getTransaction().isActive())
				try {
					entityManager.getTransaction().rollback();
				} catch (final RuntimeException e2) {
					// Log rollback failure or something
					throw e2;
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

	public void persist(final List<Country> entries) {
		if (entries == null)
			throw new IllegalArgumentException("Argument 'entries' can not be null.");

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			for (final Country entry : entries)
				entityManager.persist(entry);
			entityManager.getTransaction().commit();
		} catch (final RuntimeException e1) {
			if (entityManager.getTransaction().isActive())
				try {
					entityManager.getTransaction().rollback();
				} catch (final RuntimeException e2) {
					// Log rollback failure or something
					throw e2;
				}
			throw e1;
		} finally {
			if (entityManager != null) {
				entityManager.clear();
				entityManager.close();
			}
		}
	}

	public void persist(final Country entry) {
		if (entry == null)
			throw new IllegalArgumentException("Argument 'entry' must be set.");

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(entry);
			entityManager.getTransaction().commit();
		} catch (final RuntimeException e1) {
			if (entityManager.getTransaction().isActive())
				try {
					entityManager.getTransaction().rollback();
				} catch (final RuntimeException e2) {
					// Log rollback failure or something
					throw e2;
				}
			throw e1;
		} finally {
			if (entityManager != null) {
				entityManager.clear();
				entityManager.close();
			}
		}
	}

	public void remove(final List<Country> entries) {
		if (entries == null || entries.isEmpty())
			throw new IllegalArgumentException("Argument 'entries' can not be null or empty.");

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<Country> attached = new ArrayList<Country>(entries.size());
		for (final Country entry : entries)
			attached.add(entityManager.find(Country.class, entry.getId()));
		entityManager.getTransaction().begin();
		try {
			for (final Country entry : attached)
				entityManager.remove(entry);
			entityManager.getTransaction().commit();
		} catch (final RuntimeException e1) {
			if (entityManager.getTransaction().isActive())
				try {
					entityManager.getTransaction().rollback();
				} catch (final RuntimeException e2) {
					// Log rollback failure or something
					throw e2;
				}
			throw e1;
		} finally {
			if (entityManager != null) {
				entityManager.clear();
				entityManager.close();
			}
		}
	}

	public void remove(final Country entry) {
		if (entry == null)
			throw new IllegalArgumentException("Argument 'entry' can not be null.");

		if (entry.getId() == null)
			throw new IllegalArgumentException("The ID for an log entry can not be null.");

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Country attached = entityManager.find(Country.class, entry.getId());
		entityManager.getTransaction().begin();
		try {
			entityManager.remove(attached);
			entityManager.getTransaction().commit();
		} catch (final RuntimeException e1) {
			if (entityManager.getTransaction().isActive())
				try {
					entityManager.getTransaction().rollback();
				} catch (final RuntimeException e2) {
					// Log rollback failure or something
					throw e2;
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
		LOG.debug("Starting country repository...");
	}

	public void stop() {
		LOG.debug("Stopping country repository...");

		if (entityManagerFactory != null)
			entityManagerFactory.close();
	}

}
