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
import net.sf.jacclog.logformat.field.HttpRefererField;
import net.sf.jacclog.logformat.field.HttpStatusField;
import net.sf.jacclog.logformat.field.HttpUserAgentField;
import net.sf.jacclog.logformat.field.IgnorableField;
import net.sf.jacclog.logformat.field.LocalIpAddressField;
import net.sf.jacclog.logformat.field.ProcessIdField;
import net.sf.jacclog.logformat.field.QueryStringField;
import net.sf.jacclog.logformat.field.RemoteHostField;
import net.sf.jacclog.logformat.field.RemoteIpAddressField;
import net.sf.jacclog.logformat.field.RemoteLognameField;
import net.sf.jacclog.logformat.field.RemoteUserField;
import net.sf.jacclog.logformat.field.RequestFirstLineField;
import net.sf.jacclog.logformat.field.RequestInMillisField;
import net.sf.jacclog.logformat.field.RequestInSecondsField;
import net.sf.jacclog.logformat.field.RequestMethodField;
import net.sf.jacclog.logformat.field.RequestProtocolField;
import net.sf.jacclog.logformat.field.RequestTimeField;
import net.sf.jacclog.logformat.field.ResponseInBytesClfField;
import net.sf.jacclog.logformat.field.ResponseInBytesField;
import net.sf.jacclog.logformat.field.ServerNameField;
import net.sf.jacclog.logformat.field.ServerPortField;
import net.sf.jacclog.logformat.field.UrlPathField;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		public Builder appendHttpRefererField() {
			return appendField(HttpRefererField.getInstance());
		}

		public Builder appendHttpStatusField() {
			return appendField(HttpStatusField.getInstance());
		}

		public Builder appendHttpUserAgentField() {
			return appendField(HttpUserAgentField.getInstance());
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
				.appendResponseInBytesClfField().appendHttpRefererField().appendHttpUserAgentField().build()),

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

	private static final Logger LOG = LoggerFactory.getLogger(LogFormat.class);

	/**
	 * Parses a format string and returns an object of type <code>LogFormat</code>.<br>
	 * <br>
	 * If the format string can not be read <code>null</code> will be returned.
	 * 
	 * @param format
	 * @return
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
			} catch (final RecognitionException e) {
				LOG.debug("The format '" + format + "' can not be read: " + e.getLocalizedMessage());
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LogFormat other = (LogFormat) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
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
