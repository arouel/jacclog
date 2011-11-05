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
package net.sf.jacclog.service.importer.internal.queue;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.service.importer.api.service.LogEntryImportService;
import net.sf.jacclog.util.observer.BlockingQueueObserver;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEntryQueuePersisterObserver implements BlockingQueueObserver<ReadonlyLogEntry> {

	public static class LogEntryPersisterTask implements Runnable {

		private static final int MAX_ATTEMPTS = 3;

		private final BlockingQueue<ReadonlyLogEntry> queue;

		private final LogEntryImportService<ReadonlyLogEntry> service;

		public LogEntryPersisterTask(final LogEntryImportService<ReadonlyLogEntry> service,
				final BlockingQueue<ReadonlyLogEntry> queue) {
			if (queue == null) {
				throw new IllegalArgumentException("Argument 'queue' can not be null.");
			}

			if (service == null) {
				throw new IllegalArgumentException("Argument 'service' can not be null.");
			}

			this.queue = queue;
			this.service = service;
		}

		private boolean persist(final Collection<ReadonlyLogEntry> entries) {
			boolean result = false;
			try {
				service.create(entries);
				result = true;
			} catch (final Exception e) {
				// no problem with an exception here, we try it multiple times
				LOG.info("Persisting failed: " + e.getLocalizedMessage());
			}
			return result;
		}

		private boolean persistWithMultipleAttempts(final Collection<ReadonlyLogEntry> entries) {
			boolean isPersisted = false;
			if (!entries.isEmpty()) {
				for (int i = 0; i < MAX_ATTEMPTS; i++) {
					isPersisted = persist(entries);
					if (isPersisted) {
						break;
					}
				}
			}
			return isPersisted;
		}

		@Override
		public void run() {
			if (!queue.isEmpty()) {

				// pause for a while so that more entries can be persist
				try {
					Thread.sleep(100l);
				} catch (final InterruptedException e1) {
					LOG.warn(e1.getLocalizedMessage(), e1);
				}

				// take all available entries from the queue
				final Collection<ReadonlyLogEntry> entries = new ArrayDeque<ReadonlyLogEntry>();
				ReadonlyLogEntry entry = null;
				int count = 0;
				do {
					entry = queue.poll();
					if (entry != null) {
						entries.add(entry);
						count++;
					}
				} while (entry != null && count < BATCH_SIZE);

				if (!entries.isEmpty()) {
					if (!persistWithMultipleAttempts(entries)) {
						LOG.warn(entries.size() + " entries were not stored in the repository.");
					}
				}
			}
		}

	}

	public static class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

		private static final Logger LOG = LoggerFactory.getLogger(UncaughtExceptionHandler.class);

		@Override
		public void uncaughtException(final Thread thread, final Throwable throwable) {
			LOG.warn("An unexpected error has occurred in Thread '" + thread.getName() + "'.", throwable);
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(LogEntryQueuePersisterObserver.class);

	private final ExecutorService executor;

	private final LogEntryImportService<ReadonlyLogEntry> service;

	private final AtomicInteger counter = new AtomicInteger();

	private static final int BATCH_SIZE = 1000;

	public LogEntryQueuePersisterObserver(final LogEntryImportService<ReadonlyLogEntry> service) {
		if (service == null) {
			throw new IllegalArgumentException("Argument 'service' can not be null.");
		}

		this.service = service;

		final BasicThreadFactory factory = new BasicThreadFactory.Builder()
				// attributes
				.namingPattern("persister-%d").daemon(true).priority(Thread.MAX_PRIORITY)
				.uncaughtExceptionHandler(new UncaughtExceptionHandler()).build();
		executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), factory);
	}

	@Override
	public void added(final BlockingQueue<ReadonlyLogEntry> queue, final ReadonlyLogEntry element) {
		LOG.debug("Added entry '" + element.hashCode() + "' to queue.");

		if (counter.decrementAndGet() <= 0) {
			counter.set(BATCH_SIZE);
			executor.execute(new LogEntryPersisterTask(service, queue));
		}
	}

	@Override
	public void empty(final BlockingQueue<ReadonlyLogEntry> queue) {
		LOG.debug("Log entry queue is empty. (size: " + queue.size() + ")");

		// cleaning task
		executor.execute(new LogEntryPersisterTask(service, queue));
	}

	@Override
	public void removed(final BlockingQueue<ReadonlyLogEntry> queue, final ReadonlyLogEntry entry) {
		LOG.debug("Removed entry '" + entry.hashCode() + "' from queue.");
	}

}
