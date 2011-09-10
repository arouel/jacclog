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
package net.sf.jacclog.logformat;

import org.junit.Assert;
import org.junit.Test;

import net.sf.jacclog.logformat.field.CanonicalServerNameField;
import net.sf.jacclog.logformat.field.HttpLastStatusField;
import net.sf.jacclog.logformat.field.HttpRefererField;
import net.sf.jacclog.logformat.field.HttpStatusField;
import net.sf.jacclog.logformat.field.HttpUserAgentField;
import net.sf.jacclog.logformat.field.RemoteHostField;
import net.sf.jacclog.logformat.field.RemoteLognameField;
import net.sf.jacclog.logformat.field.RemoteUserField;
import net.sf.jacclog.logformat.field.RequestFirstLineField;
import net.sf.jacclog.logformat.field.RequestMethodField;
import net.sf.jacclog.logformat.field.RequestTimeField;
import net.sf.jacclog.logformat.field.ResponseInBytesClfField;
import net.sf.jacclog.logformat.field.ResponseInBytesField;
import net.sf.jacclog.logformat.field.UrlPathField;

public class LogFormatTest {

	@Test
	public void testAddingFields() throws Exception {
		final LogFormat.Builder builder = new LogFormat.Builder();
		builder.appendField(HttpRefererField.getInstance());
		builder.appendField(HttpStatusField.getInstance());
		builder.appendField(HttpUserAgentField.getInstance());
		builder.appendField(RemoteHostField.getInstance());
		final LogFormat format = builder.build();

		format.getFields().add(null);
		format.getFields().add(HttpLastStatusField.getInstance());
		Assert.assertEquals(4, format.getFields().size());
	}

	@Test
	public void testClearingFields() throws Exception {
		final LogFormat.Builder builder = new LogFormat.Builder();
		builder.appendField(HttpRefererField.getInstance());
		builder.appendField(HttpUserAgentField.getInstance());
		final LogFormat format = builder.build();

		format.getFields().clear();
		Assert.assertEquals(2, format.getFields().size());
	}

	@Test
	public void testParsingCombinedLogFormat() throws Exception {

		// NCSA extended/combined log format
		final LogFormat format = LogFormat.parse("%h %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-agent}i\"");

		Assert.assertEquals(RemoteHostField.getInstance(), format.getFields().get(0));
		Assert.assertEquals(RemoteLognameField.getInstance(), format.getFields().get(1));
		Assert.assertEquals(RemoteUserField.getInstance(), format.getFields().get(2));
		Assert.assertEquals(RequestTimeField.getInstance(), format.getFields().get(3));
		Assert.assertEquals(RequestFirstLineField.getInstance(), format.getFields().get(4));
		Assert.assertEquals(HttpLastStatusField.getInstance(), format.getFields().get(5));
		Assert.assertEquals(ResponseInBytesClfField.getInstance(), format.getFields().get(6));
		Assert.assertEquals(HttpRefererField.getInstance(), format.getFields().get(7));
		Assert.assertEquals(HttpUserAgentField.getInstance(), format.getFields().get(8));

		Assert.assertEquals(9, format.getFields().size());

		Assert.assertEquals(LogFormat.Defaults.COMBINED.getFormat(), format);
		Assert.assertNotSame(LogFormat.Defaults.COMMON.getFormat(), format);
	}

	@Test
	public void testParsingCommonLogFormat() throws Exception {

		// Common Log Format (CLF)
		final LogFormat format = LogFormat.parse("%h %l %u %t \"%r\" %>s %b");

		Assert.assertEquals(RemoteHostField.getInstance(), format.getFields().get(0));
		Assert.assertEquals(RemoteLognameField.getInstance(), format.getFields().get(1));
		Assert.assertEquals(RemoteUserField.getInstance(), format.getFields().get(2));
		Assert.assertEquals(RequestTimeField.getInstance(), format.getFields().get(3));
		Assert.assertEquals(RequestFirstLineField.getInstance(), format.getFields().get(4));
		Assert.assertEquals(HttpLastStatusField.getInstance(), format.getFields().get(5));
		Assert.assertEquals(ResponseInBytesClfField.getInstance(), format.getFields().get(6));

		Assert.assertEquals(7, format.getFields().size());

		Assert.assertEquals(LogFormat.Defaults.COMMON.getFormat(), format);
		Assert.assertNotSame(LogFormat.Defaults.COMMON_WITH_VHOST.getFormat(), format);
	}

	@Test
	public void testParsingCommonLogFormatWithVhost() throws Exception {

		// Common Log Format with Virtual Host
		final LogFormat format = LogFormat.parse("%v %h %l %u %t \"%r\" %>s %b");

		Assert.assertEquals(CanonicalServerNameField.getInstance(), format.getFields().get(0));
		Assert.assertEquals(RemoteHostField.getInstance(), format.getFields().get(1));
		Assert.assertEquals(RemoteLognameField.getInstance(), format.getFields().get(2));
		Assert.assertEquals(RemoteUserField.getInstance(), format.getFields().get(3));
		Assert.assertEquals(RequestTimeField.getInstance(), format.getFields().get(4));
		Assert.assertEquals(RequestFirstLineField.getInstance(), format.getFields().get(5));
		Assert.assertEquals(HttpLastStatusField.getInstance(), format.getFields().get(6));
		Assert.assertEquals(ResponseInBytesClfField.getInstance(), format.getFields().get(7));

		Assert.assertEquals(8, format.getFields().size());

		Assert.assertEquals(LogFormat.Defaults.COMMON_WITH_VHOST.getFormat(), format);
		Assert.assertNotSame(LogFormat.Defaults.COMMON.getFormat(), format);
		Assert.assertNotSame(LogFormat.Defaults.COMBINED.getFormat(), format);
	}

	@Test
	public void testParsingNullFormat() throws Exception {
		try {
			final LogFormat format = LogFormat.parse(null);
			Assert.assertNotNull(format);
			Assert.assertFalse(true);
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(true);
		} catch (final Exception e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testParsingObscureFormat() throws Exception {

		// Custom Log Format
		final LogFormat format = LogFormat.parse("%h  \r\t  %l%u\t%t    %m%U %s %B");

		Assert.assertEquals(RemoteHostField.getInstance(), format.getFields().get(0));
		Assert.assertEquals(RemoteLognameField.getInstance(), format.getFields().get(1));
		Assert.assertEquals(RemoteUserField.getInstance(), format.getFields().get(2));
		Assert.assertEquals(RequestTimeField.getInstance(), format.getFields().get(3));
		Assert.assertEquals(RequestMethodField.getInstance(), format.getFields().get(4));
		Assert.assertEquals(UrlPathField.getInstance(), format.getFields().get(5));
		Assert.assertEquals(HttpStatusField.getInstance(), format.getFields().get(6));
		Assert.assertEquals(ResponseInBytesField.getInstance(), format.getFields().get(7));

		Assert.assertEquals(8, format.getFields().size());

		Assert.assertNotSame(LogFormat.Defaults.COMMON_WITH_VHOST.getFormat(), format);
		Assert.assertNotSame(LogFormat.Defaults.COMMON.getFormat(), format);
		Assert.assertNotSame(LogFormat.Defaults.COMBINED.getFormat(), format);
	}

	@Test
	public void testParsingPredefinedCombinedLogFormat() throws Exception {

		// NCSA extended/combined log format
		final LogFormat format = LogFormat.parse("combined");

		Assert.assertEquals(RemoteHostField.getInstance(), format.getFields().get(0));
		Assert.assertEquals(RemoteLognameField.getInstance(), format.getFields().get(1));
		Assert.assertEquals(RemoteUserField.getInstance(), format.getFields().get(2));
		Assert.assertEquals(RequestTimeField.getInstance(), format.getFields().get(3));
		Assert.assertEquals(RequestFirstLineField.getInstance(), format.getFields().get(4));
		Assert.assertEquals(HttpLastStatusField.getInstance(), format.getFields().get(5));
		Assert.assertEquals(ResponseInBytesClfField.getInstance(), format.getFields().get(6));
		Assert.assertEquals(HttpRefererField.getInstance(), format.getFields().get(7));
		Assert.assertEquals(HttpUserAgentField.getInstance(), format.getFields().get(8));

		Assert.assertEquals(9, format.getFields().size());

		Assert.assertEquals(LogFormat.Defaults.COMBINED.getFormat(), format);
		Assert.assertNotSame(LogFormat.Defaults.COMMON.getFormat(), format);
	}

	@Test
	public void testParsingPredefinedCommonLogFormat() throws Exception {

		// Common Log Format
		final LogFormat format = LogFormat.parse("common");

		Assert.assertEquals(RemoteHostField.getInstance(), format.getFields().get(0));
		Assert.assertEquals(RemoteLognameField.getInstance(), format.getFields().get(1));
		Assert.assertEquals(RemoteUserField.getInstance(), format.getFields().get(2));
		Assert.assertEquals(RequestTimeField.getInstance(), format.getFields().get(3));
		Assert.assertEquals(RequestFirstLineField.getInstance(), format.getFields().get(4));
		Assert.assertEquals(HttpLastStatusField.getInstance(), format.getFields().get(5));
		Assert.assertEquals(ResponseInBytesClfField.getInstance(), format.getFields().get(6));

		Assert.assertEquals(7, format.getFields().size());

		Assert.assertEquals(LogFormat.Defaults.COMMON.getFormat(), format);
		Assert.assertNotSame(LogFormat.Defaults.COMMON_WITH_VHOST.getFormat(), format);
		Assert.assertNotSame(LogFormat.Defaults.COMBINED.getFormat(), format);
	}

	@Test
	public void testParsingPredefinedCommonLogFormatWithVhost() throws Exception {

		// Common Log Format with Virtual Host
		final LogFormat format = LogFormat.parse("common_with_vhost");

		Assert.assertEquals(CanonicalServerNameField.getInstance(), format.getFields().get(0));
		Assert.assertEquals(RemoteHostField.getInstance(), format.getFields().get(1));
		Assert.assertEquals(RemoteLognameField.getInstance(), format.getFields().get(2));
		Assert.assertEquals(RemoteUserField.getInstance(), format.getFields().get(3));
		Assert.assertEquals(RequestTimeField.getInstance(), format.getFields().get(4));
		Assert.assertEquals(RequestFirstLineField.getInstance(), format.getFields().get(5));
		Assert.assertEquals(HttpLastStatusField.getInstance(), format.getFields().get(6));
		Assert.assertEquals(ResponseInBytesClfField.getInstance(), format.getFields().get(7));

		Assert.assertEquals(8, format.getFields().size());

		Assert.assertEquals(LogFormat.Defaults.COMMON_WITH_VHOST.getFormat(), format);
		Assert.assertNotSame(LogFormat.Defaults.COMMON.getFormat(), format);
		Assert.assertNotSame(LogFormat.Defaults.COMBINED.getFormat(), format);
	}

}
