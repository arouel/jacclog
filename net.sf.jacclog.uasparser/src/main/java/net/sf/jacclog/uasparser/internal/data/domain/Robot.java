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

public class Robot extends AbstractEntry {

	public static class Builder {

		private String producer = "unknown";
		private String producerUrl = "";
		private String family = "unknown";
		private String icon = "";
		private String id;
		private String infoUrl = "";
		private String name;
		private String url = "";
		private String userAgentString = "";

		public Robot build() {
			return new Robot(family, name, url, producer, producerUrl, icon, infoUrl, id, userAgentString);
		}

		public void setProducer(final String producer) {
			if (producer == null) {
				throw new IllegalArgumentException("Argument 'producer' can not be null.");
			}

			this.producer = producer;
		}

		public void setProducerUrl(final String producerUrl) {
			if (producerUrl == null) {
				throw new IllegalArgumentException("Argument 'producerUrl' can not be null.");
			}

			this.producerUrl = producerUrl;
		}

		public void setFamily(final String family) {
			if (family == null) {
				throw new IllegalArgumentException("Argument 'family' can not be null.");
			}

			this.family = family;
		}

		public void setIcon(final String icon) {
			if (icon == null) {
				throw new IllegalArgumentException("Argument 'icon' can not be null.");
			}

			this.icon = icon;
		}

		public void setId(final String id) {
			if (id == null) {
				throw new IllegalArgumentException("Argument 'id' can not be null.");
			}

			this.id = id;
		}

		public void setInfoUrl(final String infoUrl) {
			if (infoUrl == null) {
				throw new IllegalArgumentException("Argument 'infoUrl' can not be null.");
			}

			this.infoUrl = infoUrl;
		}

		public void setName(final String name) {
			if (name == null) {
				throw new IllegalArgumentException("Argument 'name' can not be null.");
			}

			this.name = name;
		}

		public void setUrl(final String url) {
			if (url == null) {
				throw new IllegalArgumentException("Argument 'url' can not be null.");
			}

			this.url = url;
		}

		public void setUserAgentString(final String userAgentString) {
			if (userAgentString == null) {
				throw new IllegalArgumentException("Argument 'userAgentString' can not be null.");
			}

			this.userAgentString = userAgentString;
		}

	}

	private final String id;
	private final String infoUrl;
	private final String name;
	private final String userAgentString;

	private Robot(final String family, final String name, final String url, final String producer,
			final String producerUrl, final String icon, final String infoUrl, final String id, final String userAgent) {

		super(family, url, producer, producerUrl, icon);
		this.infoUrl = infoUrl;
		this.id = id;
		this.name = name;
		userAgentString = userAgent;
	}

	@Override
	public void copyTo(final UserAgentInfo.Builder builder) {
		super.copyTo(builder);
		builder.setInfoUrl(infoUrl);
		builder.setName(name);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Robot other = (Robot) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (infoUrl == null) {
			if (other.infoUrl != null)
				return false;
		} else if (!infoUrl.equals(other.infoUrl))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (userAgentString == null) {
			if (other.userAgentString != null)
				return false;
		} else if (!userAgentString.equals(other.userAgentString))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public String getInfoUrl() {
		return infoUrl;
	}

	public String getName() {
		return name;
	}

	public String getUserAgentString() {
		return userAgentString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((infoUrl == null) ? 0 : infoUrl.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((userAgentString == null) ? 0 : userAgentString.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Robot [id=");
		builder.append(id);
		builder.append(", infoUrl=");
		builder.append(infoUrl);
		builder.append(", name=");
		builder.append(name);
		builder.append(", userAgentString=");
		builder.append(userAgentString);
		builder.append(", getProducer()=");
		builder.append(getProducer());
		builder.append(", getProducerUrl()=");
		builder.append(getProducerUrl());
		builder.append(", getFamily()=");
		builder.append(getFamily());
		builder.append(", getIcon()=");
		builder.append(getIcon());
		builder.append(", getUrl()=");
		builder.append(getUrl());
		builder.append("]");
		return builder.toString();
	}

}
