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
package net.sf.jacclog.service.importer.api.reader;

import java.util.List;

import net.sf.jacclog.service.repository.LogEntry;

public interface LogReader<E extends LogEntry> {

	/**
	 * Reads a number of lines within a <code>BufferedReader</code> and converts it to a log entries.
	 * 
	 * @param count
	 *            Number of lines to read
	 */
	List<E> read(final int count);

	/**
	 * Reads one line within a <code>BufferedReader</code> and converts it to a log entry.
	 * 
	 * @return a log entry, or <code>null</code> if this log is completely ridden or empty
	 */
	E readEntry();

}
