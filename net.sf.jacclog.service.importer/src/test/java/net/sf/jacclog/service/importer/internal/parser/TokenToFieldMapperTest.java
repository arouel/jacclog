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
package net.sf.jacclog.service.importer.internal.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jacclog.logformat.field.Field;
import net.sf.jacclog.logformat.field.RemoteHostField;
import net.sf.jacclog.logformat.field.RemoteLognameField;
import net.sf.jacclog.logformat.field.RemoteUserField;
import net.sf.jacclog.logformat.field.RequestFirstLineField;

public class TokenToFieldMapperTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenToFieldMapperTest.class);

	private void printResult(final Map<Field, String> map) {
		LOGGER.info(">>>\nsize:" + map.size());
		LOGGER.info("tokens={");
		int i = 0;
		for (final Entry<Field, String> e : map.entrySet()) {
			LOGGER.info("  " + e.getKey() + "[" + i + "] = " + e.getValue());
			i++;
		}
		LOGGER.info("}");
	}

	@Test
	public void testParseCustomLogFormat() throws Exception {
		final List<String> tokens = new ArrayList<String>();
		tokens.add("First Token (remote host)");
		tokens.add("Second Token (logname)");
		tokens.add("Third Token (remote user)");
		tokens.add("Forth Token (request first line)");

		final List<Field> fields = new ArrayList<Field>();
		fields.add(RemoteHostField.getInstance());
		fields.add(RemoteLognameField.getInstance());
		fields.add(RemoteUserField.getInstance());
		fields.add(RequestFirstLineField.getInstance());

		final Map<Field, String> map = TokenToFieldMapper.map(fields, tokens);
		printResult(map);

		Assert.assertEquals(4, map.size());

		Assert.assertTrue(map.containsKey(RemoteHostField.getInstance()));
		Assert.assertTrue(map.containsKey(RemoteLognameField.getInstance()));
		Assert.assertTrue(map.containsKey(RemoteUserField.getInstance()));
		Assert.assertTrue(map.containsKey(RequestFirstLineField.getInstance()));

		Assert.assertSame(tokens.get(0), map.get(RemoteHostField.getInstance()));
		Assert.assertSame(tokens.get(1), map.get(RemoteLognameField.getInstance()));
		Assert.assertSame(tokens.get(2), map.get(RemoteUserField.getInstance()));
		Assert.assertSame(tokens.get(3), map.get(RequestFirstLineField.getInstance()));
	}

}
