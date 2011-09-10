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
package net.sf.jacclog.service.repository;

import java.util.Date;

public interface LogEntry {

	MimeType getContentType();

	Date getFinishedRequestAt();

	HttpStatus getHttpStatus();

	Long getId();

	String getLocalHost();

	String getLocalIpAddress();

	String getReferer();

	String getRemoteHost();

	String getRemoteIpAddress();

	HttpRequestMethod getRequestMethod();

	String getRequestParameter();

	String getRequestUrlPath();

	Long getResponseDataSize();

	String getSessionId();

	String getUserAgent();

	String getUserId();

	void setContentType(final MimeType contentType);

	void setFinishedRequestAt(final Date finishedRequestAt);

	void setHttpStatus(final HttpStatus httpStatus);

	void setLocalHost(final String localHost);

	void setLocalIpAddress(final String localIpAddress);

	void setReferer(final String referer);

	void setRemoteHost(final String remoteHost);

	void setRemoteIpAddress(final String remoteIpAddress);

	void setRequestMethod(final HttpRequestMethod requestMethod);

	void setRequestParameter(final String requestParameter);

	void setRequestUrlPath(final String requestUrlPath);

	void setResponseDataSize(final Long responseDataSize);

	void setSessionId(final String sessionId);

	void setUserAgent(final String userAgent);

	void setUserId(final String userId);

}
