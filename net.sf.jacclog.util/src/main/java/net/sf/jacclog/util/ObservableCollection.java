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
package net.sf.jacclog.util;

import java.util.Collection;
import java.util.List;

import net.sf.jacclog.util.observer.CollectionObserver;

/**
 * This interface marks an observable object.
 * 
 * @author André Rouél
 */
public interface ObservableCollection<O extends Collection<E>, E> {

	/**
	 * Adds an observer.
	 * 
	 * @param observer
	 *            Queue observer
	 */
	void addObserver(CollectionObserver<O, E> observer);

	/**
	 * Gets all observers.
	 * 
	 * @return list of observers
	 */
	List<CollectionObserver<Collection<E>, E>> getObservers();

	/**
	 * Removes an observer.
	 * 
	 * @param observer
	 *            Queue observer
	 */
	void removeObserver(CollectionObserver<O, E> observer);

	/**
	 * Clears the observer list so that this object no longer has any observers.
	 */
	void removeObservers();

}
