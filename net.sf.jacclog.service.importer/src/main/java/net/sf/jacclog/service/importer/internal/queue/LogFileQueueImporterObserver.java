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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jacclog.service.importer.api.LogFile;
import net.sf.jacclog.service.importer.api.service.LogEntryImportService;
import net.sf.jacclog.service.repository.LogEntry;
import net.sf.jacclog.util.observer.BlockingQueueObserver;

public class LogFileQueueImporterObserver implements BlockingQueueObserver<LogFile> {

	public static class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

		private static final Logger LOG = LoggerFactory.getLogger(UncaughtExceptionHandler.class);

		@Override
		public void uncaughtException(final Thread thread, final Throwable throwable) {
			LOG.warn("An unexpected error has occurred in Thread '" + thread.getName() + "'.", throwable);
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(LogFileQueueImporterObserver.class);

	private final ExecutorService executor;

	/**
	 * Import service for log entries
	 */
	private final LogEntryImportService<LogEntry> service;

	public LogFileQueueImporterObserver(final LogEntryImportService<LogEntry> service) {
		if (service == null) {
			throw new IllegalArgumentException("Argument 'service' must be not null.");
		}

		this.service = service;

		final BasicThreadFactory factory = new BasicThreadFactory.Builder()
				// attributes
				.namingPattern("file-importer-%d").daemon(true).priority(Thread.MIN_PRIORITY)
				.uncaughtExceptionHandler(new UncaughtExceptionHandler()).build();
		executor = Executors.newSingleThreadExecutor(factory);
	}

	@Override
	public void added(final BlockingQueue<LogFile> queue, final LogFile file) {
		LOG.debug("Added file '" + file.getFile().getPath() + "' to queue.");

		executor.execute(new Runnable() {
			@Override
			public void run() {
				final LogFile file = queue.poll();
				service.importLogEntries(file);
			}
		});
	}

	@Override
	public void empty(final BlockingQueue<LogFile> queue) {
		LOG.info("Log file queue is is empty. (size: " + queue.size() + ")");
	}

	@Override
	public void removed(final BlockingQueue<LogFile> queue, final LogFile file) {
		LOG.debug("Removed file '" + file.getFile().getPath() + "' from queue.");
	}

}
