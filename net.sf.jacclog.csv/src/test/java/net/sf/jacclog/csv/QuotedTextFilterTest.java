package net.sf.jacclog.csv;

import junit.framework.Assert;

import org.junit.Test;

public class QuotedTextFilterTest {

	@Test
	public void testQuoteFiltering1() {
		final String text = "\"Ludwig van Beethoven aka. \"\"Lu Hovi\"\"\"";
		Assert.assertEquals("Ludwig van Beethoven aka. \"Lu Hovi\"", QuotedTextFilter.filter(text));
	}

	@Test
	public void testQuoteFiltering2() {
		final String text = "\"\"\"Georg Friedrich Händel\"\"\"\"\"";
		Assert.assertEquals("\"Georg Friedrich Händel\"\"", QuotedTextFilter.filter(text));
	}

	@Test
	public void testQuoteFiltering3() {
		final String text = "\"\"\"Fryderyk Franciszek Chopin\"\"\"\"\"\"\"";
		Assert.assertEquals("\"Fryderyk Franciszek Chopin\"\"\"", QuotedTextFilter.filter(text));
	}

	@Test
	public void testQuoteFiltering4() {
		final String text = "\"\"\"\"\"\"\"\"";
		Assert.assertEquals("\"\"\"", QuotedTextFilter.filter(text));
	}

	@Test
	public void testQuoteFilteringWithNull() {
		try {
			QuotedTextFilter.filter(null);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			// null is not a valid text
		}
	}

}
