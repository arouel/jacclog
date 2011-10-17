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
import net.sf.jacclog.api.domain.http.ReadableHttpRequestHeaderField;
import net.sf.jacclog.api.domain.http.ReadableHttpResponseHeaderField;

/**
 * Defines the root interface of a log entry.<br>
 * <br>
 * The implementation of this interface may be mutable or immutable. This interface only gives access to retrieve data,
 * never to change it.
 * 
 * @author André Rouél
 */
public interface ReadableLogEntry<I extends ReadableHttpRequestHeaderField, O extends ReadableHttpResponseHeaderField> {

	/**
	 * Gets the received bytes including the request and headers. If zero returns, then no received bytes were logged.
	 * 
	 * @return the bytes received or <code>0</code>
	 */
	Long getBytesReceived();

	/**
	 * Gets the sent bytes including the headers. If zero returns, then no sent bytes were logged.
	 * 
	 * @return the bytes sent or <code>0</code>
	 */
	Long getBytesSent();

	/**
	 * Gets the connection status when response was completed.
	 * 
	 * @return the connection status
	 */
	HttpConnectionStatus getConnectionStatus();

	/**
	 * Gets the filename of the log file.
	 * 
	 * @return the filename the log file
	 */
	String getFilename();

	/**
	 * Gets the status code of the last HTTP request. When internal redirects are made​​, the final status will be
	 * returned.
	 * 
	 * @return the status code of the last HTTP request
	 */
	HttpStatus getLastStatusCode();

	/**
	 * Gets the local (usually the servers) IP address.
	 * 
	 * @return the local IP address
	 */
	String getLocalIpAddress();

	/**
	 * Gets the ID of the process that serviced the request.
	 * 
	 * @return the ID of the process
	 */
	Integer getProcessId();

	/**
	 * Gets the query string (prepended with a <code>?</code> if a query string exists, otherwise an empty string) of
	 * the requested URL.<br>
	 * <br>
	 * The query string is the part of a Uniform Resource Locator (URL) that contains data to be passed to web
	 * applications.<br>
	 * <br>
	 * For example: <code>?param1=value1&param2=value2</code>
	 * 
	 * @return the query string
	 */
	String getQueryString();

	/**
	 * Gets the client IP address (like 93.71.122.14) or the corresponding hostname (like <code>jacclog.sf.net</code>)
	 * of the remote user requesting the resource.<br>
	 * <br>
	 * For performance reasons, many web servers are configured not to do hostname lookups on the remote host. This
	 * means that all you end up within the log file is a bunch of IP addresses.
	 * 
	 * @return the remote IP address or hostname
	 */
	String getRemoteHost();

	/**
	 * Gets the client IP address (like 93.71.122.14) of the remote user requesting the resource.
	 * 
	 * @return the remote IP address
	 */
	String getRemoteIpAddress();

	/**
	 * Gets the remote logname (from identd, if supplied).
	 * 
	 * @return the remote logname
	 */
	String getRemoteLogname();

	/**
	 * Gets the remote user name.
	 * 
	 * @return the remote user name
	 */
	String getRemoteUser();

	/**
	 * Gets the logged header lines of the HTTP request. If the set is empty no header informations were logged.
	 * 
	 * @return the set of request headers
	 */
	Set<I> getRequestHeaders();

	/**
	 * Gets the request in milliseconds.
	 * 
	 * @return the request in milliseconds
	 */
	Long getRequestInMillis();

	/**
	 * Gets the logged HTTP request method. If it is a valid method the type will be a field of
	 * <code>HttpRequestMethod</code>, otherwise it will be an instance of <code>UnknownHttpRequestMethod</code>.
	 * 
	 * @return the HTTP request method
	 */
	HttpRequestMethod getRequestMethod();

	/**
	 * Gets the used HTTP protocol of the request.
	 * 
	 * @return the HTTP protocol
	 */
	String getRequestProtocol();

	/**
	 * Gets the time the request was received.
	 * 
	 * @return the request time
	 */
	Date getRequestTime();

	/**
	 * Gets the logged header lines of the HTTP response. If the set is empty no header informations were logged.
	 * 
	 * @return the set of response headers
	 */
	Set<O> getResponseHeaders();

	/**
	 * Gets the size of response in bytes, excluding HTTP headers.
	 * 
	 * @return the response in bytes
	 */
	Long getResponseInBytes();

	/**
	 * Gets the server name according to the UseCanonicalName setting.
	 * 
	 * @return the server name
	 */
	String getServerName();

	/**
	 * Gets the canonical port of the server serving the request.
	 * 
	 * @return the server port
	 */
	Integer getServerPort();

	/**
	 * Gets the status code of the <strong>original</strong> request not of the redirected one.
	 * 
	 * @return the status code
	 */
	HttpStatus getStatusCode();

	/**
	 * Gets the URL path requested, not including any query string.
	 * 
	 * @return the URL path
	 */
	String getUrlPath();

}
