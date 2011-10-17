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
package net.sf.jacclog.service.importer.api.queue;

import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.util.observer.CurrentElementCounter;
import net.sf.jacclog.util.observer.TotalElementCounter;
import net.sf.jacclog.util.queue.ObservableBlockingQueue;

/**
 * An observable queue for log entries.
 * 
 * @author André Rouél
 */
public interface LogEntryQueue<E extends ReadonlyLogEntry> extends
		ObservableBlockingQueue<E> {

	/**
	 * Gets the <code>CurrentElementCounter</code> if exists.
	 * 
	 * @return the counter or <code>null</code>
	 */
	CurrentElementCounter<E> getCurrentElementCounter();

	/**
	 * Gets the <code>TotalElementCounter</code> if exists.
	 * 
	 * @return the counter or <code>null</code>
	 */
	TotalElementCounter<E> getTotalElementCountCounter();

}
