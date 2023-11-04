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

import org.apache.tiles.request.jakarta.servlet.extractor.RequestScopeExtractor;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link RequestScopeExtractor}.
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for Jakarta EE 9 </p>
 *
 * @version $Rev$ $Date$
 */
public class RequestScopeExtractorTest {

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
     * Test method for {@link org.apache.tiles.request.servlet.extractor.RequestScopeExtractor#getKeys()}.
     */
    @SuppressWarnings("unchecked")
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
