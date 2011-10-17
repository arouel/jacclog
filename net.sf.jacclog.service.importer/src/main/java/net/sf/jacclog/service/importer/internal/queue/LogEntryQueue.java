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

import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.util.observer.BlockingQueueObserver;
import net.sf.jacclog.util.observer.CurrentElementCounter;
import net.sf.jacclog.util.observer.TotalElementCounter;
import net.sf.jacclog.util.queue.ObservableLinkedBlockingQueue;

public class LogEntryQueue extends ObservableLinkedBlockingQueue<ReadonlyLogEntry> implements
		net.sf.jacclog.service.importer.api.queue.LogEntryQueue<ReadonlyLogEntry> {

	private transient CurrentElementCounter<ReadonlyLogEntry> currentElementCounter;

	private transient TotalElementCounter<ReadonlyLogEntry> totalElementCounter;

	/**
	 * Creates a <code>LogEntryQueue</code> with the given (fixed) capacity.
	 * 
	 * @param capacity
	 */
	public LogEntryQueue(final int capacity) {
		super(capacity);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addObserver(final BlockingQueueObserver<ReadonlyLogEntry> observer) {
		if (observer instanceof CurrentElementCounter) {
			currentElementCounter = (CurrentElementCounter) observer;
		}
		if (observer instanceof TotalElementCounter) {
			totalElementCounter = (TotalElementCounter) observer;
		}
		super.addObserver(observer);
	}

	@Override
	public CurrentElementCounter<ReadonlyLogEntry> getCurrentElementCounter() {
		return currentElementCounter;
	}

	@Override
	public TotalElementCounter<ReadonlyLogEntry> getTotalElementCountCounter() {
		return totalElementCounter;
	}

}
