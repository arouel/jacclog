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
 * Defines the root interface of a country.<br>
 * <br>
 * The implementation of this interface may be mutable or immutable. This interface only gives access to retrieve data,
 * never to change it.
 * 
 * @author André Rouél
 */
public interface ReadableCountry {

	/**
	 * Gets the ISO 3166 Country Code.<br>
	 * <br>
	 * {@link http://en.wikipedia.org/wiki/ISO_3166}
	 * 
	 * @return Country Code
	 */
	String getCode();

	/**
	 * Gets the name of the country.
	 * 
	 * @return Name of the country
	 */
	String getName();

}
