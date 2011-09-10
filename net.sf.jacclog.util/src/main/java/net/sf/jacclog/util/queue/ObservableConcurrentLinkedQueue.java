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

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ObservableConcurrentLinkedQueue<E> extends AbstractObservableQueue<E> implements ObservableQueue<E> {

	protected final Queue<E> delegation;

	/**
	 * Creates a thread-safe linked queue.
	 */
	public ObservableConcurrentLinkedQueue() {
		super();
		delegation = new ConcurrentLinkedQueue<E>();
	}

	/**
	 * Creates a thread-safe linked queue with the given items.
	 * 
	 * @param collection
	 *            A collection of items
	 */
	public ObservableConcurrentLinkedQueue(final Collection<E> collection) {
		super();
		delegation = new ConcurrentLinkedQueue<E>(collection);
	}

	@Override
	public boolean add(final E element) {
		if (element == null) {
			throw new IllegalArgumentException("Argument 'element' can not be null.");
		}

		final boolean added = delegation.add(element);
		if (added) {
			notifyElementAdded(element);
		}
		return added;
	}

	@Override
	public boolean addAll(final Collection<? extends E> elements) {
		boolean collectionChanged = false;
		for (final E element : elements) {
			if (add(element) && !collectionChanged) {
				collectionChanged = true;
			}
		}
		return collectionChanged;
	}

	@Override
	public void clear() {
		E element;
		do {
			element = poll();
		} while (element != null);
	}

	@Override
	public boolean contains(final Object element) {
		return delegation.contains(element);
	}

	@Override
	public boolean containsAll(final Collection<?> elements) {
		return delegation.containsAll(elements);
	}

	@Override
	public E element() {
		return delegation.element();
	}

	@Override
	public boolean isEmpty() {
		return delegation.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return delegation.iterator();
	}

	@Override
	public boolean offer(final E element) {
		final boolean offered = delegation.offer(element);
		if (offered) {
			notifyElementAdded(element);
		}
		return offered;
	}

	@Override
	public E peek() {
		return delegation.peek();
	}

	@Override
	public E poll() {
		final E element = delegation.poll();
		if (element == null) {
			notifyEmpty();
		} else {
			notifyElementRemoved(element);
		}
		return element;
	}

	@Override
	public E remove() {
		final E element = delegation.remove();
		notifyElementRemoved(element);
		return element;
	}

	/**
	 * This method is not implemented and throws an <code>UnsupportedOperationException</code>.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(final Object element) {
		boolean result = false;
		if (delegation.remove(element)) {
			result = true;
			notifyElementRemoved((E) element);
		}
		return result;
	}

	/**
	 * This method is not implemented and throws an <code>UnsupportedOperationException</code>.
	 */
	@Override
	public boolean removeAll(final Collection<?> elements) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is not implemented and throws an <code>UnsupportedOperationException</code>.
	 */
	@Override
	public boolean retainAll(final Collection<?> elements) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return delegation.size();
	}

	@Override
	public Object[] toArray() {
		return delegation.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] elements) {
		return delegation.toArray(elements);
	}

}
