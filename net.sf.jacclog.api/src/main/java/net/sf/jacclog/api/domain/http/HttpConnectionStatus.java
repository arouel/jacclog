package net.sf.jacclog.api.domain.http;

/**
 * Defines the connection status when a Hypertext Transfer Protocol (HTTP) response is completed.
 * <ul>
 * <li>X = connection aborted before the response completed.</li>
 * <li>+ = connection may be kept alive after the response is sent.</li>
 * <li>- = connection will be closed after the response is sent.</li>
 * </ul>
 * 
 * @author André Rouél
 */
public enum HttpConnectionStatus {

	/**
	 * Connection aborted before the response completed
	 */
	ABORTED_BEFORE('X'),

	/**
	 * Connection may be kept alive after the response is sent
	 */
	KEPT_ALIVE('+'),

	/**
	 * Connection will be closed after the response is sent
	 */
	WILL_BE_CLOSED('-'),

	/**
	 * Unknown connection status<br>
	 * <br>
	 * This field is not a valid connection status. It represents an invalid or unknown state.<br>
	 * <br>
	 * If the connection status has not been logged, this field should be used.
	 */
	UNKNOWN('0');

	/**
	 * Evaluates the given character against the defined connection status.<br>
	 * <br>
	 * If the input value not matches against defined fields the <code>HttpStatus.UNKNOWN</code> will be returned.
	 * 
	 * @param statusCode
	 *            the numeric value of a connection status
	 * @return the matching connection status code field or <code>UNKNOWN</code>
	 */
	public static HttpConnectionStatus evaluate(final char status) {
		HttpConnectionStatus result = HttpConnectionStatus.UNKNOWN;
		for (final HttpConnectionStatus s : values()) {
			if (s.value() == status) {
				result = s;
				break;
			}
		}
		return result;
	}

	/**
	 * The character of the connection status
	 */
	private char status;

	private HttpConnectionStatus(final char status) {
		this.status = status;
	}

	/**
	 * Gets the character of the connection status.
	 * 
	 * @return The character of the connection status
	 */
	public char value() {
		return status;
	}

}
