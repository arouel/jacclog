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
package net.sf.jacclog.repository.jpa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import net.sf.jacclog.repository.jpa.internal.LogEntryRepository;
import net.sf.jacclog.service.repository.HttpRequestMethod;
import net.sf.jacclog.service.repository.HttpStatus;
import net.sf.jacclog.service.repository.MimeType;

@Component
@Configurable
public class LogEntryDataOnDemand {

	/**
	 * Test log entries
	 */
	private List<LogEntry> entries;

	@Autowired
	private LogEntryRepository repository;

	private Random rnd = new java.security.SecureRandom();

	public LogEntryDataOnDemand(final LogEntryRepository repository) {
		if (repository == null)
			throw new IllegalArgumentException("Argument 'repository' must be set.");

		this.repository = repository;
	}

	public LogEntry getNewTransientLogEntry(int index) {
		LogEntry obj = new LogEntry();
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
		init();
		LogEntry obj = entries.get(rnd.nextInt(entries.size()));
		return repository.find(obj.getId());
	}

	public LogEntry getSpecificLogEntry(int index) {
		init();
		if (index < 0)
			index = 0;
		if (index > (entries.size() - 1))
			index = entries.size() - 1;
		LogEntry obj = entries.get(index);
		return repository.find(obj.getId());
	}

	public void init() {
		entries = repository.find(0, 10);
		if (entries == null)
			throw new IllegalStateException(
					"Find entries implementation for 'LogEntryRepository' illegally returned null");
		if (!entries.isEmpty()) {
			return;
		}

		entries = new ArrayList<LogEntry>();
		for (int i = 0; i < 10; i++) {
			LogEntry obj = getNewTransientLogEntry(i);
			repository.persist(obj);
			repository.flush();
			entries.add(obj);
		}
	}

	public boolean modifyLogEntry(LogEntry obj) {
		return false;
	}

	public void setContentType(LogEntry obj, int index) {
		MimeType contentType = MimeType.TEXT_HTML;
		obj.setContentType(contentType);
	}

	public void setFinishedRequestAt(LogEntry obj, int index) {
		Date finishedRequestAt = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar
				.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar
				.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar
				.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
		obj.setFinishedRequestAt(finishedRequestAt);
	}

	public void setHttpStatus(LogEntry obj, int index) {
		final HttpStatus httpStatus = HttpStatus.ACCEPTED;
		obj.setHttpStatus(httpStatus);
	}

	public void setLocalHost(LogEntry obj, int index) {
		String localHost = "localHost." + index;
		obj.setLocalHost(localHost);
	}

	public void setLocalIpAddress(LogEntry obj, int index) {
		String localIpAddress = "192.168.42." + index;
		obj.setLocalIpAddress(localIpAddress);
	}

	public void setReferer(LogEntry obj, int index) {
		String referer = "referer_" + index;
		obj.setReferer(referer);
	}

	public void setRemoteHost(LogEntry obj, int index) {
		String remoteHost = "remoteHost_" + index;
		obj.setRemoteHost(remoteHost);
	}

	public void setRemoteIpAddress(LogEntry obj, int index) {
		String remoteIpAddress = "remoteIpAddress_" + index;
		obj.setRemoteIpAddress(remoteIpAddress);
	}

	public void setRequestMethod(LogEntry obj, int index) {
		HttpRequestMethod requestMethod = HttpRequestMethod.GET;
		obj.setRequestMethod(requestMethod);
	}

	public void setRequestParameter(LogEntry obj, int index) {
		String requestParameter = "requestParameter_" + index;
		obj.setRequestParameter(requestParameter);
	}

	public void setRequestUrlPath(LogEntry obj, int index) {
		String requestUrlPath = "requestUrlPath_" + index;
		obj.setRequestUrlPath(requestUrlPath);
	}

	public void setResponseDataSize(LogEntry obj, int index) {
		Long responseDataSize = new Integer(index).longValue();
		obj.setResponseDataSize(responseDataSize);
	}

	public void setSessionId(LogEntry obj, int index) {
		String sessionId = "sessionId_" + index;
		obj.setSessionId(sessionId);
	}

	public void setUserAgent(LogEntry obj, int index) {
		String userAgent = "userAgent_" + index;
		obj.setUserAgent(userAgent);
	}

	public void setUserId(LogEntry obj, int index) {
		String userId = "userId_" + index;
		obj.setUserId(userId);
	}

}
