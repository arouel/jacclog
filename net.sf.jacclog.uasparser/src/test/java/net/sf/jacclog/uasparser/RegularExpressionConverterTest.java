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
package net.sf.jacclog.uasparser;

import java.util.regex.Pattern;

import net.sf.jacclog.uasparser.internal.util.RegularExpressionConverter;

import org.junit.Assert;
import org.junit.Test;

public class RegularExpressionConverterTest {

	@Test
	public void testAllModifiersConverting() {
		final int flags = RegularExpressionConverter.translateModifiers("imsx");
		Assert.assertSame(Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.COMMENTS, flags);
		Assert.assertNotSame(Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE, flags);
	}

	@Test
	public void testFalseModifierRegexConverting() {
		final String perlStyleRegex = "/Nintendo DS/Si";
		try {
			RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals(
					"The given regular expression '/Nintendo DS/Si' seems to be not in PERL style or has unsupported modifiers.",
					e.getLocalizedMessage());
		}
	}

	@Test
	public void testFaultTolerantModifierRegexConverting() {
		final String perlStyleRegex = "/Nintendo DS/Si";
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex, true);
		final Pattern expected = Pattern.compile("Nintendo DS", Pattern.CASE_INSENSITIVE);
		Assert.assertEquals(expected.flags(), pattern.flags());
	}

	@Test
	public void testModifierConverting() {
		final int flags = RegularExpressionConverter.translateModifiers("si");
		Assert.assertEquals(Pattern.CASE_INSENSITIVE | Pattern.DOTALL, flags);
	}

	@Test
	public void testRegexConverting() {
		final String perlStyleRegex = "/\\s*[a-zA-Z0-9]*/";
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
		final Pattern expected = Pattern.compile("\\s*[a-zA-Z0-9]*");
		Assert.assertEquals(expected.flags(), pattern.flags());
	}

	@Test
	public void testRegexWithApparentlyIntersectConverting() {
		final String perlStyleRegex = "/test/([0-9a-zA-Z.\\-+]+)/s";
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
		final Pattern expected = Pattern.compile("test/([0-9a-zA-Z.\\-+]+)", Pattern.DOTALL);
		Assert.assertEquals(expected.flags(), pattern.flags());
		Assert.assertEquals(expected.pattern(), pattern.pattern());
	}

	@Test
	public void testRegexWithModifierConverting() {
		final String perlStyleRegex = "/\\s*[a-zA-Z0-9]*/im";
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
		final Pattern expected = Pattern.compile("\\s*[a-zA-Z0-9]*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Assert.assertEquals(expected.flags(), pattern.flags());
		Assert.assertEquals(expected.pattern(), pattern.pattern());
	}

	@Test
	public void testRegexWithWhitespaceConverting() {
		final String perlStyleRegex = "/\\s*/[a-zA-Z0-9]*/im ";
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
		final Pattern expected = Pattern.compile("\\s*/[a-zA-Z0-9]*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Assert.assertEquals(expected.flags(), pattern.flags());
		Assert.assertEquals(expected.pattern(), pattern.pattern());
	}

	@Test
	public void testSwiftfoxRegexConverting() {
		final String perlStyleRegex = "/mozilla.*rv:[0-9\\.]+.*gecko\\/[0-9]+.*firefox\\/([0-9a-z\\+\\-\\.]+).*swiftfox/si";
		RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex, true);
		final Pattern expected = Pattern.compile(
				"mozilla.*rv:[0-9\\.]+.*gecko\\/[0-9]+.*firefox\\/([0-9a-z\\+\\-\\.]+).*swiftfox",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Assert.assertEquals(expected.flags(), pattern.flags());
	}

}
