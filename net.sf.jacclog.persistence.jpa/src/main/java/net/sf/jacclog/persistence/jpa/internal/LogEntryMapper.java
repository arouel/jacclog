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

import net.sf.jacclog.persistence.jpa.LogEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEntryMapper {

	public static final Logger LOGGER = LoggerFactory.getLogger(LogEntryMapper.class);

	/**
	 * Map values of an alien object that only implements <code>net.sf.jacclog.service.repository.LogEntry</code> to a
	 * new <code>LogEntry</code>.
	 * 
	 * @param entry
	 * @return a log entry
	 */
	private static LogEntry mapToLogEntry(final net.sf.jacclog.service.repository.LogEntry entry) {
		LogEntry result;
		if (entry instanceof LogEntry) {
			result = (LogEntry) entry;
		} else {
			result = new LogEntry();
			result.setContentType(entry.getContentType());
			result.setFinishedRequestAt(entry.getFinishedRequestAt());
			result.setHttpStatus(entry.getHttpStatus());
			result.setLocalHost(entry.getLocalHost());
			result.setLocalIpAddress(entry.getLocalIpAddress());
			result.setReferer(entry.getReferer());
			result.setRemoteHost(entry.getRemoteHost());
			result.setRemoteIpAddress(entry.getRemoteIpAddress());
			result.setRequestMethod(entry.getRequestMethod());
			result.setRequestParameter(entry.getRequestParameter());
			result.setRequestUrlPath(entry.getRequestUrlPath());
			result.setResponseDataSize(entry.getResponseDataSize());
			result.setSessionId(entry.getSessionId());
			result.setUserAgent(entry.getUserAgent());
			result.setUserId(entry.getUserId());
		}
		return result;
	}

	/**
	 * Casts a list of <code>LogEntry</code> to a list that only works with
	 * <code>net.sf.jacclog.service.repository.LogEntry</code>.
	 * 
	 * @param list
	 * @return a list with log entries
	 */
	public static List<net.sf.jacclog.service.repository.LogEntry> translateList(final List<LogEntry> list) {
		final List<net.sf.jacclog.service.repository.LogEntry> entries = new ArrayList<net.sf.jacclog.service.repository.LogEntry>(
				list);
		return entries;
	}

	/**
	 * Translates an alien object that only implements <code>net.sf.jacclog.service.repository.LogEntry</code> into a
	 * <code>LogEntry</code>.
	 * 
	 * @param entry
	 * @return
	 */
	public static LogEntry translateLogEntry(final net.sf.jacclog.service.repository.LogEntry entry) {
		LogEntry logEntry;
		if (!(entry instanceof LogEntry)) {
			LOGGER.debug("I've found an alien log entry. Translating...");
			logEntry = mapToLogEntry(entry);
		} else {
			logEntry = (LogEntry) entry;
		}
		return logEntry;
	}

	private LogEntryMapper() {
	}

}
