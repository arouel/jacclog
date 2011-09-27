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
package net.sf.jacclog.persistence.jpa.internal;

import java.util.ArrayList;
import java.util.List;

import net.sf.jacclog.persistence.jpa.entity.Country;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryMapper {

	public static final Logger LOG = LoggerFactory.getLogger(CountryMapper.class);

	/**
	 * Casts a list of <code>net.sf.jacclog.service.repository.domain.Country</code> to a list of
	 * <code>net.sf.jacclog.service.repository.domain.Country</code>.
	 * 
	 * @param list
	 *            A list of country entities
	 * @return A list with countries
	 */
	public static List<net.sf.jacclog.service.repository.domain.Country> map(final List<Country> list) {
		final List<net.sf.jacclog.service.repository.domain.Country> entries = new ArrayList<net.sf.jacclog.service.repository.domain.Country>(
				list);
		return entries;
	}

	/**
	 * Maps the content of an alien country that only implements
	 * <code>net.sf.jacclog.service.repository.domain.Country</code> into a new <code>Country</code> entity.<br>
	 * <br>
	 * If the given country is already an entity of <code>net.sf.jacclog.service.repository.domain.Country</code>, the
	 * input will be returned directly.
	 * 
	 * @param country
	 *            A country that implements the interface <code>net.sf.jacclog.service.repository.domain.Country</code>
	 * @return A country entity
	 */
	public static Country map(final net.sf.jacclog.service.repository.domain.Country country) {
		Country logEntry;
		if (!(country instanceof Country)) {
			LOG.debug("I've found an alien country. Translating...");
			logEntry = mapToCountryEntity(country);
		} else {
			logEntry = (Country) country;
		}
		return logEntry;
	}

	/**
	 * Maps the content of an alien country that only implements
	 * <code>net.sf.jacclog.service.repository.domain.Country</code> to a new <code>Country</code> entity.
	 * 
	 * @param country
	 *            A country that implements the interface <code>net.sf.jacclog.service.repository.domain.Country</code>
	 * @return A country entity (as <code>net.sf.jacclog.service.repository.domain.Country</code>)
	 */
	private static Country mapToCountryEntity(final net.sf.jacclog.service.repository.domain.Country country) {
		Country result;
		if (country instanceof Country) {
			result = (Country) country;
		} else {
			result = new Country();
			result.setBeginIpAddress(country.getBeginIpAddress());
			result.setBeginIpAddressAsNumber(country.getEndIpAddressAsNumber());
			result.setCode(country.getCode());
			result.setEndIpAddress(country.getEndIpAddress());
			result.setEndIpAddressAsNumber(country.getEndIpAddressAsNumber());
			result.setName(country.getName());
		}
		return result;
	}

	private CountryMapper() {
		// stateless classes must not be instantiated
	}

}
