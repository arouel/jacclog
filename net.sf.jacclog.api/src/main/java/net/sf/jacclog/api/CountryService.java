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
package net.sf.jacclog.api;

import java.net.Inet4Address;
import java.util.List;

import net.sf.jacclog.api.domain.ReadableCountry;

public interface CountryService<E extends ReadableCountry> {

	/**
	 * Counts all countries within a service.
	 * 
	 * @return The number of all countries
	 */
	long countAll();

	/**
	 * Finds with the given IP address a country within the specified IP address range. The range is defined by the
	 * beginning and ending IP address of an country.
	 * 
	 * @param ipAddress
	 *            IP address as <code>Inet4Address</code>
	 * @return A country
	 */
	E find(final Inet4Address ipAddress);

	/**
	 * Finds countries in the specific range by specifying a starting position and a maximum number of results.
	 * 
	 * @param startPosition
	 *            Position of the first result, numbered from 0
	 * @param maxResults
	 *            Maximum number of results to retrieve
	 * @return A list of countries
	 */
	List<E> find(final int startPosition, final int maxResults);

	/**
	 * Read a country by ID.
	 * 
	 * @param id
	 *            ID of an country
	 * @return A country
	 */
	E find(final Long id);

}
