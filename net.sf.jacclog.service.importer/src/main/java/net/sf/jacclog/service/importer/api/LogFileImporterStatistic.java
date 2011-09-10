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
package net.sf.jacclog.service.importer.api;

import java.util.List;

import org.joda.time.Period;

public interface LogFileImporterStatistic {

	/**
	 * Statistical entry for a log file.
	 */
	public static class Entry {

		private final int count;
		private final LogFile file;
		private final Period elapsedTime;

		/**
		 * Constructs a statistical entry.
		 * 
		 * @param file
		 *            Log file
		 * @param count
		 *            Count of processed entries
		 * @param duration
		 *            Processing duration in milliseconds
		 */
		public Entry(final LogFile file, final int count, final long duration) {
			this.count = count;
			this.file = file;
			elapsedTime = new Period(duration);
		}

		/**
		 * Returns the count of processed log entries.
		 * 
		 * @return count of processed log entries
		 */
		public int getCount() {
			return count;
		}

		/**
		 * Returns the processing duration as period.
		 * 
		 * @return processing duration
		 */
		public Period getElapsedTime() {
			return elapsedTime;
		}

		public LogFile getFile() {
			return file;
		}

	}

	void addEntry(final Entry entry);

	List<Entry> getEntries();

	int getImportedLogEntryCount();

	List<LogFile> getImportedLogFiles();

}
