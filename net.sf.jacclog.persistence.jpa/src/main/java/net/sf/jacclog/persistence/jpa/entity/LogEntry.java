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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import net.sf.jacclog.api.domain.http.HttpConnectionStatus;
import net.sf.jacclog.api.domain.http.HttpRequestMethod;
import net.sf.jacclog.api.domain.http.HttpStatus;
import net.sf.jacclog.persistence.jpa.internal.HttpRequestHeaderFieldMapper;
import net.sf.jacclog.persistence.jpa.internal.HttpResponseHeaderFieldMapper;
import net.sf.jacclog.service.repository.domain.PersistableHttpRequestHeaderField;
import net.sf.jacclog.service.repository.domain.PersistableHttpResponseHeaderField;
import net.sf.jacclog.service.repository.domain.PersistableLogEntry;

@Entity
@Table(name = "log_entries")
public class LogEntry implements PersistableLogEntry {

	/**
	 * Bytes received, including request and headers
	 */
	private Long bytesReceived;

	/**
	 * Bytes sent, including headers
	 */
	private Long bytesSent;

	/**
	 * Connection status when response was completed.
	 */
	@Enumerated(EnumType.STRING)
	private HttpConnectionStatus connectionStatus;

	/**
	 * Filename
	 */
	private String filename;

	/**
	 * The primary key of the entity
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	/**
	 * Status for the last request
	 */
	@Enumerated
	private HttpStatus lastStatusCode;

	/**
	 * Local IP address
	 */
	private String localIpAddress;

	/**
	 * The process ID of the child that serviced the request.
	 */
	private Integer processId;

	/**
	 * The query string (prepended with a <code>?</code> if a query string exists, otherwise an empty string)
	 */
	private String queryString;

	/**
	 * The client IP address (like 93.71.122.14) or the corresponding hostname (like jacclog.sf.net) of the remote user
	 * requesting the page. For performance reasons, many web servers are configured not to do hostname lookups on the
	 * remote host. This means that all you end up with in the log file is a bunch of IP addresses.
	 */
	private String remoteHost;

	/**
	 * The client IP address of the request
	 */
	private String remoteIpAddress;

	/**
	 * Remote logname (from identd, if supplied). This will return a dash unless IdentityCheck is set On.
	 */
	private String remoteLogname;

	/**
	 * Remote user (from auth; may be bogus if return status (%s) is 401)
	 */
	private String remoteUser;

	/**
	 * The contents of the header line(s) in the request sent to the server.
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(joinColumns = { @JoinColumn(name = "logentry_id") }, inverseJoinColumns = { @JoinColumn(name = "request_header_id") }, uniqueConstraints = { @UniqueConstraint(columnNames = {
			"logentry_id", "request_header_id" }) })
	private Set<HttpRequestHeaderField> requestHeaders = new HashSet<HttpRequestHeaderField>();

	/**
	 * The time taken to serve the request in microseconds.
	 */
	private Long requestInMillis;

	/**
	 * The request method.
	 */
	private HttpRequestMethod requestMethod;

	/**
	 * The request protocol.
	 */
	private String requestProtocol;

	/**
	 * Time the request was received.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestTime;

	/**
	 * The contents of Foobar: header line(s) in the reply.
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(joinColumns = { @JoinColumn(name = "logentry_id") }, inverseJoinColumns = { @JoinColumn(name = "response_header_id") }, uniqueConstraints = { @UniqueConstraint(columnNames = {
			"logentry_id", "response_header_id" }) })
	private Set<HttpResponseHeaderField> responseHeaders = new HashSet<HttpResponseHeaderField>();

	/**
	 * Size of response in bytes, excluding HTTP headers.<br>
	 * In CLF format the value '-' will be interpreted as 0 (when no bytes are sent).
	 */
	private Long responseInBytes;

	/**
	 * The server name according to the UseCanonicalName setting.
	 */
	private String serverName;

	/**
	 * The canonical port of the server serving the request.
	 */
	private Integer serverPort;

	/**
	 * The status code. For requests that got internally redirected, this is the status of the <i>original</i> request.<br>
	 * <br>
	 * For the last request look at {@link lastStatusCode};
	 */
	@Enumerated
	private HttpStatus statusCode;

	/**
	 * The URL path requested, not including any query string.<br>
	 * <br>
	 * Placeholder: <code>%U</code>
	 */
	private String urlPath;

	/**
	 * The version number of the entity
	 */
	@Version
	@Column(name = "version")
	private Integer version;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final LogEntry other = (LogEntry) obj;
		if (bytesReceived == null) {
			if (other.bytesReceived != null) {
				return false;
			}
		} else if (!bytesReceived.equals(other.bytesReceived)) {
			return false;
		}
		if (bytesSent == null) {
			if (other.bytesSent != null) {
				return false;
			}
		} else if (!bytesSent.equals(other.bytesSent)) {
			return false;
		}
		if (connectionStatus != other.connectionStatus) {
			return false;
		}
		if (filename == null) {
			if (other.filename != null) {
				return false;
			}
		} else if (!filename.equals(other.filename)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lastStatusCode != other.lastStatusCode) {
			return false;
		}
		if (localIpAddress == null) {
			if (other.localIpAddress != null) {
				return false;
			}
		} else if (!localIpAddress.equals(other.localIpAddress)) {
			return false;
		}
		if (processId == null) {
			if (other.processId != null) {
				return false;
			}
		} else if (!processId.equals(other.processId)) {
			return false;
		}
		if (queryString == null) {
			if (other.queryString != null) {
				return false;
			}
		} else if (!queryString.equals(other.queryString)) {
			return false;
		}
		if (remoteHost == null) {
			if (other.remoteHost != null) {
				return false;
			}
		} else if (!remoteHost.equals(other.remoteHost)) {
			return false;
		}
		if (remoteIpAddress == null) {
			if (other.remoteIpAddress != null) {
				return false;
			}
		} else if (!remoteIpAddress.equals(other.remoteIpAddress)) {
			return false;
		}
		if (remoteLogname == null) {
			if (other.remoteLogname != null) {
				return false;
			}
		} else if (!remoteLogname.equals(other.remoteLogname)) {
			return false;
		}
		if (remoteUser == null) {
			if (other.remoteUser != null) {
				return false;
			}
		} else if (!remoteUser.equals(other.remoteUser)) {
			return false;
		}
		if (requestHeaders == null) {
			if (other.requestHeaders != null) {
				return false;
			}
		} else if (!equalsRequestHeaders(other.requestHeaders)) {
			return false;
		}
		if (requestInMillis == null) {
			if (other.requestInMillis != null) {
				return false;
			}
		} else if (!requestInMillis.equals(other.requestInMillis)) {
			return false;
		}
		if (requestMethod != other.requestMethod) {
			return false;
		}
		if (requestProtocol == null) {
			if (other.requestProtocol != null) {
				return false;
			}
		} else if (!requestProtocol.equals(other.requestProtocol)) {
			return false;
		}
		if (requestTime == null) {
			if (other.requestTime != null) {
				return false;
			}
		} else if (!requestTime.equals(other.requestTime)) {
			return false;
		}
		if (responseHeaders == null) {
			if (other.responseHeaders != null) {
				return false;
			}
		} else if (!equalsResponseHeaders(other.responseHeaders)) {
			return false;
		}
		if (responseInBytes == null) {
			if (other.responseInBytes != null) {
				return false;
			}
		} else if (!responseInBytes.equals(other.responseInBytes)) {
			return false;
		}
		if (serverName == null) {
			if (other.serverName != null) {
				return false;
			}
		} else if (!serverName.equals(other.serverName)) {
			return false;
		}
		if (serverPort == null) {
			if (other.serverPort != null) {
				return false;
			}
		} else if (!serverPort.equals(other.serverPort)) {
			return false;
		}
		if (statusCode != other.statusCode) {
			return false;
		}
		if (urlPath == null) {
			if (other.urlPath != null) {
				return false;
			}
		} else if (!urlPath.equals(other.urlPath)) {
			return false;
		}
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

	private boolean equalsRequestHeaders(final Set<HttpRequestHeaderField> other) {
		return (other != null && requestHeaders != null && requestHeaders.size() == other.size() && requestHeaders
				.hashCode() == other.hashCode()) ? true : false;
	}

	private boolean equalsResponseHeaders(final Set<HttpResponseHeaderField> other) {
		return (other != null && responseHeaders != null && responseHeaders.size() == other.size() && responseHeaders
				.hashCode() == other.hashCode()) ? true : false;
	}

	@Override
	public Long getBytesReceived() {
		return bytesReceived;
	}

	@Override
	public Long getBytesSent() {
		return bytesSent;
	}

	@Override
	public HttpConnectionStatus getConnectionStatus() {
		return connectionStatus;
	}

	@Override
	public String getFilename() {
		return filename;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public HttpStatus getLastStatusCode() {
		return lastStatusCode;
	}

	@Override
	public String getLocalIpAddress() {
		return localIpAddress;
	}

	@Override
	public Integer getProcessId() {
		return processId;
	}

	@Override
	public String getQueryString() {
		return queryString;
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
	public String getRemoteLogname() {
		return remoteLogname;
	}

	@Override
	public String getRemoteUser() {
		return remoteUser;
	}

	@Override
	public Set<PersistableHttpRequestHeaderField> getRequestHeaders() {
		return HttpRequestHeaderFieldMapper.translate(requestHeaders);
	}

	@Override
	public Long getRequestInMillis() {
		return requestInMillis;
	}

	@Override
	public HttpRequestMethod getRequestMethod() {
		return requestMethod;
	}

	@Override
	public String getRequestProtocol() {
		return requestProtocol;
	}

	@Override
	public Date getRequestTime() {
		return requestTime;
	}

	@Override
	public Set<PersistableHttpResponseHeaderField> getResponseHeaders() {
		return HttpResponseHeaderFieldMapper.translate(responseHeaders);
	}

	@Override
	public Long getResponseInBytes() {
		return responseInBytes;
	}

	@Override
	public String getServerName() {
		return serverName;
	}

	@Override
	public Integer getServerPort() {
		return serverPort;
	}

	@Override
	public HttpStatus getStatusCode() {
		return statusCode;
	}

	@Override
	public String getUrlPath() {
		return urlPath;
	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bytesReceived == null) ? 0 : bytesReceived.hashCode());
		result = prime * result + ((bytesSent == null) ? 0 : bytesSent.hashCode());
		result = prime * result + ((connectionStatus == null) ? 0 : connectionStatus.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastStatusCode == null) ? 0 : lastStatusCode.hashCode());
		result = prime * result + ((localIpAddress == null) ? 0 : localIpAddress.hashCode());
		result = prime * result + ((processId == null) ? 0 : processId.hashCode());
		result = prime * result + ((queryString == null) ? 0 : queryString.hashCode());
		result = prime * result + ((remoteHost == null) ? 0 : remoteHost.hashCode());
		result = prime * result + ((remoteIpAddress == null) ? 0 : remoteIpAddress.hashCode());
		result = prime * result + ((remoteLogname == null) ? 0 : remoteLogname.hashCode());
		result = prime * result + ((remoteUser == null) ? 0 : remoteUser.hashCode());
		result = prime * result + ((requestHeaders == null) ? 0 : requestHeaders.hashCode());
		result = prime * result + ((requestInMillis == null) ? 0 : requestInMillis.hashCode());
		result = prime * result + ((requestMethod == null) ? 0 : requestMethod.hashCode());
		result = prime * result + ((requestProtocol == null) ? 0 : requestProtocol.hashCode());
		result = prime * result + ((requestTime == null) ? 0 : requestTime.hashCode());
		result = prime * result + ((responseHeaders == null) ? 0 : responseHeaders.hashCode());
		result = prime * result + ((responseInBytes == null) ? 0 : responseInBytes.hashCode());
		result = prime * result + ((serverName == null) ? 0 : serverName.hashCode());
		result = prime * result + ((serverPort == null) ? 0 : serverPort.hashCode());
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
		result = prime * result + ((urlPath == null) ? 0 : urlPath.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public void setBytesReceived(final Long bytesReceived) {
		this.bytesReceived = bytesReceived;
	}

	@Override
	public void setBytesSent(final Long bytesSent) {
		this.bytesSent = bytesSent;
	}

	@Override
	public void setConnectionStatus(final HttpConnectionStatus connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	@Override
	public void setFilename(final String filename) {
		this.filename = filename;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public void setLastStatusCode(final HttpStatus lastStatusCode) {
		this.lastStatusCode = lastStatusCode;
	}

	@Override
	public void setLocalIpAddress(final String localIpAddress) {
		this.localIpAddress = localIpAddress;
	}

	@Override
	public void setProcessId(final Integer processId) {
		this.processId = processId;
	}

	@Override
	public void setQueryString(final String queryString) {
		this.queryString = queryString;
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
	public void setRemoteLogname(final String remoteLogname) {
		this.remoteLogname = remoteLogname;
	}

	@Override
	public void setRemoteUser(final String remoteUser) {
		this.remoteUser = remoteUser;
	}

	@Override
	public void setRequestHeaders(
			final Set<net.sf.jacclog.service.repository.domain.PersistableHttpRequestHeaderField> requestHeaders) {
		this.requestHeaders = HttpRequestHeaderFieldMapper.mapPersistables(requestHeaders);
	}

	@Override
	public void setRequestInMillis(final Long requestInMillis) {
		this.requestInMillis = requestInMillis;
	}

	@Override
	public void setRequestMethod(final HttpRequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	@Override
	public void setRequestProtocol(final String requestProtocol) {
		this.requestProtocol = requestProtocol;
	}

	@Override
	public void setRequestTime(final Date requestTime) {
		this.requestTime = requestTime;
	}

	@Override
	public void setResponseHeaders(
			final Set<net.sf.jacclog.service.repository.domain.PersistableHttpResponseHeaderField> responseHeaders) {
		this.responseHeaders = HttpResponseHeaderFieldMapper.mapPersistables(responseHeaders);
	}

	@Override
	public void setResponseInBytes(final Long responseInBytes) {
		this.responseInBytes = responseInBytes;
	}

	@Override
	public void setServerName(final String serverName) {
		this.serverName = serverName;
	}

	@Override
	public void setServerPort(final Integer serverPort) {
		this.serverPort = serverPort;
	}

	@Override
	public void setStatusCode(final HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public void setUrlPath(final String urlPath) {
		this.urlPath = urlPath;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("LogEntry [bytesReceived=");
		builder.append(bytesReceived);
		builder.append(", bytesSent=");
		builder.append(bytesSent);
		builder.append(", connectionStatus=");
		builder.append(connectionStatus);
		builder.append(", filename=");
		builder.append(filename);
		builder.append(", id=");
		builder.append(id);
		builder.append(", lastStatusCode=");
		builder.append(lastStatusCode);
		builder.append(", localIpAddress=");
		builder.append(localIpAddress);
		builder.append(", processId=");
		builder.append(processId);
		builder.append(", queryString=");
		builder.append(queryString);
		builder.append(", remoteHost=");
		builder.append(remoteHost);
		builder.append(", remoteIpAddress=");
		builder.append(remoteIpAddress);
		builder.append(", remoteLogname=");
		builder.append(remoteLogname);
		builder.append(", remoteUser=");
		builder.append(remoteUser);
		builder.append(", requestHeaders=");
		builder.append(requestHeaders);
		builder.append(", requestInMillis=");
		builder.append(requestInMillis);
		builder.append(", requestMethod=");
		builder.append(requestMethod);
		builder.append(", requestProtocol=");
		builder.append(requestProtocol);
		builder.append(", requestTime=");
		builder.append(requestTime);
		builder.append(", responseHeaders=");
		builder.append(responseHeaders);
		builder.append(", responseInBytes=");
		builder.append(responseInBytes);
		builder.append(", serverName=");
		builder.append(serverName);
		builder.append(", serverPort=");
		builder.append(serverPort);
		builder.append(", statusCode=");
		builder.append(statusCode);
		builder.append(", urlPath=");
		builder.append(urlPath);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

}
