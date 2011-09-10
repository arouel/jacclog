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
package net.sf.jacclog.uasparser;

/**
 * User Agent is an immutable entity that represents the informations about web-based client applications like Web
 * browsers, search engines or crawlers (spiders) as well as mobile phones, screen readers and braille browsers.
 * 
 * @author André Rouél
 */
public final class UserAgentInfo {

	public static final class Builder {

		private String producer = "";
		private String producerUrl = "";
		private String family = "";
		private String icon = "";
		private String infoUrl = "";
		private String name = "";
		private String osProducer = "";
		private String osProducerUrl = "";
		private String osFamily = "";
		private String osIcon = "";
		private String osName = "";
		private String osUrl = "";
		private String type = "";
		private String url = "";

		public UserAgentInfo build() {
			return new UserAgentInfo(osProducer, osProducerUrl, osFamily, osIcon, osName, osUrl, type, producer,
					producerUrl, family, icon, infoUrl, name, url);
		}

		public String getProducer() {
			return producer;
		}

		public String getProducerUrl() {
			return producerUrl;
		}

		public String getFamily() {
			return family;
		}

		public String getIcon() {
			return icon;
		}

		public String getInfoUrl() {
			return infoUrl;
		}

		public String getName() {
			return name;
		}

		public String getOsProducer() {
			return osProducer;
		}

		public String getOsProducerUrl() {
			return osProducerUrl;
		}

		public String getOsFamily() {
			return osFamily;
		}

		public String getOsIcon() {
			return osIcon;
		}

		public String getOsName() {
			return osName;
		}

		public String getOsUrl() {
			return osUrl;
		}

		public String getType() {
			return type;
		}

		public String getUrl() {
			return url;
		}

		public Builder setProducer(final String producer) {
			if (producer == null) {
				throw new IllegalArgumentException("Argument 'producer' can not be null.");
			}
			this.producer = producer;
			return this;
		}

		public Builder setProducerUrl(final String producerUrl) {
			if (producerUrl == null) {
				throw new IllegalArgumentException("Argument 'producerUrl' can not be null.");
			}
			this.producerUrl = producerUrl;
			return this;
		}

		public Builder setFamily(final String family) {
			if (family == null) {
				throw new IllegalArgumentException("Argument 'family' can not be null.");
			}
			this.family = family;
			return this;
		}

		public Builder setIcon(final String icon) {
			if (icon == null) {
				throw new IllegalArgumentException("Argument 'icon' can not be null.");
			}
			this.icon = icon;
			return this;
		}

		public Builder setInfoUrl(final String infoUrl) {
			if (infoUrl == null) {
				throw new IllegalArgumentException("Argument 'infoUrl' can not be null.");
			}
			this.infoUrl = infoUrl;
			return this;
		}

		public Builder setName(final String name) {
			if (name == null) {
				throw new IllegalArgumentException("Argument 'name' can not be null.");
			}
			this.name = name;
			return this;
		}

		public Builder setOsProducer(final String osProducer) {
			if (osProducer == null) {
				throw new IllegalArgumentException("Argument 'osProducer' can not be null.");
			}
			this.osProducer = osProducer;
			return this;
		}

		public Builder setOsProducerUrl(final String osProducerUrl) {
			if (osProducerUrl == null) {
				throw new IllegalArgumentException("Argument 'osProducerUrl' can not be null.");
			}
			this.osProducerUrl = osProducerUrl;
			return this;
		}

		public Builder setOsFamily(final String osFamily) {
			if (osFamily == null) {
				throw new IllegalArgumentException("Argument 'osFamily' can not be null.");
			}
			this.osFamily = osFamily;
			return this;
		}

		public Builder setOsIcon(final String osIcon) {
			if (osIcon == null) {
				throw new IllegalArgumentException("Argument 'osIcon' can not be null.");
			}
			this.osIcon = osIcon;
			return this;
		}

		public Builder setOsName(final String osName) {
			if (osName == null) {
				throw new IllegalArgumentException("Argument 'osName' can not be null.");
			}
			this.osName = osName;
			return this;
		}

		public Builder setOsUrl(final String osUrl) {
			// TODO rename it OsInfoUrl
			if (osUrl == null) {
				throw new IllegalArgumentException("Argument 'osUrl' can not be null.");
			}
			this.osUrl = osUrl;
			return this;
		}

		public Builder setType(final String type) {
			if (type == null) {
				throw new IllegalArgumentException("Argument 'type' can not be null.");
			}
			this.type = type;
			return this;
		}

		public Builder setUrl(final String url) {
			if (url == null) {
				throw new IllegalArgumentException("Argument 'url' can not be null.");
			}
			this.url = url;
			return this;
		}

	}

	private final String producer;
	private final String producerUrl;
	private final String family;
	private final String icon;
	private final String infoUrl;
	private final String name;
	private final String osProducer;
	private final String osProducerUrl;
	private final String osFamily;
	private final String osIcon;
	private final String osName;
	private final String osUrl;
	private final String type;
	private final String url;

	private UserAgentInfo(final String osProducer, final String osProducerUrl, final String osFamily,
			final String osIcon, final String osName, final String osUrl, final String type, final String producer,
			final String producerUrl, final String family, final String icon, final String infoUrl, final String name,
			final String url) {

		this.osProducer = osProducer;
		this.osProducerUrl = osProducerUrl;
		this.osFamily = osFamily;
		this.osIcon = osIcon;
		this.osName = osName;
		this.osUrl = osUrl;
		this.type = type;
		this.producer = producer;
		this.producerUrl = producerUrl;
		this.family = family;
		this.icon = icon;
		this.infoUrl = infoUrl;
		this.name = name;
		this.url = url;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserAgentInfo other = (UserAgentInfo) obj;
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
		if (osProducer == null) {
			if (other.osProducer != null)
				return false;
		} else if (!osProducer.equals(other.osProducer))
			return false;
		if (osProducerUrl == null) {
			if (other.osProducerUrl != null)
				return false;
		} else if (!osProducerUrl.equals(other.osProducerUrl))
			return false;
		if (osFamily == null) {
			if (other.osFamily != null)
				return false;
		} else if (!osFamily.equals(other.osFamily))
			return false;
		if (osIcon == null) {
			if (other.osIcon != null)
				return false;
		} else if (!osIcon.equals(other.osIcon))
			return false;
		if (osName == null) {
			if (other.osName != null)
				return false;
		} else if (!osName.equals(other.osName))
			return false;
		if (osUrl == null) {
			if (other.osUrl != null)
				return false;
		} else if (!osUrl.equals(other.osUrl))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public String getProducer() {
		return producer;
	}

	public String getProducerUrl() {
		return producerUrl;
	}

	public String getFamily() {
		return family;
	}

	public String getIcon() {
		return icon;
	}

	public String getInfoUrl() {
		return infoUrl;
	}

	public String getName() {
		return name;
	}

	public String getOsProducer() {
		return osProducer;
	}

	public String getOsProducerUrl() {
		return osProducerUrl;
	}

	public String getOsFamily() {
		return osFamily;
	}

	public String getOsIcon() {
		return osIcon;
	}

	public String getOsName() {
		return osName;
	}

	public String getOsUrl() {
		return osUrl;
	}

	public String getType() {
		return type;
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
		result = prime * result + ((infoUrl == null) ? 0 : infoUrl.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((osProducer == null) ? 0 : osProducer.hashCode());
		result = prime * result + ((osProducerUrl == null) ? 0 : osProducerUrl.hashCode());
		result = prime * result + ((osFamily == null) ? 0 : osFamily.hashCode());
		result = prime * result + ((osIcon == null) ? 0 : osIcon.hashCode());
		result = prime * result + ((osName == null) ? 0 : osName.hashCode());
		result = prime * result + ((osUrl == null) ? 0 : osUrl.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "UserAgentInfo [producer=" + producer + ", producerUrl=" + producerUrl + ", family=" + family
				+ ", icon=" + icon + ", infoUrl=" + infoUrl + ", name=" + name + ", osProducer=" + osProducer
				+ ", osProducerUrl=" + osProducerUrl + ", osFamily=" + osFamily + ", osIcon=" + osIcon + ", osName="
				+ osName + ", osUrl=" + osUrl + ", type=" + type + ", url=" + url + "]";
	}

}
