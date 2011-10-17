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
 * Defines the root interface of a Internet Protocol address range (called netblock).<br>
 * <br>
 * The implementation of this interface may be mutable or immutable. This interface only gives access to retrieve data,
 * never to change it.
 * 
 * @author André Rouél
 */
public interface ReadableIpAddressRange {

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

}
