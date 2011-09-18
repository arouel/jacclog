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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import net.sf.jacclog.persistence.jpa.internal.LogEntryRepository;
import net.sf.jacclog.service.repository.HttpRequestMethod;
import net.sf.jacclog.service.repository.HttpStatus;
import net.sf.jacclog.service.repository.MimeType;

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
		final LogEntry obj = new LogEntry();
		setContentType(obj, index);
		setFinishedRequestAt(obj, index);
		setHttpStatus(obj, index);
		setLocalHost(obj, index);
		setLocalIpAddress(obj, index);
		setReferer(obj, index);
		setRemoteHost(obj, index);
		setRemoteIpAddress(obj, index);
		setRequestMethod(obj, index);
		setRequestParameter(obj, index);
		setRequestUrlPath(obj, index);
		setResponseDataSize(obj, index);
		setSessionId(obj, index);
		setUserAgent(obj, index);
		setUserId(obj, index);
		return obj;
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

	public void setContentType(final LogEntry obj, final int index) {
		final MimeType contentType = MimeType.TEXT_HTML;
		obj.setContentType(contentType);
	}

	public void setFinishedRequestAt(final LogEntry obj, final int index) {
		final Date finishedRequestAt = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar
				.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar
				.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar
				.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
		obj.setFinishedRequestAt(finishedRequestAt);
	}

	public void setHttpStatus(final LogEntry obj, final int index) {
		final HttpStatus httpStatus = HttpStatus.ACCEPTED;
		obj.setHttpStatus(httpStatus);
	}

	public void setLocalHost(final LogEntry obj, final int index) {
		final String localHost = "localHost." + index;
		obj.setLocalHost(localHost);
	}

	public void setLocalIpAddress(final LogEntry obj, final int index) {
		final String localIpAddress = "192.168.42." + index;
		obj.setLocalIpAddress(localIpAddress);
	}

	public void setReferer(final LogEntry obj, final int index) {
		final String referer = "referer_" + index;
		obj.setReferer(referer);
	}

	public void setRemoteHost(final LogEntry obj, final int index) {
		final String remoteHost = "remoteHost_" + index;
		obj.setRemoteHost(remoteHost);
	}

	public void setRemoteIpAddress(final LogEntry obj, final int index) {
		final String remoteIpAddress = "remoteIpAddress_" + index;
		obj.setRemoteIpAddress(remoteIpAddress);
	}

	public void setRequestMethod(final LogEntry obj, final int index) {
		final HttpRequestMethod requestMethod = HttpRequestMethod.GET;
		obj.setRequestMethod(requestMethod);
	}

	public void setRequestParameter(final LogEntry obj, final int index) {
		final String requestParameter = "requestParameter_" + index;
		obj.setRequestParameter(requestParameter);
	}

	public void setRequestUrlPath(final LogEntry obj, final int index) {
		final String requestUrlPath = "requestUrlPath_" + index;
		obj.setRequestUrlPath(requestUrlPath);
	}

	public void setResponseDataSize(final LogEntry obj, final int index) {
		final Long responseDataSize = new Integer(index).longValue();
		obj.setResponseDataSize(responseDataSize);
	}

	public void setSessionId(final LogEntry obj, final int index) {
		final String sessionId = "sessionId_" + index;
		obj.setSessionId(sessionId);
	}

	public void setUserAgent(final LogEntry obj, final int index) {
		final String userAgent = "userAgent_" + index;
		obj.setUserAgent(userAgent);
	}

	public void setUserId(final LogEntry obj, final int index) {
		final String userId = "userId_" + index;
		obj.setUserId(userId);
	}

}
