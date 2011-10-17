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
package net.sf.jacclog.api.domain;

import net.sf.jacclog.api.domain.http.ReadableHttpRequestHeaderField;
import net.sf.jacclog.api.domain.http.ReadableHttpResponseHeaderField;

/**
 * Defines a log entry that can only be queried and not modified.<br>
 * <br>
 * The implementation of this interface must be immutable. This interface only gives access to retrieve data, never to
 * change it.
 * 
 * @author André Rouél
 */
public interface ReadonlyLogEntry extends
		ReadableLogEntry<ReadableHttpRequestHeaderField, ReadableHttpResponseHeaderField> {
}
