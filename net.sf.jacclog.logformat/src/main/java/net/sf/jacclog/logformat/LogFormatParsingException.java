package net.sf.jacclog.logformat;

/**
 * This exception indicates problems while parsing a log format.
 * 
 * @author André Rouél
 */
public class LogFormatParsingException extends RuntimeException {

	private static final long serialVersionUID = 8617238355772355038L;

	public LogFormatParsingException() {
		super();
	}

	public LogFormatParsingException(final String message) {
		super(message);
	}

	public LogFormatParsingException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public LogFormatParsingException(final Throwable cause) {
		super(cause);
	}

}
