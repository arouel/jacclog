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
package net.sf.jacclog.uasparser.internal;

import java.util.Map.Entry;
import java.util.regex.Matcher;

import net.sf.jacclog.uasparser.UserAgentInfo;
import net.sf.jacclog.uasparser.UserAgentInfo.Builder;
import net.sf.jacclog.uasparser.UserAgentStringParser;
import net.sf.jacclog.uasparser.internal.data.Data;
import net.sf.jacclog.uasparser.internal.data.domain.Browser;
import net.sf.jacclog.uasparser.internal.data.domain.BrowserPattern;
import net.sf.jacclog.uasparser.internal.data.domain.OperatingSystem;
import net.sf.jacclog.uasparser.internal.data.domain.OperatingSystemPattern;
import net.sf.jacclog.uasparser.internal.data.domain.Robot;

public abstract class AbstractUserAgentStringParser implements UserAgentStringParser {

	/**
	 * Examines the user agent string whether it is a browser.
	 * 
	 * @param userAgent
	 *            String of an user agent
	 * @param builder
	 *            Builder for an user agent information
	 */
	private static void examineAsBrowser(final String userAgent, final UserAgentInfo.Builder builder, final Data data) {
		Matcher matcher;
		for (final Entry<BrowserPattern, Browser> entry : data.getPatternBrowserMap().entrySet()) {
			matcher = entry.getKey().getPattern().matcher(userAgent);
			if (matcher.find()) {

				entry.getValue().copyTo(builder);

				// try to get the browser version from the first subgroup
				final String browserVersion = (matcher.groupCount() > 0) ? matcher.group(1) : "";
				builder.setName(builder.getFamily()
						+ (browserVersion.isEmpty() ? browserVersion : " " + browserVersion));
				break;
			}
		}
	}

	/**
	 * Examines the user agent string whether it is a robot.
	 * 
	 * @param userAgent
	 *            String of an user agent
	 * @param builder
	 *            Builder for an user agent information
	 * @return <code>true</code> if it is a robot, otherwise <code>false</code>
	 */
	private static boolean examineAsRobot(final String userAgent, final Builder builder, final Data data) {
		boolean isRobot = false;
		for (final Robot robot : data.getRobots()) {
			if (robot.getUserAgentString().equals(userAgent)) {
				isRobot = true;
				robot.copyTo(builder);
				break;
			}
		}
		return isRobot;
	}

	/**
	 * Examines the operating system of the user agent string, if not available.
	 * 
	 * @param userAgent
	 *            String of an user agent
	 * @param builder
	 *            Builder for an user agent information
	 */
	private static void examineOperatingSystem(final String userAgent, final Builder builder, final Data data) {
		if (builder.getOsName().isEmpty()) {
			for (final Entry<OperatingSystemPattern, OperatingSystem> entry : data.getPatternOsMap().entrySet()) {
				final Matcher matcher = entry.getKey().getPattern().matcher(userAgent);
				if (matcher.find()) {
					entry.getValue().copyTo(builder);
				}
			}
		}
	}

	protected abstract Data getData();

	@Override
	public UserAgentInfo parse(final String userAgent) {
		final UserAgentInfo.Builder builder = new UserAgentInfo.Builder();

		// work during the analysis always with the same reference of data
		final Data data = getData();

		if (!examineAsRobot(userAgent, builder, data)) {
			examineAsBrowser(userAgent, builder, data);
			examineOperatingSystem(userAgent, builder, data);
		}
		return builder.build();
	}

}
