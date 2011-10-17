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
package net.sf.jacclog.persistence.jpa.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.persistence.jpa.entity.LogEntry;
import net.sf.jacclog.service.repository.domain.PersistableHttpRequestHeaderField;
import net.sf.jacclog.service.repository.domain.PersistableHttpResponseHeaderField;
import net.sf.jacclog.service.repository.domain.PersistableLogEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogEntryMapper {

	public static final Logger LOG = LoggerFactory.getLogger(LogEntryMapper.class);

	/**
	 * Maps values of an alien object that only implements <code>ReadonlyLogEntry</code> to a new <code>LogEntry</code>
	 * entity.<br>
	 * <br>
	 * If the given argument is already a <code>LogEntry</code>, the input will be directly returned.
	 * 
	 * @param entry
	 *            the immutable log entry
	 * @return the log entry entity
	 * 
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public static LogEntry map(final ReadonlyLogEntry entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' must be set.");
		}

		final LogEntry e = new LogEntry();
		e.setBytesReceived(entry.getBytesReceived());
		e.setBytesSent(entry.getBytesSent());
		e.setConnectionStatus(entry.getConnectionStatus());
		e.setFilename(entry.getFilename());
		e.setLastStatusCode(entry.getLastStatusCode());
		e.setLocalIpAddress(entry.getLocalIpAddress());
		e.setProcessId(entry.getProcessId());
		e.setQueryString(entry.getQueryString());
		e.setRemoteHost(entry.getRemoteHost());
		e.setRemoteIpAddress(entry.getRemoteIpAddress());
		e.setRemoteLogname(entry.getRemoteLogname());
		e.setRemoteUser(entry.getRemoteUser());
		e.setRequestInMillis(entry.getRequestInMillis());
		e.setRequestMethod(entry.getRequestMethod());
		e.setRequestProtocol(entry.getRequestProtocol());
		e.setRequestTime(entry.getRequestTime());
		e.setResponseInBytes(entry.getResponseInBytes());
		e.setServerName(entry.getServerName());
		e.setServerPort(entry.getServerPort());
		e.setStatusCode(entry.getStatusCode());
		e.setUrlPath(entry.getUrlPath());

		final Set<PersistableHttpRequestHeaderField> requestHeaders = HttpRequestHeaderFieldMapper
				.translate(HttpRequestHeaderFieldMapper.mapReadables(entry.getRequestHeaders()));
		e.setRequestHeaders(requestHeaders);

		final Set<PersistableHttpResponseHeaderField> responseHeaders = HttpResponseHeaderFieldMapper
				.translate(HttpResponseHeaderFieldMapper.mapReadables(entry.getResponseHeaders()));
		e.setResponseHeaders(responseHeaders);

		return e;
	}

	public static List<LogEntry> map(final ReadonlyLogEntry[] entries) {
		if (entries == null) {
			throw new IllegalArgumentException("Argument 'entries' must be set.");
		}

		final List<LogEntry> list = new ArrayList<LogEntry>();
		for (final ReadonlyLogEntry entry : entries) {
			list.add(map(entry));
		}
		return list;
	}

	/**
	 * Casts a list of <code>LogEntry</code> entities to a list that only works with <code>PersistableLogEntry</code>.
	 * 
	 * @param list
	 * @return a list with log entries
	 */
	public static List<PersistableLogEntry> translateList(final List<LogEntry> list) {
		final List<PersistableLogEntry> entries = new ArrayList<PersistableLogEntry>(list);
		return entries;
	}

	private LogEntryMapper() {
		// stateless classes must not be instantiated
	}

}
