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
 * Defines an Internet Protocol address range (called netblock) that can be queried and modified.<br>
 * <br>
 * This interface gives access to change data.<br>
 * <br>
 * The implementation of this interface will be mutable. It may provide more advanced methods than those in this
 * interface.
 * 
 * @author André Rouél
 */
public interface ReadWritableIpAddressRange extends ReadableIpAddressRange {

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

}
