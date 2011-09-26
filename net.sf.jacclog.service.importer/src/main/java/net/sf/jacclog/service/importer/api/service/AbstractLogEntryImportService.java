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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.jacclog.service.importer.api.LogFile;
import net.sf.jacclog.service.importer.internal.queue.LogEntryQueue;
import net.sf.jacclog.service.repository.LogEntryRepositoryService;
import net.sf.jacclog.service.repository.domain.LogEntry;
import net.sf.jacclog.util.observer.CurrentElementCounter;
import net.sf.jacclog.util.observer.TotalElementCounter;

public abstract class AbstractLogEntryImportService implements
		net.sf.jacclog.service.importer.api.service.LogEntryImportService<LogEntry> {

	/**
	 * Repository services for log entries
	 */
	private final List<LogEntryRepositoryService<LogEntry>> services = new CopyOnWriteArrayList<LogEntryRepositoryService<LogEntry>>();

	/**
	 * Queue of entries to persist within the repository services
	 */
	private final net.sf.jacclog.service.importer.api.queue.LogEntryQueue<LogEntry> queue;

	/**
	 * Default queue capacity
	 */
	private static final int DEFAULT_CAPACITY = 10000;

	public AbstractLogEntryImportService(final LogEntryRepositoryService<LogEntry> service) {
		this(service, new LogEntryQueue(DEFAULT_CAPACITY));
	}

	public AbstractLogEntryImportService(final LogEntryRepositoryService<LogEntry> service, final LogEntryQueue queue) {
		if (service == null) {
			throw new IllegalArgumentException("Argument 'service' can not be null.");
		}

		if (queue == null) {
			throw new IllegalArgumentException("Argument 'queue' can not be null.");
		}

		services.add(service);
		this.queue = queue;

		registerDefaultObserver();
	}

	@Override
	public void create(final Collection<LogEntry> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("Argument 'entries' can not be null.");
		}

		for (final LogEntryRepositoryService<LogEntry> service : services) {
			service.create(entries);
		}
	}

	@Override
	public void create(final LogEntry entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' must be not null.");
		}

		for (final LogEntryRepositoryService<LogEntry> service : services) {
			service.create(entry);
		}
	}

	@Override
	public net.sf.jacclog.service.importer.api.queue.LogEntryQueue<LogEntry> getQueue() {
		return queue;
	}

	@Override
	public abstract void importLogEntries(final LogFile file);

	/**
	 * Register default observers to the log entry queue.<br>
	 * <br>
	 * Following observers will be registered
	 * <ul>
	 * <li><code>CurrentElementCounter</code></li>
	 * <li><code>TotalElementCounter</code></li>
	 * </ul>
	 */
	private void registerDefaultObserver() {
		final CurrentElementCounter<LogEntry> currentCounter = new CurrentElementCounter<LogEntry>();
		final TotalElementCounter<LogEntry> totalCounter = new TotalElementCounter<LogEntry>();
		queue.addObserver(currentCounter);
		queue.addObserver(totalCounter);
	}

}
