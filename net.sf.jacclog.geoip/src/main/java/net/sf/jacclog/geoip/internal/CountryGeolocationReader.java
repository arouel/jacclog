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
package net.sf.jacclog.geoip.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import net.sf.jacclog.csv.CommaSeparatedValuesReader;
import net.sf.jacclog.service.repository.CountryRepositoryService;
import net.sf.jacclog.service.repository.domain.Country;
import net.sf.jacclog.service.repository.domain.NonPersistentCountry;
import net.sf.jacclog.util.net.IpAddressTranslator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryGeolocationReader {

	public static class Mapper {

		private static final Logger LOG = LoggerFactory.getLogger(Mapper.class);

		public static Country mapFieldsToCountry(final List<String> fields) {
			if (fields == null) {
				throw new IllegalArgumentException("Argument 'fields' can not be null.");
			}

			if (fields.size() != 6) {
				throw new IllegalArgumentException("There are not enough fields in the given list.");
			}

			validateIpAddressAndIpNumber(fields.get(0), fields.get(2));
			validateIpAddressAndIpNumber(fields.get(1), fields.get(3));

			final Country country = new NonPersistentCountry();
			country.setBeginIpAddress(fields.get(0));
			country.setBeginIpAddressAsNumber(Long.parseLong(fields.get(2)));
			country.setCode(fields.get(4));
			country.setEndIpAddress(fields.get(1));
			country.setEndIpAddressAsNumber(Long.parseLong(fields.get(3)));
			country.setName(fields.get(5));
			return country;
		}

		/**
		 * Checks the equality of the calculated numeric IP address (of the given textual) with the given numeric
		 * address.
		 * 
		 * @param ipAddress
		 *            Textual representation of an IP address
		 * @param ipNumber
		 *            Numerical representation of an IP address as <code>String</code> (will be parsed as
		 *            <code>long</code>)
		 * @return <code>true</code> if equals, otherwise <code>false</code>
		 */
		private static boolean validateIpAddressAndIpNumber(final String ipAddress, final String ipNumber) {
			if (ipAddress == null) {
				throw new IllegalArgumentException("Argument 'ipAddress' can not be null.");
			}

			if (ipNumber == null) {
				throw new IllegalArgumentException("Argument 'ipNumber' can not be null.");
			}

			final long startIpNum = IpAddressTranslator.toLong(ipAddress);
			final boolean equals = Long.parseLong(ipNumber) == startIpNum;
			if (!equals) {
				LOG.warn("Calculated IP number '" + startIpNum + "' does not equals with read one '" + ipNumber + "'.");
			}
			return equals;
		}

	}

	/**
	 * Potential number of countries to be read
	 */
	private static final int DEFAULT_SIZE = 150000;

	private static final Logger LOG = LoggerFactory.getLogger(CountryGeolocationReader.class);

	public CountryGeolocationReader(final GZIPInputStream gzipInputStream,
			final CountryRepositoryService<Country> service) throws IOException {
		this(new InputStreamReader(gzipInputStream), service);
	}

	public CountryGeolocationReader(final InputStream inputStream, final CountryRepositoryService<Country> service)
			throws IOException {
		this(new InputStreamReader(inputStream), service);
	}

	public CountryGeolocationReader(final Reader reader, final CountryRepositoryService<Country> service)
			throws IOException {

		if (reader == null) {
			throw new IllegalArgumentException("Argument 'reader' can not be null.");
		}

		if (service == null) {
			throw new IllegalArgumentException("Argument 'service' can not be null.");
		}

		final List<List<String>> lines = CommaSeparatedValuesReader.read(reader);
		final List<Country> countries = new ArrayList<Country>(DEFAULT_SIZE);
		for (final List<String> fields : lines) {
			countries.add(Mapper.mapFieldsToCountry(fields));
		}
		service.create(countries);
		LOG.info("... read " + countries.size() + " countries");
	}

}
