/*
 * Copyright 2023 Web-Legacy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tiles.request.jakarta.servlet.extractor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.Enumeration;

import org.junit.Before;
import org.junit.Test;

import jakarta.servlet.ServletContext;

/**
 * Tests {@link InitParameterExtractor}.
 *
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for
 * Jakarta EE 9.</p>
 */
public class InitParameterExtractorTest {

    /**
     * Empty default constructor
     */
    public InitParameterExtractorTest() {
    }

    /**
     * The servlet context.
     */
    private ServletContext context;

    /**
     * The extractor to test.
     */
    private InitParameterExtractor extractor;

    /**
     * Sets up the test.
     */
    @Before
    public void setUp() {
        context = createMock(ServletContext.class);
        extractor = new InitParameterExtractor(context);
    }

    /**
     * Test method for {@link InitParameterExtractor#getKeys()}.
     */
    @Test
    public void testGetKeys() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(context.getInitParameterNames()).andReturn(keys);

        replay(context, keys);
        assertEquals(keys, extractor.getKeys());
        verify(context, keys);
    }

    /**
     * Test method for {@link InitParameterExtractor#getValue(String)}.
     */
    @Test
    public void testGetValue() {
        expect(context.getInitParameter("name")).andReturn("value");

        replay(context);
        assertEquals("value", extractor.getValue("name"));
        verify(context);
    }
}
