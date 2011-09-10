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

import java.util.List;

import org.apache.karaf.shell.console.Completer;

/**
 * A file name completer takes the buffer and issues a list of potential completions.
 * 
 * <p>
 * This completer tries to behave as similar as possible to bash's file name completion (using GNU readline) with the
 * following exceptions:
 * </p>
 * <ul>
 * <li>Candidates that are directories will end with "/"</li>
 * <li>Wildcard regular expressions are not evaluated or replaced</li>
 * <li>The "~" character can be used to represent the user's home, but it cannot complete to other users' homes, since
 * java does not provide any way of determining that easily</li>
 * </ul>
 * 
 * @link http://jline.sourceforge.net/apidocs/jline/FileNameCompletor.html
 */
public class FileNameCompleter implements Completer {

	/**
	 * @param buffer
	 *            it's the beginning string typed by the user
	 * @param cursor
	 *            it's the position of the cursor
	 * @param candidates
	 *            the list of completions proposed to the user
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int complete(final String buffer, final int cursor, @SuppressWarnings("rawtypes") final List candidates) {
		final jline.console.completer.FileNameCompleter completor = new jline.console.completer.FileNameCompleter();
		return completor.complete(buffer, cursor, candidates);
	}

}
