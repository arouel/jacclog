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
package net.sf.jacclog.service.importer.internal.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jacclog.logformat.LogFormat;
import net.sf.jacclog.service.repository.HttpRequestMethod;
import net.sf.jacclog.service.repository.HttpStatus;
import net.sf.jacclog.service.repository.LogEntry;

public class TokensToLogEntryMapperTest {

	private static final Logger LOG = LoggerFactory.getLogger(TokensToLogEntryMapperTest.class);

	@Test
	public void testMappingToLogEntryWithCommonLogFormat() throws ParseException {
		final List<String> tokens = new ArrayList<String>();
		tokens.add("192.168.123.12");
		tokens.add("-");
		tokens.add("-");
		tokens.add("19/Oct/2008:19:45:38 -0700");
		tokens.add("GET /search?q1=foo&st=bar HTTP/1.1");
		tokens.add("200");
		tokens.add("323");

		final LogEntry entry = TokensToLogEntryMapper.map(LogFormat.Defaults.COMMON.getFormat(), tokens);

		Assert.assertSame(tokens.get(0), entry.getRemoteHost());
		Assert.assertSame(tokens.get(2), entry.getUserId());
		Assert.assertSame(HttpRequestMethod.GET, entry.getRequestMethod());

		Assert.assertNotNull(entry.getFinishedRequestAt());
		final long epoch = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH).parse(
				"19/Oct/2008:19:45:38 -0700").getTime();
		Assert.assertEquals(new Date(epoch), entry.getFinishedRequestAt());
		Assert.assertEquals(new Date(1224470738000l), entry.getFinishedRequestAt());
		Assert.assertEquals(
				new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH).parse("19/Oct/2008:19:45:38 -0700"),
				entry.getFinishedRequestAt());

		Assert.assertEquals("/search", entry.getRequestUrlPath());
		Assert.assertEquals("q1=foo&st=bar", entry.getRequestParameter());
		Assert.assertSame(HttpStatus.OK, entry.getHttpStatus());
		Assert.assertEquals(Long.valueOf(323), entry.getResponseDataSize());

		LOG.info(entry.toString());
	}

	@Test
	public void testMappingToLogEntryWithCombinedLogFormat() throws ParseException {
		final List<String> tokens = new ArrayList<String>();
		tokens.add("192.168.123.12");
		tokens.add("-");
		tokens.add("-");
		tokens.add("19/Oct/2008:19:45:38 -0700");
		tokens.add("GET /search?q1=foo&st=bar HTTP/1.1");
		tokens.add("200");
		tokens.add("32343");
		tokens.add("http://www.mydomain.org/site/sub/index.html");
		tokens.add("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.14) Gecko/20080416 Fedora/2.0.0.14-1.fc7 Firefox/2.0.0.14");

		final LogEntry entry = TokensToLogEntryMapper.map(LogFormat.Defaults.COMBINED.getFormat(), tokens);

		Assert.assertSame(tokens.get(0), entry.getRemoteHost());
		Assert.assertSame(tokens.get(2), entry.getUserId());
		Assert.assertSame(HttpRequestMethod.GET, entry.getRequestMethod());

		Assert.assertNotNull(entry.getFinishedRequestAt());
		final long epoch = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH).parse(
				"19/Oct/2008:19:45:38 -0700").getTime();
		Assert.assertEquals(new Date(epoch), entry.getFinishedRequestAt());
		Assert.assertEquals(new Date(1224470738000l), entry.getFinishedRequestAt());
		Assert.assertEquals(
				new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH).parse("19/Oct/2008:19:45:38 -0700"),
				entry.getFinishedRequestAt());

		Assert.assertEquals("/search", entry.getRequestUrlPath());
		Assert.assertEquals("q1=foo&st=bar", entry.getRequestParameter());
		Assert.assertSame(HttpStatus.OK, entry.getHttpStatus());
		Assert.assertEquals(Long.valueOf(32343), entry.getResponseDataSize());
		Assert.assertSame(tokens.get(7), entry.getReferer());
		Assert.assertSame(tokens.get(8), entry.getUserAgent());

		LOG.info(entry.toString());
	}

}
