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

import org.junit.Assert;
import org.junit.Test;

import net.sf.jacclog.uasparser.internal.util.StringUtil;

public class ConfigurationTest {

	@Test
	public void testQuotationMarksFiltering() throws Exception { // TODO delete
		final String[] data = new String[8];
		data[0] = "\"test\"";
		data[1] = "\"te\"st\"";
		data[2] = "test";
		data[3] = "te\"st";
		data[4] = "\"te\"st";
		data[5] = "te\"st\"";
		data[6] = "";
		data[7] = "\"\"";

		final String[] result = StringUtil.filterQuotationMarks(data);
		Assert.assertEquals(data.length, result.length);
		Assert.assertEquals("test", result[0]);
		Assert.assertEquals("te\"st", result[1]);
		Assert.assertEquals("test", result[2]);
		Assert.assertEquals("te\"st", result[3]);
		Assert.assertEquals("\"te\"st", result[4]);
		Assert.assertEquals("te\"st\"", result[5]);
		Assert.assertEquals("", result[6]);
		Assert.assertEquals("", result[7]);
	}

}
