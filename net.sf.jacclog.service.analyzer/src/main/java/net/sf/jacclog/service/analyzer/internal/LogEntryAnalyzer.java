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
package net.sf.jacclog.service.analyzer.internal;

import java.util.ArrayList;
import java.util.List;

import jsr166y.ForkJoinPool;
import net.sf.jacclog.api.LogEntryService;
import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.service.analyzer.LogEntryAnalysisResult;
import net.sf.jacclog.service.analyzer.internal.task.AnalysisByEntriesTask;
import net.sf.jacclog.service.analyzer.internal.task.AnalysisByIntervalTask;
import net.sf.jacclog.uasparser.UserAgentStringParser;

import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEntryAnalyzer implements net.sf.jacclog.service.analyzer.LogEntryAnalyzer {

	private static final Logger LOG = LoggerFactory.getLogger(LogEntryAnalyzer.class);

	protected static final int THREADS = Runtime.getRuntime().availableProcessors();

	/**
	 * Casts a <code>long</code> to an <code>int</code> if the value not smaller than {@link Integer.MIN_VALUE} or
	 * greater than {@link Integer.MAX_VALUE}.
	 * 
	 * @param longValue
	 * @throws IllegalArgumentException
	 *             If the given <code>long</code> is smaller than {@link Integer.MIN_VALUE} or greater than
	 *             {@link Integer.MAX_VALUE}
	 * @return An <code>int</code>
	 */
	private static int castLongToInt(final long longValue) {
		if (longValue < Integer.MIN_VALUE || longValue > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Cannot cast '" + longValue + "' to int without changing its value.");
		}
		return (int) longValue;
	}

	private final LogEntryAnalysisResult.Builder builder = new LogEntryAnalysisResult.Builder();

	private final UserAgentStringParser parser;

	private final LogEntryService<ReadonlyLogEntry> service;

	public LogEntryAnalyzer(final LogEntryService<ReadonlyLogEntry> service, final UserAgentStringParser parser) {
		if (service == null) {
			throw new IllegalArgumentException("Argument 'service' can not be null.");
		}

		if (parser == null) {
			throw new IllegalArgumentException("Argument 'parser' can not be null.");
		}

		this.service = service;
		this.parser = parser;
	}

	@Override
	public LogEntryAnalyzer analyze(final Interval interval) {
		validateInterval(interval);

		// Number of log entries to be analyzed
		final int count = castLongToInt(service.count(interval));

		final AnalysisByIntervalTask analyzer = new AnalysisByIntervalTask(service, parser, builder, interval, 0, count);
		final ForkJoinPool pool = new ForkJoinPool(THREADS);
		pool.invoke(analyzer);

		LOG.info("Done. Analyzed: " + count);
		return this;
	}

	@Override
	public LogEntryAnalyzer analyze(final List<ReadonlyLogEntry> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("Argument 'entries' can not be null.");
		}

		final AnalysisByEntriesTask analyzer = new AnalysisByEntriesTask(entries, parser, builder, 0, entries.size());
		final ForkJoinPool pool = new ForkJoinPool(THREADS);
		pool.invoke(analyzer);

		LOG.info("Done. Analyzed: " + entries.size());
		return this;
	}

	@Override
	public LogEntryAnalyzer analyze(final ReadonlyLogEntry entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' can not be null.");
		}

		final List<ReadonlyLogEntry> entries = new ArrayList<ReadonlyLogEntry>();
		entries.add(entry);
		analyze(entries);

		return this;
	}

	public LogEntryAnalysisResult.Builder getBuilder() {
		return builder;
	}

	public UserAgentStringParser getParser() {
		return parser;
	}

	public LogEntryService<ReadonlyLogEntry> getService() {
		return service;
	}

	@Override
	public LogEntryAnalysisResult toResult() {
		return builder.build();
	}

	/**
	 * Validates a time interval if it is suitable for an analysis.
	 * 
	 * @param interval
	 *            Time interval
	 */
	private void validateInterval(final Interval interval) {
		if (interval == null) {
			throw new IllegalArgumentException("Argument 'interval' can not be null.");
		}

		if (interval.getStart().isAfter(interval.getEndMillis())) {
			throw new IllegalArgumentException("The time interval specify an negative range.");
		}
	}

}
