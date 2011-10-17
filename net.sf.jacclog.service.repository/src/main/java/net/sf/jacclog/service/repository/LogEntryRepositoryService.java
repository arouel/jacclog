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

import net.sf.jacclog.api.LogEntryService;
import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.service.repository.domain.PersistableLogEntry;

public interface LogEntryRepositoryService<E extends PersistableLogEntry> extends LogEntryService<E> {

	/**
	 * Stores all log entries.
	 * 
	 * @param entries
	 */
	void create(Collection<E> entries);

	/**
	 * Stores an array of immutable log entries in the repository.
	 * 
	 * @param entries
	 *            the array of immutable log entries
	 */
	void create(final ReadonlyLogEntry[] entries);

	/**
	 * Stores a log entry.
	 * 
	 * @param entry
	 */
	void create(E entry);

	/**
	 * Stores an immutable log entry in the repository.
	 * 
	 * @param entry
	 */
	void create(final ReadonlyLogEntry entry);

	/**
	 * Deletes a log entry.
	 * 
	 * @param entry
	 */
	void delete(E entry);

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
