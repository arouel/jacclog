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
package net.sf.jacclog.uasparser.internal.data.domain;

import net.sf.jacclog.uasparser.UserAgentInfo;

abstract class AbstractEntry {

	private final String producer;
	private final String producerUrl;
	private final String family;
	private final String icon;
	private final String url;

	protected AbstractEntry(final String family, final String url, final String producer, final String producerUrl,
			final String icon) {

		this.family = family;
		this.url = url;
		this.producer = producer;
		this.producerUrl = producerUrl;
		this.icon = icon;
	}

	public void copyTo(final UserAgentInfo.Builder builder) {
		builder.setProducer(producer);
		builder.setProducerUrl(producerUrl);
		builder.setFamily(family);
		builder.setIcon(icon);
		builder.setUrl(url);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractEntry other = (AbstractEntry) obj;
		if (producer == null) {
			if (other.producer != null)
				return false;
		} else if (!producer.equals(other.producer))
			return false;
		if (producerUrl == null) {
			if (other.producerUrl != null)
				return false;
		} else if (!producerUrl.equals(other.producerUrl))
			return false;
		if (family == null) {
			if (other.family != null)
				return false;
		} else if (!family.equals(other.family))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public String getFamily() {
		return family;
	}

	public String getIcon() {
		return icon;
	}

	public String getProducer() {
		return producer;
	}

	public String getProducerUrl() {
		return producerUrl;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((producer == null) ? 0 : producer.hashCode());
		result = prime * result + ((producerUrl == null) ? 0 : producerUrl.hashCode());
		result = prime * result + ((family == null) ? 0 : family.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("AbstractEntry [producer=");
		builder.append(producer);
		builder.append(", producerUrl=");
		builder.append(producerUrl);
		builder.append(", family=");
		builder.append(family);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}

}
