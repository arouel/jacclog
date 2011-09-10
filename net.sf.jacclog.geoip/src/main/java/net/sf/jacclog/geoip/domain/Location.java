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

public final class Location {

	private static final double EARTH_DIAMETER = 12756.28;

	private static final double RAD_CONVERT = Math.PI / 180;

	public static final Location UNKNOWN = new Location(Integer.MIN_VALUE, "", "", "N/A", Integer.MIN_VALUE, 0, 0,
			Integer.MIN_VALUE, "", "");

	private final int areaCode;

	private final String city;

	private final String countryCode;

	private final String countryName;

	private final int id;

	private final float latitude;

	private final float longitude;

	private final int metroCode;

	private final String postalCode;

	private final String region;

	public Location(final int areaCode, final String city, final String countryCode, final String countryName,
			final int id, final float latitude, final float longitude, final int metroCode, final String postalCode,
			final String region) {

		this.areaCode = areaCode;
		this.city = city;
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.metroCode = metroCode;
		this.postalCode = postalCode;
		this.region = region;
	}

	public double distance(final Location location) {
		// convert degrees to radiant
		final double latitude1 = RAD_CONVERT * latitude;
		final double latitude2 = RAD_CONVERT * location.latitude;

		final float longitude1 = longitude;
		final float longitude2 = location.longitude;

		// find the deltas
		final double latitudeDelta = latitude2 - latitude1;
		final double longitudeDelta = (longitude2 - longitude1) * RAD_CONVERT;

		// Find the great circle distance
		final double temp = Math.pow(Math.sin(latitudeDelta / 2), 2) + Math.cos(latitude1) * Math.cos(latitude2)
				* Math.pow(Math.sin(longitudeDelta / 2), 2);
		return EARTH_DIAMETER * Math.atan2(Math.sqrt(temp), Math.sqrt(1 - temp));
	}

	public int getAreaCode() {
		return areaCode;
	}

	public String getCity() {
		return city;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public int getId() {
		return id;
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public int getMetroCode() {
		return metroCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getRegion() {
		return region;
	}

}
