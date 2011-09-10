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
package net.sf.jacclog.repository.jpa.internal;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import net.sf.jacclog.repository.jpa.LogEntry;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Repository
public class LogEntryRepository {

	@PersistenceContext
	protected transient EntityManager entityManager;

	/**
	 * Clear the persistence context, causing all managed entities to become detached. Changes made to entities that
	 * have not been flushed to the database will not be persisted.
	 */
	public void clear() {
		if (entityManager == null) {
			entityManager = getEntityManager();
		}
		entityManager.clear();
	}

	/**
	 * Count all log entries within a start and end date.
	 * 
	 * @param period
	 * @return
	 */
	public long count(final Date start, final Date end) {
		if (start == null) {
			throw new IllegalArgumentException("Argument 'start' can be null.");
		}

		if (end == null) {
			throw new IllegalArgumentException("Argument 'end' can be null.");
		}

		if (entityManager == null) {
			entityManager = getEntityManager();
		}
		return getEntityManager()
				.createQuery("SELECT count(o) FROM LogEntry o WHERE o.finishedRequestAt between :start and :end",
						Long.class).setParameter("start", start, TemporalType.DATE)
				.setParameter("end", end, TemporalType.DATE).getSingleResult();
	}

	/**
	 * Count all log entries within an interval
	 * 
	 * @param interval
	 * @return
	 */
	public long count(final Interval interval) {
		if (interval == null) {
			throw new IllegalArgumentException("Argument 'interval' can be null.");
		}
		return count(interval.getStart().toDate(), interval.getEnd().toDate());
	}

	public long countAll() {
		if (entityManager == null) {
			entityManager = getEntityManager();
		}
		return getEntityManager().createQuery("SELECT count(o) FROM LogEntry o", Long.class).getSingleResult();
	}

	/**
	 * Find all log entries within a start and end date.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<LogEntry> find(final Date start, final Date end) {
		if (start == null) {
			throw new IllegalArgumentException("Argument 'start' can be null.");
		}

		if (end == null) {
			throw new IllegalArgumentException("Argument 'end' can be null.");
		}

		if (entityManager == null) {
			entityManager = getEntityManager();
		}
		return getEntityManager()
				.createQuery("SELECT o FROM LogEntry o WHERE o.finishedRequestAt between :start and :end",
						LogEntry.class).setParameter("start", start, TemporalType.DATE)
				.setParameter("end", end, TemporalType.DATE).getResultList();
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
		if (start == null) {
			throw new IllegalArgumentException("Argument 'start' can be null.");
		}

		if (end == null) {
			throw new IllegalArgumentException("Argument 'end' can be null.");
		}

		if (start.after(end)) {
			throw new IllegalArgumentException("The end date should be greater than the start date.");
		}

		if (entityManager == null) {
			entityManager = getEntityManager();
		}

		return getEntityManager()
				.createQuery(
						"SELECT o FROM LogEntry o WHERE o.finishedRequestAt BETWEEN :start and :end ORDER BY o.id",
						LogEntry.class).setParameter("start", start, TemporalType.DATE)
				.setParameter("end", end, TemporalType.DATE).setFirstResult(startPosition).setMaxResults(maxResults)
				.getResultList();
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
		if (entityManager == null) {
			entityManager = getEntityManager();
		}
		return getEntityManager().createQuery("SELECT o FROM LogEntry o", LogEntry.class).setFirstResult(startPosition)
				.setMaxResults(maxResults).getResultList();
	}

	/**
	 * Finds all log entries between an interval.
	 * 
	 * @param interval
	 *            A period of time between two dates.
	 * @return A list of log entries
	 */
	public List<LogEntry> find(final Interval interval) {
		if (interval == null) {
			throw new IllegalArgumentException("Argument 'interval' can be null.");
		}
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
		if (interval == null) {
			throw new IllegalArgumentException("Argument 'interval' can be null.");
		}
		return find(interval.getStart().toDate(), interval.getEnd().toDate(), startPosition, maxResults);
	}

	public LogEntry find(final Long id) {
		if (entityManager == null) {
			entityManager = getEntityManager();
		}

		LogEntry entry = null;
		if (id != null) {
			entry = getEntityManager().find(LogEntry.class, id);
		}
		return entry;
	}

	public List<LogEntry> findAll() {
		if (entityManager == null) {
			entityManager = getEntityManager();
		}
		return getEntityManager().createQuery("SELECT o FROM LogEntry o", LogEntry.class).getResultList();
	}

	/**
	 * Synchronize the persistence context to the underlying database.
	 */
	public void flush() {
		if (entityManager == null) {
			entityManager = getEntityManager();
		}
		entityManager.flush();
	}

	public EntityManager getEntityManager() {
		final EntityManager manager = entityManager;
		if (manager == null) {
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		}
		return manager;
	}

	@Transactional
	public LogEntry merge(final LogEntry entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' must be set.");
		}

		if (entityManager == null) {
			entityManager = getEntityManager();
		}
		final LogEntry merged = entityManager.merge(entry);
		entityManager.flush();
		return merged;
	}

	@Transactional
	public void persist(final List<LogEntry> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("Argument 'entries' can not be null.");
		}

		if (entityManager == null) {
			entityManager = getEntityManager();
		}

		for (final LogEntry entry : entries) {
			entityManager.persist(entry);
		}
	}

	@Transactional
	public void persist(final LogEntry entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' must be set.");
		}

		if (entityManager == null) {
			entityManager = getEntityManager();
		}

		entityManager.persist(entry);
	}

	@Transactional
	public void remove(final List<LogEntry> entries) {
		if (entries == null || entries.isEmpty()) {
			throw new IllegalArgumentException("Argument 'entries' can not be null or empty.");
		}

		if (entityManager == null) {
			entityManager = getEntityManager();
		}

		for (final LogEntry entry : entries) {
			final LogEntry attached = find(entry.getId());
			entityManager.remove(attached);
		}
	}

	@Transactional
	public void remove(final LogEntry entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' must be set.");
		}

		if (entityManager == null) {
			entityManager = getEntityManager();
		}

		final LogEntry attached = find(entry.getId());
		entityManager.remove(attached);
	}

}
