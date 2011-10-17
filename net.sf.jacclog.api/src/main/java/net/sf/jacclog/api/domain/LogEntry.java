package net.sf.jacclog.api.domain;

import java.net.HttpCookie;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.sf.jacclog.api.domain.http.HttpConnectionStatus;
import net.sf.jacclog.api.domain.http.HttpRequestMethod;
import net.sf.jacclog.api.domain.http.HttpStatus;
import net.sf.jacclog.api.domain.http.ReadableHttpRequestHeaderField;
import net.sf.jacclog.api.domain.http.ReadableHttpResponseHeaderField;

/**
 * LogEntry is the standard implementation of an immutable access log entry.<br>
 * <br>
 * It is recommended to use the related <code>LogEntryBuilder</code> to create instances of this class.
 * 
 * @see net.sf.jacclog.api.domain.LogEntryBuilder
 * 
 * @author André Rouél
 */
public class LogEntry implements ReadonlyLogEntry {

	/**
	 * Represents an empty log entry.
	 */
	public static final LogEntry EMPTY = new LogEntry(0l, 0l, HttpConnectionStatus.UNKNOWN, new HashSet<HttpCookie>(),
			"", HttpStatus.UNKNOWN, "", 0, "", "", "", "", "", new HashSet<ReadableHttpRequestHeaderField>(), 0l,
			HttpRequestMethod.UNKNOWN, "", new Date(0), new HashSet<ReadableHttpResponseHeaderField>(), 0l, "", 0,
			HttpStatus.UNKNOWN, "");

	/**
	 * Bytes received, including request and headers
	 */
	private final Long bytesReceived;

	/**
	 * Bytes sent, including headers
	 */
	private final Long bytesSent;

	/**
	 * Connection status when response was completed.
	 */
	private final HttpConnectionStatus connectionStatus;

	/**
	 * The contents of cookies in the request sent to the server.
	 */
	private final Set<HttpCookie> cookies;

	/**
	 * Filename
	 */
	private final String filename;

	/**
	 * Status for the last request
	 */
	private final HttpStatus lastStatusCode;

	/**
	 * Local IP address
	 */
	private final String localIpAddress;

	/**
	 * The process ID of the child that serviced the request.
	 */
	private final Integer processId;

	/**
	 * The query string (prepended with a <code>?</code> if a query string exists, otherwise an empty string)
	 */
	private final String queryString;

	/**
	 * The client IP address (like 93.71.122.14) or the corresponding hostname (like jacclog.sf.net) of the remote user
	 * requesting the page. For performance reasons, many web servers are configured not to do hostname lookups on the
	 * remote host. This means that all you end up with in the log file is a bunch of IP addresses.
	 */
	private final String remoteHost;

	/**
	 * The client IP address of the request
	 */
	private final String remoteIpAddress;

	/**
	 * Remote logname (from identd, if supplied). This will return a dash unless IdentityCheck is set On.
	 */
	private final String remoteLogname;

	/**
	 * Remote user (from auth; may be bogus if return status (%s) is 401)
	 */
	private final String remoteUser;

	/**
	 * The contents of the header line(s) in the request sent to the server.
	 */
	private final Set<ReadableHttpRequestHeaderField> requestHeaders;

	/**
	 * The time taken to serve the request in microseconds.
	 */
	private final Long requestInMillis;

	/**
	 * The request method.
	 */
	private final HttpRequestMethod requestMethod;

	/**
	 * The request protocol.
	 */
	private final String requestProtocol;

	/**
	 * Time the request was received.
	 */
	private final Date requestTime;

	/**
	 * The contents of Foobar: header line(s) in the reply.
	 */
	private final Set<ReadableHttpResponseHeaderField> responseHeaders;

	/**
	 * Size of response in bytes, excluding HTTP headers.<br>
	 * In CLF format the value '-' will be interpreted as 0 (when no bytes are sent).
	 */
	private final Long responseInBytes;

	/**
	 * The server name according to the UseCanonicalName setting.
	 */
	private final String serverName;

	/**
	 * The canonical port of the server serving the request.
	 */
	private final Integer serverPort;

	/**
	 * Status. For requests that got internally redirected, this is the status of the *original* request --- %...>s for
	 * the last.
	 */
	private final HttpStatus statusCode;

	/**
	 * The URL path requested, not including any query string.<br>
	 * <br>
	 * Placeholder: <code>%U</code>
	 */
	private final String urlPath;

	/**
	 * Constructs a log entry
	 * 
	 * @param bytesReceived
	 * @param bytesSent
	 * @param connectionStatus
	 * @param cookies
	 * @param filename
	 * @param lastStatusCode
	 * @param localIpAddress
	 * @param processId
	 * @param queryString
	 * @param remoteHost
	 * @param remoteIpAddress
	 * @param remoteLogname
	 * @param remoteUser
	 * @param requestHeaders
	 * @param requestInMillis
	 * @param requestMethod
	 * @param requestProtocol
	 * @param requestTime
	 * @param responseHeaders
	 * @param responseInBytes
	 * @param serverName
	 * @param serverPort
	 * @param statusCode
	 * @param urlPath
	 * @throws IllegalArgumentException
	 *             if some of the given arguments invalid
	 */
	public LogEntry(final Long bytesReceived, final Long bytesSent, final HttpConnectionStatus connectionStatus,
			final Set<HttpCookie> cookies, final String filename, final HttpStatus lastStatusCode,
			final String localIpAddress, final Integer processId, final String queryString, final String remoteHost,
			final String remoteIpAddress, final String remoteLogname, final String remoteUser,
			final Set<ReadableHttpRequestHeaderField> requestHeaders, final Long requestInMillis,
			final HttpRequestMethod requestMethod, final String requestProtocol, final Date requestTime,
			final Set<ReadableHttpResponseHeaderField> responseHeaders, final Long responseInBytes,
			final String serverName, final Integer serverPort, final HttpStatus statusCode, final String urlPath) {

		if (bytesReceived == null) {
			throw new IllegalArgumentException("Argument 'bytesReceived' can not be null.");
		}
		if (bytesReceived < 0) {
			throw new IllegalArgumentException("Argument 'bytesReceived' can not be smaller than 0.");
		}
		if (bytesSent == null) {
			throw new IllegalArgumentException("Argument 'bytesSent' can not be null.");
		}
		if (bytesSent < 0) {
			throw new IllegalArgumentException("Argument 'bytesSent' can not be smaller than 0.");
		}
		if (connectionStatus == null) {
			throw new IllegalArgumentException("Argument 'connectionStatus' can not be null.");
		}
		if (cookies == null) {
			throw new IllegalArgumentException("Argument 'cookies' can not be null.");
		}
		if (filename == null) {
			throw new IllegalArgumentException("Argument 'filename' can not be null.");
		}
		if (lastStatusCode == null) {
			throw new IllegalArgumentException("Argument 'lastStatusCode' can not be null.");
		}
		if (localIpAddress == null) {
			throw new IllegalArgumentException("Argument 'localIpAddress' can not be null.");
		}
		if (processId == null) {
			throw new IllegalArgumentException("Argument 'processId' can not be null.");
		}
		if (processId < 0) {
			throw new IllegalArgumentException("Argument 'processId' can not be smaller than 0.");
		}
		if (queryString == null) {
			throw new IllegalArgumentException("Argument 'queryString' can not be null.");
		}
		if (remoteHost == null) {
			throw new IllegalArgumentException("Argument 'remoteHost' can not be null.");
		}
		if (remoteIpAddress == null) {
			throw new IllegalArgumentException("Argument 'remoteIpAddress' can not be null.");
		}
		if (remoteLogname == null) {
			throw new IllegalArgumentException("Argument 'remoteLogname' can not be null.");
		}
		if (remoteUser == null) {
			throw new IllegalArgumentException("Argument 'remoteUser' can not be null.");
		}
		if (requestHeaders == null) {
			throw new IllegalArgumentException("Argument 'requestHeaders' can not be null.");
		}
		if (requestInMillis == null) {
			throw new IllegalArgumentException("Argument 'requestInMillis' can not be null.");
		}
		if (requestInMillis < 0) {
			throw new IllegalArgumentException("Argument 'requestInMillis' can not be smaller than 0.");
		}
		if (requestMethod == null) {
			throw new IllegalArgumentException("Argument 'requestMethod' can not be null.");
		}
		if (requestProtocol == null) {
			throw new IllegalArgumentException("Argument 'requestProtocol' can not be null.");
		}
		if (requestTime == null) {
			throw new IllegalArgumentException("Argument 'requestTime' can not be null.");
		}
		if (responseHeaders == null) {
			throw new IllegalArgumentException("Argument 'responseHeaders' can not be null.");
		}
		if (responseInBytes == null) {
			throw new IllegalArgumentException("Argument 'responseInBytes' can not be null.");
		}
		if (responseInBytes < 0) {
			throw new IllegalArgumentException("Argument 'responseInBytes' can not be smaller than 0.");
		}
		if (serverName == null) {
			throw new IllegalArgumentException("Argument 'serverName' can not be null.");
		}
		if (serverPort == null) {
			throw new IllegalArgumentException("Argument 'serverPort' can not be null.");
		}
		if (statusCode == null) {
			throw new IllegalArgumentException("Argument 'statusCode' can not be null.");
		}
		if (urlPath == null) {
			throw new IllegalArgumentException("Argument 'urlPath' can not be null.");
		}

		this.bytesReceived = bytesReceived;
		this.bytesSent = bytesSent;
		this.connectionStatus = connectionStatus;
		this.cookies = cookies;
		this.filename = filename;
		this.lastStatusCode = lastStatusCode;
		this.localIpAddress = localIpAddress;
		this.processId = processId;
		this.queryString = queryString;
		this.remoteHost = remoteHost;
		this.remoteIpAddress = remoteIpAddress;
		this.remoteLogname = remoteLogname;
		this.remoteUser = remoteUser;
		this.requestHeaders = requestHeaders;
		this.requestInMillis = requestInMillis;
		this.requestMethod = requestMethod;
		this.requestProtocol = requestProtocol;
		this.requestTime = requestTime;
		this.responseHeaders = responseHeaders;
		this.responseInBytes = responseInBytes;
		this.serverName = serverName;
		this.serverPort = serverPort;
		this.statusCode = statusCode;
		this.urlPath = urlPath;
	}

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
		if (cookies == null) {
			if (other.cookies != null) {
				return false;
			}
		} else if (!cookies.equals(other.cookies)) {
			return false;
		}
		if (filename == null) {
			if (other.filename != null) {
				return false;
			}
		} else if (!filename.equals(other.filename)) {
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
		} else if (!requestHeaders.equals(other.requestHeaders)) {
			return false;
		}
		if (requestInMillis == null) {
			if (other.requestInMillis != null) {
				return false;
			}
		} else if (!requestInMillis.equals(other.requestInMillis)) {
			return false;
		}
		if (requestMethod == null) {
			if (other.requestMethod != null) {
				return false;
			}
		} else if (!requestMethod.equals(other.requestMethod)) {
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
		} else if (!responseHeaders.equals(other.responseHeaders)) {
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
		return true;
	}

	/**
	 * Gets the received bytes including the request and headers. If zero returns, then no received bytes were logged.
	 * 
	 * @return the bytes received or <code>0</code>
	 */
	@Override
	public Long getBytesReceived() {
		return bytesReceived;
	}

	/**
	 * Gets the sent bytes including the headers. If zero returns, then no sent bytes were logged.
	 * 
	 * @return the bytes sent or <code>0</code>
	 */
	@Override
	public Long getBytesSent() {
		return bytesSent;
	}

	/**
	 * Gets the connection status when response was completed.
	 * 
	 * @return the connection status
	 */
	@Override
	public HttpConnectionStatus getConnectionStatus() {
		return connectionStatus;
	}

	/**
	 * Gets the filename of the log file.
	 * 
	 * @return the filename the log file
	 */
	@Override
	public String getFilename() {
		return filename;
	}

	/**
	 * Gets the status code of the last HTTP request. When internal redirects are made​​, the final status will be
	 * returned.
	 * 
	 * @return the status code of the last HTTP request
	 */
	@Override
	public HttpStatus getLastStatusCode() {
		return lastStatusCode;
	}

	/**
	 * Gets the local (usually the servers) IP address.
	 * 
	 * @return the local IP address
	 */
	@Override
	public String getLocalIpAddress() {
		return localIpAddress;
	}

	/**
	 * Gets the ID of the process that serviced the request.
	 * 
	 * @return the ID of the process
	 */
	@Override
	public Integer getProcessId() {
		return processId;
	}

	/**
	 * Gets the query string (prepended with a <code>?</code> if a query string exists, otherwise an empty string) of
	 * the requested URL.<br>
	 * <br>
	 * The query string is the part of a Uniform Resource Locator (URL) that contains data to be passed to web
	 * applications.<br>
	 * <br>
	 * For example: <code>http://server/path/program?query_string</code>
	 * 
	 * @return the query string
	 */
	@Override
	public String getQueryString() {
		return queryString;
	}

	/**
	 * Gets the client IP address (like 93.71.122.14) or the corresponding hostname (like <code>jacclog.sf.net</code>)
	 * of the remote user requesting the resource.<br>
	 * <br>
	 * For performance reasons, many web servers are configured not to do hostname lookups on the remote host. This
	 * means that all you end up within the log file is a bunch of IP addresses.
	 * 
	 * @return the remote IP address or hostname
	 */
	@Override
	public String getRemoteHost() {
		return remoteHost;
	}

	/**
	 * Gets the client IP address (like 93.71.122.14) of the remote user requesting the resource.
	 * 
	 * @return the remote IP address
	 */
	@Override
	public String getRemoteIpAddress() {
		return remoteIpAddress;
	}

	/**
	 * Gets the remote logname (from identd, if supplied).
	 * 
	 * @return the remote logname
	 */
	@Override
	public String getRemoteLogname() {
		return remoteLogname;
	}

	/**
	 * Gets the remote user name.
	 * 
	 * @return the remote user name
	 */
	@Override
	public String getRemoteUser() {
		return remoteUser;
	}

	/**
	 * Gets the logged header lines of the HTTP request. If the set is empty no header informations were logged.
	 * 
	 * @return the set of request headers
	 */
	@Override
	public Set<ReadableHttpRequestHeaderField> getRequestHeaders() {
		return Collections.unmodifiableSet(requestHeaders);
	}

	/**
	 * Gets the request in milliseconds.
	 * 
	 * @return the request in milliseconds
	 */
	@Override
	public Long getRequestInMillis() {
		return requestInMillis;
	}

	/**
	 * Gets the logged HTTP request method. If it is a valid method the type will be a field of
	 * <code>HttpRequestMethod</code>, otherwise it will be an instance of <code>UnknownHttpRequestMethod</code>.
	 * 
	 * @return the HTTP request method
	 */
	@Override
	public HttpRequestMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * Gets the used HTTP protocol of the request.
	 * 
	 * @return the HTTP protocol
	 */
	@Override
	public String getRequestProtocol() {
		return requestProtocol;
	}

	/**
	 * Gets the time the request was received.
	 * 
	 * @return the request time
	 */
	@Override
	public Date getRequestTime() {
		return requestTime;
	}

	/**
	 * Gets the logged header lines of the HTTP response. If the set is empty no header informations were logged.
	 * 
	 * @return the set of response headers
	 */
	@Override
	public Set<ReadableHttpResponseHeaderField> getResponseHeaders() {
		return Collections.unmodifiableSet(responseHeaders);
	}

	/**
	 * Gets the size of response in bytes, excluding HTTP headers.
	 * 
	 * @return the response in bytes
	 */
	@Override
	public Long getResponseInBytes() {
		return responseInBytes;
	}

	/**
	 * Gets the server name according to the UseCanonicalName setting.
	 * 
	 * @return the server name
	 */
	@Override
	public String getServerName() {
		return serverName;
	}

	/**
	 * Gets the canonical port of the server serving the request.
	 * 
	 * @return the server port
	 */
	@Override
	public Integer getServerPort() {
		return serverPort;
	}

	/**
	 * Gets the status code of the <strong>original</code> request not of the redirected one.
	 * 
	 * @return the status code
	 */
	@Override
	public HttpStatus getStatusCode() {
		return statusCode;
	}

	/**
	 * Gets the URL path requested, not including any query string.
	 * 
	 * @return the URL path
	 */
	@Override
	public String getUrlPath() {
		return urlPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bytesReceived == null) ? 0 : bytesReceived.hashCode());
		result = prime * result + ((bytesSent == null) ? 0 : bytesSent.hashCode());
		result = prime * result + ((connectionStatus == null) ? 0 : connectionStatus.hashCode());
		result = prime * result + ((cookies == null) ? 0 : cookies.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
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
		return result;
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
		builder.append(", cookies=");
		builder.append(cookies);
		builder.append(", filename=");
		builder.append(filename);
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
		builder.append("]");
		return builder.toString();
	}

}
