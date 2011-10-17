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
package net.sf.jacclog.service.analyzer.internal.task;

import java.util.List;

import jsr166y.RecursiveAction;
import net.sf.jacclog.api.LogEntryService;
import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.service.analyzer.LogEntryAnalysisResult;
import net.sf.jacclog.uasparser.UserAgentStringParser;

import org.joda.time.Interval;

public class AnalysisByIntervalTask extends RecursiveAction {

	private static final long serialVersionUID = 4387451272790339786L;

	protected static final int THRESHOLD = 200;

	private final Interval interval;

	private final int maxResults;

	private final UserAgentStringParser parser;

	private final LogEntryService<ReadonlyLogEntry> service;

	private final LogEntryAnalysisResult.Builder builder;

	private final int startPosition;

	public AnalysisByIntervalTask(final LogEntryService<ReadonlyLogEntry> service, final UserAgentStringParser parser,
			final LogEntryAnalysisResult.Builder builder, final Interval interval, final int startPosition,
			final int maxResults) {

		if (service == null) {
			throw new IllegalArgumentException("Argument 'service' can not be null.");
		}

		if (parser == null) {
			throw new IllegalArgumentException("Argument 'parser' can not be null.");
		}

		if (builder == null) {
			throw new IllegalArgumentException("Argument 'builder' can not be null.");
		}

		if (interval == null) {
			throw new IllegalArgumentException("Argument 'interval' can not be null.");
		}

		if (interval.getStart().isAfter(interval.getEndMillis())) {
			throw new IllegalArgumentException("The time interval specify an negative range.");
		}

		if (startPosition < 0) {
			throw new IllegalArgumentException("Argument 'startPosition' can not be smaller than 0.");
		}

		if (maxResults < 1) {
			throw new IllegalArgumentException("Argument 'maxResults' can not be smaller than 1.");
		}

		this.service = service;
		this.parser = parser;
		this.builder = builder;
		this.interval = interval;
		this.startPosition = startPosition;
		this.maxResults = maxResults;
	}

	@Override
	protected void compute() {
		if (maxResults < THRESHOLD) {
			final List<ReadonlyLogEntry> entries = service.find(interval, startPosition, maxResults);
			if (entries != null && !entries.isEmpty()) {
				String userAgent = null;
				for (final ReadonlyLogEntry entry : entries) {
					userAgent = AnalysisByEntriesTask.searchUserAgent(entry);
					if (userAgent != null) {
						builder.appendUserAgentInfo(parser.parse(userAgent));
					}
				}
			}
		} else {
			final int midpoint = maxResults / 2;
			final AnalysisByIntervalTask a1 = new AnalysisByIntervalTask(service, parser, builder, interval,
					startPosition, midpoint);
			final AnalysisByIntervalTask a2 = new AnalysisByIntervalTask(service, parser, builder, interval,
					startPosition + midpoint, maxResults - midpoint);
			invokeAll(a1, a2);
		}
	}

}
