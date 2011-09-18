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
package net.sf.jacclog.geoip.domain;

public final class Country {

	public static final Country UNKNOWN = new Country("", "", 0, 0, "--", "N/A");

	private final String endIpAddress;

	private final String startIpAddress;

	private final long endIpNumber;

	private final long startIpNumber;

	private final String code;

	private final String name;

	public Country(final String startIpAddress, final String endIpAddress, final long startIpNumber,
			final long endIpNumber, final String code, final String name) {
		if (startIpAddress == null) {
			throw new IllegalArgumentException("Argument 'startIpAddress' can not be null.");
		}

		if (endIpAddress == null) {
			throw new IllegalArgumentException("Argument 'endIpAddress' can not be null.");
		}

		if (startIpNumber < 0) {
			throw new IllegalArgumentException("Argument 'startIpNumber' must be greater than -1.");
		}

		if (endIpNumber < 0) {
			throw new IllegalArgumentException("Argument 'endIpNumber' must be greater than -1.");
		}

		if (code == null) {
			throw new IllegalArgumentException("Argument 'code' can not be null.");
		}

		if (code.length() != 2) {
			throw new IllegalArgumentException("Argument 'code' must be two characters long.");
		}

		if (name == null) {
			throw new IllegalArgumentException("Argument 'name' can not be null.");
		}

		this.startIpAddress = startIpAddress;
		this.endIpAddress = endIpAddress;
		this.startIpNumber = startIpNumber;
		this.endIpNumber = endIpNumber;
		this.code = code;
		this.name = name;
	}

	public Country(final String startIpAddress, final String endIpAddress, final String startIpNumber,
			final String endIpNumber, final String code, final String name) {
		this(startIpAddress, endIpAddress, Long.parseLong(startIpNumber), Long.parseLong(endIpNumber), code, name);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Country other = (Country) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (endIpAddress == null) {
			if (other.endIpAddress != null)
				return false;
		} else if (!endIpAddress.equals(other.endIpAddress))
			return false;
		if (endIpNumber != other.endIpNumber)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (startIpAddress == null) {
			if (other.startIpAddress != null)
				return false;
		} else if (!startIpAddress.equals(other.startIpAddress))
			return false;
		if (startIpNumber != other.startIpNumber)
			return false;
		return true;
	}

	public String getCode() {
		return code;
	}

	public String getEndIpAddress() {
		return endIpAddress;
	}

	public long getEndIpNumber() {
		return endIpNumber;
	}

	public String getName() {
		return name;
	}

	public String getStartIpAddress() {
		return startIpAddress;
	}

	public long getStartIpNumber() {
		return startIpNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((endIpAddress == null) ? 0 : endIpAddress.hashCode());
		result = prime * result + (int) (endIpNumber ^ (endIpNumber >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((startIpAddress == null) ? 0 : startIpAddress.hashCode());
		result = prime * result + (int) (startIpNumber ^ (startIpNumber >>> 32));
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Country [endIpAddress=");
		builder.append(endIpAddress);
		builder.append(", startIpAddress=");
		builder.append(startIpAddress);
		builder.append(", endIpNumber=");
		builder.append(endIpNumber);
		builder.append(", startIpNumber=");
		builder.append(startIpNumber);
		builder.append(", code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
