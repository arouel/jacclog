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
package net.sf.jacclog.persistence.jpa;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.sf.jacclog.api.domain.http.HttpConnectionStatus;
import net.sf.jacclog.api.domain.http.HttpRequestHeader;
import net.sf.jacclog.api.domain.http.HttpRequestMethod;
import net.sf.jacclog.api.domain.http.HttpResponseHeader;
import net.sf.jacclog.api.domain.http.HttpStatus;
import net.sf.jacclog.persistence.jpa.entity.HttpRequestHeaderField;
import net.sf.jacclog.persistence.jpa.entity.HttpResponseHeaderField;
import net.sf.jacclog.persistence.jpa.entity.LogEntry;
import net.sf.jacclog.persistence.jpa.internal.LogEntryRepository;
import net.sf.jacclog.service.repository.domain.PersistableHttpRequestHeaderField;
import net.sf.jacclog.service.repository.domain.PersistableHttpResponseHeaderField;

public class LogEntryDataOnDemand {

	/**
	 * Test log entries
	 */
	private List<LogEntry> entries;

	private final LogEntryRepository repository;

	private final Random rnd = new SecureRandom();

	public LogEntryDataOnDemand(final LogEntryRepository repository) {
		if (repository == null) {
			throw new IllegalArgumentException("Argument 'repository' can not be null.");
		}

		this.repository = repository;
	}

	public LogEntry getNewTransientLogEntry(final int index) {
		final LogEntry entry = new LogEntry();
		entry.setBytesReceived(1l ^ index);
		entry.setBytesSent(2l ^ index);
		entry.setConnectionStatus(HttpConnectionStatus.KEPT_ALIVE);
		entry.setFilename("test.log");
		entry.setLastStatusCode(HttpStatus.OK);
		entry.setLocalIpAddress("192.168.1.1");
		entry.setProcessId(4001);
		entry.setQueryString("?key=value" + index);
		entry.setRemoteHost(index + ".remote.host.net");
		entry.setRemoteIpAddress("19.12.34.15");
		entry.setRemoteLogname("-");
		entry.setRemoteUser("user" + index);
		final Set<PersistableHttpRequestHeaderField> requestHeaders = new HashSet<PersistableHttpRequestHeaderField>();
		requestHeaders.add(new HttpRequestHeaderField(HttpRequestHeader.HOST, index + ".jacclog.sf.net"));
		requestHeaders.add(new HttpRequestHeaderField(HttpRequestHeader.FROM, "mail." + index + ".user@client.net"));
		entry.setRequestHeaders(requestHeaders);
		entry.setRequestInMillis(4l ^ index);
		entry.setRequestMethod(HttpRequestMethod.GET);
		entry.setRequestProtocol("HTTP/1.1");
		entry.setRequestTime(new Date(index));
		final Set<PersistableHttpResponseHeaderField> responseHeaders = new HashSet<PersistableHttpResponseHeaderField>();
		responseHeaders.add(new HttpResponseHeaderField(HttpResponseHeader.AGE, String.valueOf(index)));
		responseHeaders.add(new HttpResponseHeaderField(HttpResponseHeader.CONTENT_ENCODING, "deflate"));
		entry.setResponseHeaders(responseHeaders);
		entry.setResponseInBytes(5l ^ index);
		entry.setServerName(index + ".jacclog.analyzer.net");
		entry.setServerPort(8000);
		entry.setStatusCode(HttpStatus.OK);
		entry.setUrlPath("/");
		return entry;
	}

	public LogEntry getRandomLogEntry() {
		initialize();
		final LogEntry obj = entries.get(rnd.nextInt(entries.size()));
		return repository.find(obj.getId());
	}

	public LogEntry getSpecificLogEntry(int index) {
		initialize();
		if (index < 0) {
			index = 0;
		}
		if (index > (entries.size() - 1)) {
			index = entries.size() - 1;
		}
		final LogEntry obj = entries.get(index);
		return repository.find(obj.getId());
	}

	public void initialize() {
		initialize(20);
	}

	/**
	 * Initialize the repository with self created log entries.
	 * 
	 * @param amount
	 *            Number of log entries to be created
	 */
	public void initialize(final int amount) {
		entries = repository.find(0, amount);
		if (entries == null) {
			throw new IllegalStateException(
					"Find entries implementation for 'LogEntryRepository' illegally returned null.");
		}
		if (!entries.isEmpty()) {
			return;
		}

		entries = new ArrayList<LogEntry>();
		for (int i = 0; i < amount; i++) {
			final LogEntry obj = getNewTransientLogEntry(i);
			repository.persist(obj);
			entries.add(obj);
		}
	}

	public boolean modifyLogEntry(final LogEntry obj) {
		return false;
	}

}
