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
package net.sf.jacclog.service.importer.api.service;

import java.util.Collection;

import net.sf.jacclog.service.importer.api.LogFile;
import net.sf.jacclog.service.importer.api.queue.LogEntryQueue;
import net.sf.jacclog.service.repository.LogEntry;

public interface LogEntryImportService<E extends LogEntry> {

	/**
	 * Creates all log entry over the repository service.
	 * 
	 * @param entry
	 *            list of log entries
	 */
	void create(Collection<E> entries);

	/**
	 * Creates a log entry over the repository service.
	 * 
	 * @param entry
	 *            log entry
	 */
	void create(E entry);

	/**
	 * Gets the queue of log entries to be imported.
	 * 
	 * @return Queue of log entries
	 */
	LogEntryQueue<E> getQueue();

	/**
	 * Imports all log entries of a log file.
	 * 
	 * @param file
	 */
	void importLogEntries(final LogFile file);

}
