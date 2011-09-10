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
import java.util.List;

import net.sf.jacclog.logformat.LogFormat;

public interface LogFileImporter {

	/**
	 * Returns statistical information about the recently finished imports.
	 * 
	 * @return statistic
	 */
	LogFileImporterStatistic getStatistic();

	/**
	 * Imports a list of log files or directories with files into a repository.
	 * 
	 * @param files
	 *            list of logs
	 */
	void importFiles(final LogFormat format, final List<File> files);

	/**
	 * Imports a list of log files or directories with files (recursively) into a repository.
	 * 
	 * @param files
	 * @param recursive
	 */
	void importFiles(final LogFormat format, final List<File> files, final boolean recursive);

}
