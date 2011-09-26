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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jacclog.logformat.LogFormat;
import net.sf.jacclog.service.importer.api.LogFile;
import net.sf.jacclog.service.importer.api.service.LogEntryImportService;
import net.sf.jacclog.service.importer.internal.queue.LogFileQueue;
import net.sf.jacclog.service.importer.internal.queue.LogFileQueueImporterObserver;
import net.sf.jacclog.service.repository.domain.LogEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogFileImporter implements net.sf.jacclog.service.importer.api.LogFileImporter {

	private static final Logger LOG = LoggerFactory.getLogger(LogFileImporter.class);

	/**
	 * Adds a file or a directory with files to a list.
	 * 
	 * @param file
	 * @return a list of files
	 */
	public static List<File> addFileToList(final File file) {
		return addFileToList(file, false);
	}

	/**
	 * Adds a file or a directory with files (recursively) to a list.
	 * 
	 * @param file
	 * @param recursive
	 * @return a list of files
	 */
	public static List<File> addFileToList(final File file, final boolean recursive) {
		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' can not be null.");
		}

		if (!file.exists()) {
			throw new IllegalArgumentException("File or directory '" + file.getPath() + "' doesn't exist.");
		}

		final List<File> result = new ArrayList<File>();
		if (file.isDirectory()) {
			for (final File f : file.listFiles()) {
				if (f.isDirectory() && recursive) {
					result.addAll(addFileToList(f, recursive));
				}
				if (f.isFile()) {
					result.add(f);
				}
			}
		}

		if (file.isFile()) {
			result.add(file);
		}

		return result;
	}

	/**
	 * Converts a list of files with a given format into a list of log files.
	 * 
	 * @param format
	 *            format of log files
	 * @param files
	 *            list of files
	 * @return list of log files
	 */
	private static List<LogFile> convertToLogFile(final LogFormat format, final List<File> files) {
		final List<LogFile> result = new ArrayList<LogFile>();
		for (final File file : files) {
			result.add(new LogFile(format, file));
		}
		return result;
	}

	/**
	 * Queue of files to be imported
	 */
	private final LogFileQueue files;

	public LogFileImporter(final int capacity, final LogEntryImportService<LogEntry> service) {
		if (service == null) {
			throw new IllegalArgumentException("Argument 'service' can not be null.");
		}

		files = new LogFileQueue(capacity);
		files.addObserver(new LogFileQueueImporterObserver(service));
	}

	/**
	 * This constructor is not really necessary, but Apache Aries Blueprint has a bug that matches to an interface not
	 * correct.
	 * 
	 * @param capacity
	 * @param service
	 */
	public LogFileImporter(final int capacity,
			final net.sf.jacclog.service.importer.internal.LogEntryImportService service) {
		this(capacity, (LogEntryImportService<LogEntry>) service);
	}

	@Override
	public net.sf.jacclog.service.importer.api.LogFileImporterStatistic getStatistic() {
		// TODO return an unmodifiable statistic
		return LogFileImporterStatistic.getInstance();
	}

	/**
	 * Imports a log file or directory in the log file queue.
	 * 
	 * @param format
	 * @param file
	 */
	public void importFile(final LogFormat format, final File file) {
		importFile(format, file, false);
	}

	/**
	 * Imports (recursively) a log file or directory in the log file queue.
	 * 
	 * @param format
	 * @param file
	 * @param recursive
	 */
	public void importFile(final LogFormat format, final File file, final boolean recursive) {
		List<File> logs = null;

		if (file.isFile()) {
			if (isFileInQueue(file)) {
				LOG.info("The path '" + file.getPath() + "' is already in the queue.");
			} else {
				logs = addFileToList(file);
			}
		}

		if (file.isDirectory()) {
			logs = addFileToList(file, recursive);
		}

		final List<LogFile> files = convertToLogFile(format, logs);
		this.files.addAll(files);
	}

	@Override
	public void importFiles(final LogFormat format, final List<File> files) {
		importFiles(format, files, false);
	}

	@Override
	public void importFiles(final LogFormat format, final List<File> files, final boolean recursive) {
		for (final File file : files) {
			if (file != null && file.exists()) {
				importFile(format, file, recursive);
			} else {
				LOG.info("Path '" + file.getPath() + "' doesn't exist or isn't a file.");
			}
		}
	}

	/**
	 * Returns <code>true</code> if this log file queue contains the specified path of the given file.
	 * 
	 * @param file
	 * @return <code>true</code> if file exists in queue, otherwise <code>false</code>
	 */
	private boolean isFileInQueue(final File file) {
		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' must be not null.");
		}

		if (!file.exists() || !file.isFile()) {
			throw new IllegalArgumentException("Argument 'file' doesn't exist or isn't a file.");
		}

		boolean result = false;

		for (final LogFile f : files) {
			try {
				if (f.getFile().getCanonicalPath().equals(file.getCanonicalPath())) {
					result = true;
					break;
				}
			} catch (final IOException e) {
				throw new IllegalStateException(e.getLocalizedMessage(), e);
			}
		}

		return result;
	}

}
