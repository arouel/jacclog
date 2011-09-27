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
package net.sf.jacclog.service.repository.domain;

public interface Country {

	/**
	 * Gets the first IP address in a netblock.
	 * 
	 * @return First IP address in a netblock
	 */
	String getBeginIpAddress();

	/**
	 * Gets the first IP address in a netblock as numerical representation.
	 * 
	 * @return Numerical representation of the first IP address in a netblock
	 */
	Long getBeginIpAddressAsNumber();

	/**
	 * Gets the ISO 3166 Country Code.<br>
	 * <br>
	 * {@link http://en.wikipedia.org/wiki/ISO_3166}
	 * 
	 * @return Country Code
	 */
	String getCode();

	/**
	 * Gets the last IP address in a netblock.
	 * 
	 * @return Last IP address in a netblock
	 */
	String getEndIpAddress();

	/**
	 * Gets the last IP address in a netblock as numerical representation.
	 * 
	 * @return Numerical representation of the last IP address in a netblock
	 */
	Long getEndIpAddressAsNumber();

	/**
	 * Gets the ID of the country.
	 * 
	 * @return ID of the country
	 */
	Long getId();

	/**
	 * Gets the name of the country.
	 * 
	 * @return Name of the country
	 */
	String getName();

	/**
	 * Sets the first IP address in a netblock.
	 * 
	 * @param ipAddress
	 *            First IP address in a netblock
	 */
	void setBeginIpAddress(final String ipAddress);

	/**
	 * Sets the first IP address in a netblock as numerical representation.
	 * 
	 * @param ipNum
	 *            Numerical representation of the first IP address in a netblock
	 */
	void setBeginIpAddressAsNumber(final Long ipNum);

	/**
	 * Sets the ISO 3166 Country Code.<br>
	 * <br>
	 * {@link http://en.wikipedia.org/wiki/ISO_3166}
	 * 
	 * @param code
	 *            Country Code
	 */
	void setCode(final String code);

	/**
	 * Sets the last IP address in a netblock.
	 * 
	 * @param ipAddress
	 *            Last IP address in a netblock
	 */
	void setEndIpAddress(final String ipAddress);

	/**
	 * Sets the last IP address in a netblock as numerical representation.
	 * 
	 * @param ipNum
	 *            Numerical representation of the last IP address in a netblock
	 */
	void setEndIpAddressAsNumber(final Long ipNum);

	/**
	 * Sets the name of the country.
	 * 
	 * @param name
	 *            Name of the country
	 */
	void setName(final String name);

}
