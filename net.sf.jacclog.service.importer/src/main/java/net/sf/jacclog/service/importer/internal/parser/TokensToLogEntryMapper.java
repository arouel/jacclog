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

import net.sf.jacclog.api.domain.LogEntryBuilder;
import net.sf.jacclog.api.domain.http.HttpRequestHeader;
import net.sf.jacclog.api.domain.http.HttpRequestHeaderField;
import net.sf.jacclog.api.domain.http.HttpRequestMethod;
import net.sf.jacclog.api.domain.http.HttpStatus;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public static LogEntryBuilder map(final LogFormat format, final List<String> tokens) {
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
		final LogEntryBuilder builder = new LogEntryBuilder();
		mapHttpLastStatus(builder, map);
		mapHttpReferer(builder, map);
		mapHttpStatus(builder, map);
		mapRemoteHost(builder, map);
		mapRemoteUser(builder, map);
		mapRequestFirstLine(builder, map);
		mapRequestTime(builder, map);
		mapUserAgent(builder, map);
		mapResponseInBytesClf(builder, map);
		mapResponseInBytes(builder, map);

		return builder;
	}

	private static void mapHttpLastStatus(final LogEntryBuilder builder, final Map<Field, String> map) {
		if (map.containsKey(HttpLastStatusField.getInstance())) {
			try {
				final int status = Integer.parseInt(map.get(HttpLastStatusField.getInstance()));
				builder.lastStatusCode(HttpStatus.evaluate(status));
			} catch (final NumberFormatException e) {
				// ignore this value
			}
		}
	}

	private static void mapHttpReferer(final LogEntryBuilder builder, final Map<Field, String> map) {
		if (map.containsKey(HttpRefererField.getInstance())) {
			final String referer = map.get(HttpRefererField.getInstance());
			final HttpRequestHeaderField header = new HttpRequestHeaderField(HttpRequestHeader.REFERER, referer);
			builder.appendRequestHeaders(header);
		}
	}

	private static void mapHttpStatus(final LogEntryBuilder builder, final Map<Field, String> map) {
		if (map.containsKey(HttpStatusField.getInstance())) {
			try {
				final int status = Integer.parseInt(map.get(HttpStatusField.getInstance()));
				builder.statusCode(HttpStatus.evaluate(status));
			} catch (final NumberFormatException e) {
				// ignore this value
			}
		}
	}

	private static void mapRemoteHost(final LogEntryBuilder builder, final Map<Field, String> map) {
		if (map.containsKey(RemoteHostField.getInstance())) {
			builder.remoteHost(map.get(RemoteHostField.getInstance()));
		}
	}

	private static void mapRemoteUser(final LogEntryBuilder builder, final Map<Field, String> map) {
		if (map.containsKey(RemoteUserField.getInstance())) {
			builder.remoteUser(map.get(RemoteUserField.getInstance()));
		}
	}

	private static void mapRequestFirstLine(final LogEntryBuilder builder, final Map<Field, String> map) {
		if (map.containsKey(RequestFirstLineField.getInstance())) {
			final String value = map.get(RequestFirstLineField.getInstance());
			final String[] split = value.split("\\s");
			if (split.length > 1) {
				builder.requestMethod(HttpRequestMethod.evaluate(split[0]));
				try {
					// TODO map more parts or create MetaData-Object
					final URI url = new URI(split[1]);
					builder.urlPath(url.getPath() != null ? url.getPath() : "");
					builder.queryString(url.getQuery() != null ? url.getQuery() : "");
				} catch (final URISyntaxException e) {
					LOG.info(e.getLocalizedMessage() + ": " + split[1]);
				}
			}
		}
	}

	private static void mapRequestTime(final LogEntryBuilder builder, final Map<Field, String> map) {
		if (map.containsKey(RequestTimeField.getInstance())) {
			Date requestTime;
			try {
				final SimpleDateFormat defaultTimeFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z",
						Locale.ENGLISH);
				requestTime = defaultTimeFormatter.parse(map.get(RequestTimeField.getInstance()));
				builder.requestTime(requestTime);
			} catch (final ParseException e) {
				LOG.info(e.getLocalizedMessage() + ": " + map.get(RequestTimeField.getInstance()));
				// ignore this value
			}
		}
	}

	private static void mapResponseInBytes(final LogEntryBuilder builder, final Map<Field, String> map) {
		if (map.containsKey(ResponseInBytesField.getInstance())) {
			try {
				builder.responseInBytes(Long.parseLong(map.get(ResponseInBytesField.getInstance())));
			} catch (final NumberFormatException e) {
				// ignore this value
			}
		}
	}

	private static void mapResponseInBytesClf(final LogEntryBuilder builder, final Map<Field, String> map) {
		if (map.containsKey(ResponseInBytesClfField.getInstance())) {
			final String bytesString = map.get(ResponseInBytesClfField.getInstance());
			if (bytesString != null) {
				if ("-".equals(bytesString)) {
					builder.responseInBytes(Long.valueOf(0));
				} else {
					try {
						builder.responseInBytes(Long.parseLong(bytesString));
					} catch (final NumberFormatException e) {
						// ignore this value
					}
				}
			}
		}
	}

	private static void mapUserAgent(final LogEntryBuilder builder, final Map<Field, String> map) {
		if (map.containsKey(HttpUserAgentField.getInstance())) {
			final String userAgent = map.get(HttpUserAgentField.getInstance());
			final HttpRequestHeaderField header = new HttpRequestHeaderField(HttpRequestHeader.USER_AGENT, userAgent);
			builder.appendRequestHeaders(header);
		}
	}

	private TokensToLogEntryMapper() {
		// stateless classes should not be instantiated
	}

}
