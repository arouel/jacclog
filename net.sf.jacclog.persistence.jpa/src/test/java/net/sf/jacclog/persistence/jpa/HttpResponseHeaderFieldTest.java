package net.sf.jacclog.persistence.jpa;

import junit.framework.Assert;
import net.sf.jacclog.api.domain.http.HttpResponseHeader;
import net.sf.jacclog.persistence.jpa.entity.HttpResponseHeaderField;

import org.junit.Test;

public class HttpResponseHeaderFieldTest {

	@Test
	public void testEqualityWithKnownType() {
		final HttpResponseHeaderField field1 = new HttpResponseHeaderField(HttpResponseHeader.AGE, "1");
		final HttpResponseHeaderField field2 = new HttpResponseHeaderField(HttpResponseHeader.AGE, "1");
		Assert.assertEquals(field1, field2);
		Assert.assertTrue(field1.hashCode() == field2.hashCode());
	}

	@Test
	public void testEqualityWithUnknownType() {
		final HttpResponseHeaderField field1 = new HttpResponseHeaderField(HttpResponseHeader.evaluate("test"), "value");
		final HttpResponseHeaderField field2 = new HttpResponseHeaderField(HttpResponseHeader.evaluate("test"), "value");
		Assert.assertEquals(field1, field2);
		Assert.assertTrue(field1.hashCode() == field2.hashCode());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullArgumentForType() {
		new HttpResponseHeaderField(null, "value");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullArgumentForValue() {
		new HttpResponseHeaderField(HttpResponseHeader.AGE, null);
	}

}
