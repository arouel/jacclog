/*******************************************************************************
 * Copyright 2011 André Rouél
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.jacclog.util.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides several methods to translate different representations of Internet Protocol addresses.
 * 
 * @author André Rouél
 */
public class IpAddressTranslator {

	/**
	 * Pattern of an Internet Protocol version 4 (IPv4) address
	 */
	private static final Pattern IPV4_PATTERN = Pattern
			.compile("^0*([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.0*([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.0*([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.0*([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])$");

	/**
	 * Translates an IPv4 address from its Four dimensions byte array representation to an <code>Inet4Address</code>.
	 * 
	 * @param ipAddress
	 *            Four dimensions byte array representation of an IP address
	 * @return IP address as <code>Inet4Address</code>
	 * @throws UnknownHostException
	 *             Thrown to indicate that the IP address of a host could not be determined.
	 */
	public static Inet4Address toInet4Address(final byte[] ipAddress) throws UnknownHostException {
		if (ipAddress == null) {
			throw new IllegalArgumentException("Argument 'ipAddress' can not be null.");
		}

		if (ipAddress.length != 4) {
			throw new IllegalArgumentException("An IP address as byte array needs 4 entries.");
		}

		return (Inet4Address) InetAddress.getByAddress(ipAddress);
	}

	/**
	 * Translates an IPv4 address from its numeric representation to an <code>Inet4Address</code>.
	 * 
	 * @param ipAddress
	 *            Numerical representation of an IP address as <code>long</code>
	 * @return IP address as <code>Inet4Address</code>
	 * @throws IllegalArgumentException
	 *             If the given argument is smaller than 0
	 * @throws UnknownHostException
	 *             Thrown to indicate that the IP address of a host could not be determined.
	 */
	public static Inet4Address toInet4Address(final long ipAddress) throws UnknownHostException {
		if (ipAddress < 0L) {
			throw new IllegalArgumentException("Argument 'ipAddress' can not be smaller than 0.");
		}

		if (ipAddress > 4294967295L) {
			throw new IllegalArgumentException("Argument 'ipAddress' can not be greater than 4294967295.");
		}

		final byte[] bytes = new byte[] {

		(byte) ((ipAddress & 0xFF000000) >> 24),

		(byte) ((ipAddress & 0x00FF0000) >> 16),

		(byte) ((ipAddress & 0x0000FF00) >> 8),

		(byte) ((ipAddress & 0x000000FF) >> 0) };

		return toInet4Address(bytes);
	}

	/**
	 * Translates an IPv4 address from its textual to numeric representation.
	 * 
	 * @param ipAddress
	 *            IP address (IPv4)
	 * @return Numerical representation of an IP address as <code>long</code>
	 * @throws IllegalArgumentException
	 *             If the given argument is <code>null</code>
	 */
	public static long toLong(final Inet4Address ipAddress) {
		if (ipAddress == null) {
			throw new IllegalArgumentException("Argument 'ipAddress' can not be null.");
		}

		return toLong(ipAddress.getHostAddress());
	}

	/**
	 * Multiplies an IP address, which consists of four numeric parts together into one numeric representation.
	 * 
	 * @param partOne
	 *            First part of an IP address
	 * @param partTwo
	 *            Second part of an IP address
	 * @param partThree
	 *            Third part of an IP address
	 * @param partFour
	 *            Fourth part of an IP address
	 * @return Numerical representation of an IP address as <code>long</code>
	 * @throws IllegalArgumentException
	 *             If any given argument is smaller than 0
	 * @throws IllegalArgumentException
	 *             If any given argument is greater than 255
	 */
	public static long toLong(final long partOne, final long partTwo, final long partThree, final long partFour) {
		validateIpAddressPart(partOne, "partOne");
		validateIpAddressPart(partTwo, "partTwo");
		validateIpAddressPart(partThree, "partThree");
		validateIpAddressPart(partFour, "partFour");

		return 16777216L * partOne + 65536L * partTwo + 256L * partThree + partFour;
	}

	/**
	 * Translates an IPv4 address from its textual to numeric representation.
	 * 
	 * @param ipAddress
	 *            IP address (IPv4) as <code>String</code>
	 * @return Numerical representation of an IP address as <code>long</code>
	 * @throws IllegalArgumentException
	 *             If the given argument is null
	 * @throws IllegalArgumentException
	 *             If the given argument is not a valid IP address (IPv4)
	 */
	public static long toLong(final String ipAddress) {
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
		return toLong(w, x, y, z);
	}

	/**
	 * Validates a part if it is not smaller than <code>0</code> and greater than <code>255</code>. If the first
	 * argument is smaller than zero an <code>IllegalArgumentException</code> will be thrown. Otherwise the value of the
	 * first argument is valid.
	 * 
	 * @param part
	 *            Value of an IP address part
	 * @param name
	 *            Name of the validating argument
	 */
	private static void validateIpAddressPart(final long part, final String name) {
		if (part < 0) {
			throw new IllegalArgumentException("Argument '" + name + "' can not be smaller than 0.");
		}

		if (part > 255) {
			throw new IllegalArgumentException("Argument '" + name + "' can not be greater than 255.");
		}
	}

	private IpAddressTranslator() {
		// stateless classes must not be instantiated
	}

}
