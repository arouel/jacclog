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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.jacclog.util.observer.CollectionObserver;
import net.sf.jacclog.util.observer.QueueObserver;

public abstract class AbstractObservableQueue<E> implements ObservableQueue<E> {

	protected final List<CollectionObserver<Queue<E>, E>> observers;

	protected AbstractObservableQueue() {
		observers = new CopyOnWriteArrayList<CollectionObserver<Queue<E>, E>>();
	}

	@Override
	public void addObserver(final CollectionObserver<Queue<E>, E> observer) {
		if (observer == null) {
			throw new IllegalArgumentException("Argument 'observer' can not be null.");
		}

		observers.add(observer);
	}

	@Override
	public void addObserver(final QueueObserver<E> observer) {
		addObserver((CollectionObserver<Queue<E>, E>) observer);
	}

	/**
	 * Gets a unmodifiable copied list of all observers at queue.
	 * 
	 * @return list of observers
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CollectionObserver<Collection<E>, E>> getObservers() {
		final List<CollectionObserver<Collection<E>, E>> result = new ArrayList<CollectionObserver<Collection<E>, E>>();
		for (final CollectionObserver collectionObserver : observers) {
			result.add(collectionObserver);
		}
		return Collections.unmodifiableList(result);
	}

	/**
	 * Notify all observers that the given element has been added from queue.
	 * 
	 * @param element
	 */
	protected void notifyElementAdded(final E element) {
		if (element != null) {
			for (final CollectionObserver<Queue<E>, E> observer : observers) {
				observer.added(this, element);
			}
		}
	}

	/**
	 * Notify all observers that the given element has been removed from queue.
	 * 
	 * @param element
	 */
	protected void notifyElementRemoved(final E element) {
		if (element != null) {
			for (final CollectionObserver<Queue<E>, E> observer : observers) {
				observer.removed(this, element);
			}
		}
	}

	/**
	 * Notify all observers that the queue is empty.
	 * 
	 * @param element
	 */
	protected void notifyEmpty() {
		for (final CollectionObserver<Queue<E>, E> observer : observers) {
			observer.empty(this);
		}
	}

	@Override
	public void removeObserver(final CollectionObserver<Queue<E>, E> observer) {
		if (observer == null) {
			throw new IllegalArgumentException("Argument 'observer' can not be null.");
		}

		observers.remove(observer);
	}

	@Override
	public void removeObserver(final QueueObserver<E> observer) {
		removeObserver((CollectionObserver<Queue<E>, E>) observer);
	}

	@Override
	public void removeObservers() {
		observers.clear();
	}

}
