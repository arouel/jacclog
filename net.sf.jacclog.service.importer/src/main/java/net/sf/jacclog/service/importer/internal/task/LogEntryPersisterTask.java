package net.sf.jacclog.service.importer.internal.task;

import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.service.repository.LogEntryRepositoryService;
import net.sf.jacclog.service.repository.domain.PersistableLogEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEntryPersisterTask implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(LogEntryPersisterTask.class);

	private final ReadonlyLogEntry entry;

	private final LogEntryRepositoryService<PersistableLogEntry> service;

	public LogEntryPersisterTask(final LogEntryRepositoryService<PersistableLogEntry> service, final ReadonlyLogEntry entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' can not be null.");
		}

		if (service == null) {
			throw new IllegalArgumentException("Argument 'service' can not be null.");
		}

		this.entry = entry;
		this.service = service;
	}

	@Override
	public void run() {
		try {
			service.create(entry);
		} catch (RuntimeException e) {
			LOG.warn(this.getClass().getName() + " failed: " + e.getLocalizedMessage());
		}
	}

	public static class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

		private static final Logger LOG = LoggerFactory.getLogger(UncaughtExceptionHandler.class);

		@Override
		public void uncaughtException(final Thread thread, final Throwable throwable) {
			LOG.warn("An unexpected error has occurred in Thread '" + thread.getName() + "'.", throwable);
		}

	}

}
