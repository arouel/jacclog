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

public abstract class AbstractCountry implements Country {

	private String beginIpAddress;

	private Long beginIpAddressAsNumber;

	private String code;

	private String endIpAddress;

	private Long endIpAddressAsNumber;

	private String name;

	@Override
	public String getBeginIpAddress() {
		return beginIpAddress;
	}

	@Override
	public Long getBeginIpAddressAsNumber() {
		return beginIpAddressAsNumber;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getEndIpAddress() {
		return endIpAddress;
	}

	@Override
	public Long getEndIpAddressAsNumber() {
		return endIpAddressAsNumber;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setBeginIpAddress(final String beginIpAddress) {
		this.beginIpAddress = beginIpAddress;
	}

	@Override
	public void setBeginIpAddressAsNumber(final Long beginIpAddressAsNumber) {
		this.beginIpAddressAsNumber = beginIpAddressAsNumber;
	}

	@Override
	public void setCode(final String code) {
		this.code = code;
	}

	@Override
	public void setEndIpAddress(final String endIpAddress) {
		this.endIpAddress = endIpAddress;
	}

	@Override
	public void setEndIpAddressAsNumber(final Long endIpAddressAsNumber) {
		this.endIpAddressAsNumber = endIpAddressAsNumber;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

}
