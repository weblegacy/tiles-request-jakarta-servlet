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
package org.apache.tiles.request.jakarta.servlet;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Locale;

import org.apache.tiles.request.ApplicationResource;
import org.apache.tiles.request.collection.ReadOnlyEnumerationMap;
import org.apache.tiles.request.collection.ScopeMap;
import org.junit.Before;
import org.junit.Test;

import jakarta.servlet.ServletContext;

/**
 * Tests {@link ServletApplicationContext}.
 *
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for
 * Jakarta EE 9.</p>
 */
public class ServletApplicationContextTest {

    /**
     * Empty default constructor
     */
    public ServletApplicationContextTest() {
    }

    /**
     * The servlet context.
     */
    private ServletContext servletContext;

    /**
     * The application context to test.
     */
    private ServletApplicationContext context;

    /**
     * Sets up the test.
     */
    @Before
    public void setUp() {
        servletContext = createMock(ServletContext.class);
        context = new ServletApplicationContext(servletContext);
    }

    /**
     * Test method for {@link ServletApplicationContext#getContext()}.
     */
    @Test
    public void testGetContext() {
        replay(servletContext);
        assertEquals(servletContext, context.getContext());
        verify(servletContext);
    }

    /**
     * Test method for {@link ServletApplicationContext#getApplicationScope()}.
     */
    @Test
    public void testGetApplicationScope() {
        replay(servletContext);
        assertTrue(context.getApplicationScope() instanceof ScopeMap);
        verify(servletContext);
    }

    /**
     * Test method for {@link ServletApplicationContext#getInitParams()}.
     */
    @Test
    public void testGetInitParams() {
        replay(servletContext);
        assertTrue(context.getInitParams() instanceof ReadOnlyEnumerationMap);
        verify(servletContext);
    }

    /**
     * Test method for {@link ServletApplicationContext#getResource(String)}.
     *
     * @throws IOException If something goes wrong.
     */
    @Test
    public void testGetResource() throws IOException {
        URL url = new URL("file:///servletContext/my/path.html");
        URL urlFr = new URL("file:///servletContext/my/path_fr.html");
        expect(servletContext.getResource("/my/path.html")).andReturn(url);
        expect(servletContext.getResource("/my/path_fr.html")).andReturn(urlFr);
        expect(servletContext.getResource("/null/path.html")).andReturn(null);

        replay(servletContext);
        ApplicationResource resource = context.getResource("/my/path.html");
        assertNotNull(resource);
        assertEquals(resource.getLocalePath(), "/my/path.html");
        assertEquals(resource.getPath(), "/my/path.html");
        assertEquals(Locale.ROOT, resource.getLocale());
        ApplicationResource resourceFr = context.getResource(resource, Locale.FRENCH);
        assertNotNull(resourceFr);
        assertEquals("/my/path_fr.html", resourceFr.getLocalePath());
        assertEquals("/my/path.html", resourceFr.getPath());
        assertEquals(Locale.FRENCH, resourceFr.getLocale());
        ApplicationResource nullResource = context.getResource("/null/path.html");
        assertNull(nullResource);
        verify(servletContext);
    }

    /**
     * Test method for {@link ServletApplicationContext#getResources(String)}.
     *
     * @throws IOException If something goes wrong.
     */
    @Test
    public void testGetResources() throws IOException {
        URL url = new URL("file:///servletContext/my/path");
        expect(servletContext.getResource("/my/path")).andReturn(url);

        replay(servletContext);
        Collection<ApplicationResource> resources = context.getResources("/my/path");
        assertEquals(1, resources.size());
        assertEquals(resources.iterator().next().getLocalePath(), "/my/path");
        verify(servletContext);
    }
}
