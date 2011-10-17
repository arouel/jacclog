package net.sf.jacclog.persistence.jpa;

import junit.framework.Assert;
import net.sf.jacclog.persistence.jpa.entity.HttpRequestHeaderFieldPK;

import org.junit.Test;

public class HttpRequestHeaderFieldPKTest {

	@Test
	public void testEquality() {
		final HttpRequestHeaderFieldPK key1 = new HttpRequestHeaderFieldPK("type", "value");
		final HttpRequestHeaderFieldPK key2 = new HttpRequestHeaderFieldPK("type", "value");
		Assert.assertEquals(key1, key2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullArgumentForType() {
		new HttpRequestHeaderFieldPK(null, "value");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullArgumentForValue() {
		new HttpRequestHeaderFieldPK("type", null);
	}

}
