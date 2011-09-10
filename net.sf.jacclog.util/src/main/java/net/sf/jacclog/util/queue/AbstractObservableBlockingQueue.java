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
package net.sf.jacclog.util.queue;

import net.sf.jacclog.util.observer.BlockingQueueObserver;
import net.sf.jacclog.util.observer.CollectionObserver;

public abstract class AbstractObservableBlockingQueue<E> extends AbstractObservableQueue<E> implements
		ObservableBlockingQueue<E> {

	protected AbstractObservableBlockingQueue() {
		super();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addObserver(final BlockingQueueObserver<E> observer) {
		super.addObserver((CollectionObserver) observer);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void removeObserver(final BlockingQueueObserver<E> observer) {
		super.removeObserver((CollectionObserver) observer);
	}

}
