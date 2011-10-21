package net.sf.jacclog.service.importer.internal.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jsr166y.RecursiveAction;
import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.service.repository.LogEntryRepositoryService;
import net.sf.jacclog.service.repository.domain.PersistableLogEntry;

public class LogEntryImportTask extends RecursiveAction {

	private static final long serialVersionUID = -6454935344529380741L;

	protected static final int THRESHOLD = 2;

	private final List<ReadonlyLogEntry> entries;

	private final int maxResults;

	private final LogEntryRepositoryService<PersistableLogEntry> service;

	private final int startPosition;

	public LogEntryImportTask(final LogEntryRepositoryService<PersistableLogEntry> service,
			final Collection<ReadonlyLogEntry> entries, final int startPosition, final int maxResults) {
		this(service, new ArrayList<ReadonlyLogEntry>(entries), startPosition, maxResults);
	}

	public LogEntryImportTask(final LogEntryRepositoryService<PersistableLogEntry> service,
			final List<ReadonlyLogEntry> entries, final int startPosition, final int maxResults) {

		if (service == null) {
			throw new IllegalArgumentException("Argument 'service' can not be null.");
		}

		if (entries == null) {
			throw new IllegalArgumentException("Argument 'entries' can not be null.");
		}

		if (startPosition < 0) {
			throw new IllegalArgumentException("Argument 'startPosition' can not be smaller than 0.");
		}

		if (maxResults < 1) {
			throw new IllegalArgumentException("Argument 'maxResults' can not be smaller than 1.");
		}

		this.service = service;
		this.entries = entries;
		this.startPosition = startPosition;
		this.maxResults = maxResults;
	}

	@Override
	protected void compute() {
		if (maxResults < THRESHOLD) {
			final List<ReadonlyLogEntry> entries = this.entries.subList(startPosition, startPosition + maxResults);
			if (entries != null && !entries.isEmpty()) {
				service.create(entries.get(0));
			}
		} else {
			final int midpoint = maxResults / 2;
			final LogEntryImportTask a1 = new LogEntryImportTask(service, this.entries, startPosition, midpoint);
			final LogEntryImportTask a2 = new LogEntryImportTask(service, this.entries, startPosition + midpoint,
					maxResults - midpoint);
			invokeAll(a1, a2);
		}
	}

}
