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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import net.sf.jacclog.uasparser.internal.data.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This parser checks by every <code>read</code> call once a day, if newer data are available remotely. When newer data
 * are available, they are downloaded, read and replaced by the current one.
 * 
 * @author André Rouél
 */
public final class OnlineUserAgentStringParser extends UserAgentStringParser {

	private static final Logger LOG = LoggerFactory.getLogger(OnlineUserAgentStringParser.class);

	private static final String DATA_URL_KEY = "ini.data.url";

	private static final String VERSION_URL_KEY = "ini.version.url";

	private static final long DEFAULT_UPDATE_INTERVAL = 1000 * 60 * 60 * 24; // 1 day

	/**
	 * Reads all configuration properties from <code>config.properties</code> file and returns a <code>Properties</code>
	 * object.
	 * 
	 * @return Configuration properties
	 * @throws IOException
	 */
	private static Properties readConfigProperties() throws IOException {
		final Properties properties = new Properties();
		final InputStream stream = OnlineUserAgentStringParser.class.getClassLoader().getResourceAsStream(
				"config.properties");
		properties.load(stream);
		stream.close();
		return properties;
	}

	/**
	 * Retrieves the current data version from <a
	 * href="http://user-agent-string.info">http://user-agent-string.info</a>.
	 * 
	 * @return A version string
	 * @throws IOException
	 */
	public static String retrieveRemoteVersion(final URL url) throws IOException {
		final InputStream stream = url.openStream();
		try {
			final byte[] buffer = new byte[4048];
			final int len = stream.read(buffer);
			return new String(buffer, 0, len);
		} finally {
			stream.close();
		}
	}

	private long updateInterval = DEFAULT_UPDATE_INTERVAL;

	private long lastUpdateCheck;

	private final transient Properties configuration;

	public OnlineUserAgentStringParser() {
		this(OnlineUserAgentStringParser.class.getClassLoader().getResourceAsStream(DEFAULT_DATA));
	}

	/**
	 * This constructor is originally for the unit testing against an older <code>uas.ini</code> file.
	 * 
	 * @param stream
	 *            <code>InputStream</code> of default <code>Data</code>
	 */
	public OnlineUserAgentStringParser(final InputStream stream) {
		super(stream);

		// reading configuration
		Properties configuration = new Properties();
		try {
			configuration = readConfigProperties();
		} catch (final IOException e) {
			LOG.info(e.getLocalizedMessage());
		}
		this.configuration = configuration;

		// reading remote data
		try {
			final URL url = new URL(configuration.getProperty(DATA_URL_KEY));
			retrieveRemoteData(url);
		} catch (final MalformedURLException e) {
			LOG.info(e.getLocalizedMessage());
		} catch (final IOException e) {
			LOG.info(e.getLocalizedMessage());
		}
	}

	protected Properties getConfiguration() {
		return configuration;
	}

	@Override
	protected Data getData() {
		try {
			final URL url = new URL(configuration.getProperty(DATA_URL_KEY));
			retrieveRemoteData(url);
		} catch (final IOException e) {
			LOG.info(e.getLocalizedMessage());
		}
		return super.getData();
	}

	public long getLastUpdateCheck() {
		return lastUpdateCheck;
	}

	public long getUpdateInterval() {
		return updateInterval;
	}

	/**
	 * Checks the version address <code>VERSION_URL</code> for updates.
	 * 
	 * @return <code>true</code> if an update exists, otherwise <code>false</code>
	 */
	private boolean isUpdateAvailable() {
		boolean result = false;
		if (lastUpdateCheck == 0 || lastUpdateCheck < System.currentTimeMillis() - getUpdateInterval()) {
			try {
				final URL url = new URL(configuration.getProperty(VERSION_URL_KEY));
				final String version = retrieveRemoteVersion(url);
				if (getCurrentVersion() == null || version.compareTo(getCurrentVersion()) > 0) {
					LOG.debug("An update is available. Current version is '" + getCurrentVersion()
							+ "' and remote version is '" + version + "'.");
					result = true;
				} else {
					LOG.debug("No update available. Current version is '" + getCurrentVersion() + "'.");
				}
			} catch (final IOException e) {
				LOG.info(e.getLocalizedMessage());
			}
			lastUpdateCheck = System.currentTimeMillis();
		} else {
			LOG.debug("There is no check necessary because the update interval has not expired.");
		}
		return result;
	}

	/**
	 * Loads the data file from user-agent-string.info
	 * 
	 * @throws IOException
	 */
	private void retrieveRemoteData(final URL url) throws IOException {
		if (url == null) {
			throw new IllegalArgumentException("Argument 'url' can not be null.");
		}

		if (isUpdateAvailable()) {
			LOG.debug("Reading remote data...");
			setData(getDataReader().read(url));
		}
	}

	public void setLastUpdateCheck(final long lastUpdateCheck) {
		this.lastUpdateCheck = lastUpdateCheck;
	}

	public void setUpdateInterval(final long updateInterval) {
		this.updateInterval = updateInterval;
	}

}
