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

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.jacclog.service.importer.api.LogFile;

/**
 * Thread safe implementation of statistical informations about log file importation.
 * 
 * @author André Rouél
 */
public final class LogFileImporterStatistic implements net.sf.jacclog.service.importer.api.LogFileImporterStatistic {

	private final List<LogFile> files = new CopyOnWriteArrayList<LogFile>();

	private final List<Entry> entries = new CopyOnWriteArrayList<LogFileImporterStatistic.Entry>();

	private static final LogFileImporterStatistic INSTANCE = new LogFileImporterStatistic();

	public static LogFileImporterStatistic getInstance() {
		return INSTANCE;
	}

	/**
	 * Import service for log entries
	 */
	// private final LogEntryImportService<LogEntry> service;

	private LogFileImporterStatistic() {
	}

	// public LogFileImporterStatistic(final LogEntryImportService<LogEntry> service) {
	// if (service == null) {
	// throw new IllegalArgumentException("Argument 'service' can not be null.");
	// }
	//
	// this.service = service;
	// }

	@Override
	public void addEntry(final Entry entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' can not be null.");
		}

		entries.add(entry);
	}

	public void addLogFile(final LogFile file) {
		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' can not be null.");
		}

		files.add(file);
	}

	@Override
	public List<Entry> getEntries() {
		return Collections.unmodifiableList(entries);
	}

	@Override
	public int getImportedLogEntryCount() {
		final int result = -1;
		// if (service.getQueue().getTotalElementCountCounter() != null) {
		// result = service.getQueue().getTotalElementCountCounter().getCount();
		// }
		return result;
	}

	@Override
	public List<LogFile> getImportedLogFiles() {
		return Collections.unmodifiableList(files);
	}

	public void reset() {
		entries.clear();
		files.clear();
	}

}
