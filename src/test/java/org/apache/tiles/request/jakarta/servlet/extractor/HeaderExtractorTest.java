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

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Enumeration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link HeaderExtractor}.
 *
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for
 * Jakarta EE 9.</p>
 */
public class HeaderExtractorTest {

    /**
     * Empty default constructor
     */
    public HeaderExtractorTest() {
    }

    /**
     * The request.
     */
    private HttpServletRequest request;

    /**
     * The response.
     */
    private HttpServletResponse response;

    /**
     * The extractor to test.
     */
    private HeaderExtractor extractor;

    /**
     * Sets up the test.
     */
    @Before
    public void setUp() {
        request = createMock(HttpServletRequest.class);
        response = createMock(HttpServletResponse.class);
        extractor = new HeaderExtractor(request, response);
    }

    /**
     * Test method for {@link HeaderExtractor#getKeys()}.
     */
    @Test
    public void testGetKeys() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(request.getHeaderNames()).andReturn(keys);

        replay(request, response, keys);
        assertEquals(keys, extractor.getKeys());
        verify(request, response, keys);
    }

    /**
     * Test method for {@link HeaderExtractor#getValue(String)}.
     */
    @Test
    public void testGetValue() {
        expect(request.getHeader("name")).andReturn("value");

        replay(request, response);
        assertEquals("value", extractor.getValue("name"));
        verify(request, response);
    }

    /**
     * Test method for {@link HeaderExtractor#getValues(String)}.
     */
    @Test
    public void testGetValues() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(request.getHeaders("name")).andReturn(keys);

        replay(request, response, keys);
        assertEquals(keys, extractor.getValues("name"));
        verify(request, response, keys);
    }

    /**
     * Test method for {@link HeaderExtractor#setValue(String, String)}.
     */
    @Test
    public void testSetValue() {
        response.setHeader("name", "value");

        replay(request, response);
        extractor.setValue("name", "value");
        verify(request, response);
    }

}
