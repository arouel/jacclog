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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import net.sf.jacclog.uasparser.internal.data.domain.Browser;
import net.sf.jacclog.uasparser.internal.data.domain.BrowserType;

public class BrowserTest {

	@Test
	public void testBrowserBuilding() throws Exception {
		BrowserType browserType = new BrowserType.Builder().setId("1").setName("browser type test").build();
		final Map<Integer, BrowserType> browserTypes = new HashMap<Integer, BrowserType>();
		browserTypes.put(Integer.valueOf(1), browserType);

		Browser.Builder builder = new Browser.Builder();

		builder.setProducer("producer");
		builder.setProducerUrl("producer url");
		builder.setFamily("family");
		builder.setIcon("icon");
		builder.setInfoUrl("info url");
		builder.setType(browserType);
		builder.setUrl("url");
		Browser browser = builder.build();
		Assert.assertEquals("producer", browser.getProducer());
		Assert.assertEquals("producer url", browser.getProducerUrl());
		Assert.assertEquals("family", browser.getFamily());
		Assert.assertEquals("icon", browser.getIcon());
		Assert.assertEquals("info url", browser.getInfoUrl());
		Assert.assertEquals("browser type test", browser.getType().getName());
		Assert.assertEquals(1, browser.getType().getId());
		Assert.assertEquals("url", browser.getUrl());
	}

}
