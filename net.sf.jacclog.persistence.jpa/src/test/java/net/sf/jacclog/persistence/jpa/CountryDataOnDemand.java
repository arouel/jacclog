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
package net.sf.jacclog.persistence.jpa;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sf.jacclog.persistence.jpa.entity.Country;
import net.sf.jacclog.persistence.jpa.internal.CountryRepository;
import net.sf.jacclog.util.net.IpAddressGenerator;
import net.sf.jacclog.util.net.IpAddressTranslator;

public class CountryDataOnDemand {

	public static String getRandomCountryCode() {
		int i = 0;
		int randomNumber;
		final StringBuilder builder = new StringBuilder();
		final Random randomNumberGenerator = new Random();
		while (i++ < 2) {
			randomNumber = randomNumberGenerator.nextInt(26) + 65;
			builder.append((char) randomNumber);
		}
		return builder.toString();
	}

	private List<Country> entries;

	private final CountryRepository repository;

	final static Random rnd = new SecureRandom();

	public CountryDataOnDemand(final CountryRepository repository) {
		if (repository == null) {
			throw new IllegalArgumentException("Argument 'repository' can not be null.");
		}

		this.repository = repository;
	}

	public Country getNewTransientCountry(final int index) {
		final Country country = new Country();
		setNetBlock(country, index);
		setCode(country, index);
		setName(country, index);
		return country;
	}

	public Country getRandomCountry() {
		initialize();
		final Country obj = entries.get(rnd.nextInt(entries.size()));
		return repository.find(obj.getId());
	}

	public Country getSpecificCountry(int index) {
		initialize();
		if (index < 0) {
			index = 0;
		}
		if (index > entries.size() - 1) {
			index = entries.size() - 1;
		}
		final Country obj = entries.get(index);
		return repository.find(obj.getId());
	}

	public void initialize() {
		initialize(20);
	}

	/**
	 * Initialize the repository with self created log entries.
	 * 
	 * @param amount
	 *            Number of log entries to be created
	 */
	public void initialize(final int amount) {
		entries = repository.find(0, amount);
		if (entries == null) {
			throw new IllegalStateException(
					"Find entries implementation for 'CountryRepository' illegally returned null.");
		}
		if (!entries.isEmpty()) {
			return;
		}

		entries = new ArrayList<Country>();
		for (int i = 0; i < amount; i++) {
			final Country obj = getNewTransientCountry(i);
			repository.persist(obj);
			entries.add(obj);
		}
	}

	public boolean modifyCountry(final Country obj) {
		return false;
	}

	public void setCode(final Country country, final int index) {
		country.setCode(getRandomCountryCode());
	}

	public void setName(final Country country, final int index) {
		country.setName("name_" + index);
	}

	public void setNetBlock(final Country country, final int index) {
		final long a = IpAddressGenerator.generateRandomLongForIpAddress();
		final long b = IpAddressGenerator.generateRandomLongForIpAddress();
		final long begin = Math.max(a, b);
		final long end = Math.min(a, b);
		country.setBeginIpAddressAsNumber(begin);
		country.setEndIpAddressAsNumber(end);
		country.setBeginIpAddress(IpAddressTranslator.toInet4Address(begin).getHostAddress());
		country.setEndIpAddress(IpAddressTranslator.toInet4Address(end).getHostAddress());
	}

}
