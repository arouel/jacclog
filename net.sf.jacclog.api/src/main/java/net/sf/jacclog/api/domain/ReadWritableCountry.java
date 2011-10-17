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
package net.sf.jacclog.api.domain;

/**
 * Defines a country that can be queried and modified.<br>
 * <br>
 * This interface gives access to change data.<br>
 * <br>
 * The implementation of this interface will be mutable. It may provide more advanced methods than those in this
 * interface.
 * 
 * @author André Rouél
 */
public interface ReadWritableCountry extends ReadableCountry {

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
	 * Sets the name of the country.
	 * 
	 * @param name
	 *            Name of the country
	 */
	void setName(final String name);

}
