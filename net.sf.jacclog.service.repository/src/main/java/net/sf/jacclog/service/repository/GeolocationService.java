package net.sf.jacclog.service.repository;

import java.net.InetAddress;

import net.sf.jacclog.service.repository.domain.Country;
import net.sf.jacclog.service.repository.domain.Location;
import net.sf.jacclog.service.repository.domain.Region;

public interface GeolocationService {

	Country findCountry(final InetAddress ipAddress);

	Country findCountry(final String ipAddress);

	Location findLocation(final InetAddress ipAddress);

	Location findLocation(final String ipAddress);

	Region findRegion(final InetAddress ipAddress);

	Region findRegion(final String ipAddress);

}
