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
package net.sf.jacclog.api.domain;

import java.util.Date;
import java.util.Set;

import net.sf.jacclog.api.domain.http.HttpConnectionStatus;
import net.sf.jacclog.api.domain.http.HttpRequestMethod;
import net.sf.jacclog.api.domain.http.HttpStatus;
import net.sf.jacclog.api.domain.http.ReadWritableHttpRequestHeaderField;
import net.sf.jacclog.api.domain.http.ReadWritableHttpResponseHeaderField;

/**
 * Defines a log entry that can be queried and modified.<br>
 * <br>
 * This interface gives access to change data.<br>
 * <br>
 * The implementation of this interface will be mutable. It may provide more advanced methods than those in this
 * interface.
 * 
 * @author André Rouél
 */
public interface ReadWritableLogEntry<I extends ReadWritableHttpRequestHeaderField, O extends ReadWritableHttpResponseHeaderField>
		extends ReadableLogEntry<I, O> {

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
	 */
	void setBytesReceived(final Long bytesReceived);

	/**
	 * Sets the sent bytes including the headers. If no sent bytes were logged, the given argument should be zero.
	 * 
	 * @param bytesSent
	 *            the bytes sent or <code>0</code>
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the given argument is smaller than <code>0</code>
	 */
	void setBytesSent(final Long bytesSent);

	/**
	 * Sets the connection status when response was completed. If no connection status was logged, the given argument
	 * should be an <code>HttpConnectionStatus.UNKNOWN</code>.
	 * 
	 * @param connectionStatus
	 *            the connection status
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setConnectionStatus(final HttpConnectionStatus connectionStatus);

	/**
	 * Sets the filename of the log file.
	 * 
	 * @param filename
	 *            the filename of the log file
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setFilename(final String filename);

	/**
	 * Sets the status code of the last HTTP request. When internal redirects are made​​, the final status should be set
	 * with this method.
	 * 
	 * @param lastStatusCode
	 *            the status code of the last HTTP request
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setLastStatusCode(final HttpStatus lastStatusCode);

	/**
	 * Sets the local (usually the servers) IP address.
	 * 
	 * @param localIpAddress
	 *            the local IP address
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setLocalIpAddress(final String localIpAddress);

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
	 */
	void setProcessId(final Integer processId);

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
	 */
	void setQueryString(final String queryString);

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
	 */
	void setRemoteHost(final String remoteHost);

	/**
	 * Sets the client IP address (like 93.71.122.14) of the remote user requesting the resource.
	 * 
	 * @param remoteIpAddress
	 *            the remote IP address
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setRemoteIpAddress(final String remoteIpAddress);

	/**
	 * Sets the remote logname (from identd, if supplied).
	 * 
	 * @param remoteLogname
	 *            the remote logname
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setRemoteLogname(final String remoteLogname);

	/**
	 * Sets the remote user name.
	 * 
	 * @param remoteUser
	 *            the remote user name
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setRemoteUser(final String remoteUser);

	/**
	 * Sets the logged header lines of the HTTP request. If the set is empty no header informations were logged.
	 * 
	 * @param requestHeaders
	 *            the set of request headers
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setRequestHeaders(final Set<I> requestHeaders);

	/**
	 * Sets the request in milliseconds.
	 * 
	 * @param requestInMillis
	 *            the request in milliseconds
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setRequestInMillis(final Long requestInMillis);

	/**
	 * Sets the logged HTTP request method. If it is a valid method the type will be a field of
	 * <code>HttpRequestMethod</code>, otherwise it will be an instance of <code>UnknownHttpRequestMethod</code>.
	 * 
	 * @param requestMethod
	 *            the HTTP request method
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setRequestMethod(final HttpRequestMethod requestMethod);

	/**
	 * Sets the used HTTP protocol of the request.
	 * 
	 * @param requestProtocol
	 *            the HTTP protocol
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setRequestProtocol(final String requestProtocol);

	/**
	 * Sets the time the request was received.
	 * 
	 * @param requestTime
	 *            the request time
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setRequestTime(final Date requestTime);

	/**
	 * Sets the logged header lines of the HTTP response. If the set is empty no header informations were logged.
	 * 
	 * @param responseHeaders
	 *            the set of response headers
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setResponseHeaders(final Set<O> responseHeaders);

	/**
	 * Sets the size of response in bytes, excluding HTTP headers.
	 * 
	 * @param responseInBytes
	 *            the response in bytes
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setResponseInBytes(final Long responseInBytes);

	/**
	 * Sets the server name according to the UseCanonicalName setting.
	 * 
	 * @param serverName
	 *            the server name
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setServerName(final String serverName);

	/**
	 * Sets the canonical port of the server serving the request.
	 * 
	 * @param serverPort
	 *            the server port
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setServerPort(final Integer serverPort);

	/**
	 * Sets the status code of the <strong>original</code> request not of the redirected one.
	 * 
	 * @param statusCode
	 *            the status code
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setStatusCode(final HttpStatus statusCode);

	/**
	 * Sets the URL path requested, not including any query string.
	 * 
	 * @param urlPath
	 *            the URL path
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	void setUrlPath(final String urlPath);

}
