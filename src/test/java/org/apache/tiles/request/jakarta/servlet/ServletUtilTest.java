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


import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

import org.apache.tiles.request.ApplicationAccess;
import org.apache.tiles.request.ApplicationContext;
import org.junit.Test;

/**
 * Tests {@link ServletUtil}.
 *
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for Jakarta EE 9.</p>
 */
public class ServletUtilTest {

    /**
     * Test method for {@link ServletUtil#wrapServletException(ServletException, String)}.
     */
    @Test
    public void testWrapServletException() {
        ServletException servletException = new ServletException();
        IOException exception = ServletUtil.wrapServletException(servletException, "my message");
        assertEquals(servletException, exception.getCause());
        assertEquals("my message", exception.getMessage());
    }

    /**
     */
    @Test
    public void testWrapServletExceptionWithCause() {
        Throwable cause = createMock(Throwable.class);

        replay(cause);
        ServletException servletException = new ServletException(cause);
        IOException exception = ServletUtil.wrapServletException(servletException, "my message");
        assertEquals(cause, exception.getCause());
        assertEquals("my message", exception.getMessage());
        verify(cause);
    }

    /**
     * Test method for {@link ServletUtil#getApplicationContext(ServletContext)}.
     */
    @Test
    public void testGetApplicationContext() {
        ServletContext servletContext = createMock(ServletContext.class);
        ApplicationContext applicationContext = createMock(ApplicationContext.class);

        expect(servletContext.getAttribute(ApplicationAccess
                .APPLICATION_CONTEXT_ATTRIBUTE)).andReturn(applicationContext);

        replay(servletContext, applicationContext);
        assertEquals(applicationContext, ServletUtil.getApplicationContext(servletContext));
        verify(servletContext, applicationContext);
    }
}
