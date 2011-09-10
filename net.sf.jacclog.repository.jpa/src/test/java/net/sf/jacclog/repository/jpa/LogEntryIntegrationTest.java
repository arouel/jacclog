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
package net.sf.jacclog.repository.jpa;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jacclog.repository.jpa.internal.LogEntryRepository;

@Configurable
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LogEntryIntegrationTest {

	@Autowired
	private LogEntryRepository repository;

	private LogEntryDataOnDemand dod;

	@Before
	public void setup() {
		dod = new LogEntryDataOnDemand(repository);
	}

	@Test
	public void testCount() {
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());
		long count = repository.countAll();
		Assert.assertTrue("Counter for 'LogEntry' incorrectly reported there were no entries", count > 0);
	}

	@Test
	public void testFind() {
		LogEntry entry = dod.getRandomLogEntry();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", entry);
		Long id = entry.getId();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide an identifier", id);
		entry = repository.find(id);
		Assert.assertNotNull("Find method for 'LogEntry' illegally returned null for id '" + id + "'", entry);
		Assert.assertEquals("Find method for 'LogEntry' returned the incorrect identifier", id, entry.getId());
	}

	@Test
	public void testFindAll() {
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());
		long count = repository.countAll();
		Assert.assertTrue(
				"Too expensive to perform a find all test for 'LogEntry', as there are "
						+ count
						+ " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test",
				count < 250);
		java.util.List<LogEntry> result = repository.findAll();
		Assert.assertNotNull("Find all method for 'LogEntry' illegally returned null", result);
		Assert.assertTrue("Find all method for 'LogEntry' failed to return any data", result.size() > 0);
	}

	@Test
	public void testFindLogEntryEntries() {
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());
		long count = repository.countAll();
		if (count > 20)
			count = 20;
		java.util.List<LogEntry> result = repository.find(0, (int) count);
		Assert.assertNotNull("Find entries method for 'LogEntry' illegally returned null", result);
		Assert.assertEquals("Find entries method for 'LogEntry' returned an incorrect number of entries", count,
				result.size());
	}

	@Test
	public void testFlush() {
		LogEntry entry = dod.getRandomLogEntry();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", entry);
		Long id = entry.getId();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide an identifier", id);
		entry = repository.find(id);
		Assert.assertNotNull("Find method for 'LogEntry' illegally returned null for id '" + id + "'", entry);
		boolean modified = dod.modifyLogEntry(entry);
		Integer currentVersion = entry.getVersion();
		repository.flush();
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
		Long id = entry.getId();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide an identifier", id);
		entry = repository.find(id);
		boolean modified = dod.modifyLogEntry(entry);
		Integer currentVersion = entry.getVersion();
		LogEntry merged = repository.merge(entry);
		repository.flush();
		Assert.assertEquals("Identifier of merged object not the same as identifier of original object",
				merged.getId(), id);
		Assert.assertTrue("Version for 'LogEntry' failed to increment on merge and flush directive",
				(currentVersion != null && entry.getVersion() > currentVersion) || !modified);
	}

	@Test
	public void testPersist() {
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());
		LogEntry entry = dod.getNewTransientLogEntry(Integer.MAX_VALUE);
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide a new transient entity", entry);
		Assert.assertNull("Expected 'LogEntry' identifier to be null", entry.getId());
		repository.persist(entry);
		repository.flush();
		Assert.assertNotNull("Expected 'LogEntry' identifier to no longer be null", entry.getId());
	}

	@Test
	public void testRemove() {
		LogEntry entry = dod.getRandomLogEntry();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", entry);
		final Long id = entry.getId();
		Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide an identifier", id);
		entry = repository.find(id);
		repository.remove(entry);
		repository.flush();
		Assert.assertNull("Failed to remove 'LogEntry' with identifier '" + id + "'", repository.find(id));
	}

}
