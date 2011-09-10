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
import net.sf.jacclog.service.analyzer.LogEntryAnalysisResult;
import net.sf.jacclog.service.repository.LogEntry;
import net.sf.jacclog.uasparser.UserAgentStringParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisByEntriesTask extends RecursiveAction {

	private static final Logger LOG = LoggerFactory.getLogger(AnalysisByEntriesTask.class);

	private static final long serialVersionUID = -4821190673644807216L;

	protected static final int THRESHOLD = 200;

	private final int maxResults;

	private final UserAgentStringParser parser;

	private final List<LogEntry> entries;

	private final LogEntryAnalysisResult.Builder builder;

	private final int startPosition;

	public AnalysisByEntriesTask(final List<LogEntry> entries, final UserAgentStringParser parser,
			final LogEntryAnalysisResult.Builder builder, final int startPosition, final int maxResults) {

		if (entries == null) {
			throw new IllegalArgumentException("Argument 'entries' can not be null.");
		}

		if (parser == null) {
			throw new IllegalArgumentException("Argument 'parser' can not be null.");
		}

		if (builder == null) {
			throw new IllegalArgumentException("Argument 'builder' can not be null.");
		}

		if (startPosition < 0) {
			throw new IllegalArgumentException("Argument 'startPosition' can not be smaller than 0.");
		}

		if (maxResults < 1) {
			throw new IllegalArgumentException("Argument 'maxResults' can not be smaller than 1.");
		}

		this.entries = entries;
		this.parser = parser;
		this.builder = builder;
		this.startPosition = startPosition;
		this.maxResults = maxResults;
	}

	@Override
	protected void compute() {
		if (maxResults < THRESHOLD) {
			try {
				final List<LogEntry> entries = this.entries.subList(startPosition, startPosition + maxResults);
				if (!entries.isEmpty()) {
					for (final LogEntry entry : entries) {
						builder.appendUserAgentInfo(parser.parse(entry.getUserAgent()));
					}
				}
			} catch (final IndexOutOfBoundsException e) {
				LOG.warn("Analyzing user agent information failed: " + e.getLocalizedMessage());
			}
		} else {
			final int midpoint = maxResults / 2;
			final AnalysisByEntriesTask a1 = new AnalysisByEntriesTask(entries, parser, builder, startPosition,
					midpoint);
			final AnalysisByEntriesTask a2 = new AnalysisByEntriesTask(entries, parser, builder, startPosition
					+ midpoint, maxResults - midpoint);
			invokeAll(a1, a2);
		}
	}

}
