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
package net.sf.jacclog.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CommaSeparatedValuesReader {

	private static final Logger LOG = LoggerFactory.getLogger(CommaSeparatedValuesReader.class);

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

	public static List<List<String>> read(final File file) {
		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' can not be null.");
		}

		List<List<String>> result = new ArrayList<List<String>>();
		try {
			result = read(new FileReader(file));
		} catch (final FileNotFoundException e) {
			LOG.debug("The file '" + file.getPath() + "' can not be read: " + e.getLocalizedMessage());
		}

		return result;
	}

	public static List<List<String>> read(final Reader reader) {
		if (reader == null) {
			throw new IllegalArgumentException("Argument 'reader' can not be null.");
		}

		final List<List<String>> result = new ArrayList<List<String>>();
		try {
			final LineNumberReader lineNumberReader = createLineNumberReader(reader);
			String line = null;
			do {
				line = readLine(lineNumberReader);
				if (line == null) {
					lineNumberReader.close();
					reader.close();
				} else {
					result.add(read(line));
				}
			} while (line != null);
		} catch (final IOException e) {
			LOG.warn(e.getLocalizedMessage());
		}

		return result;
	}

	public static List<String> read(final String line) {
		if (line == null) {
			throw new IllegalArgumentException("Argument 'line' can not be null.");
		}

		final ANTLRStringStream input = new ANTLRStringStream(line);
		final CommaSeparatedValuesLexer lexer = new CommaSeparatedValuesLexer(input);
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final CommaSeparatedValuesParser parser = new CommaSeparatedValuesParser(tokens);

		List<String> result = new ArrayList<String>(0);
		try {
			result = parser.line();
		} catch (final RecognitionException e) {
			LOG.debug("The line '" + line + "' can not be read: " + e.getLocalizedMessage());
		}

		return result;
	}

	private static String readLine(final LineNumberReader reader) throws IOException {
		final String line = reader.readLine();
		if (line == null) {
			reader.close();
		}
		return line;
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private CommaSeparatedValuesReader() {
		// This class is not intended to create objects from it.
	}

}
