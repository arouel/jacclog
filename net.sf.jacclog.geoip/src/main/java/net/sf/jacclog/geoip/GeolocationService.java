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
package net.sf.jacclog.geoip;

import java.net.InetAddress;

import net.sf.jacclog.geoip.domain.Country;
import net.sf.jacclog.geoip.domain.Location;
import net.sf.jacclog.geoip.domain.Region;

public interface GeolocationService {

	Country seekCountry(final InetAddress ipAddress);

	Country seekCountry(final String ipAddress);

	Location seekLocation(final InetAddress ipAddress);

	Location seekLocation(final String ipAddress);

	Region seekRegion(final InetAddress ipAddress);

	Region seekRegion(final String ipAddress);

}
