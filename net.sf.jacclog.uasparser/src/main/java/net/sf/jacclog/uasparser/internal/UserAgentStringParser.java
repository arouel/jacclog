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
package net.sf.jacclog.uasparser.internal;

import java.io.InputStream;

import net.sf.jacclog.uasparser.internal.data.Data;
import net.sf.jacclog.uasparser.internal.data.DataReader;
import net.sf.jacclog.uasparser.internal.data.XmlDataReader;

public class UserAgentStringParser extends AbstractUserAgentStringParser {

	public static final String DEFAULT_DATA = "uas.xml";

	private Data data;

	private DataReader dataReader;

	private String currentVersion;

	public UserAgentStringParser() {
		this(UserAgentStringParser.class.getClassLoader().getResourceAsStream(DEFAULT_DATA));
	}

	public UserAgentStringParser(final InputStream stream) {
		super();

		if (stream == null) {
			throw new IllegalArgumentException("Argument 'stream' can not be null.");
		}

		dataReader = new XmlDataReader();
		setData(read(stream));
	}

	protected String getCurrentVersion() {
		return currentVersion;
	}

	@Override
	protected Data getData() {
		return data;
	}

	protected final DataReader getDataReader() {
		return dataReader;
	}

	/**
	 * Reads user agent data from <code>InputStream</code> and returns a <code>Data</data> object.
	 * 
	 * @param stream
	 * @return
	 */
	private final Data read(final InputStream stream) {
		if (stream == null) {
			throw new IllegalArgumentException("Argument 'stream' can not be empty.");
		}
		return dataReader.read(stream);
	}

	protected final void setData(final Data data) {
		if (data == null) {
			throw new IllegalArgumentException("Argument 'data' can not be empty.");
		}
		this.data = data;
		currentVersion = data.getVersion();
	}

	protected final void setDataReader(final DataReader dataReader) {
		if (dataReader == null) {
			throw new IllegalArgumentException("Argument 'dataReader' can not be empty.");
		}
		this.dataReader = dataReader;
	}

}
