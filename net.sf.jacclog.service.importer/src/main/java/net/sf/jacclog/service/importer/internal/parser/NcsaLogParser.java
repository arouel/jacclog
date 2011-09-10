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
package net.sf.jacclog.service.importer.internal.parser;

import java.util.ArrayList;
import java.util.List;

import net.sf.jacclog.logformat.LogFormat;
import net.sf.jacclog.service.importer.api.parser.LogEntryPostProcessor;
import net.sf.jacclog.service.importer.api.parser.LogParser;
import net.sf.jacclog.service.repository.LogEntry;

/**
 * This is a NCSA-conform log parser.<br>
 * <br>
 * The NCSA log formats are based on NCSA httpd and are widely accepted as standard among HTTP server vendors.<br>
 * <br>
 * This implementation is inspired from Salmon Run. More information can be found at <a
 * href="http://sujitpal.blogspot.com/2009/06/some-access-log-parsers.html#char"
 * >http://sujitpal.blogspot.com/2009/06/some-access-log-parsers.html#char</a>.
 * 
 * @author André Rouél
 */
public class NcsaLogParser implements LogParser<LogEntry> {

	public static List<String> parse(final String line) {
		final List<String> tokens = new ArrayList<String>();
		StringBuilder buffer = new StringBuilder();
		final char[] character = line.toCharArray();
		boolean inQuotes = false;
		boolean inBrackets = false;
		for (int i = 0; i < character.length; i++) {
			if (character[i] == '"') {
				inQuotes = inQuotes ? false : true;
			} else if (character[i] == '[') {
				inBrackets = true;
			} else if (character[i] == ']') {
				if (inBrackets) {
					inBrackets = false;
				}
			} else if (character[i] == ' ' && (!inQuotes) && (!inBrackets)) {
				tokens.add(buffer.toString());
				buffer = new StringBuilder();
			} else {
				buffer.append(character[i]);
			}
		}
		if (buffer.length() > 0) {
			tokens.add(buffer.toString());
		}
		return tokens;
	}

	private final LogFormat format;

	/**
	 * Post processor for an log entry
	 */
	private LogEntryPostProcessor<LogEntry> postProcessor;

	public NcsaLogParser(final LogFormat format) {
		if (format == null) {
			throw new IllegalArgumentException("Argument 'format' can not be null.");
		}

		this.format = format;
	}

	/**
	 * Parses a line of a log file.
	 * <p>
	 * If errors happen while mapping the tokens to the fields of a log entry, then a <code>MappingException</code> will
	 * be thrown.
	 * </p>
	 */
	@Override
	public LogEntry parseLine(final String line) {
		if (line == null) {
			throw new IllegalArgumentException("Argument 'line' can not be null.");
		}

		final List<String> tokens = parse(line);
		LogEntry entry = null;
		entry = TokensToLogEntryMapper.map(format, tokens);
		if (postProcessor != null) {
			postProcessor.process(entry);
		}

		return entry;
	}

	@Override
	public void setPostProcessor(final LogEntryPostProcessor<LogEntry> processor) {
		postProcessor = processor;
	}

}
