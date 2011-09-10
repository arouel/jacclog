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
package net.sf.jacclog.logformat.field;

public abstract class AbstractParameterizedLogFormatField<P> implements ParameterizedField<P> {

	private final FieldType type;
	private final P parameter;

	protected AbstractParameterizedLogFormatField(final FieldType type, final P parameter) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		if (!type.isParameterized()) {
			throw new IllegalArgumentException("Argument 'type' can not be non-parameterized field.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Argument 'parameter' can not be null.");
		}

		this.type = type;
		this.parameter = parameter;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractParameterizedLogFormatField<?> other = (AbstractParameterizedLogFormatField<?>) obj;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public P getParameter() {
		return parameter;
	}

	@Override
	public FieldType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

}
