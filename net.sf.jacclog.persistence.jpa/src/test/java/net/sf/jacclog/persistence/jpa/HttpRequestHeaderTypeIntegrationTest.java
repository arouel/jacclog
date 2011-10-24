/*******************************************************************************
 * Copyright 2011 André Rouél
 * 
 * Licensed under the Apache License, Id 2.0 (the "License");
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

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.jacclog.persistence.jpa.entity.HttpRequestHeaderType;
import net.sf.jacclog.persistence.jpa.internal.HttpRequestHeaderTypeRepository;

import org.eclipse.persistence.config.TargetServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestHeaderTypeIntegrationTest {

	private static Logger LOG = LoggerFactory.getLogger(HttpRequestHeaderTypeIntegrationTest.class);

	private HttpRequestHeaderTypeRepository repository;

	private HttpRequestHeaderTypeDataOnDemand dod;

	private static final Random rnd = new SecureRandom();

	private static HttpRequestHeaderType getRandomHttpRequestHeaderType(final List<HttpRequestHeaderType> entries) {
		final int index = rnd.nextInt(entries.size());
		final HttpRequestHeaderType expected = entries.get(index);
		return expected;
	}

	private void compareRandomlyEqualityOfEntries(final List<HttpRequestHeaderType> entries) {
		final int amount = 100;
		for (int i = 0; i < amount; i++) {
			final HttpRequestHeaderType expected = getRandomHttpRequestHeaderType(entries);
			Assert.assertNotNull(expected);
			final HttpRequestHeaderType actual = repository.find(expected.getType());
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

		repository = new HttpRequestHeaderTypeRepository(properties);
		dod = new HttpRequestHeaderTypeDataOnDemand(repository);
	}

	@Test
	public void testCount() {
		Assert.assertNotNull("Data on demand for 'HttpRequestHeaderType' failed to initialize correctly",
				dod.getRandomHttpRequestHeaderType());
		final long count = repository.countAll();
		Assert.assertTrue("Counter for 'HttpRequestHeaderType' incorrectly reported there were no entries", count > 0);
	}

	@Test
	public void testFind() {
		HttpRequestHeaderType type = dod.getRandomHttpRequestHeaderType();
		Assert.assertNotNull("Data on demand for 'HttpRequestHeaderType' failed to initialize correctly", type);
		final Long id = type.getId();
		Assert.assertNotNull("Data on demand for 'HttpRequestHeaderType' failed to provide an identifier", id);
		type = repository.find(id);
		Assert.assertNotNull("Find method for 'LogEntry' illegally returned null for id '" + id + "'", type);
		Assert.assertEquals("Find method for 'LogEntry' returned the incorrect identifier", id, type.getId());
	}

	@Test
	public void testFindByType() {
		final HttpRequestHeaderType type = dod.getRandomHttpRequestHeaderType();
		Assert.assertNotNull("Data on demand for 'HttpRequestHeaderType' failed to initialize correctly", type);
		final HttpRequestHeaderType found = repository.find(type.getType());

		Assert.assertNotNull("Find method for 'HttpRequestHeaderType' illegally returned null for id '" + type.getId()
				+ "'", found);
		Assert.assertEquals("Find method for 'HttpRequestHeaderType' returned the incorrect identifier",
				type.getName(), found.getName());
		Assert.assertEquals("Find method for 'HttpRequestHeaderType' returned the incorrect identifier",
				type.getType(), type.getType());
	}

	@Test
	public void testFindAll() {
		Assert.assertNotNull("Data on demand for 'HttpRequestHeaderType' failed to initialize correctly",
				dod.getRandomHttpRequestHeaderType());
		final long count = repository.countAll();
		Assert.assertTrue(
				"Too expensive to perform a find all test for 'HttpRequestHeaderType', as there are "
						+ count
						+ " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test",
				count < 250);
		final List<HttpRequestHeaderType> result = repository.findAll();
		Assert.assertNotNull("Find all method for 'HttpRequestHeaderType' illegally returned null", result);
		Assert.assertTrue("Find all method for 'HttpRequestHeaderType' failed to return any data", result.size() > 0);
	}

	@Test
	public void testFindTypes() {
		Assert.assertNotNull("Data on demand for 'HttpRequestHeaderType' failed to initialize correctly",
				dod.getRandomHttpRequestHeaderType());
		long count = repository.countAll();
		if (count > 20) {
			count = 20;
		}
		final List<HttpRequestHeaderType> result = repository.find(0, (int) count);
		Assert.assertNotNull("Find entries method for 'HttpRequestHeaderType' illegally returned null", result);
		Assert.assertEquals("Find entries method for 'HttpRequestHeaderType' returned an incorrect number of entries",
				count, result.size());
	}

	@Test
	public void testMarkerMethod() {
	}

	@Test
	public void testPersist() {
		Assert.assertNotNull("Data on demand for 'HttpRequestHeaderType' failed to initialize correctly",
				dod.getRandomHttpRequestHeaderType());
		final HttpRequestHeaderType type = dod.getNewTransientHttpRequestHeaderType(Integer.MAX_VALUE);
		Assert.assertNotNull("Data on demand for 'HttpRequestHeaderType' failed to provide a new transient entity",
				type);

		// Check entity integrity
		Assert.assertNotNull(type.getType());

		Assert.assertNull(type.getId());
		repository.persist(type);
		Assert.assertNotNull(type.getId());
	}

	//@Test
	public void testPersistDuplicate() {
		// remove all currently persisted fields
		if (repository.findAll() != null && !repository.findAll().isEmpty()) {
			repository.remove(repository.findAll());
		}

		final HttpRequestHeaderType type = dod.getNewTransientHttpRequestHeaderType(Integer.MAX_VALUE);
		Assert.assertNotNull("Data on demand for 'HttpRequestHeaderType' failed to provide a new transient entity",
				type);

		Assert.assertNull(repository.find(type.getType()));
		try {
			repository.persist(type);
			repository.persist(type);
		} catch (final Exception e) {
			Assert.assertEquals("org.eclipse.persistence.exceptions.DatabaseException", e.getCause().getClass()
					.getName());
			Assert.assertNotSame(-1, e.getCause().getMessage().indexOf("Unique index or primary key violation"));
		}
	}

	//@Test
	public void testPersistTypes() {
		Assert.assertNotNull("Data on demand for 'HttpRequestHeaderType' failed to initialize correctly",
				dod.getRandomHttpRequestHeaderType());

		LOG.info("Test persisting of a list of HTTP request header types...");
		LOG.info("Current number of entries: " + repository.countAll());

		// remove all currently persisted types and create more
		repository.remove(repository.findAll());
		Assert.assertEquals(0, repository.countAll());

		final int amount = 1000;
		final List<HttpRequestHeaderType> entries = new ArrayList<HttpRequestHeaderType>();
		for (int i = 0; i < amount; i++) {
			final HttpRequestHeaderType obj = dod.getNewTransientHttpRequestHeaderType(i);
			entries.add(obj);
		}

		// Testing version type before persisting
		final HttpRequestHeaderType entryWithoutId = getRandomHttpRequestHeaderType(entries);
		Assert.assertNull(entryWithoutId.getId());

		repository.persist(entries);
		Assert.assertEquals(amount, repository.countAll());

		// Testing version type after persisting
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

	//@Test
	public void testRemove() {
		HttpRequestHeaderType type = dod.getRandomHttpRequestHeaderType();
		Assert.assertNotNull("Data on demand for 'HttpRequestHeaderType' failed to initialize correctly", type);
		type = repository.find(type.getName());
		repository.remove(type);
		Assert.assertNull("Failed to remove 'HttpRequestHeaderType' with identifier '" + type.getName() + "'",
				repository.find(type.getName()));
	}

	//@Test
	public void testRemoveTypes() {
		Assert.assertNotNull("Data on demand for 'HttpRequestHeaderType' failed to initialize correctly",
				dod.getRandomHttpRequestHeaderType());

		LOG.info("Test removing of a list of HTTP request header types...");
		LOG.info("Current number of entries: " + repository.countAll());

		// remove all currently persisted types and create more
		repository.remove(repository.findAll());
		Assert.assertEquals(0, repository.countAll());
		dod.initialize(1000);
		Assert.assertEquals(1000, repository.countAll());

		final List<HttpRequestHeaderType> entries = repository.find(0, (int) repository.countAll());
		LOG.info("Current number of entries: " + repository.countAll());
		repository.remove(entries);
		Assert.assertEquals(0, repository.countAll());

		LOG.info("Current number of entries: " + repository.countAll());
		dod.initialize();
		Assert.assertEquals(20, repository.countAll());

		LOG.info("Current number of entries: " + repository.countAll());
	}

}
