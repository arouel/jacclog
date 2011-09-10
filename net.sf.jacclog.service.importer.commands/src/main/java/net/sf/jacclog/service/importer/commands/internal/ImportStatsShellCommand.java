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
package net.sf.jacclog.service.importer.commands.internal;

import java.util.Formatter;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import net.sf.jacclog.service.importer.api.LogFileImporter;
import net.sf.jacclog.service.importer.api.LogFileImporterStatistic;
import net.sf.jacclog.service.importer.api.LogFileImporterStatistic.Entry;

/**
 * Command to show statistics of the recently finished imports.
 * 
 * @author André Rouél
 */
@Command(scope = "jacclog", name = "import-stats", description = "Shows statistics of the recently finished imports.")
@SuppressWarnings("PMD.SystemPrintln")
public class ImportStatsShellCommand extends OsgiCommandSupport {

	private LogFileImporter importer;

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

	@Override
	protected Object doExecute() throws Exception {
		if (importer != null) {
			final LogFileImporterStatistic statistic = importer.getStatistic();
			renderEntries(statistic);

		} else {
			log.warn("No log file importer is available.");
		}

		return null;
	}

	public LogFileImporter getImporter() {
		return importer;
	}

	private void renderEntries(final LogFileImporterStatistic statistic) {
		if (statistic.getEntries() != null && !statistic.getEntries().isEmpty()) {
			final int size = (statistic.getEntries().get(0).getFile() != null) ? statistic.getEntries().get(0)
					.getFile().getFile().getPath().length() + 8 : 32;
			final String format = "%-" + size + "s%10s%18s";
			final StringBuilder builder = new StringBuilder();
			builder.append('\n');
			final Formatter formatter = new Formatter(builder);
			formatter.format(format, "Path", "Count", "Elapsed time");
			builder.append('\n');

			String path;
			Period p;
			int totalCount = 0;
			Duration totalElapsedTime = new Duration(0);
			for (final Entry entry : statistic.getEntries()) {
				path = entry.getFile().getFile().getPath();
				p = entry.getElapsedTime();
				totalElapsedTime = totalElapsedTime.plus(p.toStandardDuration());
				totalCount += entry.getCount();
				formatter.format(format, path, entry.getCount(), p.toString(FORMATTER));
				builder.append('\n');
			}

			builder.append('\n');
			builder.append("Total imported entries: " + totalCount);
			builder.append('\n');
			builder.append("Total processing time: " + totalElapsedTime.toPeriod().toString(FORMATTER));
			builder.append('\n');

			System.out.println(builder);
		} else {
			System.out.println("No files have been recently imported.");
		}
	}

	public void setImporter(final LogFileImporter importer) {
		this.importer = importer;
	}

}
