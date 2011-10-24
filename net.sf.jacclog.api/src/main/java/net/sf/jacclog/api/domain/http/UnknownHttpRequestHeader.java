package net.sf.jacclog.api.domain.http;

/**
 * Represents an immutable unknown (probably non-standardized) Hypertext Transfer Protocol request header.
 * 
 * @author André Rouél
 */
public class UnknownHttpRequestHeader implements ReadableHttpRequestHeader {

	/**
	 * The name of the unknown HTTP request header
	 */
	private final String name;

	/**
	 * Constructs an unknown HTTP request header.
	 * 
	 * @param name
	 *            the name of the unknown request header
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public UnknownHttpRequestHeader(final String name) {
		if (name == null) {
			throw new IllegalArgumentException("Argument 'name' can not be null.");
		}

		this.name = name;
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
		final UnknownHttpRequestHeader other = (UnknownHttpRequestHeader) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the name of the unknown (probably non-standardized) request header which is never <code>null</code>.
	 * 
	 * @return the name of the request header
	 */
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UnknownHttpRequestHeader [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
