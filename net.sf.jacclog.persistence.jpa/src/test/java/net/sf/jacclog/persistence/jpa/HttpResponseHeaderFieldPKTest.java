package net.sf.jacclog.persistence.jpa;

import junit.framework.Assert;
import net.sf.jacclog.persistence.jpa.entity.HttpResponseHeaderFieldPK;

import org.junit.Test;

public class HttpResponseHeaderFieldPKTest {

	@Test
	public void testEquality() {
		final HttpResponseHeaderFieldPK key1 = new HttpResponseHeaderFieldPK("type", "value");
		final HttpResponseHeaderFieldPK key2 = new HttpResponseHeaderFieldPK("type", "value");
		Assert.assertEquals(key1, key2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullArgumentForType() {
		new HttpResponseHeaderFieldPK(null, "value");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullArgumentForValue() {
		new HttpResponseHeaderFieldPK("type", null);
	}

}
