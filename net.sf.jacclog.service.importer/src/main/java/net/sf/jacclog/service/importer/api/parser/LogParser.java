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
package net.sf.jacclog.service.importer.api.parser;

import net.sf.jacclog.api.domain.ReadonlyLogEntry;

public interface LogParser<E extends ReadonlyLogEntry> {

	/**
	 * Parse a log line.
	 * 
	 * @param line
	 *            Line of a log file
	 * @return log entry
	 */
	E parseLine(final String line);

	/**
	 * The given filter will be used to clean out any line with it.
	 * 
	 * @param filter
	 */
	void setPostProcessor(final LogEntryPostProcessor filter);

}
