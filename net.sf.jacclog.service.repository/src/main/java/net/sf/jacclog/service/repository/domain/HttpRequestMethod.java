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

public enum HttpRequestMethod {

	GET("GET"), POST("POST"), HEAD("HEAD"), PUT("PUT"), DELETE("DELETE"), TRACE("TRACE"), OPTIONS("OPTIONS"), CONNECT(
			"CONNECT");

	/**
	 * Return the enum constant of this type with the specified numeric value of HTTP status.
	 * 
	 * @param requestMethod
	 *            the numeric value of the HTTP status to be returned
	 * @return the enum constant with the specified numeric value
	 * @throws IllegalArgumentException
	 *             if this enum has no constant for the specified numeric value
	 */
	public static HttpRequestMethod evaluate(final String requestMethod) {
		HttpRequestMethod result = null;
		for (final HttpRequestMethod method : values()) {
			if (method.value().equals(requestMethod)) {
				result = method;
				break;
			}
		}
		return result;
	}

	public static boolean isValid(final String requestMethod) {
		boolean result = false;
		if (requestMethod != null) {
			for (final HttpRequestMethod method : values()) {
				if (method.equals(requestMethod)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	private final String method;

	HttpRequestMethod(final String requestMethod) {
		method = requestMethod;
	}

	public String value() {
		return method;
	}

}
