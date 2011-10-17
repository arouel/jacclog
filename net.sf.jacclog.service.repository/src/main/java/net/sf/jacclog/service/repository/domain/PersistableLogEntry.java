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
package net.sf.jacclog.service.repository.domain;

import net.sf.jacclog.api.domain.ReadWritableLogEntry;

/**
 * Specifies a mutable and persistable log entry with an ID and version field.
 * 
 * @author André Rouél
 */
public interface PersistableLogEntry extends ReadWritableLogEntry<PersistableHttpRequestHeaderField, PersistableHttpResponseHeaderField>,
		Persistable {

	/**
	 * Gets the primary key of the entity.
	 * 
	 * @return the ID
	 */
	Long getId();

}
