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

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jacclog.logformat.LogFormat;
import net.sf.jacclog.logformat.field.Field;
import net.sf.jacclog.logformat.field.HttpLastStatusField;
import net.sf.jacclog.logformat.field.HttpRefererField;
import net.sf.jacclog.logformat.field.HttpStatusField;
import net.sf.jacclog.logformat.field.HttpUserAgentField;
import net.sf.jacclog.logformat.field.RemoteHostField;
import net.sf.jacclog.logformat.field.RemoteUserField;
import net.sf.jacclog.logformat.field.RequestFirstLineField;
import net.sf.jacclog.logformat.field.RequestTimeField;
import net.sf.jacclog.logformat.field.ResponseInBytesClfField;
import net.sf.jacclog.logformat.field.ResponseInBytesField;
import net.sf.jacclog.service.repository.HttpRequestMethod;
import net.sf.jacclog.service.repository.HttpStatus;
import net.sf.jacclog.service.repository.LogEntry;
import net.sf.jacclog.service.repository.NonPersistentLogEntry;

public final class TokensToLogEntryMapper {

	private static final Logger LOG = LoggerFactory.getLogger(TokensToLogEntryMapper.class);

	/**
	 * <p>
	 * Be aware if the size of tokens does not fit together with the set of fields an <code>MappingException</code> will
	 * thrown.
	 * </p>
	 * 
	 * @param format
	 * @param tokens
	 * @return
	 */
	public static LogEntry map(final LogFormat format, final List<String> tokens) {
		if (format == null) {
			throw new IllegalArgumentException("Argument 'format' can not be null.");
		}

		if (tokens == null) {
			throw new IllegalArgumentException("Argument 'tokens' can not be null.");
		}

		if (tokens.size() != format.getFields().size()) {
			final StringBuilder buffer = new StringBuilder(100);
			buffer.append("The amount of tokens (");
			buffer.append(tokens.size());
			buffer.append(") will not fit together with the amount of fields (");
			buffer.append(format.getFields().size());
			buffer.append(").");
			throw new MappingException(buffer.toString());
		}

		final Map<Field, String> map = TokenToFieldMapper.map(format.getFields(), tokens);
		final LogEntry entry = new NonPersistentLogEntry();
		mapHttpLastStatus(entry, map);
		mapHttpReferer(entry, map);
		mapHttpStatus(entry, map);
		mapRemoteHost(entry, map);
		mapRemoteUser(entry, map);
		mapRequestFirstLine(entry, map);
		mapRequestTime(entry, map);
		mapUserAgent(entry, map);
		mapResponseInBytesClf(entry, map);
		mapResponseInBytes(entry, map);

		return entry;
	}

	private static void mapHttpLastStatus(final LogEntry entry, final Map<Field, String> map) {
		if (map.containsKey(HttpLastStatusField.getInstance())) {
			try {
				final int status = Integer.parseInt(map.get(HttpLastStatusField.getInstance()));
				// TODO create a new field in LogEntry for the last status
				entry.setHttpStatus(HttpStatus.evaluate(status));
			} catch (final NumberFormatException e) {
				// ignore this value
			}
		}
	}

	private static void mapHttpReferer(final LogEntry entry, final Map<Field, String> map) {
		if (map.containsKey(HttpRefererField.getInstance())) {
			entry.setReferer(map.get(HttpRefererField.getInstance()));
		}
	}

	private static void mapHttpStatus(final LogEntry entry, final Map<Field, String> map) {
		if (map.containsKey(HttpStatusField.getInstance())) {
			try {
				final int status = Integer.parseInt(map.get(HttpStatusField.getInstance()));
				entry.setHttpStatus(HttpStatus.evaluate(status));
			} catch (final NumberFormatException e) {
				// ignore this value
			}
		}
	}

	private static void mapRemoteHost(final LogEntry entry, final Map<Field, String> map) {
		if (map.containsKey(RemoteHostField.getInstance())) {
			entry.setRemoteHost(map.get(RemoteHostField.getInstance()));
		}
	}

	private static void mapRemoteUser(final LogEntry entry, final Map<Field, String> map) {
		if (map.containsKey(RemoteUserField.getInstance())) {
			entry.setUserId(map.get(RemoteUserField.getInstance()));
		}
	}

	private static void mapRequestFirstLine(final LogEntry entry, final Map<Field, String> map) {
		if (map.containsKey(RequestFirstLineField.getInstance())) {
			final String value = map.get(RequestFirstLineField.getInstance());
			final String[] split = value.split("\\s");
			if (split.length > 1) {
				entry.setRequestMethod(HttpRequestMethod.evaluate(split[0]));
				try {
					// TODO map more parts or create MetaData-Object
					final URI url = new URI(split[1]);
					entry.setRequestUrlPath(url.getPath());
					entry.setRequestParameter(url.getQuery());
				} catch (final URISyntaxException e) {
					LOG.info(e.getLocalizedMessage() + ": " + split[1]);
				}
			}
		}
	}

	private static void mapRequestTime(final LogEntry entry, final Map<Field, String> map) {
		if (map.containsKey(RequestTimeField.getInstance())) {
			Date finishedRequestAt;
			try {
				final SimpleDateFormat defaultTimeFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z",
						Locale.ENGLISH);
				finishedRequestAt = defaultTimeFormatter.parse(map.get(RequestTimeField.getInstance()));
				entry.setFinishedRequestAt(finishedRequestAt);
			} catch (final ParseException e) {
				LOG.info(e.getLocalizedMessage() + ": " + map.get(RequestTimeField.getInstance()));
				// ignore this value
			}
		}
	}

	private static void mapResponseInBytes(final LogEntry entry, final Map<Field, String> map) {
		if (map.containsKey(ResponseInBytesField.getInstance())) {
			try {
				final Long bytes = Long.parseLong(map.get(ResponseInBytesField.getInstance()));
				entry.setResponseDataSize(bytes);
			} catch (final NumberFormatException e) {
				// ignore this value
			}
		}
	}

	private static void mapResponseInBytesClf(final LogEntry entry, final Map<Field, String> map) {
		if (map.containsKey(ResponseInBytesClfField.getInstance())) {
			final String bytesString = map.get(ResponseInBytesClfField.getInstance());
			if (bytesString != null) {
				if ("-".equals(bytesString)) {
					entry.setResponseDataSize(Long.valueOf(0));
				} else {
					try {
						entry.setResponseDataSize(Long.parseLong(bytesString));
					} catch (final NumberFormatException e) {
						// ignore this value
					}
				}
			}
		}
	}

	private static void mapUserAgent(final LogEntry entry, final Map<Field, String> map) {
		if (map.containsKey(HttpUserAgentField.getInstance())) {
			entry.setUserAgent(map.get(HttpUserAgentField.getInstance()));
		}
	}

	private TokensToLogEntryMapper() {
	}

}
