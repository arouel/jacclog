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
package net.sf.jacclog.service.analyzer.commands.internal;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.jacclog.service.analyzer.LogEntryAnalysisResult;
import net.sf.jacclog.service.analyzer.LogEntryAnalyzer;
import net.sf.jacclog.service.repository.LogEntryRepositoryService;
import net.sf.jacclog.service.repository.domain.LogEntry;
import net.sf.jacclog.uasparser.UserAgentInfo;

import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Command to analyze log entries for a specific day or within a specified period.
 * 
 * <strong>Examples:</strong><br>
 * <code>analyze -f 2011-08-28</code> or<br>
 * <code>analyze -f 2011-08-28 -t 2011-08-30</code>
 * 
 * @author André Rouél
 */
@Command(scope = "jacclog", name = "analyze", description = "Analyzes log entries of a specific day or within a specified period.")
@SuppressWarnings("PMD.SystemPrintln")
public class AnalyzeLogEntriesShellCommand extends OsgiCommandSupport {

	/**
	 * Separator for the period formatter
	 */
	private static final String SEPARATOR = " ";

	/**
	 * Formatter to format the elapsed time (period) to import log entries
	 */
	private static final PeriodFormatter FORMATTER = new PeriodFormatterBuilder().printZeroRarelyLast().appendHours()
			.appendSuffix("H").appendSeparator(SEPARATOR).appendMinutes().appendSuffix("m").appendSeparator(SEPARATOR)
			.appendSeconds().appendSuffix("s").appendSeparator(SEPARATOR).appendMillis().appendSuffix("ms")
			.toFormatter();

	/**
	 * Requests an user input to proceed with the analysis.
	 * 
	 * @param count
	 * @return
	 */
	private static boolean proceed(final long count) {
		int inChar;
		System.out.println("Do you really want to analyze " + count + " log entries (y/n):");
		boolean result = false;
		try {
			inChar = System.in.read();
			if (inChar == 121 || inChar == 89) {
				result = true;
			}
		} catch (final IOException e) {
			System.out.println("Error reading an answer from user.");
		}
		return result;
	}

	private LogEntryRepositoryService<LogEntry> repositoryService;

	private LogEntryAnalyzer analyzerService;

	@Option(name = "-f", aliases = "--from", description = "The day to be analyzed or the start date of the interval.", required = true, multiValued = false)
	private final String from = null;

	@Option(name = "-t", aliases = "--to", description = "The end date of the interval. This option must be used in conjunction with option '-f'.", required = false, multiValued = false)
	private final String to = null;

	private void analyzeEntries() {
		if (repositoryService != null) {
			final DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
			final DateTime from = format.parseDateTime(this.from);
			final DateTime to = (this.to != null) ? format.parseDateTime(this.to) : from.plusDays(1);
			final Interval interval = new Interval(from, to);
			final Period period = interval.toPeriod();

			final StringBuffer buffer = new StringBuffer();
			buffer.append("Analyse the log entries from '");
			buffer.append(from.toString()).append('\'');
			buffer.append(" to '").append(to);

			// print the period
			final String space = " ";
			buffer.append("'. The period includes ");
			final PeriodFormatter dateFormat = new PeriodFormatterBuilder().appendYears()
					.appendSuffix(" year", " years").appendSeparator(space).appendMonths()
					.appendSuffix(" month", " months").appendSeparator(space).appendWeeks()
					.appendSuffix(" week", " weeks").appendSeparator(space).appendDays().appendSuffix(" day", " days")
					.appendSeparator(space).appendHours().appendSuffix(" hour", " hours").appendSeparator(space)
					.appendMinutes().appendSuffix(" minute", " minutes").appendSeparator(space).toFormatter();
			dateFormat.printTo(buffer, period);
			buffer.append('.');

			System.out.println(buffer.toString());

			final long maxResults = repositoryService.count(interval);
			if (maxResults > 0) {
				int maxCount = 0;
				if (proceed(maxResults)) {
					final long startTime = System.currentTimeMillis();

					final LogEntryAnalysisResult result = analyzerService.analyze(interval).toResult();
					final Map<UserAgentInfo, AtomicInteger> stats = result.getUserAgentInfos();
					System.out.println("User agent information count: " + stats.size());

					String name;
					String osName;
					int count;
					for (final Entry<UserAgentInfo, AtomicInteger> entry : stats.entrySet()) {
						name = entry.getKey().getName();
						osName = entry.getKey().getOsName();
						count = entry.getValue().get();
						maxCount += count;
						System.out.println(name + " (" + osName + ") \t" + count);
					}
					System.out.println("Sum: " + maxCount);

					final long elapsedTime = System.currentTimeMillis() - startTime;
					final Period p = new Period(elapsedTime);
					System.out.println("Total processing time: " + p.toString(FORMATTER));
				}
			} else {
				System.out.println("There is nothing to analyze.");
			}
		}
	}

	@Override
	protected Object doExecute() throws Exception {
		if (repositoryService != null) {
			analyzeEntries();
		} else {
			log.warn("Can not analyze the log entries from '" + from + "' to '" + to + "'.");
		}

		return null;
	}

	public LogEntryAnalyzer getAnalyzerService() {
		return analyzerService;
	}

	public LogEntryRepositoryService<LogEntry> getRepositoryService() {
		return repositoryService;
	}

	public void setAnalyzerService(final LogEntryAnalyzer analyzerService) {
		this.analyzerService = analyzerService;
	}

	public void setRepositoryService(final LogEntryRepositoryService<LogEntry> repositoryService) {
		this.repositoryService = repositoryService;
	}

}
