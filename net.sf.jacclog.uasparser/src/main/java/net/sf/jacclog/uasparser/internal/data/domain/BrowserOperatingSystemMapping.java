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

public final class BrowserOperatingSystemMapping {

	public static class Builder {

		private int browserId;

		private int operatingSystemId;

		public BrowserOperatingSystemMapping build() {
			return new BrowserOperatingSystemMapping(browserId, operatingSystemId);
		}

		/**
		 * Sets the identification number.
		 * 
		 * @param id
		 */
		public Builder setBrowserId(final int browserId) {
			if (browserId < 0) {
				throw new IllegalArgumentException("Argument 'browserId' can not be smaller than 0.");
			}

			this.browserId = browserId;
			return this;
		}

		/**
		 * Sets the identification number via a string.<br>
		 * <br>
		 * An opening and closing Square brackets at the end of a string will be filtered. If the string can not be
		 * parsed as a long, a <code>NumberFormatException</code> will be thrown.
		 * 
		 * @param id
		 */
		public Builder setBrowserId(final String browserId) {
			if (browserId == null) {
				throw new IllegalArgumentException("Argument 'browserId' can not be null.");
			}

			this.setBrowserId(Integer.parseInt(browserId));
			return this;
		}

		/**
		 * Sets the identification number.
		 * 
		 * @param id
		 */
		public Builder setOperatingSystemId(final int operatingSystemId) {
			if (operatingSystemId < 0) {
				throw new IllegalArgumentException("Argument 'operatingSystemId' can not be smaller than 0.");
			}

			this.operatingSystemId = operatingSystemId;
			return this;
		}

		/**
		 * Sets the identification number via a string.<br>
		 * <br>
		 * An opening and closing Square brackets at the end of a string will be filtered. If the string can not be
		 * parsed as a long, a <code>NumberFormatException</code> will be thrown.
		 * 
		 * @param id
		 */
		public Builder setOperatingSystemId(final String operatingSystemId) {
			if (operatingSystemId == null) {
				throw new IllegalArgumentException("Argument 'operatingSystemId' can not be null.");
			}

			this.setOperatingSystemId(Integer.parseInt(operatingSystemId));
			return this;
		}

	}

	private final int browserId;

	private final int operatingSystemId;

	private BrowserOperatingSystemMapping(final int browserId, final int operatingSystemId) {
		if (browserId < 0) {
			throw new IllegalArgumentException("Argument 'browserId' can not be smaller than 0.");
		}

		if (operatingSystemId < 0) {
			throw new IllegalArgumentException("Argument 'operatingSystemId' can not be smaller than 0.");
		}

		this.browserId = browserId;
		this.operatingSystemId = operatingSystemId;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BrowserOperatingSystemMapping other = (BrowserOperatingSystemMapping) obj;
		if (browserId != other.browserId)
			return false;
		if (operatingSystemId != other.operatingSystemId)
			return false;
		return true;
	}

	public int getBrowserId() {
		return browserId;
	}

	public int getOperatingSystemId() {
		return operatingSystemId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + browserId;
		result = prime * result + operatingSystemId;
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BrowserOperatingSystemMapping [browserId=");
		builder.append(browserId);
		builder.append(", operatingSystemId=");
		builder.append(operatingSystemId);
		builder.append("]");
		return builder.toString();
	}

}
