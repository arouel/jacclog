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

import java.util.regex.Pattern;

import net.sf.jacclog.uasparser.internal.data.OrderedPattern;
import net.sf.jacclog.uasparser.internal.util.RegularExpressionConverter;

public class OperatingSystemPattern implements OrderedPattern {

	public static class Builder {

		private int id;

		private Pattern pattern;

		private int order;

		public OperatingSystemPattern build() {
			return new OperatingSystemPattern(id, pattern, order);
		}

		public Builder setId(final int id) {
			if (id < 0) {
				throw new IllegalArgumentException("Argument 'id' can not be null.");
			}

			this.id = id;
			return this;
		}

		public Builder setId(final String id) {
			if (id == null) {
				throw new IllegalArgumentException("Argument 'id' can not be null.");
			}

			this.setId(Integer.parseInt(id));
			return this;
		}

		public Builder setOrder(final int order) {
			if (order < 0) {
				throw new IllegalArgumentException("Argument 'order' can not be null.");
			}

			this.order = order;
			return this;
		}

		public Builder setOrder(final String order) {
			if (order == null) {
				throw new IllegalArgumentException("Argument 'order' can not be null.");
			}

			this.setOrder(Integer.parseInt(order));
			return this;
		}

		public Builder setPattern(final Pattern pattern) {
			if (pattern == null) {
				throw new IllegalArgumentException("Argument 'pattern' can not be null.");
			}
			this.pattern = pattern;
			return this;
		}

		public Builder setPattern(final String regex) {
			if (regex == null) {
				throw new IllegalArgumentException("Argument 'regex' can not be null.");
			}
			this.setPattern(RegularExpressionConverter.convertPerlRegexToPattern(regex));
			return this;
		}

	}

	private final int id;

	private final Pattern pattern;

	private final int order;

	public OperatingSystemPattern(final int id, final Pattern pattern, final int order) {
		if (id < 0) {
			throw new IllegalArgumentException("Argument 'id' can not be null.");
		}

		if (pattern == null) {
			throw new IllegalArgumentException("Argument 'pattern' can not be null.");
		}

		if (order < 0) {
			throw new IllegalArgumentException("Argument 'order' can not be smaller than 0.");
		}

		this.id = id;
		this.pattern = pattern;
		this.order = order;
	}

	@Override
	public int compareTo(final OrderedPattern pattern) {
		int result = 0;
		if (pattern == null) {
			result = 1;
		} else if (pattern != null) {
			if (getOrder() < pattern.getOrder()) {
				result = 1;
			} else if (getOrder() > pattern.getOrder()) {
				result = -1;
			}
		}
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OperatingSystemPattern other = (OperatingSystemPattern) obj;
		if (id != other.id)
			return false;
		if (order != other.order)
			return false;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public Pattern getPattern() {
		return pattern;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + order;
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("OperatingSystemPattern [id=");
		builder.append(id);
		builder.append(", pattern=");
		builder.append(pattern);
		builder.append(", order=");
		builder.append(order);
		builder.append("]");
		return builder.toString();
	}

}
