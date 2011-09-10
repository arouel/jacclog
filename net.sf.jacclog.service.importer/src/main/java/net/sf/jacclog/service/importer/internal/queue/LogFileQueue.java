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
package net.sf.jacclog.service.importer.internal.queue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import net.sf.jacclog.service.importer.api.LogFile;
import net.sf.jacclog.util.queue.ObservableLinkedBlockingQueue;

/**
 * Thread-safe queue of log files.
 * 
 * @author André Rouél
 */
public class LogFileQueue extends ObservableLinkedBlockingQueue<LogFile> {

	/**
	 * Creates a <code>LogFileQueue</code> with the given (fixed) capacity.
	 * 
	 * @param capacity
	 */
	public LogFileQueue(final int capacity) {
		super(capacity);
	}

	/**
	 * Copies all files in the queue to a list and returns. <br>
	 * <br>
	 * The returned list will be "safe" in that no references to it are maintained by this queue. The caller is thus
	 * free to modify the returned list.
	 * 
	 * @return list of files
	 */
	public List<File> copyAsList() {
		final File[] files = (File[]) this.toArray();
		return Arrays.asList(files);
	}

}
