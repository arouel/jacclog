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
package net.sf.jacclog.csv;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommaSeparatedValuesReaderTest {

	private static final Logger LOG = LoggerFactory.getLogger(CommaSeparatedValuesReaderTest.class);

	@Test
	public void testFileParsing1() {
		final String fileName = "test.csv";
		final InputStream input = this.getClass().getClassLoader().getResourceAsStream(fileName);
		final List<List<String>> lines = CommaSeparatedValuesReader.read(new InputStreamReader(input));
		LOG.info("Testing CSV reading of '" + fileName + "'.");

		final List<List<String>> expectedList = new ArrayList<List<String>>();
		final List<String> firstLine = new ArrayList<String>(4);
		firstLine.add("1");
		firstLine.add("2");
		firstLine.add("3");
		firstLine.add("4");
		expectedList.add(firstLine);
		final List<String> secondLine = new ArrayList<String>(4);
		secondLine.add("test1");
		secondLine.add("test22");
		secondLine.add("test333");
		secondLine.add("test4444");
		expectedList.add(secondLine);
		final List<String> thirdLine = new ArrayList<String>(4);
		thirdLine.add("test1");
		thirdLine.add("test22");
		thirdLine.add("test333");
		thirdLine.add("test4444");
		expectedList.add(thirdLine);
		final List<String> fourthLine = new ArrayList<String>(4);
		fourthLine.add("test 1");
		fourthLine.add("test 22");
		fourthLine.add("test 333");
		fourthLine.add("test 4444");
		expectedList.add(fourthLine);
		final List<String> fivthLine = new ArrayList<String>(4);
		fivthLine.add("test \"1\"");
		fivthLine.add("test \"22\"");
		fivthLine.add("test \"333\"");
		fivthLine.add("test \"4444\"\"\"");
		expectedList.add(fivthLine);

		LOG.info("expected: " + Arrays.deepToString(expectedList.toArray()));
		LOG.info("actual: " + Arrays.deepToString(lines.toArray()));

		Assert.assertEquals(5, lines.size());
		Assert.assertTrue(Arrays.deepEquals(expectedList.toArray(), lines.toArray()));
	}

	@Test
	public void testLineParsing1() {
		final String input = "1,2,3,4";
		LOG.info("Testing: " + input);

		final List<String> fields = CommaSeparatedValuesReader.read(input);
		Assert.assertEquals(4, fields.size());
		Assert.assertArrayEquals(input.split(","), fields.toArray());
	}

	@Test
	public void testLineParsing2() {
		final String input = "1,22,333,4444";
		LOG.info("Testing: " + input);

		final List<String> fields = CommaSeparatedValuesReader.read(input);
		Assert.assertEquals(4, fields.size());
		Assert.assertArrayEquals(input.split(","), fields.toArray());
	}

	@Test
	public void testLineParsing3() {
		final String input = "1,\"text22\",333,\"text4444\"\"\"";
		LOG.info("Testing: " + input);

		final List<String> fields = CommaSeparatedValuesReader.read(input);
		Assert.assertEquals(4, fields.size());

		final String[] expected = new String[] { "1", "text22", "333", "text4444\"" };
		Assert.assertArrayEquals(expected, fields.toArray());
	}

	@Test
	public void testLineParsing4() {
		final String input = "test1,\"text 22\",test333,\"text 4444\"\"\"";
		LOG.info("Testing: " + input);

		final List<String> fields = CommaSeparatedValuesReader.read(input);
		Assert.assertEquals(4, fields.size());

		final String[] expected = new String[] { "test1", "text 22", "test333", "text 4444\"" };
		Assert.assertArrayEquals(expected, fields.toArray());
	}

	@Test
	public void testLineParsingWithQuotedText() {
		final String input = "\"Ludwig van Beethoven aka. \"\"Lu Hovi\"\"\",\"Johann Sebastian Bach aka. \"\"Jo Sebb\"\"\",\"\"\"Georg Friedrich Händel\"\"\"\"\"";
		LOG.info("Testing: " + input);

		final List<String> fields = CommaSeparatedValuesReader.read(input);

		for (final String field : fields) {
			LOG.info("field: " + field);
		}

		Assert.assertEquals(3, fields.size());

		final String[] expected = new String[] { "Ludwig van Beethoven aka. \"Lu Hovi\"",
				"Johann Sebastian Bach aka. \"Jo Sebb\"", "\"Georg Friedrich Händel\"\"" };
		Assert.assertArrayEquals(expected, fields.toArray());
	}

}
