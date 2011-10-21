package net.sf.jacclog.service.importer.internal.task;

import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.service.importer.api.service.LogEntryImportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEntryPersisterTask implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(LogEntriesPersisterTask.class);

	private final ReadonlyLogEntry entry;

	private final LogEntryImportService<ReadonlyLogEntry> service;

	public LogEntryPersisterTask(final LogEntryImportService<ReadonlyLogEntry> service, final ReadonlyLogEntry entry) {
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
		} catch (final Exception e) {
			LOG.warn(this.getClass().getName() + " failed: " + e.getLocalizedMessage(), e);
		}
	}

}
