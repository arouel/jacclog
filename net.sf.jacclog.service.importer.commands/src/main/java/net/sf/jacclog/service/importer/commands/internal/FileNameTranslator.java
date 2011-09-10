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

/**
 * A file name translator takes the path and issues a <code>java.io.File</code>.
 * 
 * <p>
 * Translates a file system path into a <code>java.io.File</code> if it exists. if they do not exist, then
 * <code>null</code> is returned.
 * </p>
 * 
 * @author André Rouél
 */
public class FileNameTranslator {

	public static File translate(final String path) {
		String pathname = (path == null) ? "" : path;

		// special character: ~ maps to the user's home directory
		if (pathname.startsWith("~" + File.separator)) {
			pathname = System.getProperty("user.home") + pathname.substring(1);
		} else if (pathname.startsWith("~")) {
			pathname = new File(System.getProperty("user.home")).getParentFile().getAbsolutePath();
		} else if (!(pathname.startsWith(File.separator))) {
			pathname = new File("").getAbsolutePath() + File.separator + pathname;
		}

		final File f = new File(pathname);

		return (f.isFile() || f.isDirectory()) ? f : null;
	}

}
