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
package net.sf.jacclog.service.repository;

import java.util.Collection;
import java.util.List;

import net.sf.jacclog.service.repository.domain.LogEntry;

import org.joda.time.Interval;

public interface LogEntryRepositoryService<E extends LogEntry> {

	/**
	 * Counts all elements within an interval.
	 * 
	 * @return Count of log entries
	 */
	long count(final Interval interval);

	/**
	 * Counts all log entries.
	 * 
	 * @return Count of all log entries
	 */
	long countAll();

	/**
	 * Stores all log entries.
	 * 
	 * @param entries
	 */
	void create(Collection<E> entries);

	/**
	 * Stores a log entry.
	 * 
	 * @param entry
	 */
	void create(E entry);

	/**
	 * Deletes a log entry.
	 * 
	 * @param entry
	 */
	void delete(E entry);

	/**
	 * Reads log entries in the specific range by specifying a starting position and a maximum number of results.
	 * 
	 * @param startPosition
	 *            Position of the first result, numbered from 0
	 * @param maxResults
	 *            Maximum number of results to retrieve
	 * @return A list of log entries
	 */
	List<E> read(final int startPosition, final int maxResults);

	/**
	 * Reads all elements within an interval.
	 * 
	 * @return A list of log entries
	 */
	List<E> read(final Interval interval);

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
	 * @return A list of log entries
	 */
	List<E> read(final Interval interval, final int startPosition, final int maxResults);

	/**
	 * Read a log entry.
	 * 
	 * @param id
	 *            ID of an entry
	 * @return A list of log entries
	 */
	E read(final Long id);

	/**
	 * Reads all log entries.
	 * 
	 * @return A list of log entries
	 */
	List<E> readAll();

	/**
	 * Updates a log entry.
	 * 
	 * @param entry
	 * @return An updated log entry
	 */
	E update(E entry);

}
