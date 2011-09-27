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

import java.net.Inet4Address;

import net.sf.jacclog.geoip.domain.Country;
import net.sf.jacclog.geoip.domain.Location;
import net.sf.jacclog.geoip.domain.Region;
import net.sf.jacclog.service.repository.CountryRepositoryService;
import net.sf.jacclog.util.net.IpAddressTranslator;

public class GeolocationService implements net.sf.jacclog.geoip.GeolocationService {

	private final CountryRepositoryService<net.sf.jacclog.service.repository.domain.Country> countryRepository;

	public GeolocationService(
			final CountryRepositoryService<net.sf.jacclog.service.repository.domain.Country> countryRepositoryService) {
		if (countryRepositoryService == null) {
			throw new IllegalArgumentException("Argument 'countryRepositoryService' can not be null.");
		}

		this.countryRepository = countryRepositoryService;
	}

	@Override
	public Country findCountry(final Inet4Address ipAddress) {
		// TODO here should be an mapper
		net.sf.jacclog.service.repository.domain.Country entity = countryRepository.find(ipAddress);
		return new Country(entity.getBeginIpAddress(), entity.getEndIpAddress(), entity.getBeginIpAddressAsNumber(),
				entity.getEndIpAddressAsNumber(), entity.getCode(), entity.getName());
	}

	@Override
	public Country findCountry(final String ipAddress) {
		Inet4Address address = IpAddressTranslator.toInet4Address(ipAddress);
		return address != null ? findCountry(address) : Country.UNKNOWN;
	}

	@Override
	public Location findLocation(final Inet4Address ipAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location findLocation(final String ipAddress) {
		Inet4Address address = IpAddressTranslator.toInet4Address(ipAddress);
		return address != null ? findLocation(address) : Location.UNKNOWN;
	}

	@Override
	public Region findRegion(final Inet4Address ipAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Region findRegion(final String ipAddress) {
		Inet4Address address = IpAddressTranslator.toInet4Address(ipAddress);
		return address != null ? findRegion(address) : Region.UNKNOWN;
	}

}
