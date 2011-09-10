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
package net.sf.jacclog.service.importer.commands.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.jacclog.logformat.LogFormat;
import net.sf.jacclog.service.importer.api.LogFileImporter;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command to import log files.
 * 
 * <strong>Example:</strong><br>
 * <code>import -f "%h %l %u %t %r %>s %b %{Referer}i %{User-agent}i %0 %0 %0" /var/httpd/access_log</code>
 * 
 * @author André Rouél
 */
@Command(scope = "jacclog", name = "import", description = "Imports log files or directories")
@SuppressWarnings("PMD.SystemPrintln")
public class ImportShellCommand extends OsgiCommandSupport {

	private static final Logger LOG = LoggerFactory.getLogger(ImportShellCommand.class);

	/**
	 * List of files to be imported
	 */
	private final List<File> files = new ArrayList<File>();

	@Option(name = "-f", aliases = "--format", description = "The log format of the files to be imported", required = true, multiValued = false, valueToShowInHelp = "COMMON, COMMON_WITH_VHOST, COMBINED")
	private final String format = null;

	private LogFileImporter importer;

	@Argument(index = 0, name = "files", description = "The files to be imported", required = true, multiValued = true)
	private final String[] paths = null;

	@Override
	protected Object doExecute() throws Exception {
		for (int i = 0; i < paths.length; i++) {
			final String path = paths[i];
			log.info("Importing path: " + path);
		}
		importFiles(paths);
		System.out.println("Importing " + files.size() + " file(s) with format '" + format + "'");

		startImporter();

		return null;
	}

	public LogFileImporter getImporter() {
		return importer;
	}

	/**
	 * Returns true if this queue contains the specified path.
	 * 
	 * @param file
	 * @return <code>true</code> if file exists in queue, otherwise <code>false</code>
	 */
	private boolean hasFile(final File file) {
		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' must be not null.");
		}

		if (!file.exists() || !file.isFile()) {
			throw new IllegalArgumentException("Argument 'file' doesn't exist or isn't a file.");
		}

		boolean result = false;
		for (final File f : files) {
			try {
				if (f.getCanonicalPath().equals(file.getCanonicalPath())) {
					result = true;
					break;
				}
			} catch (final IOException e) {
				throw new IllegalStateException(e.getLocalizedMessage(), e);
			}
		}

		return result;
	}

	/**
	 * Imports a list of files.
	 * 
	 * @param files
	 *            a list of <code>File</code> objects
	 */
	private void importFiles(final List<File> files) {
		for (final File file : files) {
			if (file != null && file.exists()) {
				if (file.isFile()) {
					if (hasFile(file)) {
						log.info("The path '" + file.getPath() + "' is already in the list and will be ignored.");
					} else {
						this.files.add(file);
					}
				} else if (file.isDirectory()) {
					importFiles(Arrays.asList(file.listFiles()));
				}
			} else {
				log.warn("Path '" + file.getPath() + "' doesn't exist or isn't a file.");
			}
		}
	}

	/**
	 * Imports a list of files.
	 * 
	 * @param paths
	 *            an array of paths
	 */
	private void importFiles(final String[] paths) {
		final List<File> files = new ArrayList<File>(paths.length);
		for (int i = 0; i < paths.length; i++) {
			if (paths[i] != null && !paths[i].trim().isEmpty()) {
				final File file = FileNameTranslator.translate(paths[i]);
				if (file != null) {
					files.add(file);
				}
			}
		}
		importFiles(files);
	}

	public void setImporter(final LogFileImporter importer) {
		this.importer = importer;
	}

	/**
	 * Starts the log file importer if it is available.
	 */
	private void startImporter() {
		if (importer != null) {
			LOG.debug("Log file importer is available.");
			importer.importFiles(LogFormat.parse(format), files);
		} else {
			log.warn("No log file importer is available.");
		}
	}

}
