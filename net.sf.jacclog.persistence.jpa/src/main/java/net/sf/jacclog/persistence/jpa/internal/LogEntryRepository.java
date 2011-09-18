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
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;

import net.sf.jacclog.persistence.jpa.LogEntry;

import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEntryRepository {

	private static final Logger LOG = LoggerFactory.getLogger(LogEntryRepository.class);

	private static final String PERSISTENCE_UNIT_NAME = "jacclogPU";

	private final EntityManagerFactory entityManagerFactory;

	public LogEntryRepository() {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public LogEntryRepository(final Map<String, String> properties) {
		if (properties == null)
			throw new IllegalArgumentException("Argument 'properties' can not be null.");

		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
	}

	/**
	 * Count all log entries within a start and end date.
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
				.createQuery("SELECT count(o) FROM LogEntry o WHERE o.finishedRequestAt between :start and :end",
						Long.class).setParameter("start", start, TemporalType.DATE)
				.setParameter("end", end, TemporalType.DATE).getSingleResult();
		entityManager.close();
		return count;
	}

	/**
	 * Count all log entries within an interval
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
		final Long count = entityManager.createQuery("SELECT count(o) FROM LogEntry o", Long.class).getSingleResult();
		entityManager.close();
		return count;
	}

	/**
	 * Find all log entries within a start and end date.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<LogEntry> find(final Date start, final Date end) {
		if (start == null)
			throw new IllegalArgumentException("Argument 'start' can not be null.");

		if (end == null)
			throw new IllegalArgumentException("Argument 'end' can not be null.");

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<LogEntry> entries = entityManager
				.createQuery("SELECT o FROM LogEntry o WHERE o.finishedRequestAt between :start and :end",
						LogEntry.class).setParameter("start", start, TemporalType.DATE)
				.setParameter("end", end, TemporalType.DATE).getResultList();
		entityManager.close();
		return entries;
	}

	/**
	 * Finds all log entries between a start and end date ordered by ID. In addition by specifying a starting position
	 * and maximum number of results it restricts the size of the result set.<br>
	 * <br>
	 * The end date should be greater than start date.
	 * 
	 * @param start
	 *            Start date
	 * @param end
	 *            End date
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
	 * @return A list of log entries
	 */
	public List<LogEntry> find(final Date start, final Date end, final int startPosition, final int maxResults) {
		if (start == null)
			throw new IllegalArgumentException("Argument 'start' can not be null.");

		if (end == null)
			throw new IllegalArgumentException("Argument 'end' can not be null.");

		if (start.after(end))
			throw new IllegalArgumentException("The end date should be greater than the start date.");

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<LogEntry> entries = entityManager
				.createQuery(
						"SELECT o FROM LogEntry o WHERE o.finishedRequestAt BETWEEN :start and :end ORDER BY o.id",
						LogEntry.class).setParameter("start", start, TemporalType.DATE)
				.setParameter("end", end, TemporalType.DATE).setFirstResult(startPosition).setMaxResults(maxResults)
				.getResultList();
		entityManager.close();
		return entries;
	}

	/**
	 * Finds all log entries in the specific range by specifying a starting position and a maximum number of results.
	 * 
	 * @param startPosition
	 *            Position of the first result, numbered from 0
	 * @param maxResults
	 *            Maximum number of results to retrieve
	 * @return A list of log entries
	 */
	public List<LogEntry> find(final int startPosition, final int maxResults) {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<LogEntry> entries = entityManager.createQuery("SELECT o FROM LogEntry o", LogEntry.class)
				.setFirstResult(startPosition).setMaxResults(maxResults).getResultList();
		entityManager.close();
		return entries;
	}

	/**
	 * Finds all log entries between an interval.
	 * 
	 * @param interval
	 *            A period of time between two dates.
	 * @return A list of log entries
	 */
	public List<LogEntry> find(final Interval interval) {
		if (interval == null)
			throw new IllegalArgumentException("Argument 'interval' can not be null.");

		return find(interval.getStart().toDate(), interval.getEnd().toDate());
	}

	/**
	 * Finds all log entries between an interval and ordered by ID. In addition by specifying a starting position and a
	 * maximum number of results it restricts the size of the result set.
	 * 
	 * @param interval
	 *            A period of time between two dates.
	 * @param startPosition
	 *            Position of the first result, numbered from 0
	 * @param maxResults
	 *            Maximum number of results to retrieve
	 * @throws IllegalArgumentException
	 *             If the interval is null
	 * @return A list of log entries
	 */
	public List<LogEntry> find(final Interval interval, final int startPosition, final int maxResults) {
		if (interval == null)
			throw new IllegalArgumentException("Argument 'interval' can not be null.");

		return find(interval.getStart().toDate(), interval.getEnd().toDate(), startPosition, maxResults);
	}

	public LogEntry find(final Long id) {
		if (id == null)
			throw new IllegalArgumentException("Argument 'id' can not be null.");

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final LogEntry entry = entityManager.find(LogEntry.class, id);
		entityManager.close();
		return entry;
	}

	public List<LogEntry> findAll() {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<LogEntry> entries = entityManager.createQuery("SELECT o FROM LogEntry o", LogEntry.class)
				.getResultList();
		entityManager.close();
		return entries;
	}

	public LogEntry merge(final LogEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Argument 'entry' must be set.");

		LogEntry merged = null;
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

	public void persist(final List<LogEntry> entries) {
		if (entries == null)
			throw new IllegalArgumentException("Argument 'entries' can not be null.");

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			for (final LogEntry entry : entries)
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

	public void persist(final LogEntry entry) {
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

	public void remove(final List<LogEntry> entries) {
		if (entries == null || entries.isEmpty())
			throw new IllegalArgumentException("Argument 'entries' can not be null or empty.");

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final List<LogEntry> attached = new ArrayList<LogEntry>(entries.size());
		for (final LogEntry entry : entries)
			attached.add(entityManager.find(LogEntry.class, entry.getId()));
		entityManager.getTransaction().begin();
		try {
			for (final LogEntry entry : attached)
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

	public void remove(final LogEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Argument 'entry' can not be null.");

		if (entry.getId() == null)
			throw new IllegalArgumentException("The ID for an log entry can not be null.");

		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final LogEntry attached = entityManager.find(LogEntry.class, entry.getId());
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
		LOG.debug("Starting LogEntryRepository...");
	}

	public void stop() {
		LOG.debug("Closing EntityManagerFactory of LogEntryRepository...");

		if (entityManagerFactory != null)
			entityManagerFactory.close();
	}

}
