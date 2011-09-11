package net.sf.jacclog.csv;

public final class QuotedTextFilter {

	public static final String filter(final String text) {
		if (text == null) {
			throw new IllegalArgumentException("Argument 'text' can not be null.");
		}

		String txt = text;
		// Remove first and last double-quote
		txt = txt.substring(0, txt.length() - 1);
		txt = txt.substring(1);
		return txt.replaceAll("\"\"", "\"");
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private QuotedTextFilter() {
		// This class is not intended to create objects from it.
	}

}
