package net.sf.jacclog.api;

import junit.framework.Assert;
import net.sf.jacclog.api.domain.LogEntry;
import net.sf.jacclog.api.domain.LogEntryBuilder;
import net.sf.jacclog.api.domain.http.HttpStatus;

import org.junit.Test;

public class LogEntryBuilderTest {

	@Test
	public void testBuildingWithoutSetting() {
		try {
			final LogEntryBuilder builder = new LogEntryBuilder();
			final LogEntry entry = builder.build();
			Assert.assertEquals(LogEntry.EMPTY, entry);
		} catch (Exception e) {
			Assert.fail("No exception should be thrown if the builder builds an entry in default state: "
					+ e.getLocalizedMessage());
		}
	}

	@Test
	public void testSomeEnums() {
		final LogEntryBuilder builder = new LogEntryBuilder();
		builder.lastStatusCode(HttpStatus.OK);
		builder.statusCode(HttpStatus.ACCEPTED);
		final LogEntry entry = builder.build();
		Assert.assertSame(HttpStatus.OK, entry.getLastStatusCode());
		Assert.assertSame(HttpStatus.ACCEPTED, entry.getStatusCode());
	}

}
