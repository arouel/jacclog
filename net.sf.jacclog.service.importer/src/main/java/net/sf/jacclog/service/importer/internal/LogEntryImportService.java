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
package net.sf.jacclog.service.importer.internal;

import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.service.importer.api.LogFile;
import net.sf.jacclog.service.importer.api.LogFileImporterStatistic.Entry;
import net.sf.jacclog.service.importer.api.service.AbstractLogEntryImportService;
import net.sf.jacclog.service.importer.internal.parser.MappingException;
import net.sf.jacclog.service.importer.internal.queue.LogEntryQueue;
import net.sf.jacclog.service.importer.internal.queue.LogEntryQueuePersisterObserver;
import net.sf.jacclog.service.repository.LogEntryRepositoryService;
import net.sf.jacclog.service.repository.domain.PersistableLogEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEntryImportService extends AbstractLogEntryImportService {

	private static final Logger LOG = LoggerFactory.getLogger(LogEntryImportService.class);

	public LogEntryImportService(final LogEntryRepositoryService<PersistableLogEntry> service) {
		super(service);
		registerPersisterTask();
	}

	public LogEntryImportService(final LogEntryRepositoryService<PersistableLogEntry> service, final LogEntryQueue queue) {
		super(service, queue);
		registerPersisterTask();
	}

	@Override
	public void importLogEntries(final LogFile file) {
		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' can not be null.");
		}

		final long startTime = System.currentTimeMillis();

		int count = 0;
		final LogFileReader reader = new LogFileReader(file);
		while (true) {
			ReadonlyLogEntry entry = null;
			try {
				entry = reader.readEntry();
			} catch (final MappingException e) {
				final String prefix = (file != null) ? file.getFile().getPath() + " " : "";
				LOG.warn(prefix + e.getLocalizedMessage());
			}

			if (entry == null) {
				break;
			} else {
				try {
					count++;
					getQueue().put(entry);
				} catch (final InterruptedException e) {
					LOG.warn(e.getLocalizedMessage());
				}
			}
		}

		final long elapsedTime = System.currentTimeMillis() - startTime;
		final Entry entry = new Entry(file, count, elapsedTime);
		LogFileImporterStatistic.getInstance().addEntry(entry);
	}

	private void registerPersisterTask() {
		getQueue().addObserver(new LogEntryQueuePersisterObserver(this));
	}

}
