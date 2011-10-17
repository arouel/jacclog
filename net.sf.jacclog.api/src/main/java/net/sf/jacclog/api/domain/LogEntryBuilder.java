package net.sf.jacclog.api.domain;

import java.net.HttpCookie;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.sf.jacclog.api.domain.http.HttpConnectionStatus;
import net.sf.jacclog.api.domain.http.HttpRequestHeaderField;
import net.sf.jacclog.api.domain.http.HttpRequestMethod;
import net.sf.jacclog.api.domain.http.HttpResponseHeaderField;
import net.sf.jacclog.api.domain.http.HttpStatus;
import net.sf.jacclog.api.domain.http.ReadableHttpRequestHeaderField;
import net.sf.jacclog.api.domain.http.ReadableHttpResponseHeaderField;

/**
 * Log entry builder creates complex immutable instances of <code>LogEntry</code>.
 * 
 * @see net.sf.jacclog.api.domain.LogEntry
 * 
 * @author André Rouél
 */
public class LogEntryBuilder implements
		ReadableLogEntry<ReadableHttpRequestHeaderField, ReadableHttpResponseHeaderField> {

	/**
	 * Bytes received, including request and headers
	 */
	private Long bytesReceived = 0l;

	/**
	 * Bytes sent, including headers
	 */
	private Long bytesSent = 0l;

	/**
	 * Connection status when response was completed.
	 */
	private HttpConnectionStatus connectionStatus = HttpConnectionStatus.UNKNOWN;

	/**
	 * The contents of cookies in the request sent to the server.
	 */
	private final Set<HttpCookie> cookies = new HashSet<HttpCookie>();

	/**
	 * Filename
	 */
	private String filename = "";

	/**
	 * Status for the last request
	 */
	private HttpStatus lastStatusCode = HttpStatus.UNKNOWN;

	/**
	 * Local IP address
	 */
	private String localIpAddress = "";

	/**
	 * The process ID of the child that serviced the request.
	 */
	private Integer processId = 0;

	/**
	 * The query string (prepended with a <code>?</code> if a query string exists, otherwise an empty string)
	 */
	private String queryString = "";

	/**
	 * The client IP address (like 93.71.122.14) or the corresponding hostname (like jacclog.sf.net) of the remote user
	 * requesting the page. For performance reasons, many web servers are configured not to do hostname lookups on the
	 * remote host. This means that all you end up with in the log file is a bunch of IP addresses.
	 */
	private String remoteHost = "";

	/**
	 * The client IP address of the request
	 */
	private String remoteIpAddress = "";

	/**
	 * Remote logname (from identd, if supplied). This will return a dash unless IdentityCheck is set On.
	 */
	private String remoteLogname = "";

	/**
	 * Remote user (from auth; may be bogus if return status (%s) is 401)
	 */
	private String remoteUser = "";

	/**
	 * The contents of the header line(s) in the request sent to the server.
	 */
	private final Set<ReadableHttpRequestHeaderField> requestHeaders = new HashSet<ReadableHttpRequestHeaderField>();

	/**
	 * The time taken to serve the request in microseconds.
	 */
	private Long requestInMillis = 0l;

	/**
	 * The request method.
	 */
	private HttpRequestMethod requestMethod = HttpRequestMethod.UNKNOWN;

	/**
	 * The request protocol.
	 */
	private String requestProtocol = "";

	/**
	 * Time the request was received.
	 */
	private Date requestTime = new Date(0);

	/**
	 * The contents of Foobar: header line(s) in the reply.
	 */
	private final Set<ReadableHttpResponseHeaderField> responseHeaders = new HashSet<ReadableHttpResponseHeaderField>();

	/**
	 * Size of response in bytes, excluding HTTP headers.<br>
	 * In CLF format the value '-' will be interpreted as 0 (when no bytes are sent).
	 */
	private Long responseInBytes = 0l;

	/**
	 * The server name according to the UseCanonicalName setting.
	 */
	private String serverName = "";

	/**
	 * The canonical port of the server serving the request.
	 */
	private Integer serverPort = 0;

	/**
	 * Status. For requests that got internally redirected, this is the status of the *original* request --- %...>s for
	 * the last.
	 */
	private HttpStatus statusCode = HttpStatus.UNKNOWN;

	/**
	 * The URL path requested, not including any query string.<br>
	 * <br>
	 * Placeholder: <code>%U</code>
	 */
	private String urlPath = "";

	/**
	 * Appends a cookie to the set of cookies.
	 * 
	 * @param cookie
	 *            a HTTP cookie
	 * @return itself, for chaining
	 */
	public LogEntryBuilder appendCookies(final HttpCookie cookie) {
		if (cookie == null) {
			throw new IllegalArgumentException("Argument 'cookie' can not be null.");
		}
		this.cookies.add(cookie);
		return this;
	}

	/**
	 * Appends a HTTP request header to the set of request headers.
	 * 
	 * @param requestHeader
	 *            a HTTP request header
	 * @return itself, for chaining
	 */
	public LogEntryBuilder appendRequestHeaders(final HttpRequestHeaderField requestHeader) {
		if (requestHeader == null) {
			throw new IllegalArgumentException("Argument 'requestHeader' can not be null.");
		}
		this.requestHeaders.add(requestHeader);
		return this;
	}

	/**
	 * Appends a HTTP response header to the set of response headers.
	 * 
	 * @param responseHeader
	 *            a HTTP response header
	 * @return itself, for chaining
	 */
	public LogEntryBuilder appendResponseHeaders(final HttpResponseHeaderField responseHeader) {
		if (responseHeader == null) {
			throw new IllegalArgumentException("Argument 'responseHeader' can not be null.");
		}
		this.responseHeaders.add(responseHeader);
		return this;
	}

	/**
	 * Constructs an immutable <code>LogEntry</code>.
	 * 
	 * @throws IllegalArgumentException
	 *             if the construction arguments for <code>LogEntry</code> invalid
	 * @return a new log entry
	 */
	public LogEntry build() {
		return new LogEntry(bytesReceived, bytesSent, connectionStatus, cookies, filename, lastStatusCode,
				localIpAddress, processId, queryString, remoteHost, remoteIpAddress, remoteLogname, remoteUser,
				requestHeaders, requestInMillis, requestMethod, requestProtocol, requestTime, responseHeaders,
				responseInBytes, serverName, serverPort, statusCode, urlPath);
	}

	/**
	 * Sets the received bytes including the request and headers. If no received bytes were logged, the given argument
	 * should be zero.
	 * 
	 * @param bytesReceived
	 *            the bytes received or <code>0</code>
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the given argument is smaller than <code>0</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder bytesReceived(final Long bytesReceived) {
		if (bytesReceived == null) {
			throw new IllegalArgumentException("Argument 'bytesReceived' can not be null.");
		}
		if (bytesReceived < 0) {
			throw new IllegalArgumentException("Argument 'bytesReceived' can not be smaller than 0.");
		}
		this.bytesReceived = bytesReceived;
		return this;
	}

	/**
	 * Sets the sent bytes including the headers. If no sent bytes were logged, the given argument should be zero.
	 * 
	 * @param bytesSend
	 *            the bytes sent or <code>0</code>
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the given argument is smaller than <code>0</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder bytesSent(final Long bytesSend) {
		if (bytesSend == null) {
			throw new IllegalArgumentException("Argument 'bytesSend' can not be null.");
		}
		if (bytesSend < 0) {
			throw new IllegalArgumentException("Argument 'bytesSend' can not be smaller than 0.");
		}
		this.bytesSent = bytesSend;
		return this;
	}

	/**
	 * Sets the connection status when response was completed. If no connection status was logged, the given argument
	 * should be an <code>HttpConnectionStatus.UNKNOWN</code>.
	 * 
	 * @param connectionStatus
	 *            the connection status
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder connectionStatus(final HttpConnectionStatus connectionStatus) {
		if (connectionStatus == null) {
			throw new IllegalArgumentException("Argument 'connectionStatus' can not be null.");
		}
		this.connectionStatus = connectionStatus;
		return this;
	}

	/**
	 * Sets the filename of the log file.
	 * 
	 * @param filename
	 *            the filename of the log file
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder filename(final String filename) {
		if (filename == null) {
			throw new IllegalArgumentException("Argument 'filename' can not be null.");
		}
		this.filename = filename;
		return this;
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

	public Set<HttpCookie> getCookies() {
		return cookies;
	}

	@Override
	public String getFilename() {
		return filename;
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
	public Set<ReadableHttpRequestHeaderField> getRequestHeaders() {
		return requestHeaders;
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
	public Set<ReadableHttpResponseHeaderField> getResponseHeaders() {
		return responseHeaders;
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

	/**
	 * Sets the status code of the last HTTP request. When internal redirects are made​​, the final status should be set
	 * with this method.
	 * 
	 * @param lastStatusCode
	 *            the status code of the last HTTP request
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder lastStatusCode(final HttpStatus lastStatusCode) {
		if (lastStatusCode == null) {
			throw new IllegalArgumentException("Argument 'lastStatusCode' can not be null.");
		}
		this.lastStatusCode = lastStatusCode;
		return this;
	}

	/**
	 * Sets the local (usually the servers) IP address.
	 * 
	 * @param localIpAddress
	 *            the local IP address
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder localIpAddress(final String localIpAddress) {
		if (localIpAddress == null) {
			throw new IllegalArgumentException("Argument 'localIpAddress' can not be null.");
		}
		this.localIpAddress = localIpAddress;
		return this;
	}

	/**
	 * Sets the ID of the process that serviced the request. If no process ID was logged, the given argument should be
	 * zero.
	 * 
	 * @param processId
	 *            the ID of the process
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the given argument is smaller than <code>0</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder processId(final Integer processId) {
		if (processId == null) {
			throw new IllegalArgumentException("Argument 'processId' can not be null.");
		}
		if (processId < 0) {
			throw new IllegalArgumentException("Argument 'processId' can not be smaller than 0.");
		}
		this.processId = processId;
		return this;
	}

	/**
	 * Sets the query string (prepended with a <code>?</code> if a query string exists, otherwise an empty string) of
	 * the requested URL.<br>
	 * <br>
	 * The query string is the part of a Uniform Resource Locator (URL) that contains data to be passed to web
	 * applications.<br>
	 * <br>
	 * For example: <code>?param1=value1&param2=value2</code>
	 * 
	 * @param queryString
	 *            the query string
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder queryString(final String queryString) {
		if (queryString == null) {
			throw new IllegalArgumentException("Argument 'queryString' can not be null.");
		}
		this.queryString = queryString;
		return this;
	}

	/**
	 * Sets the client IP address (like 93.71.122.14) or the corresponding hostname (like <code>jacclog.sf.net</code>)
	 * of the remote user requesting the resource.<br>
	 * <br>
	 * For performance reasons, many web servers are configured not to do hostname lookups on the remote host. This
	 * means that all you end up within the log file is a bunch of IP addresses.
	 * 
	 * @param remoteHost
	 *            the remote IP address or hostname
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder remoteHost(final String remoteHost) {
		if (remoteHost == null) {
			throw new IllegalArgumentException("Argument 'remoteHost' can not be null.");
		}
		this.remoteHost = remoteHost;
		return this;
	}

	/**
	 * Sets the client IP address (like 93.71.122.14) of the remote user requesting the resource.
	 * 
	 * @param remoteIpAddress
	 *            the remote IP address
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder remoteIpAddress(final String remoteIpAddress) {
		if (remoteIpAddress == null) {
			throw new IllegalArgumentException("Argument 'remoteIpAddress' can not be null.");
		}
		this.remoteIpAddress = remoteIpAddress;
		return this;
	}

	/**
	 * Sets the remote logname (from identd, if supplied).
	 * 
	 * @param remoteLogname
	 *            the remote logname
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder remoteLogname(final String remoteLogname) {
		if (remoteLogname == null) {
			throw new IllegalArgumentException("Argument 'remoteLogname' can not be null.");
		}
		this.remoteLogname = remoteLogname;
		return this;
	}

	/**
	 * Sets the remote user name.
	 * 
	 * @param remoteUser
	 *            the remote user name
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder remoteUser(final String remoteUser) {
		if (remoteUser == null) {
			throw new IllegalArgumentException("Argument 'remoteUser' can not be null.");
		}
		this.remoteUser = remoteUser;
		return this;
	}

	/**
	 * Sets the request in milliseconds.
	 * 
	 * @param requestInMillis
	 *            the request in milliseconds
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder requestInMillis(final Long requestInMillis) {
		if (requestInMillis == null) {
			throw new IllegalArgumentException("Argument 'requestInMillis' can not be null.");
		}
		if (requestInMillis < 0) {
			throw new IllegalArgumentException("Argument 'requestInMillis' can not be smaller than 0.");
		}
		this.requestInMillis = requestInMillis;
		return this;
	}

	/**
	 * Sets the logged HTTP request method. If it is a valid method the type will be a field of
	 * <code>HttpRequestMethod</code>, otherwise it will be an instance of <code>UnknownHttpRequestMethod</code>.
	 * 
	 * @param requestMethod
	 *            the HTTP request method
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder requestMethod(final HttpRequestMethod requestMethod) {
		if (requestMethod == null) {
			throw new IllegalArgumentException("Argument 'requestMethod' can not be null.");
		}
		this.requestMethod = requestMethod;
		return this;
	}

	/**
	 * Sets the used HTTP protocol of the request.
	 * 
	 * @param requestProtocol
	 *            the HTTP protocol
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder requestProtocol(final String requestProtocol) {
		if (requestProtocol == null) {
			throw new IllegalArgumentException("Argument 'requestProtocol' can not be null.");
		}
		this.requestProtocol = requestProtocol;
		return this;
	}

	/**
	 * Sets the time the request was received.
	 * 
	 * @param requestTime
	 *            the request time
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder requestTime(final Date requestTime) {
		if (requestTime == null) {
			throw new IllegalArgumentException("Argument 'requestTime' can not be null.");
		}
		this.requestTime = requestTime;
		return this;
	}

	/**
	 * Sets the size of response in bytes, excluding HTTP headers.
	 * 
	 * @param responseInBytes
	 *            the response in bytes
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder responseInBytes(final Long responseInBytes) {
		if (responseInBytes == null) {
			throw new IllegalArgumentException("Argument 'responseInBytes' can not be null.");
		}
		if (responseInBytes < 0) {
			throw new IllegalArgumentException("Argument 'responseInBytes' can not be smaller than 0.");
		}
		this.responseInBytes = responseInBytes;
		return this;
	}

	/**
	 * Sets the server name according to the UseCanonicalName setting.
	 * 
	 * @param serverName
	 *            the server name
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder serverName(final String serverName) {
		if (serverName == null) {
			throw new IllegalArgumentException("Argument 'serverName' can not be null.");
		}
		this.serverName = serverName;
		return this;
	}

	/**
	 * Sets the canonical port of the server serving the request.
	 * 
	 * @param serverPort
	 *            the server port
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder serverPort(final Integer serverPort) {
		if (serverPort == null) {
			throw new IllegalArgumentException("Argument 'serverPort' can not be null.");
		}
		this.serverPort = serverPort;
		return this;
	}

	/**
	 * Sets the status code of the <strong>original</code> request not of the redirected one.
	 * 
	 * @param statusCode
	 *            the status code
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder statusCode(final HttpStatus statusCode) {
		if (statusCode == null) {
			throw new IllegalArgumentException("Argument 'statusCode' can not be null.");
		}
		this.statusCode = statusCode;
		return this;
	}

	/**
	 * Sets the URL path requested, not including any query string.
	 * 
	 * @param urlPath
	 *            the URL path
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @return itself, for chaining
	 */
	public LogEntryBuilder urlPath(final String urlPath) {
		if (urlPath == null) {
			throw new IllegalArgumentException("Argument 'urlPath' can not be null.");
		}
		this.urlPath = urlPath;
		return this;
	}

}
