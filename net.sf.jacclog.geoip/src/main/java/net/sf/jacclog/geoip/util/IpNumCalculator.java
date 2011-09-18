package net.sf.jacclog.geoip.util;

import java.net.Inet4Address;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IpNumCalculator {

	private static final Pattern IPV4_PATTERN = Pattern
			.compile("^0*([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.0*([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.0*([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.0*([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])$");

	public static long calculate(final Inet4Address ipAddress) {
		if (ipAddress == null) {
			throw new IllegalArgumentException("Argument 'ipAddress' can not be null.");
		}

		return calculate(ipAddress.getHostAddress());
	}

	public static long calculate(final long w, final long x, final long y, final long z) {
		return (16777216L * w) + (65536L * x) + (256L * y) + z;
	}

	public static long calculate(final String ipAddress) {
		if (ipAddress == null) {
			throw new IllegalArgumentException("Argument 'ipAddress' can not be null.");
		}

		final Matcher matcher = IPV4_PATTERN.matcher(ipAddress);
		if (!matcher.find()) {
			throw new IllegalArgumentException("Argument 'ipAddress' is not a valid IP address.");
		}

		final int w = Integer.parseInt(matcher.group(1));
		final int x = Integer.parseInt(matcher.group(2));
		final int y = Integer.parseInt(matcher.group(3));
		final int z = Integer.parseInt(matcher.group(4));
		return calculate(w, x, y, z);
	}

	private IpNumCalculator() {
		// this is an utility class and can not be instantiated
	}

}
