package net.sf.jacclog.util.net;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Random;

public final class IpAddressGenerator {

	private static Random rnd = new SecureRandom();

	/**
	 * Generates randomly an IP address (IPv4).
	 * 
	 * @return A randomly generated IP address as <code>Inet4Address</code>
	 */
	public static Inet4Address generateRandomInet4Address() throws UnknownHostException {
		return IpAddressTranslator.toInet4Address(generateRandomLongForIpAddress());
	}

	/**
	 * Generates randomly a numerical representation of an IP address (IPv4).
	 * 
	 * @return Numerical representation of an randomly generated IP address as <code>long</code>
	 */
	public static long generateRandomLongForIpAddress() {
		final long random = (long) rnd.nextInt() * 2;
		return random < 0 ? random * -1 : random;
	}

	private IpAddressGenerator() {
		// stateless classes must not be instantiated
	}

}
