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

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.jacclog.persistence.jpa.entity.LogEntry;
import net.sf.jacclog.persistence.jpa.internal.LogEntryRepository;

import org.eclipse.persistence.config.TargetServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEntryIntegrationTest {

	private static Logger LOG = LoggerFactory.getLogger(LogEntryIntegrationTest.class);

	private LogEntryRepository repository;

	private LogEntryDataOnDemand dod;

	private static final Random rnd = new SecureRandom();

	private static LogEntry getRandomLogEntry(final List<LogEntry> entries) {
		final int index = rnd.nextInt(entries.size());
		final LogEntry expected = entries.get(index);
		return expected;
	}

	private void compareRandomlyEqualityOfEntries(final List<LogEntry> entries) {
		final int amount = 100;
		for (int i = 0; i < amount; i++) {
			final LogEntry expected = getRandomLogEntry(entries);
			Assert.assertNotNull(expected);
			final LogEntry actual = repository.find(expected.getId());
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

		repository = new LogEntryRepository(properties);
		dod = new LogEntryDataOnDemand(repository);
	}

	@Test
	public void testCount() {
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());
		final long count = repository.countAll();
		Assert.assertTrue("Counter for 'LogEntry' incorrectly reported there were no entries", count > 0);
	}

	@Test
	public void testFind() {
		LogEntry entry = dod.getRandomLogEntry();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", entry);
		final Long id = entry.getId();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide an identifier", id);
		entry = repository.find(id);
		Assert.assertNotNull("Find method for 'LogEntry' illegally returned null for id '" + id + "'", entry);
		Assert.assertEquals("Find method for 'LogEntry' returned the incorrect identifier", id, entry.getId());
	}

	@Test
	public void testFindAll() {
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());
		final long count = repository.countAll();
		Assert.assertTrue(
				"Too expensive to perform a find all test for 'LogEntry', as there are "
						+ count
						+ " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test",
				count < 250);
		final List<LogEntry> result = repository.findAll();
		Assert.assertNotNull("Find all method for 'LogEntry' illegally returned null", result);
		Assert.assertTrue("Find all method for 'LogEntry' failed to return any data", result.size() > 0);
	}

	@Test
	public void testFindLogEntries() {
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());
		long count = repository.countAll();
		if (count > 20) {
			count = 20;
		}
		final List<LogEntry> result = repository.find(0, (int) count);
		Assert.assertNotNull("Find entries method for 'LogEntry' illegally returned null", result);
		Assert.assertEquals("Find entries method for 'LogEntry' returned an incorrect number of entries", count,
				result.size());
	}

	@Test
	public void testFlush() {
		LogEntry entry = dod.getRandomLogEntry();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", entry);
		final Long id = entry.getId();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide an identifier", id);
		entry = repository.find(id);
		Assert.assertNotNull("Find method for 'LogEntry' illegally returned null for id '" + id + "'", entry);
		final boolean modified = dod.modifyLogEntry(entry);
		final Integer currentVersion = entry.getVersion();
		Assert.assertTrue("Version for 'LogEntry' failed to increment on flush directive",
				(currentVersion != null && entry.getVersion() > currentVersion) || !modified);
	}

	@Test
	public void testMarkerMethod() {
	}

	@Test
	public void testMerge() {
		LogEntry entry = dod.getRandomLogEntry();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", entry);
		final Long id = entry.getId();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide an identifier", id);
		entry = repository.find(id);
		final boolean modified = dod.modifyLogEntry(entry);
		final Integer currentVersion = entry.getVersion();
		final LogEntry merged = repository.merge(entry);
		Assert.assertEquals("Identifier of merged object not the same as identifier of original object",
				merged.getId(), id);
		Assert.assertTrue("Version for 'LogEntry' failed to increment on merge and flush directive",
				(currentVersion != null && entry.getVersion() > currentVersion) || !modified);
	}

	@Test
	public void testPersist() {
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());
		final LogEntry entry = dod.getNewTransientLogEntry(Integer.MAX_VALUE);
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide a new transient entity", entry);
		Assert.assertNull("Expected 'LogEntry' identifier to be null", entry.getId());
		repository.persist(entry);
		Assert.assertNotNull("Expected 'LogEntry' identifier to no longer be null", entry.getId());
	}

	@Test
	public void testPersistLogEntries() {
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());

		LOG.info("Test persisting of a list of log entries...");
		LOG.info("Current number of entries: " + repository.countAll());

		// remove all currently log entries and create more
		repository.remove(repository.findAll());
		Assert.assertEquals(0, repository.countAll());

		final int amount = 10000;
		final List<LogEntry> entries = new ArrayList<LogEntry>();
		for (int i = 0; i < amount; i++) {
			final LogEntry obj = dod.getNewTransientLogEntry(i);
			entries.add(obj);
		}

		// Testing id field before persisting
		final LogEntry entryWithoutId = getRandomLogEntry(entries);
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
		LogEntry entry = dod.getRandomLogEntry();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", entry);
		final Long id = entry.getId();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide an identifier", id);
		entry = repository.find(id);
		repository.remove(entry);
		Assert.assertNull("Failed to remove 'LogEntry' with identifier '" + id + "'", repository.find(id));
	}

	@Test
	public void testRemoveLogEntries() {
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());

		LOG.info("Test removing of a list of log entries...");
		LOG.info("Current number of entries: " + repository.countAll());

		// remove all currently log entries and create more
		repository.remove(repository.findAll());
		Assert.assertEquals(0, repository.countAll());
		dod.initialize(10000);
		Assert.assertEquals(10000, repository.countAll());

		final List<LogEntry> entries = repository.find(0, (int) repository.countAll());
		LOG.info("Current number of entries: " + repository.countAll());
		repository.remove(entries);
		Assert.assertEquals(0, repository.countAll());

		LOG.info("Current number of entries: " + repository.countAll());
		dod.initialize();
		Assert.assertEquals(20, repository.countAll());

		LOG.info("Current number of entries: " + repository.countAll());
	}

}
