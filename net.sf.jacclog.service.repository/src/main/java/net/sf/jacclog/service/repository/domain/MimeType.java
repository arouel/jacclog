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
package net.sf.jacclog.service.repository.domain;

public enum MimeType {

	/**
	 * text/plain
	 */
	TEXT_PLAIN("text/plain", "txt"),

	/**
	 * text/xml
	 */
	TEXT_XML("text/xml", "xml"),

	/**
	 * text/html
	 */
	TEXT_HTML("text/html", "html"),

	/**
	 * application/xml
	 */
	APPLICATION_XML("application/xml", "xml"),

	/**
	 * application/pdf
	 */
	APPLICATION_PDF("application/pdf", "pdf"),

	/**
	 * multipart/mixed
	 */
	MULTIPART_MIXED("multipart/mixed", "???"),

	/**
	 * multipart
	 */
	MULTIPART("multipart", "???"),

	/**
	 * unknown
	 */
	UNKNOWN(null, "txt");

	private final String type;
	private final String suffix;

	private MimeType(final String type, final String suffix) {
		this.type = type;
		this.suffix = suffix;
	}

	public String suffix() {
		return suffix;
	}

	public String type() {
		return type;
	}

	/**
	 * Finds the usual MIME type by an (file) extension out.
	 * 
	 * @param extension
	 * @return the adopted MIME type
	 */
	public static MimeType detectByExtension(final String extension) {
		// TODO implementing
		return MimeType.TEXT_PLAIN;
	}

}
