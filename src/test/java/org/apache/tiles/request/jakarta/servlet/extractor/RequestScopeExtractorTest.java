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

import jakarta.servlet.http.HttpServletRequest;

/**
 * Tests {@link RequestScopeExtractor}.
 *
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for
 * Jakarta EE 9.</p>
 */
public class RequestScopeExtractorTest {

    /**
     * Empty default constructor
     */
    public RequestScopeExtractorTest() {
    }

    /**
     * The request.
     */
    private HttpServletRequest request;

    /**
     * The extractor to test.
     */
    private RequestScopeExtractor extractor;

    /**
     * Sets up the test.
     */
    @Before
    public void setUp() {
        request = createMock(HttpServletRequest.class);
        extractor = new RequestScopeExtractor(request);
    }

    /**
     * Test method for {@link RequestScopeExtractor#setValue(String, Object)}.
     */
    @Test
    public void testSetValue() {
        request.setAttribute("name", "value");

        replay(request);
        extractor.setValue("name", "value");
        verify(request);
    }

    /**
     * Test method for {@link RequestScopeExtractor#removeValue(String)}.
     */
    @Test
    public void testRemoveValue() {
        request.removeAttribute("name");

        replay(request);
        extractor.removeValue("name");
        verify(request);
    }

    /**
     * Test method for {@link RequestScopeExtractor#getKeys()}.
     */
    @Test
    public void testGetKeys() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(request.getAttributeNames()).andReturn(keys);

        replay(request, keys);
        assertEquals(keys, extractor.getKeys());
        verify(request, keys);
    }

    /**
     * Test method for {@link RequestScopeExtractor#getValue(String)}.
     */
    @Test
    public void testGetValue() {
        expect(request.getAttribute("name")).andReturn("value");

        replay(request);
        assertEquals("value", extractor.getValue("name"));
        verify(request);
    }
}
