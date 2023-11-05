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
import jakarta.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link SessionScopeExtractor}.
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for Jakarta EE 9 </p>
 *
 * @version $Rev$ $Date$
 */
public class SessionScopeExtractorTest {

    /**
     * The request.
     */
    private HttpServletRequest request;

    /**
     * The session.
     */
    private HttpSession session;

    /**
     * The extractot to test.
     */
    private SessionScopeExtractor extractor;

    /**
     * Sets up the test.
     */
    @Before
    public void setUp() {
        request = createMock(HttpServletRequest.class);
        session = createMock(HttpSession.class);
        extractor = new SessionScopeExtractor(request);
    }

    /**
     * Test method for {@link SessionScopeExtractor#setValue(java.lang.String, java.lang.Object)}.
     */
    @Test
    public void testSetValue() {
        expect(request.getSession()).andReturn(session);
        session.setAttribute("name", "value");

        replay(request, session);
        extractor.setValue("name", "value");
        verify(request, session);
    }

    /**
     * Test method for {@link SessionScopeExtractor#removeValue(java.lang.String)}.
     */
    @Test
    public void testRemoveValue() {
        expect(request.getSession(false)).andReturn(session);
        session.removeAttribute("name");

        replay(request, session);
        extractor.removeValue("name");
        verify(request, session);
    }

    /**
     * Test method for {@link SessionScopeExtractor#getKeys()}.
     */
    @Test
    public void testGetKeys() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(request.getSession(false)).andReturn(session);
        expect(session.getAttributeNames()).andReturn(keys);

        replay(request, session, keys);
        assertEquals(keys, extractor.getKeys());
        verify(request, session, keys);
    }

    /**
     * Test method for {@link SessionScopeExtractor#getKeys()}.
     */
    @Test
    public void testGetKeysNoSession() {
        expect(request.getSession(false)).andReturn(null);

        replay(request, session);
        Enumeration<String> keys = extractor.getKeys();
        assertNotNull(keys);
        assertFalse(keys.hasMoreElements());
        verify(request, session);
    }

    /**
     * Test method for {@link SessionScopeExtractor#getValue(java.lang.String)}.
     */
    @Test
    public void testGetValue() {
        expect(request.getSession(false)).andReturn(session);
        expect(session.getAttribute("name")).andReturn("value");

        replay(request, session);
        assertEquals("value", extractor.getValue("name"));
        verify(request, session);
    }

    /**
     * Test method for {@link SessionScopeExtractor#getValue(java.lang.String)}.
     */
    @Test
    public void testGetValueNoSession() {
        expect(request.getSession(false)).andReturn(null);

        replay(request, session);
        assertNull(extractor.getValue("name"));
        verify(request, session);
    }

}
