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
package net.sf.jacclog.uasparser;

import net.sf.jacclog.uasparser.internal.OnlineUserAgentStringParser;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnlineUserAgentStringParserTest {

	private static final OnlineUserAgentStringParser parser = new OnlineUserAgentStringParser(
			OnlineUserAgentStringParserTest.class.getClassLoader().getResourceAsStream("uas_test.xml"));

	private static Logger LOG = LoggerFactory.getLogger(OnlineUserAgentStringParserTest.class);

	@Test
	public void testChrome13Parsing() {
		final UserAgentInfo.Builder builder = new UserAgentInfo.Builder();
		builder.setProducerUrl("http://www.google.com/");
		builder.setProducer("Google Inc.");
		builder.setFamily("Chrome");
		builder.setIcon("chrome.png");
		builder.setInfoUrl("/list-of-ua/browser-detail?browser=Chrome");
		builder.setName("Chrome 13.0.782.112");
		builder.setOsProducer("Apple Computer, Inc.");
		builder.setOsProducerUrl("http://www.apple.com/");
		builder.setOsFamily("Mac OS X");
		builder.setOsIcon("macosx.png");
		builder.setOsName("Mac OS X 10.6 Snow Leopard");
		builder.setOsUrl("http://www.apple.com/macosx/");
		builder.setType("Browser");
		builder.setUrl("http://www.google.com/chrome");
		final UserAgentInfo expected = builder.build();
		final UserAgentInfo unknownAgentString = parser
				.parse("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1");
		Assert.assertEquals(expected, unknownAgentString);
	}

	@Test
	public void testEmptyUserAgentStringParsing() {
		final UserAgentInfo.Builder builder = new UserAgentInfo.Builder();
		final UserAgentInfo expected = builder.build();
		final UserAgentInfo unknownAgentString = parser.parse("");
		Assert.assertEquals(expected, unknownAgentString);
	}

	@Test
	public void testGooglebotParsing() {
		final UserAgentInfo.Builder builder = new UserAgentInfo.Builder();
		builder.setProducerUrl("http://www.google.com/");
		builder.setProducer("Google Inc.");
		builder.setFamily("Googlebot");
		builder.setIcon("bot_googlebot.png");
		builder.setInfoUrl("/list-of-ua/bot-detail?bot=Googlebot");
		builder.setName("Googlebot/2.1");
		builder.setUrl("");
		final UserAgentInfo expected = builder.build();
		final UserAgentInfo unknownAgentString = parser
				.parse("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
		Assert.assertEquals(expected, unknownAgentString);
	}

	@Test
	public void testSiteSuckerParsing() {
		final UserAgentInfo.Builder builder = new UserAgentInfo.Builder();
		builder.setProducerUrl("");
		builder.setProducer("Rick Cranisky");
		builder.setFamily("SiteSucker");
		builder.setIcon("sitesucker.png");
		builder.setInfoUrl("/list-of-ua/browser-detail?browser=SiteSucker");
		builder.setName("SiteSucker 1.6.9");
		builder.setOsProducer("Apple Computer, Inc.");
		builder.setOsProducerUrl("http://www.apple.com/");
		builder.setOsFamily("Mac OS");
		builder.setOsIcon("macos.png");
		builder.setOsName("Mac OS");
		builder.setOsUrl("http://en.wikipedia.org/wiki/Mac_OS");
		builder.setType("Offline Browser");
		builder.setUrl("http://www.sitesucker.us/");
		final UserAgentInfo expected = builder.build();
		final UserAgentInfo unknownAgentString = parser.parse("SiteSucker/1.6.9");
		Assert.assertEquals(expected, unknownAgentString);
	}

	@Test
	public void testUnknownUserAgentStringParsing() throws InterruptedException {
		final long firstLastUpdateCheck = parser.getLastUpdateCheck();

		final UserAgentInfo.Builder builder = new UserAgentInfo.Builder();
		final UserAgentInfo expected = builder.build();
		final UserAgentInfo unknownAgentString = parser.parse("qwertzuiopasdfghjklyxcvbnm");
		Assert.assertEquals(expected, unknownAgentString);

		// Check update mechanism
		final long currentLastUpdateCheck = parser.getLastUpdateCheck();
		Assert.assertTrue(firstLastUpdateCheck == currentLastUpdateCheck);
	}

	@Test
	public void testUpdateMechanismWhileParsing() throws InterruptedException {
		final long firstLastUpdateCheck = parser.getLastUpdateCheck();
		LOG.debug("LastUpdateCheck at: " + firstLastUpdateCheck);
		final long originalInterval = parser.getUpdateInterval();

		// reduce the interval since testing
		LOG.debug("Reducing the update interval during the test.");
		parser.setUpdateInterval(10l);
		// we have to read to activate the update mechanism
		parser.parse("check for updates");

		Thread.sleep(100l);
		final long currentLastUpdateCheck = parser.getLastUpdateCheck();
		LOG.debug("LastUpdateCheck at: " + currentLastUpdateCheck);
		Assert.assertTrue(firstLastUpdateCheck < currentLastUpdateCheck);

		parser.setUpdateInterval(originalInterval);
	}

}
