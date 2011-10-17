package net.sf.jacclog.api.domain.http;

/**
 * Defines an immutable Hypertext Transfer Protocol (HTTP) request header field consisting of a type and value.
 * 
 * @author André Rouél
 */
public class HttpRequestHeaderField implements ReadableHttpRequestHeaderField {

	/**
	 * The type of the request header
	 */
	private final ReadableHttpRequestHeader type;

	/**
	 * The value of the request header
	 */
	private final String value;

	/**
	 * Constructs a HTTP request header field.
	 * 
	 * @param type
	 *            the type of the request header
	 * @param value
	 *            the value of the request header
	 * @throws IllegalArgumentException
	 *             if the given arguments are <code>null</code>
	 */
	public HttpRequestHeaderField(final ReadableHttpRequestHeader type, final String value) {
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
		final HttpRequestHeaderField other = (HttpRequestHeaderField) obj;
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
	 * Gets the type of the request header which is never <code>null</code>.
	 * 
	 * @return the type of the request header
	 */
	public ReadableHttpRequestHeader getType() {
		return type;
	}

	/**
	 * Gets the value of the request header which is never <code>null</code>.
	 * 
	 * @return the value of the request header
	 */
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
		builder.append("HttpRequestHeaderField [header=");
		builder.append(type);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
