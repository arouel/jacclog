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
package net.sf.jacclog.uasparser.internal.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.sf.jacclog.uasparser.internal.data.Data.Builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Reader for the XML data for UASparser from {@link http://user-agent-string.info/}.<br>
 * <br>
 * This reader is safe for use by multiple threads.
 * 
 * @author André Rouél
 */
public class XmlDataReader implements DataReader {

	private static class XmlParser {

		public static void parse(final File file, final Builder builder) throws ParserConfigurationException,
				SAXException, IOException {
			final SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			final XmlDataHandler handler = new XmlDataHandler(builder);
			parser.parse(file, handler);
		}

		public static void parse(final InputStream stream, final Builder builder) throws ParserConfigurationException,
				SAXException, IOException {
			final SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			final XmlDataHandler handler = new XmlDataHandler(builder);
			parser.parse(stream, handler);
		}

		public static void parse(final String uri, final Builder builder) throws ParserConfigurationException,
				SAXException, IOException {
			final SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			final XmlDataHandler handler = new XmlDataHandler(builder);
			parser.parse(uri, handler);
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(XmlDataReader.class);

	@Override
	public Data read(final File file) {
		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' can not be null.");
		}
		final Builder builder = new Builder();
		try {
			XmlParser.parse(file, builder);
		} catch (final ParserConfigurationException e) {
			LOG.warn(e.getLocalizedMessage());
		} catch (final SAXException e) {
			LOG.warn(e.getLocalizedMessage());
		} catch (final IOException e) {
			LOG.warn(e.getLocalizedMessage());
		}
		return builder.build();
	}

	@Override
	public Data read(final InputStream inputStream) {
		if (inputStream == null) {
			throw new IllegalArgumentException("Argument 'inputStream' can not be null.");
		}
		final Builder builder = new Builder();
		try {
			XmlParser.parse(inputStream, builder);
		} catch (final ParserConfigurationException e) {
			LOG.warn(e.getLocalizedMessage());
		} catch (final SAXException e) {
			LOG.warn(e.getLocalizedMessage());
		} catch (final IOException e) {
			LOG.warn(e.getLocalizedMessage());
		}
		return builder.build();
	}

	@Override
	public Data read(final Reader reader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data read(final URL url) {
		if (url == null) {
			throw new IllegalArgumentException("Argument 'url' can not be null.");
		}
		final Builder builder = new Builder();
		try {
			XmlParser.parse(url.toExternalForm(), builder);
		} catch (final ParserConfigurationException e) {
			LOG.warn(e.getLocalizedMessage());
		} catch (final SAXException e) {
			LOG.warn(e.getLocalizedMessage());
		} catch (final IOException e) {
			LOG.warn(e.getLocalizedMessage());
		}
		return builder.build();
	}

}
