package net.sf.jacclog.service.importer.internal.task;

import java.util.Collection;

import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.service.importer.api.service.LogEntryImportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEntriesPersisterTask implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(LogEntriesPersisterTask.class);

	private final Collection<ReadonlyLogEntry> entries;

	private final LogEntryImportService<ReadonlyLogEntry> service;

	public LogEntriesPersisterTask(final LogEntryImportService<ReadonlyLogEntry> service,
			final Collection<ReadonlyLogEntry> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("Argument 'entries' can not be null.");
		}

		if (service == null) {
			throw new IllegalArgumentException("Argument 'service' can not be null.");
		}

		this.entries = entries;
		this.service = service;
	}

	@Override
	public void run() {
		if (!entries.isEmpty()) {
			try {
				service.create(entries);
			} catch (final Exception e) {
				LOG.warn(this.getClass().getName() + " failed: " + e.getLocalizedMessage(), e);
			}
		}
	}

}
