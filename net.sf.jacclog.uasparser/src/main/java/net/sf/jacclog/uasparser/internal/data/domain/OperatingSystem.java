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

import java.util.SortedSet;

import net.sf.jacclog.uasparser.UserAgentInfo;

public final class OperatingSystem {

	public static class Builder {

		private String family = "unknown";

		private String icon = "";

		private int id;

		private String infoUrl = "";

		private String name = "unknown";

		private SortedSet<OperatingSystemPattern> patternSet;

		private String producer = "unknown";

		private String producerUrl = "";

		private String url = "";

		public OperatingSystem build() {
			return new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
		}

		public String getFamily() {
			return family;
		}

		public String getIcon() {
			return icon;
		}

		public int getId() {
			return id;
		}

		public String getInfoUrl() {
			return infoUrl;
		}

		public String getName() {
			return name;
		}

		public SortedSet<OperatingSystemPattern> getPatternSet() {
			return patternSet;
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

		public Builder setFamily(final String family) {
			validateFamily(family);
			this.family = family;
			return this;
		}

		public Builder setIcon(final String icon) {
			validateIcon(icon);

			this.icon = icon;
			return this;
		}

		public Builder setId(final int id) {
			validateId(id);

			this.id = id;
			return this;
		}

		public Builder setId(final String id) {
			validateId(id);

			this.setId(Integer.parseInt(id));
			return this;
		}

		public Builder setInfoUrl(final String infoUrl) {
			validateInfoUrl(infoUrl);

			this.infoUrl = infoUrl;
			return this;
		}

		public Builder setName(final String name) {
			validateName(name);

			this.name = name;
			return this;
		}

		public Builder setPatternSet(final SortedSet<OperatingSystemPattern> patternSet) {
			validatePatternSet(patternSet);
			this.patternSet = patternSet;
			return this;
		}

		public Builder setProducer(final String producer) {
			validateProducer(producer);

			this.producer = producer;
			return this;
		}

		public Builder setProducerUrl(final String producerUrl) {
			validateProducerUrl(producerUrl);

			this.producerUrl = producerUrl;
			return this;
		}

		public Builder setUrl(final String url) {
			validateUrl(url);

			this.url = url;
			return this;
		}

	}

	private static void validateFamily(final String family) {
		if (family == null) {
			throw new IllegalArgumentException("Argument 'family' can not be null.");
		}
	}

	private static void validateIcon(final String icon) {
		if (icon == null) {
			throw new IllegalArgumentException("Argument 'icon' can not be null.");
		}
	}

	private static void validateId(final int id) {
		if (id < 0) {
			throw new IllegalArgumentException("Argument 'id' can not be smaller than 0.");
		}
	}

	private static void validateId(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Argument 'id' can not be null.");
		}
	}

	private static void validateInfoUrl(final String infoUrl) {
		if (infoUrl == null) {
			throw new IllegalArgumentException("Argument 'infoUrl' can not be null.");
		}
	}

	private static void validateName(final String name) {
		if (name == null) {
			throw new IllegalArgumentException("Argument 'name' can not be null.");
		}
	}

	private static void validatePatternSet(final SortedSet<OperatingSystemPattern> patternSet) {
		if (patternSet == null) {
			throw new IllegalArgumentException("Argument 'patternSet' can not be null.");
		}
	}

	private static void validateProducer(final String producer) {
		if (producer == null) {
			throw new IllegalArgumentException("Argument 'producer' can not be null.");
		}
	}

	private static void validateProducerUrl(final String producerUrl) {
		if (producerUrl == null) {
			throw new IllegalArgumentException("Argument 'producerUrl' can not be null.");
		}
	}

	private static void validateUrl(final String url) {
		if (url == null) {
			throw new IllegalArgumentException("Argument 'url' can not be null.");
		}
	}

	private final String family;
	private final String icon;
	private final int id;
	private final String infoUrl;
	private final String name;
	private final SortedSet<OperatingSystemPattern> patternSet;
	private final String producer;
	private final String producerUrl;
	private final String url;

	private OperatingSystem(final String family, final String icon, final int id, final String infoUrl,
			final String name, final SortedSet<OperatingSystemPattern> patternSet, final String producer,
			final String producerUrl, final String url) {

		validateFamily(family);
		validateIcon(icon);
		validateId(id);
		validateInfoUrl(infoUrl);
		validateName(name);
		validatePatternSet(patternSet);
		validateProducer(producerUrl);
		validateProducerUrl(producerUrl);
		validateUrl(url);

		this.family = family;
		this.id = id;
		this.icon = icon;
		this.infoUrl = infoUrl;
		this.name = name;
		this.patternSet = patternSet;
		this.producer = producer;
		this.producerUrl = producerUrl;
		this.url = url;
	}

	public void copyTo(final UserAgentInfo.Builder builder) {
		builder.setOsProducer(producer);
		builder.setOsProducerUrl(producerUrl);
		builder.setOsFamily(family);
		builder.setOsIcon(icon);
		builder.setOsName(name);
		builder.setOsUrl(url);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OperatingSystem other = (OperatingSystem) obj;
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
		if (id != other.id)
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
		if (patternSet == null) {
			if (other.patternSet != null)
				return false;
		} else if (!patternSet.equals(other.patternSet))
			return false;
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

	public int getId() {
		return id;
	}

	public String getInfoUrl() {
		return infoUrl;
	}

	public String getName() {
		return name;
	}

	public SortedSet<OperatingSystemPattern> getPatternSet() {
		return patternSet;
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
		result = prime * result + ((family == null) ? 0 : family.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + id;
		result = prime * result + ((infoUrl == null) ? 0 : infoUrl.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((patternSet == null) ? 0 : patternSet.hashCode());
		result = prime * result + ((producer == null) ? 0 : producer.hashCode());
		result = prime * result + ((producerUrl == null) ? 0 : producerUrl.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("OperatingSystem [family=");
		builder.append(family);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", id=");
		builder.append(id);
		builder.append(", infoUrl=");
		builder.append(infoUrl);
		builder.append(", name=");
		builder.append(name);
		builder.append(", patternSet=");
		builder.append(patternSet);
		builder.append(", producer=");
		builder.append(producer);
		builder.append(", producerUrl=");
		builder.append(producerUrl);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}

}
