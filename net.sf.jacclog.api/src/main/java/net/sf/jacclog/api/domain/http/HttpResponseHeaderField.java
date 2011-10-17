package net.sf.jacclog.api.domain.http;

/**
 * Defines an immutable Hypertext Transfer Protocol (HTTP) response header field consisting of a type and a value.
 * 
 * @author André Rouél
 */
public class HttpResponseHeaderField implements ReadableHttpResponseHeaderField {

	/**
	 * The type of the response header
	 */
	private final ReadableHttpResponseHeader type;

	/**
	 * The value of the response header
	 */
	private final String value;

	/**
	 * Constructs a HTTP response header field.
	 * 
	 * @param type
	 *            the type of the response header
	 * @param value
	 *            the value of the response header
	 * @throws IllegalArgumentException
	 *             if the given arguments are <code>null</code>
	 */
	public HttpResponseHeaderField(final ReadableHttpResponseHeader type, final String value) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		if (value == null) {
			throw new IllegalArgumentException("Argument 'value' can not be null.");
		}

		this.type = type;
		this.value = value;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final HttpResponseHeaderField other = (HttpResponseHeaderField) obj;
		if (type != other.type) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the type of the response header which is never <code>null</code>.
	 * 
	 * @return the type of the response header
	 */
	@Override
	public ReadableHttpResponseHeader getType() {
		return type;
	}

	/**
	 * Gets the value of the response header which is never <code>null</code>.
	 * 
	 * @return the value of the response header
	 */
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("HttpResponseHeaderField [header=");
		builder.append(type);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
