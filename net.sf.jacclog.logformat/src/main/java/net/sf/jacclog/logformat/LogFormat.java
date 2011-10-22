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

import java.util.ArrayList;
import java.util.List;

import net.sf.jacclog.logformat.field.BytesReceivedField;
import net.sf.jacclog.logformat.field.BytesSendField;
import net.sf.jacclog.logformat.field.CanonicalServerNameField;
import net.sf.jacclog.logformat.field.ConnectionStatusField;
import net.sf.jacclog.logformat.field.CustomRequestTimeField;
import net.sf.jacclog.logformat.field.Field;
import net.sf.jacclog.logformat.field.FilenameField;
import net.sf.jacclog.logformat.field.HttpLastStatusField;
import net.sf.jacclog.logformat.field.HttpStatusField;
import net.sf.jacclog.logformat.field.IgnorableField;
import net.sf.jacclog.logformat.field.LocalIpAddressField;
import net.sf.jacclog.logformat.field.ProcessIdField;
import net.sf.jacclog.logformat.field.QueryStringField;
import net.sf.jacclog.logformat.field.RemoteHostField;
import net.sf.jacclog.logformat.field.RemoteIpAddressField;
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
import net.sf.jacclog.logformat.field.RequestInMillisField;
import net.sf.jacclog.logformat.field.RequestInSecondsField;
import net.sf.jacclog.logformat.field.RequestMethodField;
import net.sf.jacclog.logformat.field.RequestProtocolField;
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
import net.sf.jacclog.logformat.field.ServerNameField;
import net.sf.jacclog.logformat.field.ServerPortField;
import net.sf.jacclog.logformat.field.UrlPathField;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;

public class LogFormat {

	public final static class Builder {

		private transient final List<Field> fields = new ArrayList<Field>();

		public Builder appendBytesReceivedField() {
			return appendField(BytesReceivedField.getInstance());
		}

		public Builder appendBytesSendField() {
			return appendField(BytesSendField.getInstance());
		}

		public Builder appendCanonicalServerNameField() {
			return appendField(CanonicalServerNameField.getInstance());
		}

		public Builder appendConnectionStatusField() {
			return appendField(ConnectionStatusField.getInstance());
		}

		public Builder appendCustomRequestTimeField() {
			return appendField(CustomRequestTimeField.getInstance());
		}

		/**
		 * Appends a field to list.
		 * 
		 * @param field
		 * @return Builder (itself)
		 */
		public Builder appendField(final Field field) {
			if (field == null) {
				throw new IllegalArgumentException("Argument 'field' can not be null.");
			}

			fields.add(field);
			return this;
		}

		public Builder appendFilenameField() {
			return appendField(FilenameField.getInstance());
		}

		public Builder appendHttpLastStatusField() {
			return appendField(HttpLastStatusField.getInstance());
		}

		public Builder appendHttpStatusField() {
			return appendField(HttpStatusField.getInstance());
		}

		public Builder appendIgnorableField() {
			return appendField(IgnorableField.getInstance());
		}

		public Builder appendLocalIpAddressField() {
			return appendField(LocalIpAddressField.getInstance());
		}

		public Builder appendProcessIdField() {
			return appendField(ProcessIdField.getInstance());
		}

		public Builder appendQueryStringField() {
			return appendField(QueryStringField.getInstance());
		}

		public Builder appendRemoteHostField() {
			return appendField(RemoteHostField.getInstance());
		}

		public Builder appendRemoteIpAddressField() {
			return appendField(RemoteIpAddressField.getInstance());
		}

		public Builder appendRemoteLognameField() {
			return appendField(RemoteLognameField.getInstance());
		}

		public Builder appendRemoteUserField() {
			return appendField(RemoteUserField.getInstance());
		}

		public Builder appendRequestFirstLineField() {
			return appendField(RequestFirstLineField.getInstance());
		}

		public Builder appendRequestHeaderAcceptCharsetField() {
			return appendField(RequestHeaderAcceptCharsetField.getInstance());
		}

		public Builder appendRequestHeaderAcceptEncodingField() {
			return appendField(RequestHeaderAcceptEncodingField.getInstance());
		}

		public Builder appendRequestHeaderAcceptField() {
			return appendField(RequestHeaderAcceptField.getInstance());
		}

		public Builder appendRequestHeaderAcceptLanguageField() {
			return appendField(RequestHeaderAcceptLanguageField.getInstance());
		}

		public Builder appendRequestHeaderAuthorizationField() {
			return appendField(RequestHeaderAuthorizationField.getInstance());
		}

		public Builder appendRequestHeaderCacheControlField() {
			return appendField(RequestHeaderCacheControlField.getInstance());
		}

		public Builder appendRequestHeaderConnectionField() {
			return appendField(RequestHeaderConnectionField.getInstance());
		}

		public Builder appendRequestHeaderContentLengthField() {
			return appendField(RequestHeaderContentLengthField.getInstance());
		}

		public Builder appendRequestHeaderContentTypeField() {
			return appendField(RequestHeaderContentTypeField.getInstance());
		}

		public Builder appendRequestHeaderCookieField() {
			return appendField(RequestHeaderCookieField.getInstance());
		}

		public Builder appendRequestHeaderDateField() {
			return appendField(RequestHeaderDateField.getInstance());
		}

		public Builder appendRequestHeaderExpectField() {
			return appendField(RequestHeaderExpectField.getInstance());
		}

		public Builder appendRequestHeaderFromField() {
			return appendField(RequestHeaderFromField.getInstance());
		}

		public Builder appendRequestHeaderHostField() {
			return appendField(RequestHeaderHostField.getInstance());
		}

		public Builder appendRequestHeaderIfMatchField() {
			return appendField(RequestHeaderIfMatchField.getInstance());
		}

		public Builder appendRequestHeaderIfModifiedSinceField() {
			return appendField(RequestHeaderIfModifiedSinceField.getInstance());
		}

		public Builder appendRequestHeaderIfNoneMatchField() {
			return appendField(RequestHeaderIfNoneMatchField.getInstance());
		}

		public Builder appendRequestHeaderIfRangeField() {
			return appendField(RequestHeaderIfRangeField.getInstance());
		}

		public Builder appendRequestHeaderIfUnmodifiedSinceField() {
			return appendField(RequestHeaderIfUnmodifiedSinceField.getInstance());
		}

		public Builder appendRequestHeaderMaxForwardsField() {
			return appendField(RequestHeaderMaxForwardsField.getInstance());
		}

		public Builder appendRequestHeaderPragmaField() {
			return appendField(RequestHeaderPragmaField.getInstance());
		}

		public Builder appendRequestHeaderProxyAuthorizationField() {
			return appendField(RequestHeaderProxyAuthorizationField.getInstance());
		}

		public Builder appendRequestHeaderRangeField() {
			return appendField(RequestHeaderRangeField.getInstance());
		}

		public Builder appendRequestHeaderRefererField() {
			return appendField(RequestHeaderRefererField.getInstance());
		}

		public Builder appendRequestHeaderTeField() {
			return appendField(RequestHeaderTeField.getInstance());
		}

		public Builder appendRequestHeaderUpgradeField() {
			return appendField(RequestHeaderUpgradeField.getInstance());
		}

		public Builder appendRequestHeaderUserAgentField() {
			return appendField(RequestHeaderUserAgentField.getInstance());
		}

		public Builder appendRequestHeaderViaField() {
			return appendField(RequestHeaderViaField.getInstance());
		}

		public Builder appendRequestHeaderWarningField() {
			return appendField(RequestHeaderWarningField.getInstance());
		}

		public Builder appendRequestInMillisField() {
			return appendField(RequestInMillisField.getInstance());
		}

		public Builder appendRequestInSecondsField() {
			return appendField(RequestInSecondsField.getInstance());
		}

		public Builder appendRequestMethodField() {
			return appendField(RequestMethodField.getInstance());
		}

		public Builder appendRequestProtocolField() {
			return appendField(RequestProtocolField.getInstance());
		}

		public Builder appendRequestTimeField() {
			return appendField(RequestTimeField.getInstance());
		}

		public Builder appendResponseHeaderAcceptRangesField() {
			return appendField(ResponseHeaderAcceptRangesField.getInstance());
		}

		public Builder appendResponseHeaderAgeField() {
			return appendField(ResponseHeaderAgeField.getInstance());
		}

		public Builder appendResponseHeaderAllowField() {
			return appendField(ResponseHeaderAllowField.getInstance());
		}

		public Builder appendResponseHeaderCacheControlField() {
			return appendField(ResponseHeaderCacheControlField.getInstance());
		}

		public Builder appendResponseHeaderConnectionField() {
			return appendField(ResponseHeaderConnectionField.getInstance());
		}

		public Builder appendResponseHeaderContentDispositionField() {
			return appendField(ResponseHeaderContentDispositionField.getInstance());
		}

		public Builder appendResponseHeaderContentEncodingField() {
			return appendField(ResponseHeaderContentEncodingField.getInstance());
		}

		public Builder appendResponseHeaderContentLanguageField() {
			return appendField(ResponseHeaderContentLanguageField.getInstance());
		}

		public Builder appendResponseHeaderContentLengthField() {
			return appendField(ResponseHeaderContentLengthField.getInstance());
		}

		public Builder appendResponseHeaderContentLocationField() {
			return appendField(ResponseHeaderContentLocationField.getInstance());
		}

		public Builder appendResponseHeaderContentRangeField() {
			return appendField(ResponseHeaderContentRangeField.getInstance());
		}

		public Builder appendResponseHeaderContentTypeField() {
			return appendField(ResponseHeaderContentTypeField.getInstance());
		}

		public Builder appendResponseHeaderDateField() {
			return appendField(ResponseHeaderDateField.getInstance());
		}

		public Builder appendResponseHeaderEtagField() {
			return appendField(ResponseHeaderEtagField.getInstance());
		}

		public Builder appendResponseHeaderExpiresField() {
			return appendField(ResponseHeaderExpiresField.getInstance());
		}

		public Builder appendResponseHeaderLastModifiedField() {
			return appendField(ResponseHeaderLastModifiedField.getInstance());
		}

		public Builder appendResponseHeaderLinkField() {
			return appendField(ResponseHeaderLinkField.getInstance());
		}

		public Builder appendResponseHeaderLocationField() {
			return appendField(ResponseHeaderLocationField.getInstance());
		}

		public Builder appendResponseHeaderP3pField() {
			return appendField(ResponseHeaderP3pField.getInstance());
		}

		public Builder appendResponseHeaderPragmaField() {
			return appendField(ResponseHeaderPragmaField.getInstance());
		}

		public Builder appendResponseHeaderProxyAuthenticateField() {
			return appendField(ResponseHeaderProxyAuthenticateField.getInstance());
		}

		public Builder appendResponseHeaderRefreshField() {
			return appendField(ResponseHeaderRefreshField.getInstance());
		}

		public Builder appendResponseHeaderRetryAfterField() {
			return appendField(ResponseHeaderRetryAfterField.getInstance());
		}

		public Builder appendResponseHeaderServerField() {
			return appendField(ResponseHeaderServerField.getInstance());
		}

		public Builder appendResponseHeaderSetCookieField() {
			return appendField(ResponseHeaderSetCookieField.getInstance());
		}

		public Builder appendResponseHeaderStrictTransportSecurityField() {
			return appendField(ResponseHeaderStrictTransportSecurityField.getInstance());
		}

		public Builder appendResponseHeaderTrailerField() {
			return appendField(ResponseHeaderTrailerField.getInstance());
		}

		public Builder appendResponseHeaderTransferEncodingField() {
			return appendField(ResponseHeaderTransferEncodingField.getInstance());
		}

		public Builder appendResponseHeaderVaryField() {
			return appendField(ResponseHeaderVaryField.getInstance());
		}

		public Builder appendResponseHeaderViaField() {
			return appendField(ResponseHeaderViaField.getInstance());
		}

		public Builder appendResponseHeaderWarningField() {
			return appendField(ResponseHeaderWarningField.getInstance());
		}

		public Builder appendResponseHeaderWwwAuthenticateField() {
			return appendField(ResponseHeaderWwwAuthenticateField.getInstance());
		}

		public Builder appendResponseInBytesClfField() {
			return appendField(ResponseInBytesClfField.getInstance());
		}

		public Builder appendResponseInBytesField() {
			return appendField(ResponseInBytesField.getInstance());
		}

		public Builder appendServerNameField() {
			return appendField(ServerNameField.getInstance());
		}

		public Builder appendServerPortField() {
			return appendField(ServerPortField.getInstance());
		}

		public Builder appendUrlPathField() {
			return appendField(UrlPathField.getInstance());
		}

		public LogFormat build() {
			return new LogFormat(fields);
		}

	}

	public static enum Defaults {

		/**
		 * NCSA extended/combined log format<br>
		 * <br>
		 * Format string: <code>%h %l %u %t "%r" %>s %b "%{Referer}i" "%{User-agent}i"</code>
		 */
		COMBINED(new LogFormat.Builder().appendRemoteHostField().appendRemoteLognameField().appendRemoteUserField()
				.appendRequestTimeField().appendRequestFirstLineField().appendHttpLastStatusField()
				.appendResponseInBytesClfField().appendRequestHeaderRefererField().appendRequestHeaderUserAgentField()
				.build()),

		/**
		 * Common Log Format (CLF)<br>
		 * <br>
		 * Format string: <code>%h %l %u %t "%r" %>s %b</code>
		 */
		COMMON(new LogFormat.Builder().appendRemoteHostField().appendRemoteLognameField().appendRemoteUserField()
				.appendRequestTimeField().appendRequestFirstLineField().appendHttpLastStatusField()
				.appendResponseInBytesClfField().build()),

		/**
		 * Common Log Format with Virtual Host<br>
		 * <br>
		 * Format string: <code>%v %h %l %u %t "%r" %>s %b</code>
		 */
		COMMON_WITH_VHOST(new LogFormat.Builder().appendCanonicalServerNameField().appendRemoteHostField()
				.appendRemoteLognameField().appendRemoteUserField().appendRequestTimeField()
				.appendRequestFirstLineField().appendHttpLastStatusField().appendResponseInBytesClfField().build());

		private LogFormat format;

		Defaults(final LogFormat format) {
			this.format = format;
		}

		public LogFormat getFormat() {
			return format;
		}

	}

	/**
	 * Parses a format string and returns an object of type <code>LogFormat</code>.<br>
	 * <br>
	 * If the format string can not be read <code>null</code> will be returned.
	 * 
	 * @param format
	 * @return
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 * @throws LogFormatParsingException
	 *             if the given log format can not be parsed
	 */
	public static LogFormat parse(final String format) {
		if (format == null) {
			throw new IllegalArgumentException("Argument 'format' can not be null.");
		}

		LogFormat result = null;
		if (format.equalsIgnoreCase("COMMON")) {
			result = Defaults.COMMON.getFormat();
		} else if (format.equalsIgnoreCase("COMMON_WITH_VHOST")) {
			result = Defaults.COMMON_WITH_VHOST.getFormat();
		} else if (format.equalsIgnoreCase("COMBINED")) {
			result = Defaults.COMBINED.getFormat();
		} else {
			final ANTLRStringStream input = new ANTLRStringStream(format);
			final LogFormatLexer lexer = new LogFormatLexer(input);
			final CommonTokenStream tokens = new CommonTokenStream(lexer);
			final LogFormatParser parser = new LogFormatParser(tokens);
			try {
				result = parser.format();
			} catch (final Exception e) {
				throw new LogFormatParsingException("The format '" + format + "' is not valid.", e);
			}
		}

		return result;
	}

	private final List<Field> fields;

	private LogFormat(final List<Field> fields) {
		if (fields == null || fields.isEmpty()) {
			throw new IllegalArgumentException("Argument 'fields' can not be null or empty.");
		}

		this.fields = fields;
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
		final LogFormat other = (LogFormat) obj;
		if (fields == null) {
			if (other.fields != null) {
				return false;
			}
		} else if (!fields.equals(other.fields)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets a copied list of fields.
	 * 
	 * @return a copied field list
	 */
	public List<Field> getFields() {
		return new ArrayList<Field>(fields);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		return result;
	}

}
