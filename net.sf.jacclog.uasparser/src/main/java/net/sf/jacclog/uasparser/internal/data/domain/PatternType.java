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

public class PatternType {

	private final Long id;
	private final Pattern pattern;
	private final int position;

	public PatternType(final Long id, final Pattern pattern, final int position) {
		if (id == null) {
			throw new IllegalArgumentException("Argument 'id' can not be null.");
		}

		if (pattern == null) {
			throw new IllegalArgumentException("Argument 'pattern' can not be null.");
		}

		if (position < 0) {
			throw new IllegalArgumentException("Argument 'position' can not be smaller than 0.");
		}

		this.id = id;
		this.pattern = pattern;
		this.position = position;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PatternType other = (PatternType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		if (position != other.position)
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public int getPosition() {
		return position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		result = prime * result + position;
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PatternType [id=");
		builder.append(id);
		builder.append(", pattern=");
		builder.append(pattern);
		builder.append(", position=");
		builder.append(position);
		builder.append("]");
		return builder.toString();
	}

}
