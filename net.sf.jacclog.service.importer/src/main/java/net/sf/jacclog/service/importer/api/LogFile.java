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
package net.sf.jacclog.service.importer.api;

import java.io.File;

import net.sf.jacclog.logformat.LogFormat;

public class LogFile {

	private final LogFormat format;

	private final File file;

	public LogFile(final LogFormat format, final File file) {
		if (format == null) {
			throw new IllegalArgumentException("Argument 'format' can not be null.");
		}

		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' can not be null.");
		}

		if (file.isDirectory()) {
			throw new IllegalArgumentException("Argument 'file' can not be a directory.");
		}

		this.file = file;
		this.format = format;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LogFile other = (LogFile) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		return true;
	}

	public File getFile() {
		return file;
	}

	public LogFormat getFormat() {
		return format;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		return result;
	}

}
