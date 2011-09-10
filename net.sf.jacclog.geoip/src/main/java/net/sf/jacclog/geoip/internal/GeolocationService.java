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
package net.sf.jacclog.geoip.internal;

import java.net.InetAddress;
import java.net.UnknownHostException;

import net.sf.jacclog.geoip.domain.Country;
import net.sf.jacclog.geoip.domain.Location;
import net.sf.jacclog.geoip.domain.Region;

public class GeolocationService implements net.sf.jacclog.geoip.GeolocationService {

	@Override
	public Country seekCountry(final InetAddress ipAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Country seekCountry(final String ipAddress) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName(ipAddress);
		} catch (final UnknownHostException e) {
			// no problem, we return an unknown country object
		}
		return address != null ? seekCountry(address) : Country.UNKNOWN;
	}

	@Override
	public Location seekLocation(final InetAddress ipAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location seekLocation(final String ipAddress) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName(ipAddress);
		} catch (final UnknownHostException e) {
			// no problem, we return an unknown country object
		}
		return address != null ? seekLocation(address) : Location.UNKNOWN;
	}

	@Override
	public Region seekRegion(final InetAddress ipAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Region seekRegion(final String ipAddress) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName(ipAddress);
		} catch (final UnknownHostException e) {
			// no problem, we return an unknown country object
		}
		return address != null ? seekRegion(address) : Region.UNKNOWN;
	}

}
