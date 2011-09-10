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
package net.sf.jacclog.util.observer;

import java.util.Collection;

public interface CollectionObserver<O extends Collection<E>, E> {

	/**
	 * Gives feedback that an element was added to the observing collection.
	 * 
	 * @param objectToObserve
	 * @param element
	 */
	void added(final O objectToObserve, final E element);

	/**
	 * Gives feedback that the observing collection is empty.
	 * 
	 * @param objectToObserve
	 * @param element
	 */
	void empty(final O objectToObserve);

	/**
	 * Gives feedback that an element was removed to the observing collection.
	 * 
	 * @param objectToObserve
	 * @param element
	 */
	void removed(final O objectToObserve, final E element);

}
