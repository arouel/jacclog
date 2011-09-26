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
package net.sf.jacclog.service.importer.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jacclog.service.importer.api.LogFile;
import net.sf.jacclog.service.importer.api.parser.LogParser;
import net.sf.jacclog.service.importer.internal.parser.MappingException;
import net.sf.jacclog.service.importer.internal.parser.NcsaLogParser;
import net.sf.jacclog.service.repository.domain.LogEntry;

public class LogFileReader implements net.sf.jacclog.service.importer.api.reader.LogReader<LogEntry> {

	/**
	 * The logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(LogFileReader.class);

	/**
	 * Sets a <code>LineNumberReader</code> by file input.
	 * 
	 * @param file
	 */
	private static LineNumberReader createLineNumberReader(final File file) {
		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' must be set.");
		}

		LineNumberReader result = null;
		try {
			final FileReader reader = new FileReader(file);
			result = createLineNumberReader(reader);
		} catch (final FileNotFoundException e) {
			if (LOG.isWarnEnabled()) {
				LOG.warn(e.getLocalizedMessage());
			}
		}

		return result;
	}

	/**
	 * Creates a <code>LineNumberReader</code> from a <code>Reader</code> source.
	 * 
	 * @param reader
	 */
	private static LineNumberReader createLineNumberReader(final Reader reader) {
		if (reader == null) {
			throw new IllegalArgumentException("Argument 'reader' must be set.");
		}

		final LineNumberReader result;
		if (reader instanceof LineNumberReader) {
			result = (LineNumberReader) reader;
		} else {
			result = new LineNumberReader(reader);
		}

		return result;
	}

	/**
	 * A log entry parser
	 */
	private final LogParser<LogEntry> parser;

	/**
	 * A line number reader
	 */
	private final LineNumberReader reader;

	/**
	 * Creates an access log reader.
	 * 
	 * @param file
	 *            An access log file
	 * @param parser
	 *            A parser for the log entry lines
	 */
	public LogFileReader(final File file, final LogParser<LogEntry> parser) {
		this(createLineNumberReader(file), parser);
	}

	/**
	 * Creates an access log reader.
	 * 
	 * @param file
	 */
	public LogFileReader(final LogFile file) {
		this(file.getFile(), new NcsaLogParser(file.getFormat()));
	}

	/**
	 * Creates an access log reader.
	 * 
	 * @param reader
	 *            A reader of a character-input stream
	 * @param parser
	 *            A parser for the log entry lines
	 */
	public LogFileReader(final Reader reader, final LogParser<LogEntry> parser) {
		if (reader == null) {
			throw new IllegalArgumentException("Argument 'reader' can not be null.");
		}

		if (parser == null) {
			throw new IllegalArgumentException("Argument 'parser' can not be null.");
		}

		this.parser = parser;
		this.reader = createLineNumberReader(reader);
	}

	/**
	 * Returns the parser which interprets the log entries of a log-file.
	 * 
	 * @return log parser
	 */
	public LogParser<LogEntry> getParser() {
		return parser;
	}

	/**
	 * Gets the reader.
	 * 
	 * @return Reader
	 */
	public LineNumberReader getReader() {
		return reader;
	}

	@Override
	public List<LogEntry> read(final int count) {
		final List<LogEntry> entries = new ArrayList<LogEntry>();
		int counter = 0;
		LogEntry entry;

		do {
			entry = readEntry();
			if (entry != null) {
				entries.add(entry);
			}
			counter++;
		} while (entry != null && counter < count);

		return entries;
	}

	/**
	 * Reads one line within a <code>LineNumberReader</code> and converts it to a log entry.
	 * <p>
	 * If errors happen while mapping the tokens to the fields of a log entry, then a <code>MappingException</code> will
	 * be thrown.
	 * </p>
	 */
	@Override
	public LogEntry readEntry() {
		LogEntry entry = null;
		if (reader != null) {
			try {
				final String line = reader.readLine();
				if (line == null) {
					reader.close();
				} else {
					try {
						entry = parser.parseLine(line);
					} catch (final MappingException e) {
						final String prefix = "at line " + reader.getLineNumber() + ": ";
						throw new MappingException(prefix + e.getLocalizedMessage());
					}
				}
			} catch (final IOException e) {
				LOG.warn(e.getLocalizedMessage());
			}
		}
		return entry;
	}
}
