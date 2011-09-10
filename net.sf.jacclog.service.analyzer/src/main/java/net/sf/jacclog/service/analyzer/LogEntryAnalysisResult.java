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
package net.sf.jacclog.service.analyzer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.jacclog.uasparser.UserAgentInfo;

public final class LogEntryAnalysisResult {

	public static class Builder {

		private final ConcurrentMap<UserAgentInfo, AtomicInteger> userAgentInfos = new ConcurrentHashMap<UserAgentInfo, AtomicInteger>();

		public Builder appendUserAgentInfo(final UserAgentInfo info) {
			if (info == null) {
				throw new IllegalArgumentException("Argument 'info' can not be null.");
			}

			final AtomicInteger counter = userAgentInfos.putIfAbsent(info, new AtomicInteger(1));
			if (counter != null) {
				counter.incrementAndGet();
			}
			return this;
		}

		public LogEntryAnalysisResult build() {
			return new LogEntryAnalysisResult(userAgentInfos);
		}

		/**
		 * Copies information from itself to another object.
		 * 
		 * @param builder
		 *            Analysis builder
		 * @throws IllegalArgumentException
		 *             If argument <code>builder</code> is <code>null</code>
		 * @throws IllegalArgumentException
		 *             If argument <code>builder</code> is itself
		 * @return Builder (itself)
		 */
		public Builder copyTo(final Builder builder) {
			if (builder == null) {
				throw new IllegalArgumentException("Argument 'builder' can not be null.");
			}

			if (equals(builder)) {
				throw new IllegalArgumentException("Copying information to itself is not permitted.");
			}

			builder.getUserAgentInfos().putAll(getUserAgentInfos());
			return this;
		}

		public ConcurrentMap<UserAgentInfo, AtomicInteger> getUserAgentInfos() {
			return userAgentInfos;
		}

	}

	private final Map<UserAgentInfo, AtomicInteger> userAgentInfos;

	private LogEntryAnalysisResult(final Map<UserAgentInfo, AtomicInteger> userAgentInfos) {
		if (userAgentInfos == null) {
			throw new IllegalArgumentException("Argument 'userAgentInfos' can not be null.");
		}

		this.userAgentInfos = userAgentInfos;
	}

	public Map<UserAgentInfo, AtomicInteger> getUserAgentInfos() {
		return userAgentInfos;
	}

}
