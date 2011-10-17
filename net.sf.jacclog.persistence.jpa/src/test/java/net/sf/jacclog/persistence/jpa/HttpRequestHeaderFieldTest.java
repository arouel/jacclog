package net.sf.jacclog.persistence.jpa;

import junit.framework.Assert;
import net.sf.jacclog.api.domain.http.HttpRequestHeader;
import net.sf.jacclog.persistence.jpa.entity.HttpRequestHeaderField;

import org.junit.Test;

public class HttpRequestHeaderFieldTest {

	@Test
	public void testEqualityWithKnownType() {
		final HttpRequestHeaderField field1 = new HttpRequestHeaderField(HttpRequestHeader.HOST, "jacclog.sf.net");
		final HttpRequestHeaderField field2 = new HttpRequestHeaderField(HttpRequestHeader.HOST, "jacclog.sf.net");
		Assert.assertEquals(field1, field2);
		Assert.assertTrue(field1.hashCode() == field2.hashCode());
	}

	@Test
	public void testEqualityWithUnknownType() {
		final HttpRequestHeaderField field1 = new HttpRequestHeaderField(HttpRequestHeader.evaluate("test"), "value");
		final HttpRequestHeaderField field2 = new HttpRequestHeaderField(HttpRequestHeader.evaluate("test"), "value");
		Assert.assertEquals(field1, field2);
		Assert.assertTrue(field1.hashCode() == field2.hashCode());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullArgumentForType() {
		new HttpRequestHeaderField(null, "value");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullArgumentForValue() {
		new HttpRequestHeaderField(HttpRequestHeader.HOST, null);
	}

}
