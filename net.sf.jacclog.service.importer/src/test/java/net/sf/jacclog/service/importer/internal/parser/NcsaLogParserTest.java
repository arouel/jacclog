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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.api.domain.http.HttpRequestHeader;
import net.sf.jacclog.api.domain.http.HttpRequestHeaderField;
import net.sf.jacclog.api.domain.http.HttpRequestMethod;
import net.sf.jacclog.api.domain.http.HttpStatus;
import net.sf.jacclog.logformat.LogFormat;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test for NCSA Log parser.
 */
public class NcsaLogParserTest {

	private static final Logger LOG = LoggerFactory.getLogger(NcsaLogParserTest.class);

	private void printResult(final String line, final List<String> tokens) {
		LOG.info(">>>\n" + line);
		LOG.info("tokens={");
		int i = 0;
		for (final String token : tokens) {
			LOG.info("  [" + i + "] = " + token);
			i++;
		}
		LOG.info("}");
	}

	@Test
	public void testParseCombinedLogFormat() throws Exception {
		final String line = "192.168.123.12 - - [19/Oct/2008:19:45:38 -0700] \""
				+ "GET /search?q1=foo&st=bar HTTP/1.1\" 200 323 "
				+ "\"-\" \"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.14) "
				+ "Gecko/20080416 Fedora/2.0.0.14-1.fc7 Firefox/2.0.0.14\"";
		final List<String> tokens = NcsaLogParser.parse(line);
		printResult(line, tokens);

		final ReadonlyLogEntry entry = new NcsaLogParser(LogFormat.Defaults.COMBINED.getFormat()).parseLine(line);
		// TODO %l is not tested yet
		Assert.assertEquals("192.168.123.12", entry.getRemoteHost());
		Assert.assertEquals("-", entry.getRemoteUser());
		Assert.assertSame(HttpRequestMethod.GET, entry.getRequestMethod());
		final long epoch = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH).parse(
				"19/Oct/2008:19:45:38 -0700").getTime();
		Assert.assertEquals(new Date(epoch), entry.getRequestTime());
		Assert.assertEquals("/search", entry.getUrlPath());
		Assert.assertEquals("q1=foo&st=bar", entry.getQueryString());
		Assert.assertSame(HttpStatus.OK, entry.getLastStatusCode());
		Assert.assertEquals(Long.valueOf(323), entry.getResponseInBytes());

		// check size
		Assert.assertEquals(2, entry.getRequestHeaders().size());

		// check referer
		HttpRequestHeaderField referer = new HttpRequestHeaderField(HttpRequestHeader.REFERER, "-");
		Assert.assertTrue(entry.getRequestHeaders().contains(referer));

		// check user agent
		HttpRequestHeaderField userAgent = new HttpRequestHeaderField(HttpRequestHeader.USER_AGENT,
				"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.14) Gecko/20080416 Fedora/2.0.0.14-1.fc7 Firefox/2.0.0.14");
		Assert.assertTrue(entry.getRequestHeaders().contains(userAgent));

		LOG.info(entry.toString());
	}

	@Test
	public void testParseCombinedLogFormatWithCookie() throws Exception {
		final String line = "192.168.123.12 - - [19/Oct/2008:19:45:38 -0700] \""
				+ "GET /search?q1=foo&st=bar HTTP/1.1\" 200 323 "
				+ "\"-\" \"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.14) "
				+ "Gecko/20080416 Fedora/2.0.0.14-1.fc7 Firefox/2.0.0.14\" "
				+ "\"USER_ID=12345,jsession_id=3BFY342211\"";
		final List<String> tokens = NcsaLogParser.parse(line);
		printResult(line, tokens);
	}

	@Test
	public void testParseCommonLogFormatNoCookie() throws Exception {
		final String line = "192.168.123.12 - - [19/Oct/2008:19:45:38 -0700] \""
				+ "GET /search?q1=foo&st=bar HTTP/1.1\" 200 323";
		final List<String> tokens = NcsaLogParser.parse(line);
		printResult(line, tokens);

		final ReadonlyLogEntry entry = new NcsaLogParser(LogFormat.Defaults.COMMON.getFormat()).parseLine(line);
		// TODO %l is not tested yet
		Assert.assertEquals("192.168.123.12", entry.getRemoteHost());
		Assert.assertEquals("-", entry.getRemoteUser());
		Assert.assertSame(HttpRequestMethod.GET, entry.getRequestMethod());
		final long epoch = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH).parse(
				"19/Oct/2008:19:45:38 -0700").getTime();
		Assert.assertEquals(new Date(epoch), entry.getRequestTime());
		Assert.assertEquals("/search", entry.getUrlPath());
		Assert.assertEquals("q1=foo&st=bar", entry.getQueryString());
		Assert.assertSame(HttpStatus.OK, entry.getLastStatusCode());
		Assert.assertEquals(Long.valueOf(323), entry.getResponseInBytes());

		LOG.info(entry.toString());
	}

	@Test
	public void testParseCustomLogFormat() throws Exception {
		LOG.info("TEST--------------------------------");
		final String line = "192.168.123.12 - - [19/Oct/2008:19:45:38 -0700] \""
				+ "GET /search?q1=foo&st=bar HTTP/1.1\" 200 323 "
				+ "\"-\" 34567 \"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.14) "
				+ "Gecko/20080416 Fedora/2.0.0.14-1.fc7 Firefox/2.0.0.14\"";
		final List<String> tokens = NcsaLogParser.parse(line);
		printResult(line, tokens);

		final ReadonlyLogEntry entry = new NcsaLogParser(
				LogFormat.parse("%h %l %u %t \"%r\" %>s %b \"%{Referer}i\" %D \"%{User-agent}i\"")).parseLine(line);
		// TODO %l is not tested yet
		// TODO %D is not tested yet
		Assert.assertEquals("192.168.123.12", entry.getRemoteHost());
		Assert.assertEquals("-", entry.getRemoteUser());
		Assert.assertSame(HttpRequestMethod.GET, entry.getRequestMethod());
		final long epoch = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH).parse(
				"19/Oct/2008:19:45:38 -0700").getTime();
		Assert.assertEquals(new Date(epoch), entry.getRequestTime());
		Assert.assertEquals("/search", entry.getUrlPath());
		Assert.assertEquals("q1=foo&st=bar", entry.getQueryString());
		Assert.assertSame(HttpStatus.OK, entry.getLastStatusCode());
		Assert.assertEquals(Long.valueOf(323), entry.getResponseInBytes());

		// check size
		Assert.assertEquals(2, entry.getRequestHeaders().size());

		// check referer
		HttpRequestHeaderField referer = new HttpRequestHeaderField(HttpRequestHeader.REFERER, "-");
		Assert.assertTrue(entry.getRequestHeaders().contains(referer));

		// check user agent
		HttpRequestHeaderField userAgent = new HttpRequestHeaderField(HttpRequestHeader.USER_AGENT,
				"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.14) Gecko/20080416 Fedora/2.0.0.14-1.fc7 Firefox/2.0.0.14");
		Assert.assertTrue(entry.getRequestHeaders().contains(userAgent));

		LOG.info(entry.toString());
	}

	// @Test
	public void testParsing() throws Exception {
		final File[] accesslogs = (new File("src/test/resources/access_logs")).listFiles();
		for (final File accesslog : accesslogs) {
			String line;
			final BufferedReader reader = new BufferedReader(new FileReader(accesslog));
			while ((line = reader.readLine()) != null) {
				final List<String> tokens = NcsaLogParser.parse(line);
				printResult(line, tokens);
				// final String url = tokens.get(4);
				// if (url.contains("/search")) {
				// printResult(line, tokens);
				// }
			}
			reader.close();
		}
	}

}
