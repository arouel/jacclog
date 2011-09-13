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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.GZIPInputStream;

import net.sf.jacclog.csv.CommaSeparatedValuesReader;
import net.sf.jacclog.geoip.domain.Country;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryGeolocationReader {

	public static class Mapper {

		public static Country mapFieldsToCountry(final List<String> fields) {
			if (fields == null) {
				throw new IllegalArgumentException("Argument 'fields' can not be null.");
			}

			if (fields.size() != 6) {
				throw new IllegalArgumentException("There are not enough fields in the given list.");
			}

			final Country country = new Country(fields.get(4), fields.get(5));
			return country;
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(CountryGeolocationReader.class);

	public CountryGeolocationReader(final GZIPInputStream gzipInputStream) throws IOException {
		this(new InputStreamReader(gzipInputStream));
	}

	public CountryGeolocationReader(final InputStream inputStream) throws IOException {
		this(new GZIPInputStream(inputStream));
	}

	public CountryGeolocationReader(final InputStreamReader reader) throws IOException {
		if (reader == null) {
			throw new IllegalArgumentException("Argument 'reader' can not be null.");
		}

		final List<List<String>> lines = CommaSeparatedValuesReader.read(reader);
		for (final List<String> fields : lines) {
			Country country = Mapper.mapFieldsToCountry(fields);
			// for (final String field : fields) {
			//
			// LOG.info("field: " + field);
			// }
			LOG.info("... " + country);
		}
	}

	public CountryGeolocationReader(final String fileName) throws FileNotFoundException, IOException {
		this(new FileInputStream(fileName));
	}

}
