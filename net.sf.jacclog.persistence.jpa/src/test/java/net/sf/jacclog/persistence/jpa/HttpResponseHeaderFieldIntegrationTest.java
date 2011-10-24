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

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.jacclog.persistence.jpa.entity.HttpResponseHeaderField;
import net.sf.jacclog.persistence.jpa.internal.HttpResponseHeaderFieldRepository;

import org.eclipse.persistence.config.TargetServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponseHeaderFieldIntegrationTest {

	private static Logger LOG = LoggerFactory.getLogger(HttpResponseHeaderFieldIntegrationTest.class);

	private HttpResponseHeaderFieldRepository repository;

	private HttpResponseHeaderFieldDataOnDemand dod;

	private static final Random rnd = new SecureRandom();

	private static HttpResponseHeaderField getRandomHttpResponseHeaderField(final List<HttpResponseHeaderField> entries) {
		final int index = rnd.nextInt(entries.size());
		final HttpResponseHeaderField expected = entries.get(index);
		return expected;
	}

	private void compareRandomlyEqualityOfEntries(final List<HttpResponseHeaderField> entries) {
		final int amount = 100;
		for (int i = 0; i < amount; i++) {
			final HttpResponseHeaderField expected = getRandomHttpResponseHeaderField(entries);
			Assert.assertNotNull(expected);
			final HttpResponseHeaderField actual = repository.find(new HttpResponseHeaderField(expected.getType(),
					expected.getValue()));
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

		repository = new HttpResponseHeaderFieldRepository(properties);
		dod = new HttpResponseHeaderFieldDataOnDemand(repository);
	}

	@Test
	public void testCount() {
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to initialize correctly",
				dod.getRandomHttpResponseHeaderField());
		final long count = repository.countAll();
		Assert.assertTrue("Counter for 'HttpResponseHeaderField' incorrectly reported there were no entries", count > 0);
	}

	@Test
	public void testFind() {
		HttpResponseHeaderField field = dod.getRandomHttpResponseHeaderField();
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to initialize correctly", field);
		final Long id = field.getId();
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to provide an identifier", id);
		field = repository.find(id);
		Assert.assertNotNull("Find method for 'LogEntry' illegally returned null for id '" + id + "'", field);
		Assert.assertEquals("Find method for 'LogEntry' returned the incorrect identifier", id, field.getId());
	}

	@Test
	public void testFindByTypeAndValue() {
		HttpResponseHeaderField field = dod.getRandomHttpResponseHeaderField();
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to initialize correctly", field);
		final HttpResponseHeaderField duplicate = new HttpResponseHeaderField(field.getType(), field.getValue());
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to provide an identifier", duplicate);
		field = repository.find(duplicate);
		Assert.assertNotNull("Find method for 'HttpResponseHeaderField' illegally returned null for id '" + duplicate
				+ "'", field);

		Assert.assertEquals("Find method for 'HttpResponseHeaderField' returned the incorrect identifier",
				duplicate.getType(), field.getType());
		Assert.assertEquals("Find method for 'HttpResponseHeaderField' returned the incorrect identifier",
				duplicate.getValue(), field.getValue());
	}

	@Test
	public void testFindAll() {
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to initialize correctly",
				dod.getRandomHttpResponseHeaderField());
		final long count = repository.countAll();
		Assert.assertTrue(
				"Too expensive to perform a find all test for 'HttpResponseHeaderField', as there are "
						+ count
						+ " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test",
				count < 250);
		final List<HttpResponseHeaderField> result = repository.findAll();
		Assert.assertNotNull("Find all method for 'HttpResponseHeaderField' illegally returned null", result);
		Assert.assertTrue("Find all method for 'HttpResponseHeaderField' failed to return any data", result.size() > 0);
	}

	@Test
	public void testFindFields() {
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to initialize correctly",
				dod.getRandomHttpResponseHeaderField());
		long count = repository.countAll();
		if (count > 20) {
			count = 20;
		}
		final List<HttpResponseHeaderField> result = repository.find(0, (int) count);
		Assert.assertNotNull("Find entries method for 'HttpResponseHeaderField' illegally returned null", result);
		Assert.assertEquals(
				"Find entries method for 'HttpResponseHeaderField' returned an incorrect number of entries", count,
				result.size());
	}

	@Test
	public void testFlush() {
		HttpResponseHeaderField field = dod.getRandomHttpResponseHeaderField();
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to initialize correctly", field);
		final HttpResponseHeaderField duplicate = new HttpResponseHeaderField(field.getType(), field.getValue());
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to provide an identifier", duplicate);
		field = repository.find(duplicate);
		Assert.assertNotNull("Find method for 'HttpResponseHeaderField' illegally returned null for id '"
				+ duplicate.getType().getName() + " | " + duplicate.getValue() + "'", field);
		final boolean modified = dod.modifyHttpResponseHeaderField(field);
		final Integer currentVersion = field.getVersion();
		Assert.assertTrue("Version for 'HttpResponseHeaderField' failed to increment on flush directive",
				(currentVersion != null && field.getVersion() > currentVersion) || !modified);
	}

	@Test
	public void testMarkerMethod() {
	}

	@Test
	public void testMerge() {
		HttpResponseHeaderField field = dod.getRandomHttpResponseHeaderField();
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to initialize correctly", field);
		final HttpResponseHeaderField duplicate = new HttpResponseHeaderField(field.getType(), field.getValue());
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to provide an identifier", duplicate);
		field = repository.find(duplicate);
		final boolean modified = dod.modifyHttpResponseHeaderField(field);
		final Integer currentVersion = field.getVersion();
		final HttpResponseHeaderField merged = repository.merge(field);

		Assert.assertEquals("Identifier of merged object not the same as identifier of original object",
				merged.getType(), duplicate.getType());
		Assert.assertEquals("Identifier of merged object not the same as identifier of original object",
				merged.getValue(), duplicate.getValue());
		Assert.assertTrue("Version for 'HttpResponseHeaderField' failed to increment on merge and flush directive",
				(currentVersion != null && field.getVersion() > currentVersion) || !modified);
	}

	@Test
	public void testPersist() {
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to initialize correctly",
				dod.getRandomHttpResponseHeaderField());
		final HttpResponseHeaderField field = dod.getNewTransientHttpResponseHeaderField(Integer.MAX_VALUE);
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to provide a new transient entity",
				field);

		// Check entity integrity
		Assert.assertNotNull(field.getType());
		Assert.assertNotNull(field.getValue());

		Assert.assertNull(field.getVersion());
		repository.persist(field);
		Assert.assertNotNull(field.getVersion());
	}

	@Test
	public void testPersistDuplicate() {
		final HttpResponseHeaderField field = dod.getNewTransientHttpResponseHeaderField(Integer.MAX_VALUE);
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to provide a new transient entity",
				field);

		Assert.assertNotNull(repository.find(field));
		try {
			repository.persist(field);
		} catch (final Exception e) {
			Assert.assertEquals("org.eclipse.persistence.exceptions.DatabaseException", e.getCause().getClass()
					.getName());
			Assert.assertNotSame(-1, e.getCause().getMessage().indexOf("Unique index or primary key violation"));
		}
	}

	@Test
	public void testPersistFields() {
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to initialize correctly",
				dod.getRandomHttpResponseHeaderField());

		LOG.info("Test persisting of a list of HTTP response header fields...");
		LOG.info("Current number of entries: " + repository.countAll());

		// remove all currently persisted fields and create more
		repository.remove(repository.findAll());
		Assert.assertEquals(0, repository.countAll());

		final int amount = 1000;
		final List<HttpResponseHeaderField> entries = new ArrayList<HttpResponseHeaderField>();
		for (int i = 0; i < amount; i++) {
			final HttpResponseHeaderField obj = dod.getNewTransientHttpResponseHeaderField(i);
			entries.add(obj);
		}

		// Testing version field before persisting
		final HttpResponseHeaderField entryWithoutVersion = getRandomHttpResponseHeaderField(entries);
		Assert.assertNull(entryWithoutVersion.getVersion());

		repository.persist(entries);
		Assert.assertEquals(amount, repository.countAll());

		// Testing version field after persisting
		Assert.assertNotNull(entryWithoutVersion.getVersion());

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
		HttpResponseHeaderField field = dod.getRandomHttpResponseHeaderField();
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to initialize correctly", field);
		final HttpResponseHeaderField duplicate = new HttpResponseHeaderField(field.getType(), field.getValue());
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to provide an identifier", duplicate);
		field = repository.find(duplicate);
		repository.remove(field);
		Assert.assertNull("Failed to remove 'HttpResponseHeaderField' with identifier '" + duplicate + "'",
				repository.find(duplicate));
	}

	@Test
	public void testRemoveFields() {
		Assert.assertNotNull("Data on demand for 'HttpResponseHeaderField' failed to initialize correctly",
				dod.getRandomHttpResponseHeaderField());

		LOG.info("Test removing of a list of HTTP response header fields...");
		LOG.info("Current number of entries: " + repository.countAll());

		// remove all currently persisted fields and create more
		repository.remove(repository.findAll());
		Assert.assertEquals(0, repository.countAll());
		dod.initialize(1000);
		Assert.assertEquals(1000, repository.countAll());

		final List<HttpResponseHeaderField> entries = repository.find(0, (int) repository.countAll());
		LOG.info("Current number of entries: " + repository.countAll());
		repository.remove(entries);
		Assert.assertEquals(0, repository.countAll());

		LOG.info("Current number of entries: " + repository.countAll());
		dod.initialize();
		Assert.assertEquals(20, repository.countAll());

		LOG.info("Current number of entries: " + repository.countAll());
	}

}
