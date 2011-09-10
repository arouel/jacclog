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

import net.sf.jacclog.uasparser.internal.data.Data;
import net.sf.jacclog.uasparser.internal.data.DataReader;
import net.sf.jacclog.uasparser.internal.data.XmlDataReader;

import org.junit.Assert;
import org.junit.Test;

public class XmlDataReaderTest {

	@Test
	public void testVersionParsing() {
		final DataReader reader = new XmlDataReader();
		final Data data = reader.read(getClass().getClassLoader().getResourceAsStream("uas_test.xml"));
		Assert.assertEquals("20110827-01", data.getVersion());
	}

}
