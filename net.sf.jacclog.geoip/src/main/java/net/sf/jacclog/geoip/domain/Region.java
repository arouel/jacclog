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

public final class Region {

	public static final Region UNKNOWN = new Region("--", "N/A", "N/A");

	private final String countryCode;

	private final String countryName;

	private final String region;

	public Region(final String countryCode, final String countryName, final String region) {
		if (countryCode == null) {
			throw new IllegalArgumentException("Argument 'countryCode' can not be null.");
		}

		if (countryCode.length() != 2) {
			throw new IllegalArgumentException("Argument 'countryCode' must be two characters long.");
		}

		if (countryName == null || countryName.isEmpty()) {
			throw new IllegalArgumentException("Argument 'countryName' can not be null or empty.");
		}

		if (region == null || countryName.isEmpty()) {
			throw new IllegalArgumentException("Argument 'region' can not be null or empty.");
		}

		this.countryCode = countryCode;
		this.countryName = countryName;
		this.region = region;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Region other = (Region) obj;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		return true;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getRegion() {
		return region;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Region [countryCode=");
		builder.append(countryCode);
		builder.append(", countryName=");
		builder.append(countryName);
		builder.append(", region=");
		builder.append(region);
		builder.append("]");
		return builder.toString();
	}

}
