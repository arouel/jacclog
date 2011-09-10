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

public final class Browser {

	public static class Builder {

		private String family = "unknown";
		private String icon = "";
		private int id;
		private String infoUrl = "";
		private OperatingSystem operatingSystem;
		private SortedSet<BrowserPattern> patternSet;
		private String producer = "unknown";
		private String producerUrl = "";
		private BrowserType type;
		private int typeId;
		private String url = "";

		public Browser build() {
			return new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
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

		public OperatingSystem getOperatingSystem() {
			return operatingSystem;
		}

		public SortedSet<BrowserPattern> getPatternSet() {
			return patternSet;
		}

		public String getProducer() {
			return producer;
		}

		public String getProducerUrl() {
			return producerUrl;
		}

		public BrowserType getType() {
			return type;
		}

		public int getTypeId() {
			return typeId;
		}

		public String getUrl() {
			return url;
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

		public Builder setId(final int id) {
			if (id < 0) {
				throw new IllegalArgumentException("Argument 'icon' can not be smaller than 0.");
			}

			this.id = id;
			return this;
		}

		public Builder setId(final String id) {
			if (id == null) {
				throw new IllegalArgumentException("Argument 'icon' can not be null.");
			}

			this.setId(Integer.parseInt(id));
			return this;
		}

		public Builder setInfoUrl(final String infoUrl) {
			if (infoUrl == null) {
				throw new IllegalArgumentException("Argument 'infoUrl' can not be null.");
			}

			this.infoUrl = infoUrl;
			return this;
		}

		public Builder setOperatingSystem(final OperatingSystem operatingSystem) {
			if (operatingSystem == null) {
				throw new IllegalArgumentException("Argument 'operatingSystem' can not be null.");
			}

			this.operatingSystem = operatingSystem;
			return this;
		}

		public void setPatternSet(final SortedSet<BrowserPattern> patternSet) {
			if (patternSet == null) {
				throw new IllegalArgumentException("Argument 'patternSet' can not be null.");
			}

			this.patternSet = patternSet;
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

		/**
		 * Sets the browser type.<br>
		 * <br>
		 * If the given type id is <code>null</code>, a <code>IllegalArgumentException</code> will be thrown.
		 * 
		 * @param type
		 *            A browser type
		 */
		public Builder setType(final BrowserType type) {
			if (type == null) {
				throw new IllegalArgumentException("Argument 'type' can not be null.");
			}

			this.type = type;
			return this;
		}

		public Builder setTypeId(final int typeId) {
			if (typeId < 0) {
				throw new IllegalArgumentException("Argument 'typeId' can not be smaller than 0.");
			}

			this.typeId = typeId;
			return this;
		}

		public Builder setTypeId(final String typeId) {
			if (typeId == null) {
				throw new IllegalArgumentException("Argument 'typeId' can not be null.");
			}

			setTypeId(Integer.parseInt(typeId));
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

	private final String family;
	private final String icon;
	private final int id;
	private final String infoUrl;
	private final OperatingSystem operatingSystem;
	private final SortedSet<BrowserPattern> patternSet;
	private final String producer;
	private final String producerUrl;
	private final BrowserType type;
	private final String url;

	private Browser(final int id, final BrowserType type, final String family, final String url, final String producer,
			final String producerUrl, final String icon, final String infoUrl,
			final SortedSet<BrowserPattern> patternSet, final OperatingSystem operatingSystem) {

		this.family = family;
		this.icon = icon;
		this.id = id;
		this.infoUrl = infoUrl;
		this.operatingSystem = operatingSystem;
		this.patternSet = patternSet;
		this.producer = producer;
		this.producerUrl = producerUrl;
		this.type = type;
		this.url = url;
	}

	/**
	 * Copy values from itself to a <code>UserAgentInfo.Builder</code>.
	 */
	public void copyTo(final UserAgentInfo.Builder builder) {
		builder.setFamily(family);
		builder.setIcon(icon);
		builder.setInfoUrl(getInfoUrl());
		builder.setProducer(producer);
		builder.setProducerUrl(producerUrl);
		builder.setType(type.getName());
		builder.setUrl(url);
		if (operatingSystem != null) {
			operatingSystem.copyTo(builder);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Browser other = (Browser) obj;
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
		if (operatingSystem == null) {
			if (other.operatingSystem != null)
				return false;
		} else if (!operatingSystem.equals(other.operatingSystem))
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

	public OperatingSystem getOperatingSystem() {
		return operatingSystem;
	}

	public SortedSet<BrowserPattern> getPatternSet() {
		return patternSet;
	}

	public String getProducer() {
		return producer;
	}

	public String getProducerUrl() {
		return producerUrl;
	}

	public BrowserType getType() {
		return type;
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
		result = prime * result + ((operatingSystem == null) ? 0 : operatingSystem.hashCode());
		result = prime * result + ((patternSet == null) ? 0 : patternSet.hashCode());
		result = prime * result + ((producer == null) ? 0 : producer.hashCode());
		result = prime * result + ((producerUrl == null) ? 0 : producerUrl.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Browser [id=");
		builder.append(id);
		builder.append(", infoUrl=");
		builder.append(infoUrl);
		builder.append(", operatingSystem=");
		builder.append(operatingSystem);
		builder.append(", patternSet=");
		builder.append(patternSet);
		builder.append(", type=");
		builder.append(type);
		builder.append(", producer=");
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
