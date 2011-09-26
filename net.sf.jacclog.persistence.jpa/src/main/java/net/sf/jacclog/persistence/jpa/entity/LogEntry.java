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
package net.sf.jacclog.persistence.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import net.sf.jacclog.service.repository.HttpRequestMethod;
import net.sf.jacclog.service.repository.HttpStatus;
import net.sf.jacclog.service.repository.MimeType;

@Entity
public class LogEntry implements net.sf.jacclog.service.repository.LogEntry {

	@Enumerated(EnumType.STRING)
	private MimeType contentType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date finishedRequestAt;

	@Enumerated
	private HttpStatus httpStatus;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private String localHost;

	private String localIpAddress;

	private String referer;

	private String remoteHost;

	private String remoteIpAddress;

	@Enumerated(EnumType.STRING)
	private HttpRequestMethod requestMethod;

	private String requestParameter;

	private String requestUrlPath;

	private Long responseDataSize;

	private String sessionId;

	private String userAgent;

	private String userId;

	@Version
	@Column(name = "version")
	private Integer version;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LogEntry other = (LogEntry) obj;
		if (contentType != other.contentType)
			return false;
		if (finishedRequestAt == null) {
			if (other.finishedRequestAt != null)
				return false;
		} else if (!finishedRequestAt.equals(other.finishedRequestAt))
			return false;
		if (httpStatus != other.httpStatus)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (localHost == null) {
			if (other.localHost != null)
				return false;
		} else if (!localHost.equals(other.localHost))
			return false;
		if (localIpAddress == null) {
			if (other.localIpAddress != null)
				return false;
		} else if (!localIpAddress.equals(other.localIpAddress))
			return false;
		if (referer == null) {
			if (other.referer != null)
				return false;
		} else if (!referer.equals(other.referer))
			return false;
		if (remoteHost == null) {
			if (other.remoteHost != null)
				return false;
		} else if (!remoteHost.equals(other.remoteHost))
			return false;
		if (remoteIpAddress == null) {
			if (other.remoteIpAddress != null)
				return false;
		} else if (!remoteIpAddress.equals(other.remoteIpAddress))
			return false;
		if (requestMethod != other.requestMethod)
			return false;
		if (requestParameter == null) {
			if (other.requestParameter != null)
				return false;
		} else if (!requestParameter.equals(other.requestParameter))
			return false;
		if (requestUrlPath == null) {
			if (other.requestUrlPath != null)
				return false;
		} else if (!requestUrlPath.equals(other.requestUrlPath))
			return false;
		if (responseDataSize == null) {
			if (other.responseDataSize != null)
				return false;
		} else if (!responseDataSize.equals(other.responseDataSize))
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (userAgent == null) {
			if (other.userAgent != null)
				return false;
		} else if (!userAgent.equals(other.userAgent))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

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
	public Long getId() {
		return id;
	}

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

	public Integer getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + ((finishedRequestAt == null) ? 0 : finishedRequestAt.hashCode());
		result = prime * result + ((httpStatus == null) ? 0 : httpStatus.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((localHost == null) ? 0 : localHost.hashCode());
		result = prime * result + ((localIpAddress == null) ? 0 : localIpAddress.hashCode());
		result = prime * result + ((referer == null) ? 0 : referer.hashCode());
		result = prime * result + ((remoteHost == null) ? 0 : remoteHost.hashCode());
		result = prime * result + ((remoteIpAddress == null) ? 0 : remoteIpAddress.hashCode());
		result = prime * result + ((requestMethod == null) ? 0 : requestMethod.hashCode());
		result = prime * result + ((requestParameter == null) ? 0 : requestParameter.hashCode());
		result = prime * result + ((requestUrlPath == null) ? 0 : requestUrlPath.hashCode());
		result = prime * result + ((responseDataSize == null) ? 0 : responseDataSize.hashCode());
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result + ((userAgent == null) ? 0 : userAgent.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public void setContentType(final MimeType contentType) {
		this.contentType = contentType;
	}

	@Override
	public void setFinishedRequestAt(final Date finishedRequestAt) {
		this.finishedRequestAt = finishedRequestAt;
	}

	@Override
	public void setHttpStatus(final HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public void setLocalHost(final String localHost) {
		this.localHost = localHost;
	}

	@Override
	public void setLocalIpAddress(final String localIpAddress) {
		this.localIpAddress = localIpAddress;
	}

	@Override
	public void setReferer(final String referer) {
		this.referer = referer;
	}

	@Override
	public void setRemoteHost(final String remoteHost) {
		this.remoteHost = remoteHost;
	}

	@Override
	public void setRemoteIpAddress(final String remoteIpAddress) {
		this.remoteIpAddress = remoteIpAddress;
	}

	@Override
	public void setRequestMethod(final HttpRequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	@Override
	public void setRequestParameter(final String requestParameter) {
		this.requestParameter = requestParameter;
	}

	@Override
	public void setRequestUrlPath(final String requestUrlPath) {
		this.requestUrlPath = requestUrlPath;
	}

	@Override
	public void setResponseDataSize(final Long responseDataSize) {
		this.responseDataSize = responseDataSize;
	}

	@Override
	public void setSessionId(final String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public void setUserAgent(final String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("ContentType: ").append(getContentType()).append(", ");
		buffer.append("FinishedRequestAt: ").append(getFinishedRequestAt()).append(", ");
		buffer.append("HttpStatus: ").append(getHttpStatus()).append(", ");
		buffer.append("Id: ").append(getId()).append(", ");
		buffer.append("LocalHost: ").append(getLocalHost()).append(", ");
		buffer.append("LocalIpAddress: ").append(getLocalIpAddress()).append(", ");
		buffer.append("Referer: ").append(getReferer()).append(", ");
		buffer.append("RemoteHost: ").append(getRemoteHost()).append(", ");
		buffer.append("RemoteIpAddress: ").append(getRemoteIpAddress()).append(", ");
		buffer.append("RequestMethod: ").append(getRequestMethod()).append(", ");
		buffer.append("RequestParameter: ").append(getRequestParameter()).append(", ");
		buffer.append("RequestUrlPath: ").append(getRequestUrlPath()).append(", ");
		buffer.append("ResponseDataSize: ").append(getResponseDataSize()).append(", ");
		buffer.append("SessionId: ").append(getSessionId()).append(", ");
		buffer.append("UserAgent: ").append(getUserAgent()).append(", ");
		buffer.append("UserId: ").append(getUserId()).append(", ");
		buffer.append("Version: ").append(getVersion());
		return buffer.toString();
	}

}
