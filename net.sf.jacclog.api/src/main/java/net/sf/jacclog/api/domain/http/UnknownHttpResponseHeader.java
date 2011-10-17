package net.sf.jacclog.api.domain.http;

/**
 * Represents an immutable unknown (probably non-standardized) Hypertext Transfer Protocol response header.
 * 
 * @author André Rouél
 */
public class UnknownHttpResponseHeader implements ReadableHttpResponseHeader {

	/**
	 * The name of the unknown HTTP response header
	 */
	private final String name;

	/**
	 * Constructs an unknown HTTP response header.
	 * 
	 * @param name
	 *            the name of the unknown response header
	 * @throws IllegalArgumentException
	 *             if the given argument is <code>null</code>
	 */
	public UnknownHttpResponseHeader(final String name) {
		if (name == null) {
			throw new IllegalArgumentException("Argument 'name' can not be null.");
		}

		this.name = name;
	}

	/**
	 * Gets the name of the unknown (probably non-standardized) response header which is never <code>null</code>.
	 * 
	 * @return the name of the response header
	 */
	@Override
	public String getName() {
		return name;
	}

}
