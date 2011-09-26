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


public class NonPersistentLogEntry extends AbstractLogEntry implements UnsavedLogEntry {

	@Override
	public Long getId() {
		return Long.MIN_VALUE;
	}

	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append("NonPersistentLogEntry [\n");
		buffer.append("\ngetId() = " + getId());
		buffer.append(",\ngetContentType() = " + getContentType());
		buffer.append(",\ngetFinishedRequestAt() = " + getFinishedRequestAt());
		buffer.append(",\ngetHttpStatus() = " + getHttpStatus());
		buffer.append(",\ngetLocalHost() = " + getLocalHost());
		buffer.append(",\ngetLocalIpAddress() = " + getLocalIpAddress());
		buffer.append(",\ngetReferer() = " + getReferer());
		buffer.append(",\ngetRemoteHost() = " + getRemoteHost());
		buffer.append(",\ngetRemoteIpAddress() = " + getRemoteIpAddress());
		buffer.append(",\ngetRequestMethod() = " + getRequestMethod());
		buffer.append(",\ngetRequestParameter() = " + getRequestParameter());
		buffer.append(",\ngetRequestUrlPath() = " + getRequestUrlPath());
		buffer.append(",\ngetResponseDataSize() = " + getResponseDataSize());
		buffer.append(",\ngetSessionId() = " + getSessionId());
		buffer.append(",\ngetUserAgent() = " + getUserAgent());
		buffer.append(",\ngetUserId() = " + getUserId());
		buffer.append(",\ngetClass() = " + getClass());
		buffer.append(",\nhashCode() = " + hashCode());
		buffer.append(",\ntoString() = " + super.toString());
		buffer.append("\n]");
		return buffer.toString();
	}

}
