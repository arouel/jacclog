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

import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_DRIVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_PASSWORD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_URL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_USER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_LEVEL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TARGET_SERVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.WEAVING;

import java.net.Inet4Address;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.jacclog.persistence.jpa.entity.Country;
import net.sf.jacclog.persistence.jpa.internal.CountryRepository;
import net.sf.jacclog.util.net.IpAddressTranslator;

import org.eclipse.persistence.config.TargetServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryIntegrationTest {

	private static Logger LOG = LoggerFactory.getLogger(CountryIntegrationTest.class);

	private CountryRepository repository;

	private CountryDataOnDemand dod;

	private static final Random rnd = new SecureRandom();

	private static Country getRandomCountry(final List<Country> entries) {
		final int index = rnd.nextInt(entries.size());
		final Country expected = entries.get(index);
		return expected;
	}

	private void compareRandomlyEqualityOfEntries(final List<Country> entries) {
		final int amount = 100;
		for (int i = 0; i < amount; i++) {
			final Country expected = getRandomCountry(entries);
			Assert.assertNotNull(expected);
			final Country actual = repository.find(expected.getId());
			Assert.assertNotNull(actual);
			Assert.assertEquals(expected, actual);
			Assert.assertNotSame(expected, actual);
		}
	}

	@Before
	public void setup() {
		final Map<String, String> properties = new HashMap<String, String>();
		properties.put(JDBC_DRIVER, "org.h2.Driver");
		properties.put(JDBC_URL, "jdbc:h2:mem:provider;DB_CLOSE_DELAY=-1");
		properties.put(JDBC_USER, "sa");
		properties.put(JDBC_PASSWORD, "");

		// Configure logging. FINE ensures all SQL is shown
		properties.put(LOGGING_LEVEL, "INFO");

		// Ensure that no server-platform is configured
		properties.put(TARGET_SERVER, TargetServer.None);

		// weaving should be not performed in unit tests
		properties.put(WEAVING, "static");

		repository = new CountryRepository(properties);
		dod = new CountryDataOnDemand(repository);
	}

	@Test
	public void testCount() {
		Assert.assertNotNull("Data on demand for 'Country' failed to initialize correctly", dod.getRandomCountry());
		final long count = repository.countAll();
		Assert.assertTrue("Counter for 'Country' incorrectly reported there were no entries", count > 0);
	}

	@Test
	public void testFind() {
		Country entry = dod.getRandomCountry();
		Assert.assertNotNull("Data on demand for 'Country' failed to initialize correctly", entry);
		final Long id = entry.getId();
		Assert.assertNotNull("Data on demand for 'Country' failed to provide an identifier", id);
		entry = repository.find(id);
		Assert.assertNotNull("Find method for 'Country' illegally returned null for id '" + id + "'", entry);
		Assert.assertEquals("Find method for 'Country' returned the incorrect identifier", id, entry.getId());
	}

	@Test
	public void testFindAll() {
		Assert.assertNotNull("Data on demand for 'Country' failed to initialize correctly", dod.getRandomCountry());
		final long count = repository.countAll();
		Assert.assertTrue(
				"Too expensive to perform a find all test for 'Country', as there are "
						+ count
						+ " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test",
				count < 250);
		final List<Country> result = repository.findAll();
		Assert.assertNotNull("Find all method for 'Country' illegally returned null", result);
		Assert.assertTrue("Find all method for 'Country' failed to return any data", result.size() > 0);
	}

	@Test
	public void testFindByIpAddress() {
		repository.remove(repository.findAll());

		final Country japan = new Country();
		japan.setBeginIpAddress("220.156.192.0");
		japan.setBeginIpAddressAsNumber(3701260288L);
		japan.setEndIpAddress("220.157.63.255");
		japan.setEndIpAddressAsNumber(3701293055L);
		japan.setCode("JP");
		japan.setName("Japan");
		repository.persist(japan);

		final Country germany = new Country();
		germany.setBeginIpAddress("217.224.0.0");
		germany.setBeginIpAddressAsNumber(3655335936L);
		germany.setEndIpAddress("217.237.163.71");
		germany.setEndIpAddressAsNumber(3656229703L);
		germany.setCode("DE");
		germany.setName("Germany");
		repository.persist(germany);

		final Country poland = new Country();
		poland.setBeginIpAddress("217.212.230.0");
		poland.setBeginIpAddressAsNumber(3654608384L);
		poland.setEndIpAddress("217.212.231.255");
		poland.setEndIpAddressAsNumber(3654608895L);
		poland.setCode("PL");
		poland.setName("Poland");
		repository.persist(poland);

		final Inet4Address japaneseIpAddress = IpAddressTranslator.toInet4Address("220.156.254.12");
		Assert.assertEquals(japan.getEndIpAddress(), repository.find(japaneseIpAddress).getEndIpAddress());

		final Inet4Address germanIpAddress = IpAddressTranslator.toInet4Address("217.227.163.70");
		Assert.assertEquals(germany.getEndIpAddress(), repository.find(germanIpAddress).getEndIpAddress());

		final Inet4Address polishIpAddress = IpAddressTranslator.toInet4Address("217.212.230.81");
		Assert.assertEquals(poland.getEndIpAddress(), repository.find(polishIpAddress).getEndIpAddress());
	}

	@Test
	public void testFindCountries() {
		Assert.assertNotNull("Data on demand for 'Country' failed to initialize correctly", dod.getRandomCountry());
		long count = repository.countAll();
		if (count > 20) {
			count = 20;
		}
		final List<Country> result = repository.find(0, (int) count);
		Assert.assertNotNull("Find entries method for 'Country' illegally returned null", result);
		Assert.assertEquals("Find entries method for 'Country' returned an incorrect number of entries", count,
				result.size());
	}

	@Test
	public void testFlush() {
		Country entry = dod.getRandomCountry();
		Assert.assertNotNull("Data on demand for 'Country' failed to initialize correctly", entry);
		final Long id = entry.getId();
		Assert.assertNotNull("Data on demand for 'Country' failed to provide an identifier", id);
		entry = repository.find(id);
		Assert.assertNotNull("Find method for 'Country' illegally returned null for id '" + id + "'", entry);
		final boolean modified = dod.modifyCountry(entry);
		final Integer currentVersion = entry.getVersion();
		Assert.assertTrue("Version for 'Country' failed to increment on flush directive", currentVersion != null
				&& entry.getVersion() > currentVersion || !modified);
	}

	@Test
	public void testMarkerMethod() {
	}

	@Test
	public void testMerge() {
		Country entry = dod.getRandomCountry();
		Assert.assertNotNull("Data on demand for 'Country' failed to initialize correctly", entry);
		final Long id = entry.getId();
		Assert.assertNotNull("Data on demand for 'Country' failed to provide an identifier", id);
		entry = repository.find(id);
		final boolean modified = dod.modifyCountry(entry);
		final Integer currentVersion = entry.getVersion();
		final Country merged = repository.merge(entry);
		Assert.assertEquals("Identifier of merged object not the same as identifier of original object",
				merged.getId(), id);
		Assert.assertTrue("Version for 'Country' failed to increment on merge and flush directive",
				currentVersion != null && entry.getVersion() > currentVersion || !modified);
	}

	@Test
	public void testPersist() {
		Assert.assertNotNull("Data on demand for 'Country' failed to initialize correctly", dod.getRandomCountry());
		final Country entry = dod.getNewTransientCountry(Integer.MAX_VALUE);
		Assert.assertNotNull("Data on demand for 'Country' failed to provide a new transient entity", entry);
		Assert.assertNull("Expected 'Country' identifier to be null", entry.getId());
		repository.persist(entry);
		Assert.assertNotNull("Expected 'Country' identifier to no longer be null", entry.getId());
	}

	@Test
	public void testPersistCountries() {
		Assert.assertNotNull("Data on demand for 'Country' failed to initialize correctly", dod.getRandomCountry());

		LOG.info("Test persisting of a list of log entries...");
		LOG.info("Current number of entries: " + repository.countAll());

		// remove all currently log entries and create more
		repository.remove(repository.findAll());
		Assert.assertEquals(0, repository.countAll());

		final int amount = 10000;
		final List<Country> entries = new ArrayList<Country>();
		for (int i = 0; i < amount; i++) {
			final Country obj = dod.getNewTransientCountry(i);
			entries.add(obj);
		}

		// Testing id field before persisting
		final Country entryWithoutId = getRandomCountry(entries);
		Assert.assertNull(entryWithoutId.getId());

		repository.persist(entries);
		Assert.assertEquals(amount, repository.countAll());

		// Testing id field after persisting
		Assert.assertNotNull(entryWithoutId.getId());

		LOG.info("Current number of entries: " + repository.countAll());
		compareRandomlyEqualityOfEntries(entries);

		repository.remove(entries);
		Assert.assertEquals(0, repository.countAll());

		LOG.info("Current number of entries: " + repository.countAll());
		dod.initialize();
		Assert.assertEquals(20, repository.countAll());

		LOG.info("Current number of entries: " + repository.countAll());
	}

	@Test
	public void testRemove() {
		Country entry = dod.getRandomCountry();
		Assert.assertNotNull("Data on demand for 'Country' failed to initialize correctly", entry);
		final Long id = entry.getId();
		Assert.assertNotNull("Data on demand for 'Country' failed to provide an identifier", id);
		entry = repository.find(id);
		repository.remove(entry);
		Assert.assertNull("Failed to remove 'Country' with identifier '" + id + "'", repository.find(id));
	}

	@Test
	public void testRemoveCountries() {
		Assert.assertNotNull("Data on demand for 'Country' failed to initialize correctly", dod.getRandomCountry());

		LOG.info("Test removing of a list of log entries...");
		LOG.info("Current number of entries: " + repository.countAll());

		// remove all currently countries and create more
		repository.remove(repository.findAll());
		Assert.assertEquals(0, repository.countAll());
		dod.initialize(10000);
		Assert.assertEquals(10000, repository.countAll());

		final List<Country> entries = repository.find(0, (int) repository.countAll());
		LOG.info("Current number of entries: " + repository.countAll());
		repository.remove(entries);
		Assert.assertEquals(0, repository.countAll());

		LOG.info("Current number of entries: " + repository.countAll());
		dod.initialize();
		Assert.assertEquals(20, repository.countAll());

		LOG.info("Current number of entries: " + repository.countAll());
	}

}
