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
package net.sf.jacclog.uasparser.internal.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import net.sf.jacclog.uasparser.internal.data.domain.Browser;
import net.sf.jacclog.uasparser.internal.data.domain.BrowserOperatingSystemMapping;
import net.sf.jacclog.uasparser.internal.data.domain.BrowserPattern;
import net.sf.jacclog.uasparser.internal.data.domain.BrowserType;
import net.sf.jacclog.uasparser.internal.data.domain.OperatingSystem;
import net.sf.jacclog.uasparser.internal.data.domain.OperatingSystemPattern;
import net.sf.jacclog.uasparser.internal.data.domain.Robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>Data</code> class represents the data for <i>UASparser</i> from {@link http://user-agent-string.info/}.<br>
 * <br>
 * A <code>Data</code> object is immutable; their values cannot be changed after creation.
 * 
 * @author André Rouél
 */
public class Data {

	/**
	 * This builder is not thread safe.
	 * 
	 * @author André Rouél
	 */
	public static class Builder {

		private static final Logger LOG = LoggerFactory.getLogger(Builder.class);

		private static void addOperatingSystemToBrowser(final Map<Integer, Browser.Builder> browserBuilders,
				final Map<Integer, OperatingSystem> operatingSystems, final Map<Integer, Integer> browserOsMap) {
			Browser.Builder browserBuilder;
			for (final Map.Entry<Integer, Integer> entry : browserOsMap.entrySet()) {
				if (browserBuilders.containsKey(entry.getKey())) {
					browserBuilder = browserBuilders.get(entry.getKey());
					if (operatingSystems.containsKey(entry.getValue())) {
						browserBuilder.setOperatingSystem(operatingSystems.get(entry.getValue()));
					} else {
						LOG.warn("Can not find an operating system with ID '" + entry.getValue() + "' for browser '"
								+ browserBuilder.getProducer() + " " + browserBuilder.getFamily() + "'.");
					}
				} else {
					LOG.warn("Can not find a browser with ID '" + entry.getKey() + "'.");
				}
			}
		}

		private static void addPatternToBrowser(final Map<Integer, Browser.Builder> builders,
				final Map<Integer, SortedSet<BrowserPattern>> patterns) {
			for (final Map.Entry<Integer, Browser.Builder> entry : builders.entrySet()) {
				if (patterns.containsKey(entry.getKey())) {
					entry.getValue().setPatternSet(patterns.get(entry.getKey()));
				} else {
					LOG.warn("No pattern available for '" + entry.getValue().getProducer() + " "
							+ entry.getValue().getFamily() + "'.");
				}
			}
		}

		private static void addPatternToOperatingSystem(final Map<Integer, OperatingSystem.Builder> builders,
				final Map<Integer, SortedSet<OperatingSystemPattern>> patterns) {
			for (final Map.Entry<Integer, OperatingSystem.Builder> entry : builders.entrySet()) {
				if (patterns.containsKey(entry.getKey())) {
					entry.getValue().setPatternSet(patterns.get(entry.getKey()));
				} else {
					LOG.warn("No pattern available for '" + entry.getValue().getProducer() + " "
							+ entry.getValue().getFamily() + "'.");
				}
			}
		}

		private static void addTypeToBrowser(final Map<Integer, Browser.Builder> builders,
				final Map<Integer, BrowserType> types) {
			int typeId;
			for (final Map.Entry<Integer, Browser.Builder> entry : builders.entrySet()) {
				typeId = entry.getValue().getTypeId();
				if (types.containsKey(typeId)) {
					entry.getValue().setType(types.get(typeId));
				} else {
					LOG.warn("No type available for '" + entry.getValue().getProducer() + " "
							+ entry.getValue().getFamily() + "'.");
				}
			}
		}

		private static Set<Browser> buildBrowsers(final Map<Integer, Browser.Builder> browserBuilders) {
			final Set<Browser> browsers = new HashSet<Browser>();
			for (final Map.Entry<Integer, Browser.Builder> entry : browserBuilders.entrySet()) {
				try {
					browsers.add(entry.getValue().build());
				} catch (final Exception e) {
					LOG.error("Can not build browser.", e);
				}
			}
			return browsers;
		}

		private static Map<Integer, OperatingSystem> buildOperatingSystems(
				final Map<Integer, OperatingSystem.Builder> osBuilders) {
			final Map<Integer, OperatingSystem> operatingSystems = new HashMap<Integer, OperatingSystem>();
			for (final Map.Entry<Integer, OperatingSystem.Builder> entry : osBuilders.entrySet()) {
				try {
					operatingSystems.put(entry.getKey(), entry.getValue().build());
				} catch (final Exception e) {
					LOG.error("Can not build operating system.", e);
				}
			}
			return operatingSystems;
		}

		private static SortedMap<BrowserPattern, Browser> buildPatternBrowserMap(final Set<Browser> browserSet) {
			final SortedMap<BrowserPattern, Browser> patternBrowser = new TreeMap<BrowserPattern, Browser>(
					ORDERED_PATTERN_COMPARATOR);
			for (final Browser browser : browserSet) {
				for (final BrowserPattern pattern : browser.getPatternSet()) {
					patternBrowser.put(pattern, browser);
				}
			}
			return patternBrowser;
		}

		private static SortedMap<OperatingSystemPattern, OperatingSystem> buildPatternOperatingSystemMap(
				final Set<OperatingSystem> osSet) {
			final SortedMap<OperatingSystemPattern, OperatingSystem> map = new TreeMap<OperatingSystemPattern, OperatingSystem>(
					ORDERED_PATTERN_COMPARATOR);
			for (final OperatingSystem os : osSet) {
				for (final OperatingSystemPattern pattern : os.getPatternSet()) {
					map.put(pattern, os);
				}
			}
			return map;
		}

		private static Map<Integer, Integer> convertBrowserOsMapping(
				final Set<BrowserOperatingSystemMapping> browserOperatingSystemMappings) {
			final Map<Integer, Integer> result = new HashMap<Integer, Integer>();
			for (final BrowserOperatingSystemMapping mapping : browserOperatingSystemMappings) {
				result.put(mapping.getBrowserId(), mapping.getOperatingSystemId());
			}
			return result;
		}

		private static Set<OperatingSystem> convertOperatingSystems(final Map<Integer, OperatingSystem> operatingSystems) {
			final Set<OperatingSystem> result = new HashSet<OperatingSystem>();
			for (final Entry<Integer, OperatingSystem> entry : operatingSystems.entrySet()) {
				result.add(entry.getValue());
			}
			return result;
		}

		private final Map<Integer, BrowserType> browserTypes = new HashMap<Integer, BrowserType>();

		private final Map<Integer, SortedSet<BrowserPattern>> browserPatterns = new HashMap<Integer, SortedSet<BrowserPattern>>();

		private final Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns = new HashMap<Integer, SortedSet<OperatingSystemPattern>>();

		private final Map<Integer, Browser.Builder> browserBuilders = new HashMap<Integer, Browser.Builder>();

		private final Set<Browser> browsers = new HashSet<Browser>();

		private final Map<Integer, OperatingSystem.Builder> operatingSystemBuilders = new HashMap<Integer, OperatingSystem.Builder>();

		private final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();

		private final Set<Robot> robots = new HashSet<Robot>();

		private String version;

		private final Set<BrowserOperatingSystemMapping> browserOperatingSystemMappings = new HashSet<BrowserOperatingSystemMapping>();

		private static final OrderedPatternComparator ORDERED_PATTERN_COMPARATOR = new OrderedPatternComparator();

		public Builder appendBrowser(final Browser browser) {
			if (browser == null) {
				throw new IllegalArgumentException("Argument 'browser' can not be null.");
			}

			browsers.add(browser);
			return this;
		}

		public Builder appendBrowserBuilder(final Browser.Builder browserBuilder) {
			if (browserBuilder == null) {
				throw new IllegalArgumentException("Argument 'browserBuilder' can not be null.");
			}

			if (browserBuilder.getId() < 0) {
				throw new IllegalArgumentException("The ID of argument 'browserBuilder' can not be smaller than 0.");
			}

			if (browserBuilders.containsKey(browserBuilder.getId())) {
				throw new IllegalArgumentException("The pattern '" + browserBuilder.getProducer() + " "
						+ browserBuilder.getFamily() + "' is already in the map.");
			}

			browserBuilders.put(browserBuilder.getId(), browserBuilder);
			return this;
		}

		public Builder appendBrowserOperatingSystemMapping(final BrowserOperatingSystemMapping browserOsMapping) {
			if (browserOsMapping == null) {
				throw new IllegalArgumentException("Argument 'browserOsMapping' can not be null.");
			}

			browserOperatingSystemMappings.add(browserOsMapping);
			return this;
		}

		/**
		 * Appends a browser pattern to the map unless the ID is not already present. If the ID of the pattern is
		 * already set an <code>IllegalArgumentException</code> will be thrown.
		 * 
		 * @param pattern
		 *            A pattern for a browser
		 * @throws IllegalArgumentException
		 *             If the pattern is null
		 * @throws IllegalArgumentException
		 *             If the ID of the pattern is smaller than null
		 * @return itself
		 */
		public Builder appendBrowserPattern(final BrowserPattern pattern) {
			if (pattern == null) {
				throw new IllegalArgumentException("Argument 'pattern' can not be null.");
			}

			if (pattern.getId() < 0) {
				throw new IllegalArgumentException("The ID of argument 'pattern' can not be smaller than 0.");
			}

			if (!browserPatterns.containsKey(pattern.getId())) {
				browserPatterns.put(pattern.getId(), new TreeSet<BrowserPattern>(ORDERED_PATTERN_COMPARATOR));
			}

			browserPatterns.get(pattern.getId()).add(pattern);
			return this;
		}

		public Builder appendBrowserType(final BrowserType type) {
			if (type == null) {
				throw new IllegalArgumentException("Argument 'type' can not be null.");
			}

			browserTypes.put(type.getId(), type);
			return this;
		}

		public Builder appendOperatingSystem(final OperatingSystem operatingSystem) {
			if (operatingSystem == null) {
				throw new IllegalArgumentException("Argument 'operatingSystem' can not be null.");
			}

			operatingSystems.add(operatingSystem);
			return this;
		}

		public Builder appendOperatingSystemBuilder(final OperatingSystem.Builder operatingSystemBuilder) {
			if (operatingSystemBuilder == null) {
				throw new IllegalArgumentException("Argument 'operatingSystemBuilder' can not be null.");
			}

			if (operatingSystemBuilder.getId() < 0) {
				throw new IllegalArgumentException(
						"The ID of argument 'operatingSystemBuilder' can not be smaller than 0.");
			}

			operatingSystemBuilders.put(operatingSystemBuilder.getId(), operatingSystemBuilder);
			return this;
		}

		/**
		 * Appends a operating system pattern to the map unless the ID is not already present. If the ID of the pattern
		 * is already set an <code>IllegalArgumentException</code> will be thrown.
		 * 
		 * @param pattern
		 *            A pattern for a browser
		 * @throws IllegalArgumentException
		 *             If the pattern is null
		 * @throws IllegalArgumentException
		 *             If the ID of the pattern is smaller than null
		 * @return itself
		 */
		public Builder appendOperatingSystemPattern(final OperatingSystemPattern pattern) {
			if (pattern == null) {
				throw new IllegalArgumentException("Argument 'pattern' can not be null.");
			}

			if (pattern.getId() < 0) {
				throw new IllegalArgumentException("The ID of argument 'pattern' can not be smaller than 0.");
			}

			if (!operatingSystemPatterns.containsKey(pattern.getId())) {
				operatingSystemPatterns.put(pattern.getId(), new TreeSet<OperatingSystemPattern>(
						ORDERED_PATTERN_COMPARATOR));
			}

			operatingSystemPatterns.get(pattern.getId()).add(pattern);
			return this;
		}

		public Builder appendRobot(final Robot robot) {
			if (robot == null) {
				throw new IllegalArgumentException("Argument 'robot' can not be null.");
			}

			robots.add(robot);
			return this;
		}

		public Data build() {
			addTypeToBrowser(browserBuilders, browserTypes);
			addPatternToBrowser(browserBuilders, browserPatterns);
			addPatternToOperatingSystem(operatingSystemBuilders, operatingSystemPatterns);

			final Map<Integer, OperatingSystem> operatingSystems = buildOperatingSystems(operatingSystemBuilders);
			addOperatingSystemToBrowser(browserBuilders, operatingSystems,
					convertBrowserOsMapping(browserOperatingSystemMappings));

			final Set<OperatingSystem> osSet = convertOperatingSystems(operatingSystems);
			osSet.addAll(this.operatingSystems);

			final Set<Browser> browserSet = buildBrowsers(browserBuilders);
			browserSet.addAll(browsers);

			final SortedMap<BrowserPattern, Browser> patternBrowserMap = buildPatternBrowserMap(browserSet);
			final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = buildPatternOperatingSystemMap(osSet);

			return new Data(browserSet, osSet, robots, patternBrowserMap, patternOsMap, version);
		}

		public Builder setVersion(final String version) {
			if (version == null) {
				throw new IllegalArgumentException("Argument 'version' can not be null.");
			}

			this.version = version;
			return this;
		}

	}

	/**
	 * An immutable empty <code>Data</code> object.
	 */
	public static final Data EMPTY = new Data(new HashSet<Browser>(), new HashSet<OperatingSystem>(),
			new HashSet<Robot>(0), new TreeMap<BrowserPattern, Browser>(),
			new TreeMap<OperatingSystemPattern, OperatingSystem>(), "");

	private final Set<Browser> browsers;

	private final Set<OperatingSystem> operatingSystems;

	private final Set<Robot> robots;

	private final String version;

	private final SortedMap<BrowserPattern, Browser> patternBrowserMap;

	private final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap;

	public Data(final Set<Browser> browsers, final Set<OperatingSystem> operatingSystems, final Set<Robot> robots,
			final SortedMap<BrowserPattern, Browser> patternBrowserMap,
			final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap, final String version) {
		if (browsers == null) {
			throw new IllegalArgumentException("Argument 'browsers' can not be null.");
		}

		if (operatingSystems == null) {
			throw new IllegalArgumentException("Argument 'operatingSystems' can not be null.");
		}

		if (robots == null) {
			throw new IllegalArgumentException("Argument 'robots' can not be null.");
		}

		if (patternBrowserMap == null) {
			throw new IllegalArgumentException("Argument 'patternBrowserMap' can not be null.");
		}

		if (patternOsMap == null) {
			throw new IllegalArgumentException("Argument 'patternOsMap' can not be null.");
		}

		if (version == null) {
			throw new IllegalArgumentException("Argument 'version' can not be null.");
		}

		this.browsers = browsers;
		this.operatingSystems = operatingSystems;
		this.patternBrowserMap = patternBrowserMap;
		this.patternOsMap = patternOsMap;
		this.robots = robots;
		this.version = version;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Data other = (Data) obj;
		if (browsers == null) {
			if (other.browsers != null)
				return false;
		} else if (!browsers.equals(other.browsers))
			return false;
		if (operatingSystems == null) {
			if (other.operatingSystems != null)
				return false;
		} else if (!operatingSystems.equals(other.operatingSystems))
			return false;
		if (robots == null) {
			if (other.robots != null)
				return false;
		} else if (!robots.equals(other.robots))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	public Set<Browser> getBrowsers() {
		return Collections.unmodifiableSet(browsers);
	}

	public Set<OperatingSystem> getOperatingSystems() {
		return Collections.unmodifiableSet(operatingSystems);
	}

	public SortedMap<BrowserPattern, Browser> getPatternBrowserMap() {
		return patternBrowserMap;
	}

	public SortedMap<OperatingSystemPattern, OperatingSystem> getPatternOsMap() {
		return patternOsMap;
	}

	public Set<Robot> getRobots() {
		return Collections.unmodifiableSet(robots);
	}

	public String getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((browsers == null) ? 0 : browsers.hashCode());
		result = prime * result + ((operatingSystems == null) ? 0 : operatingSystems.hashCode());
		result = prime * result + ((robots == null) ? 0 : robots.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Data [browsers=");
		builder.append(browsers);
		builder.append(", operatingSystems=");
		builder.append(operatingSystems);
		builder.append(", robots=");
		builder.append(robots);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

}
