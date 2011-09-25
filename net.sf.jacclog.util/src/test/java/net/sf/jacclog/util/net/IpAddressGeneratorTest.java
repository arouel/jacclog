package net.sf.jacclog.util.net;

import java.net.UnknownHostException;

import junit.framework.Assert;

import org.junit.Test;

public class IpAddressGeneratorTest {

	@Test
	public void testGenerateRandomInet4Address() throws UnknownHostException {
		try {
			for (int i = 0; i < 100000; i++) {
				IpAddressGenerator.generateRandomInet4Address().getHostAddress();
			}
		} catch (final UnknownHostException e) {
			Assert.fail(e.getLocalizedMessage());
		}
		Assert.assertTrue(true);
	}

}
