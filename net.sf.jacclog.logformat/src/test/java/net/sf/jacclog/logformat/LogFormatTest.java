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

import java.util.HashSet;
import java.util.Set;

import net.sf.jacclog.logformat.field.CanonicalServerNameField;
import net.sf.jacclog.logformat.field.Field;
import net.sf.jacclog.logformat.field.HttpLastStatusField;
import net.sf.jacclog.logformat.field.HttpStatusField;
import net.sf.jacclog.logformat.field.RemoteHostField;
import net.sf.jacclog.logformat.field.RemoteLognameField;
import net.sf.jacclog.logformat.field.RemoteUserField;
import net.sf.jacclog.logformat.field.RequestFirstLineField;
import net.sf.jacclog.logformat.field.RequestHeaderAcceptCharsetField;
import net.sf.jacclog.logformat.field.RequestHeaderAcceptEncodingField;
import net.sf.jacclog.logformat.field.RequestHeaderAcceptField;
import net.sf.jacclog.logformat.field.RequestHeaderAcceptLanguageField;
import net.sf.jacclog.logformat.field.RequestHeaderAuthorizationField;
import net.sf.jacclog.logformat.field.RequestHeaderCacheControlField;
import net.sf.jacclog.logformat.field.RequestHeaderConnectionField;
import net.sf.jacclog.logformat.field.RequestHeaderContentLengthField;
import net.sf.jacclog.logformat.field.RequestHeaderContentTypeField;
import net.sf.jacclog.logformat.field.RequestHeaderCookieField;
import net.sf.jacclog.logformat.field.RequestHeaderDateField;
import net.sf.jacclog.logformat.field.RequestHeaderExpectField;
import net.sf.jacclog.logformat.field.RequestHeaderFromField;
import net.sf.jacclog.logformat.field.RequestHeaderHostField;
import net.sf.jacclog.logformat.field.RequestHeaderIfMatchField;
import net.sf.jacclog.logformat.field.RequestHeaderIfModifiedSinceField;
import net.sf.jacclog.logformat.field.RequestHeaderIfNoneMatchField;
import net.sf.jacclog.logformat.field.RequestHeaderIfRangeField;
import net.sf.jacclog.logformat.field.RequestHeaderIfUnmodifiedSinceField;
import net.sf.jacclog.logformat.field.RequestHeaderMaxForwardsField;
import net.sf.jacclog.logformat.field.RequestHeaderPragmaField;
import net.sf.jacclog.logformat.field.RequestHeaderProxyAuthorizationField;
import net.sf.jacclog.logformat.field.RequestHeaderRangeField;
import net.sf.jacclog.logformat.field.RequestHeaderRefererField;
import net.sf.jacclog.logformat.field.RequestHeaderTeField;
import net.sf.jacclog.logformat.field.RequestHeaderUpgradeField;
import net.sf.jacclog.logformat.field.RequestHeaderUserAgentField;
import net.sf.jacclog.logformat.field.RequestHeaderViaField;
import net.sf.jacclog.logformat.field.RequestHeaderWarningField;
import net.sf.jacclog.logformat.field.RequestMethodField;
import net.sf.jacclog.logformat.field.RequestTimeField;
import net.sf.jacclog.logformat.field.ResponseHeaderAcceptRangesField;
import net.sf.jacclog.logformat.field.ResponseHeaderAgeField;
import net.sf.jacclog.logformat.field.ResponseHeaderAllowField;
import net.sf.jacclog.logformat.field.ResponseHeaderCacheControlField;
import net.sf.jacclog.logformat.field.ResponseHeaderConnectionField;
import net.sf.jacclog.logformat.field.ResponseHeaderContentDispositionField;
import net.sf.jacclog.logformat.field.ResponseHeaderContentEncodingField;
import net.sf.jacclog.logformat.field.ResponseHeaderContentLanguageField;
import net.sf.jacclog.logformat.field.ResponseHeaderContentLengthField;
import net.sf.jacclog.logformat.field.ResponseHeaderContentLocationField;
import net.sf.jacclog.logformat.field.ResponseHeaderContentRangeField;
import net.sf.jacclog.logformat.field.ResponseHeaderContentTypeField;
import net.sf.jacclog.logformat.field.ResponseHeaderDateField;
import net.sf.jacclog.logformat.field.ResponseHeaderEtagField;
import net.sf.jacclog.logformat.field.ResponseHeaderExpiresField;
import net.sf.jacclog.logformat.field.ResponseHeaderLastModifiedField;
import net.sf.jacclog.logformat.field.ResponseHeaderLinkField;
import net.sf.jacclog.logformat.field.ResponseHeaderLocationField;
import net.sf.jacclog.logformat.field.ResponseHeaderP3pField;
import net.sf.jacclog.logformat.field.ResponseHeaderPragmaField;
import net.sf.jacclog.logformat.field.ResponseHeaderProxyAuthenticateField;
import net.sf.jacclog.logformat.field.ResponseHeaderRefreshField;
import net.sf.jacclog.logformat.field.ResponseHeaderRetryAfterField;
import net.sf.jacclog.logformat.field.ResponseHeaderServerField;
import net.sf.jacclog.logformat.field.ResponseHeaderSetCookieField;
import net.sf.jacclog.logformat.field.ResponseHeaderStrictTransportSecurityField;
import net.sf.jacclog.logformat.field.ResponseHeaderTrailerField;
import net.sf.jacclog.logformat.field.ResponseHeaderTransferEncodingField;
import net.sf.jacclog.logformat.field.ResponseHeaderVaryField;
import net.sf.jacclog.logformat.field.ResponseHeaderViaField;
import net.sf.jacclog.logformat.field.ResponseHeaderWarningField;
import net.sf.jacclog.logformat.field.ResponseHeaderWwwAuthenticateField;
import net.sf.jacclog.logformat.field.ResponseInBytesClfField;
import net.sf.jacclog.logformat.field.ResponseInBytesField;
import net.sf.jacclog.logformat.field.UrlPathField;

import org.junit.Assert;
import org.junit.Test;

public class LogFormatTest {

	@Test
	public void testAddingFields() throws Exception {
		final LogFormat.Builder builder = new LogFormat.Builder();
		builder.appendField(RequestHeaderRefererField.getInstance());
		builder.appendField(HttpStatusField.getInstance());
		builder.appendField(RequestHeaderUserAgentField.getInstance());
		builder.appendField(RemoteHostField.getInstance());
		final LogFormat format = builder.build();

		format.getFields().add(null);
		format.getFields().add(HttpLastStatusField.getInstance());
		Assert.assertEquals(4, format.getFields().size());
	}

	@Test
	public void testClearingFields() throws Exception {
		final LogFormat.Builder builder = new LogFormat.Builder();
		builder.appendField(RequestHeaderRefererField.getInstance());
		builder.appendField(RequestHeaderUserAgentField.getInstance());
		final LogFormat format = builder.build();

		format.getFields().clear();
		Assert.assertEquals(2, format.getFields().size());
	}

	@Test
	public void testHeaderFields() throws Exception {
		final String requestHeaders = "%{Accept}i %{Accept-Charset}i %{Accept-Encoding}i %{Accept-Language}i %{Authorization}i %{Cache-Control}i %{Connection}i %{Cookie}i %{Content-Length}i %{Content-Type}i %{Date}i %{Expect}i %{From}i %{Host}i %{If-Match}i %{If-Modified-Since}i %{If-None-Match}i %{If-Range}i %{If-Unmodified-Since}i %{Max-Forwards}i %{Pragma}i %{Proxy-Authorization}i %{Range}i %{Referer}i %{TE}i %{Upgrade}i %{User-Agent}i %{Via}i %{Warning}i";
		final String responseHeaders = "%{Accept-Ranges}o %{Age}o %{Allow}o %{Cache-Control}o %{Connection}o %{Content-Encoding}o %{Content-Language}o %{Content-Length}o %{Content-Location}o %{Content-Disposition}o %{Content-Range}o %{Content-Type}o %{Date}o %{ETag}o %{Expires}o %{Last-Modified}o %{Link}o %{Location}o %{P3P}o %{Pragma}o %{Proxy-Authenticate}o %{Refresh}o %{Retry-After}o %{Server}o %{Set-Cookie}o %{Strict-Transport-Security}o %{Trailer}o %{Transfer-Encoding}o %{Vary}o %{Via}o %{Warning}o %{WWW-Authenticate}o";
		final LogFormat format = LogFormat.parse(requestHeaders + " " + responseHeaders);

		final Set<Field> expectedFields = new HashSet<Field>(61);

		// expected response header fields
		expectedFields.add(RequestHeaderAcceptCharsetField.getInstance());
		expectedFields.add(RequestHeaderAcceptEncodingField.getInstance());
		expectedFields.add(RequestHeaderAcceptField.getInstance());
		expectedFields.add(RequestHeaderAcceptLanguageField.getInstance());
		expectedFields.add(RequestHeaderAuthorizationField.getInstance());
		expectedFields.add(RequestHeaderCacheControlField.getInstance());
		expectedFields.add(RequestHeaderConnectionField.getInstance());
		expectedFields.add(RequestHeaderContentLengthField.getInstance());
		expectedFields.add(RequestHeaderContentTypeField.getInstance());
		expectedFields.add(RequestHeaderCookieField.getInstance());
		expectedFields.add(RequestHeaderDateField.getInstance());
		expectedFields.add(RequestHeaderExpectField.getInstance());
		expectedFields.add(RequestHeaderFromField.getInstance());
		expectedFields.add(RequestHeaderHostField.getInstance());
		expectedFields.add(RequestHeaderIfMatchField.getInstance());
		expectedFields.add(RequestHeaderIfModifiedSinceField.getInstance());
		expectedFields.add(RequestHeaderIfNoneMatchField.getInstance());
		expectedFields.add(RequestHeaderIfRangeField.getInstance());
		expectedFields.add(RequestHeaderIfUnmodifiedSinceField.getInstance());
		expectedFields.add(RequestHeaderMaxForwardsField.getInstance());
		expectedFields.add(RequestHeaderPragmaField.getInstance());
		expectedFields.add(RequestHeaderProxyAuthorizationField.getInstance());
		expectedFields.add(RequestHeaderRangeField.getInstance());
		expectedFields.add(RequestHeaderRefererField.getInstance());
		expectedFields.add(RequestHeaderTeField.getInstance());
		expectedFields.add(RequestHeaderUpgradeField.getInstance());
		expectedFields.add(RequestHeaderUserAgentField.getInstance());
		expectedFields.add(RequestHeaderViaField.getInstance());
		expectedFields.add(RequestHeaderWarningField.getInstance());

		// expected response header fields
		expectedFields.add(ResponseHeaderAcceptRangesField.getInstance());
		expectedFields.add(ResponseHeaderAgeField.getInstance());
		expectedFields.add(ResponseHeaderAllowField.getInstance());
		expectedFields.add(ResponseHeaderCacheControlField.getInstance());
		expectedFields.add(ResponseHeaderConnectionField.getInstance());
		expectedFields.add(ResponseHeaderContentDispositionField.getInstance());
		expectedFields.add(ResponseHeaderContentEncodingField.getInstance());
		expectedFields.add(ResponseHeaderContentLanguageField.getInstance());
		expectedFields.add(ResponseHeaderContentLengthField.getInstance());
		expectedFields.add(ResponseHeaderContentLocationField.getInstance());
		expectedFields.add(ResponseHeaderContentRangeField.getInstance());
		expectedFields.add(ResponseHeaderContentTypeField.getInstance());
		expectedFields.add(ResponseHeaderDateField.getInstance());
		expectedFields.add(ResponseHeaderEtagField.getInstance());
		expectedFields.add(ResponseHeaderExpiresField.getInstance());
		expectedFields.add(ResponseHeaderLastModifiedField.getInstance());
		expectedFields.add(ResponseHeaderLinkField.getInstance());
		expectedFields.add(ResponseHeaderLocationField.getInstance());
		expectedFields.add(ResponseHeaderP3pField.getInstance());
		expectedFields.add(ResponseHeaderPragmaField.getInstance());
		expectedFields.add(ResponseHeaderProxyAuthenticateField.getInstance());
		expectedFields.add(ResponseHeaderRefreshField.getInstance());
		expectedFields.add(ResponseHeaderRetryAfterField.getInstance());
		expectedFields.add(ResponseHeaderServerField.getInstance());
		expectedFields.add(ResponseHeaderSetCookieField.getInstance());
		expectedFields.add(ResponseHeaderStrictTransportSecurityField.getInstance());
		expectedFields.add(ResponseHeaderTrailerField.getInstance());
		expectedFields.add(ResponseHeaderTransferEncodingField.getInstance());
		expectedFields.add(ResponseHeaderVaryField.getInstance());
		expectedFields.add(ResponseHeaderViaField.getInstance());
		expectedFields.add(ResponseHeaderWarningField.getInstance());
		expectedFields.add(ResponseHeaderWwwAuthenticateField.getInstance());

		Assert.assertEquals(expectedFields.size(), format.getFields().size());
		Assert.assertTrue(format.getFields().containsAll(expectedFields));
	}

	@Test
	public void testParsingCombinedLogFormat() throws Exception {

		// NCSA extended/combined log format
		final LogFormat format = LogFormat.parse("%h %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\"");

		Assert.assertEquals(RemoteHostField.getInstance(), format.getFields().get(0));
		Assert.assertEquals(RemoteLognameField.getInstance(), format.getFields().get(1));
		Assert.assertEquals(RemoteUserField.getInstance(), format.getFields().get(2));
		Assert.assertEquals(RequestTimeField.getInstance(), format.getFields().get(3));
		Assert.assertEquals(RequestFirstLineField.getInstance(), format.getFields().get(4));
		Assert.assertEquals(HttpLastStatusField.getInstance(), format.getFields().get(5));
		Assert.assertEquals(ResponseInBytesClfField.getInstance(), format.getFields().get(6));
		Assert.assertEquals(RequestHeaderRefererField.getInstance(), format.getFields().get(7));
		Assert.assertEquals(RequestHeaderUserAgentField.getInstance(), format.getFields().get(8));

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
		Assert.assertEquals(RequestHeaderRefererField.getInstance(), format.getFields().get(7));
		Assert.assertEquals(RequestHeaderUserAgentField.getInstance(), format.getFields().get(8));

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
