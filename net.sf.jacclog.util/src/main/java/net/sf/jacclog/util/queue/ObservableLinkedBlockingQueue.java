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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ObservableLinkedBlockingQueue<E> extends AbstractObservableBlockingQueue<E> implements
		ObservableBlockingQueue<E> {

	protected final BlockingQueue<E> delegation;

	/**
	 * Creates a queue with the given (fixed) capacity.
	 * 
	 * @param capacity
	 */
	public ObservableLinkedBlockingQueue(final int capacity) {
		super();
		delegation = new LinkedBlockingQueue<E>(capacity);
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

	/**
	 * This method is not implemented and throws an <code>UnsupportedOperationException</code>.
	 */
	@Override
	public int drainTo(final Collection<? super E> elements) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is not implemented and throws an <code>UnsupportedOperationException</code>.
	 */
	@Override
	public int drainTo(final Collection<? super E> elements, final int maxElements) {
		throw new UnsupportedOperationException();
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
	public boolean offer(final E element, final long timeout, final TimeUnit unit) throws InterruptedException {
		final boolean offered = delegation.offer(element, timeout, unit);
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
	public E poll(final long timeout, final TimeUnit unit) throws InterruptedException {
		final E element = delegation.poll(timeout, unit);
		if (element == null) {
			notifyEmpty();
		} else {
			notifyElementRemoved(element);
		}
		return element;
	}

	@Override
	public void put(final E element) throws InterruptedException {
		delegation.put(element);
		notifyElementAdded(element);
	}

	/**
	 * Returns the number of additional elements that this queue can ideally (in the absence of memory or resource
	 * constraints) accept without blocking.<br>
	 * <br>
	 * This is always equal to the initial capacity of this queue less the current size of this queue. Note that you
	 * cannot always tell if an attempt to insert an element will succeed by inspecting remainingCapacity because it may
	 * be the case that another thread is about to insert or remove an element.
	 * 
	 * @return the remaining capacity
	 */
	@Override
	public int remainingCapacity() {
		return delegation.remainingCapacity();
	}

	@Override
	public E remove() {
		final E element = delegation.remove();
		notifyElementRemoved(element);
		return element;
	}

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
	public E take() throws InterruptedException {
		final E element = delegation.take();
		notifyElementRemoved(element);
		return element;
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
