package net.sf.jacclog.persistence.jpa;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import net.sf.jacclog.api.domain.http.HttpConnectionStatus;
import net.sf.jacclog.api.domain.http.HttpRequestHeader;
import net.sf.jacclog.api.domain.http.HttpRequestMethod;
import net.sf.jacclog.api.domain.http.HttpResponseHeader;
import net.sf.jacclog.api.domain.http.HttpStatus;
import net.sf.jacclog.persistence.jpa.entity.HttpRequestHeaderField;
import net.sf.jacclog.persistence.jpa.entity.HttpResponseHeaderField;
import net.sf.jacclog.persistence.jpa.entity.LogEntry;
import net.sf.jacclog.service.repository.domain.PersistableHttpRequestHeaderField;
import net.sf.jacclog.service.repository.domain.PersistableHttpResponseHeaderField;

import org.junit.Test;

public class LogEntryTest {

	@Test
	public void testEquality() {

		final LogEntry entry1 = new LogEntry();
		entry1.setBytesReceived(1l);
		entry1.setBytesSent(2l);
		entry1.setConnectionStatus(HttpConnectionStatus.KEPT_ALIVE);
		entry1.setFilename("test.log");
		entry1.setId(3l);
		entry1.setLastStatusCode(HttpStatus.OK);
		entry1.setLocalIpAddress("192.168.1.1");
		entry1.setProcessId(4001);
		entry1.setQueryString("?key=value");
		entry1.setRemoteHost("remote.host.net");
		entry1.setRemoteIpAddress("19.12.34.15");
		entry1.setRemoteLogname("-");
		entry1.setRemoteUser("user");
		final Set<PersistableHttpRequestHeaderField> requestHeaders = new HashSet<PersistableHttpRequestHeaderField>();
		requestHeaders.add(new HttpRequestHeaderField(HttpRequestHeader.HOST, "jacclog.sf.net"));
		requestHeaders.add(new HttpRequestHeaderField(HttpRequestHeader.FROM, "mail.of.user@client.net"));
		entry1.setRequestHeaders(requestHeaders);
		entry1.setRequestInMillis(4l);
		entry1.setRequestMethod(HttpRequestMethod.GET);
		entry1.setRequestProtocol("HTTP/1.1");
		entry1.setRequestTime(new Date(0));
		final Set<PersistableHttpResponseHeaderField> responseHeaders = new HashSet<PersistableHttpResponseHeaderField>();
		responseHeaders.add(new HttpResponseHeaderField(HttpResponseHeader.AGE, "1"));
		responseHeaders.add(new HttpResponseHeaderField(HttpResponseHeader.CONTENT_ENCODING, "deflate"));
		entry1.setResponseHeaders(responseHeaders);
		entry1.setResponseInBytes(5l);
		entry1.setServerName("jacclog.analyzer.net");
		entry1.setServerPort(8000);
		entry1.setStatusCode(HttpStatus.OK);
		entry1.setUrlPath("/");
		entry1.setVersion(1);

		final LogEntry entry2 = new LogEntry();
		entry2.setBytesReceived(1l);
		entry2.setBytesSent(2l);
		entry2.setConnectionStatus(HttpConnectionStatus.KEPT_ALIVE);
		entry2.setFilename("test.log");
		entry2.setId(3l);
		entry2.setLastStatusCode(HttpStatus.OK);
		entry2.setLocalIpAddress("192.168.1.1");
		entry2.setProcessId(4001);
		entry2.setQueryString("?key=value");
		entry2.setRemoteHost("remote.host.net");
		entry2.setRemoteIpAddress("19.12.34.15");
		entry2.setRemoteLogname("-");
		entry2.setRemoteUser("user");
		final Set<PersistableHttpRequestHeaderField> requestHeaders2 = new HashSet<PersistableHttpRequestHeaderField>();
		requestHeaders2.add(new HttpRequestHeaderField(HttpRequestHeader.HOST, "jacclog.sf.net"));
		requestHeaders2.add(new HttpRequestHeaderField(HttpRequestHeader.FROM, "mail.of.user@client.net"));
		entry2.setRequestHeaders(requestHeaders2);
		entry2.setRequestInMillis(4l);
		entry2.setRequestMethod(HttpRequestMethod.GET);
		entry2.setRequestProtocol("HTTP/1.1");
		entry2.setRequestTime(new Date(0));
		final Set<PersistableHttpResponseHeaderField> responseHeaders2 = new HashSet<PersistableHttpResponseHeaderField>();
		responseHeaders2.add(new HttpResponseHeaderField(HttpResponseHeader.AGE, "1"));
		responseHeaders2.add(new HttpResponseHeaderField(HttpResponseHeader.CONTENT_ENCODING, "deflate"));
		entry2.setResponseHeaders(responseHeaders2);
		entry2.setResponseInBytes(5l);
		entry2.setServerName("jacclog.analyzer.net");
		entry2.setServerPort(8000);
		entry2.setStatusCode(HttpStatus.OK);
		entry2.setUrlPath("/");
		entry2.setVersion(1);

		Assert.assertEquals(entry1, entry2);
		Assert.assertTrue(entry1.hashCode() == entry2.hashCode());
	}
}
