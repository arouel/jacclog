package net.sf.jacclog.geoip;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import junit.framework.Assert;
import net.sf.jacclog.geoip.util.IpNumCalculator;

import org.junit.Test;

public class IpNumCalculatorTest {

	@Test
	public void testIllegalArgumentException1() {
		try {
			IpNumCalculator.calculate((String) null);
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'ipAddress' can not be null.", e.getLocalizedMessage());
			return;
		}
		Assert.assertTrue(false);
	}

	@Test
	public void testIllegalArgumentException2() {
		try {
			IpNumCalculator.calculate("256.255.255.255");
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'ipAddress' is not a valid IP address.", e.getLocalizedMessage());
			return;
		}
		Assert.assertTrue(false);
	}

	@Test
	public void testIllegalArgumentException3() {
		try {
			IpNumCalculator.calculate((Inet4Address) null);
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("Argument 'ipAddress' can not be null.", e.getLocalizedMessage());
			return;
		}
		Assert.assertTrue(false);
	}

	@Test
	public void testInetAddressCalculation1() throws UnknownHostException {
		// "91.213.36.0","91.213.36.255","1540695040","1540695295","DE","Germany"
		final long ipnum = IpNumCalculator.calculate((Inet4Address) InetAddress.getByName("091.213.036.001"));
		Assert.assertEquals(1540695041, ipnum);
	}

	@Test
	public void testInetAddressCalculation2() throws UnknownHostException {
		// "91.213.36.0","91.213.36.255","1540695040","1540695295","DE","Germany"
		final long ipnum = IpNumCalculator.calculate((Inet4Address) InetAddress.getByName("091.213.036.254"));
		Assert.assertEquals(1540695294, ipnum);
	}

	@Test
	public void testIntCalculation1() {
		Assert.assertEquals(1540695040, IpNumCalculator.calculate(91, 213, 36, 0));
		Assert.assertEquals(1540695041, IpNumCalculator.calculate(91, 213, 36, 1));
		Assert.assertEquals(1540695042, IpNumCalculator.calculate(91, 213, 36, 2));
	}

	@Test
	public void testIntCalculation2() {
		Assert.assertEquals(1540695293, IpNumCalculator.calculate(91, 213, 36, 253));
		Assert.assertEquals(1540695294, IpNumCalculator.calculate(91, 213, 36, 254));
		Assert.assertEquals(1540695295, IpNumCalculator.calculate(91, 213, 36, 255));
	}

	@Test
	public void testInvalidIpAddress1() {
		try {
			IpNumCalculator.calculate("0.0.0");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(true);
			return;
		}
		Assert.assertTrue(false);
	}

	@Test
	public void testInvalidIpAddress2() {
		try {
			IpNumCalculator.calculate("0.0.0.");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(true);
			return;
		}
		Assert.assertTrue(false);
	}

	@Test
	public void testInvalidIpAddress3() {
		try {
			IpNumCalculator.calculate("127");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(true);
			return;
		}
		Assert.assertTrue(false);
	}

	@Test
	public void testIpAddressCalculation1() {
		// "91.213.36.0","91.213.36.255","1540695040","1540695295","DE","Germany"
		final long ipnum = IpNumCalculator.calculate("91.213.36.254");
		Assert.assertTrue(1540695040 < ipnum && ipnum < 1540695295);
	}

	@Test
	public void testIpAddressCalculation2() {
		// "91.213.36.0","91.213.36.255","1540695040","1540695295","DE","Germany"
		final long ipnum = IpNumCalculator.calculate("91.213.36.1");
		Assert.assertTrue(1540695040 < ipnum && ipnum < 1540695295);
	}

	@Test
	public void testIpAddressCalculation3() {
		// "91.213.36.0","91.213.36.255","1540695040","1540695295","DE","Germany"
		final long ipnum = IpNumCalculator.calculate("091.213.036.001");
		Assert.assertTrue(1540695040 < ipnum && ipnum < 1540695295);
	}

	@Test
	public void testNumberPrecision1() {
		// Calculated IP number '-2147483648' does not equals with read one '2147483648'
		final long ipnum = IpNumCalculator.calculate("128.0.0.0");
		Assert.assertEquals(2147483648L, ipnum);
	}

	@Test
	public void testNumberPrecision2() {
		final long ipnum = IpNumCalculator.calculate("255.255.255.255");
		Assert.assertEquals(4294967295L, ipnum);
	}

	@Test
	public void testRangeEnd() {
		// "91.213.36.0","91.213.36.255","1540695040","1540695295","DE","Germany"
		final long ipnum = IpNumCalculator.calculate("91.213.36.255");
		Assert.assertEquals(1540695295, ipnum);
	}

	@Test
	public void testRangeStart() {
		// "91.213.36.0","91.213.36.255","1540695040","1540695295","DE","Germany"
		final long ipnum = IpNumCalculator.calculate("91.213.36.0");
		Assert.assertEquals(1540695040, ipnum);
	}

}
