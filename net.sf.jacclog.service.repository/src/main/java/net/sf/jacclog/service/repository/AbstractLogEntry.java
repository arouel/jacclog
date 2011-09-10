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

public abstract class AbstractLogEntry implements LogEntry {

	private MimeType contentType;

	private Date finishedRequestAt;

	private HttpStatus httpStatus;

	private String localHost;

	private String localIpAddress;

	private String referer;

	private String remoteHost;

	private String remoteIpAddress;

	private HttpRequestMethod requestMethod;

	private String requestParameter;

	private String requestUrlPath;

	private Long responseDataSize;

	private String sessionId;

	private String userAgent;

	private String userId;

	@Override
	public MimeType getContentType() {
		return contentType;
	}

	@Override
	public Date getFinishedRequestAt() {
		return finishedRequestAt;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public abstract Long getId();

	@Override
	public String getLocalHost() {
		return localHost;
	}

	@Override
	public String getLocalIpAddress() {
		return localIpAddress;
	}

	@Override
	public String getReferer() {
		return referer;
	}

	@Override
	public String getRemoteHost() {
		return remoteHost;
	}

	@Override
	public String getRemoteIpAddress() {
		return remoteIpAddress;
	}

	@Override
	public HttpRequestMethod getRequestMethod() {
		return requestMethod;
	}

	@Override
	public String getRequestParameter() {
		return requestParameter;
	}

	@Override
	public String getRequestUrlPath() {
		return requestUrlPath;
	}

	@Override
	public Long getResponseDataSize() {
		return responseDataSize;
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public String getUserAgent() {
		return userAgent;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	public void setContentType(final MimeType contentType) {
		this.contentType = contentType;
	}

	public void setFinishedRequestAt(final Date finishedRequestAt) {
		this.finishedRequestAt = finishedRequestAt;
	}

	public void setHttpStatus(final HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public void setLocalHost(final String localHost) {
		this.localHost = localHost;
	}

	public void setLocalIpAddress(final String localIpAddress) {
		this.localIpAddress = localIpAddress;
	}

	public void setReferer(final String referer) {
		this.referer = referer;
	}

	public void setRemoteHost(final String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public void setRemoteIpAddress(final String remoteIpAddress) {
		this.remoteIpAddress = remoteIpAddress;
	}

	public void setRequestMethod(final HttpRequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	public void setRequestParameter(final String requestParameter) {
		this.requestParameter = requestParameter;
	}

	public void setRequestUrlPath(final String requestUrlPath) {
		this.requestUrlPath = requestUrlPath;
	}

	public void setResponseDataSize(final Long responseDataSize) {
		this.responseDataSize = responseDataSize;
	}

	public void setSessionId(final String sessionId) {
		this.sessionId = sessionId;
	}

	public void setUserAgent(final String userAgent) {
		this.userAgent = userAgent;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

}
