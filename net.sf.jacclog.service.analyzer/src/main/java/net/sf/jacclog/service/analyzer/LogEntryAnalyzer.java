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
package net.sf.jacclog.service.analyzer;

import java.util.List;

import net.sf.jacclog.api.domain.ReadableLogEntry;
import net.sf.jacclog.api.domain.http.ReadableHttpRequestHeaderField;
import net.sf.jacclog.api.domain.http.ReadableHttpResponseHeaderField;

import org.joda.time.Interval;

/**
 * Interface for analyzing log entries.
 * 
 * @author André Rouél
 */
public interface LogEntryAnalyzer {

	/**
	 * Analyzes log entries between a time interval.
	 * 
	 * @param interval
	 *            Time interval
	 * @return LogEntryAnalyzer (itself)
	 */
	LogEntryAnalyzer analyze(final Interval interval);

	/**
	 * Analyzes a list of log entries.
	 * 
	 * @param entries
	 *            List of log entry
	 * @return LogEntryAnalyzer (itself)
	 */
	LogEntryAnalyzer analyze(
			final List<ReadableLogEntry<ReadableHttpRequestHeaderField, ReadableHttpResponseHeaderField>> entries);

	/**
	 * Analyzes a log entry.
	 * 
	 * @param entry
	 *            Log entry
	 * @return LogEntryAnalyzer (itself)
	 */
	LogEntryAnalyzer analyze(
			final ReadableLogEntry<ReadableHttpRequestHeaderField, ReadableHttpResponseHeaderField> entry);

	/**
	 * Builds an analysis result over the given log entries.
	 * 
	 * @return An analysis result
	 */
	LogEntryAnalysisResult toResult();

}
