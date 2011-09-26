package net.sf.jacclog.util.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import junit.framework.Assert;

import org.junit.Test;

public class IpAddressTranslatorTest {

	@Test
	public void testBytesToInet4AddressFails1() {
		try {
			IpAddressTranslator.toInet4Address((byte[]) null);
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'ipAddress' can not be null.", e.getMessage());
			return;
		} catch (final InvalidIpAddressException e) {
			Assert.fail("InvalidIpAddressException should not be thrown.");
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testBytesToInet4AddressFails2() {
		try {
			IpAddressTranslator.toInet4Address(new byte[] {});
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("An IP address as byte array needs 4 entries.", e.getMessage());
			return;
		} catch (final InvalidIpAddressException e) {
			Assert.fail("InvalidIpAddressException should not be thrown.");
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testInetAddressCalculation1() throws UnknownHostException {
		final long ipnum = IpAddressTranslator.toLong((Inet4Address) InetAddress.getByName("091.213.036.001"));
		Assert.assertEquals(1540695041, ipnum);
	}

	@Test
	public void testInetAddressCalculation2() throws UnknownHostException {
		final long ipnum = IpAddressTranslator.toLong((Inet4Address) InetAddress.getByName("091.213.036.254"));
		Assert.assertEquals(1540695294, ipnum);
	}

	@Test
	public void testInetAddressToLong1() throws UnknownHostException {
		final long actual = IpAddressTranslator.toLong((Inet4Address) InetAddress.getByAddress(new byte[] { (byte) 255,
				(byte) 255, (byte) 255, (byte) 255 }));
		final long expected = 16777216L * 255 + 65536L * 255 + 256 * 255 + 255;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testInetAddressToLong2() throws UnknownHostException {
		final long actual = IpAddressTranslator.toLong((Inet4Address) InetAddress.getByAddress(new byte[] { (byte) 0,
				(byte) 0, (byte) 0, (byte) 0 }));
		Assert.assertEquals(0L, actual);
	}

	@Test
	public void testInetAddressToLongFails() {
		try {
			IpAddressTranslator.toLong((Inet4Address) null);
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'ipAddress' can not be null.", e.getMessage());
			return;
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testIntCalculation1() {
		Assert.assertEquals(1540695040, IpAddressTranslator.toLong(91, 213, 36, 0));
		Assert.assertEquals(1540695041, IpAddressTranslator.toLong(91, 213, 36, 1));
		Assert.assertEquals(1540695042, IpAddressTranslator.toLong(91, 213, 36, 2));
	}

	@Test
	public void testIntCalculation2() {
		Assert.assertEquals(1540695293, IpAddressTranslator.toLong(91, 213, 36, 253));
		Assert.assertEquals(1540695294, IpAddressTranslator.toLong(91, 213, 36, 254));
		Assert.assertEquals(1540695295, IpAddressTranslator.toLong(91, 213, 36, 255));
	}

	@Test
	public void testInvalidIpAddress1() {
		try {
			IpAddressTranslator.toLong("0.0.0");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(true);
			return;
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testInvalidIpAddress2() {
		try {
			IpAddressTranslator.toLong("0.0.0.");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(true);
			return;
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testInvalidIpAddress3() {
		try {
			IpAddressTranslator.toLong("127");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(true);
			return;
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testIpAddressCalculation1() {
		final long ipnum = IpAddressTranslator.toLong("91.213.36.254");
		Assert.assertTrue(1540695040 < ipnum && ipnum < 1540695295);
	}

	@Test
	public void testIpAddressCalculation2() {
		final long ipnum = IpAddressTranslator.toLong("91.213.36.1");
		Assert.assertTrue(1540695040 < ipnum && ipnum < 1540695295);
	}

	@Test
	public void testIpAddressCalculation3() {
		final long ipnum = IpAddressTranslator.toLong("091.213.036.001");
		Assert.assertTrue(1540695040 < ipnum && ipnum < 1540695295);
	}

	@Test
	public void testLongToInet4AddressFails1() {
		try {
			IpAddressTranslator.toInet4Address(-1L);
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'ipAddress' can not be smaller than 0.", e.getMessage());
			return;
		} catch (final InvalidIpAddressException e) {
			Assert.fail("InvalidIpAddressException should not be thrown.");
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testLongToInet4AddressFails2() {
		try {
			IpAddressTranslator.toInet4Address(4294967296L);
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'ipAddress' can not be greater than 4294967295.", e.getMessage());
			return;
		} catch (final InvalidIpAddressException e) {
			Assert.fail("InvalidIpAddressException should not be thrown.");
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testLongToInetAddress1() {
		final Inet4Address actual = IpAddressTranslator.toInet4Address(new byte[] { (byte) 255, (byte) 255, (byte) 255,
				(byte) 255 });
		final String expected = "255.255.255.255";
		Assert.assertEquals(expected, actual.getHostAddress());
	}

	@Test
	public void testLongToInetAddress2() {
		final Inet4Address actual = IpAddressTranslator.toInet4Address(0L);
		final String expected = "0.0.0.0";
		Assert.assertEquals(expected, actual.getHostAddress());
	}

	@Test
	public void testLongToInetAddress3() {
		final long ipnum = 16777216L * 230 + 65536L * 12 + 256 * 123 + 245;
		final Inet4Address actual = IpAddressTranslator.toInet4Address(ipnum);
		final String expected = "230.12.123.245";
		Assert.assertEquals(expected, actual.getHostAddress());
	}

	@Test
	public void testNumberPrecision1() {
		final long ipnum = IpAddressTranslator.toLong("128.0.0.0");
		Assert.assertEquals(2147483648L, ipnum);
	}

	@Test
	public void testNumberPrecision2() {
		final long ipnum = IpAddressTranslator.toLong("255.255.255.255");
		Assert.assertEquals(4294967295L, ipnum);
	}

	@Test
	public void testPartsToLong1() {
		final long actual = IpAddressTranslator.toLong(230, 12, 123, 245);
		final long expected = 16777216L * 230 + 65536L * 12 + 256 * 123 + 245;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testPartsToLong2() {
		final long actual = IpAddressTranslator.toLong(255, 255, 255, 255);
		final long expected = 16777216L * 255 + 65536L * 255 + 256 * 255 + 255;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testPartsToLong3() {
		final long actual = IpAddressTranslator.toLong(0, 0, 0, 0);
		Assert.assertEquals(0L, actual);
	}

	@Test
	public void testPartsToLongFails1() {
		try {
			IpAddressTranslator.toLong(255, 255, 255, 256);
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'partFour' can not be greater than 255.", e.getMessage());
			return;
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testPartsToLongFails2() {
		try {
			IpAddressTranslator.toLong(255, 255, -1, 255);
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'partThree' can not be smaller than 0.", e.getMessage());
			return;
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testRangeEnd() {
		final long ipnum = IpAddressTranslator.toLong("91.213.36.255");
		Assert.assertEquals(1540695295, ipnum);
	}

	@Test
	public void testRangeStart() {
		final long ipnum = IpAddressTranslator.toLong("91.213.36.0");
		Assert.assertEquals(1540695040, ipnum);
	}

	@Test
	public void testTextToInet4Address1() {
		final String ipAddress = "230.12.123.245";
		final Inet4Address actual = IpAddressTranslator.toInet4Address(ipAddress);
		Assert.assertEquals(ipAddress, actual.getHostAddress());
	}

	@Test
	public void testTextToInet4Address2() {
		final String ipAddress = "255.255.255.255";
		final Inet4Address actual = IpAddressTranslator.toInet4Address(ipAddress);
		Assert.assertEquals(ipAddress, actual.getHostAddress());
	}

	@Test
	public void testTextToLong1() {
		final long actual = IpAddressTranslator.toLong("230.12.123.245");
		final long expected = 16777216L * 230 + 65536L * 12 + 256 * 123 + 245;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testTextToLong2() {
		final long actual = IpAddressTranslator.toLong("255.255.255.255");
		final long expected = 16777216L * 255 + 65536L * 255 + 256 * 255 + 255;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testTextToLong3() {
		final long actual = IpAddressTranslator.toLong("0.0.0.0");
		Assert.assertEquals(0L, actual);
	}

	@Test
	public void testTextToLongFails1() {
		try {
			IpAddressTranslator.toLong("255.255.255.256");
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'ipAddress' is not a valid IP address.", e.getMessage());
			return;
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testTextToLongFails2() {
		try {
			IpAddressTranslator.toLong("255.255.255.");
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'ipAddress' is not a valid IP address.", e.getMessage());
			return;
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testTextToLongFails3() {
		try {
			IpAddressTranslator.toLong("255.255");
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'ipAddress' is not a valid IP address.", e.getMessage());
			return;
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testTextToLongFails4() {
		try {
			IpAddressTranslator.toLong((String) null);
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'ipAddress' can not be null.", e.getMessage());
			return;
		}
		Assert.fail("An IllegalArgumentException was expected but was not thrown.");
	}

	@Test
	public void testTextToLongFails5() {
		try {
			IpAddressTranslator.toLong("256.0.0.0");
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'ipAddress' is not a valid IP address.", e.getLocalizedMessage());
			return;
		}
		Assert.assertTrue(false);
	}

}
