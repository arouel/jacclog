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

	/**
	 * Gets the name of the unknown (probably non-standardized) request header which is never <code>null</code>.
	 * 
	 * @return the name of the request header
	 */
	@Override
	public String getName() {
		return name;
	}

}
