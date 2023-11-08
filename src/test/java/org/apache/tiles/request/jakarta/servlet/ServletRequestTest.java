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
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.collection.HeaderValuesMap;
import org.apache.tiles.request.collection.ReadOnlyEnumerationMap;
import org.apache.tiles.request.collection.ScopeMap;
import org.apache.tiles.request.jakarta.servlet.extractor.HeaderExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Tests {@link ServletRequest}.
 *
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for
 * Jakarta EE 9.</p>
 */
public class ServletRequestTest {

    /**
     * Empty default constructor
     */
    public ServletRequestTest() {
    }

    /**
     * The application context.
     */
    private ApplicationContext applicationContext;

    /**
     * The request.
     */
    private HttpServletRequest request;

    /**
     * The response.
     */
    private HttpServletResponse response;

    /**
     * The request to test.
     */
    private ServletRequest req;

    /**
     * Sets up the test.
     */
    @BeforeEach
    public void setUp() {
        applicationContext = createMock(ApplicationContext.class);
        request = createMock(HttpServletRequest.class);
        response = createMock(HttpServletResponse.class);
        req = new ServletRequest(applicationContext, request, response);
    }

    /**
     * Test method for {@link ServletRequest#doForward(String)}.
     */
    @Test
    public void testDoForward() {
        assertDoesNotThrow(() -> {
            RequestDispatcher rd = createMock(RequestDispatcher.class);

            expect(response.isCommitted()).andReturn(false);
            expect(request.getRequestDispatcher("/my/path")).andReturn(rd);
            rd.forward(request, response);

            replay(applicationContext, request, response, rd);
            req.doForward("/my/path");
            verify(applicationContext, request, response, rd);
        });
    }

    /**
     * Test method for {@link ServletRequest#doForward(String)}.
     */
    @Test
    public void testDoForwardNoDispatcher() {
        assertThrowsExactly(IOException.class, () -> {
            expect(response.isCommitted()).andReturn(false);
            expect(request.getRequestDispatcher("/my/path")).andReturn(null);

            replay(applicationContext, request, response);
            try {
                req.doForward("/my/path");
            } finally {
                verify(applicationContext, request, response);
            }
        });
    }

    /**
     * Test method for {@link ServletRequest#doForward(String)}.
     */
    @Test
    public void testDoForwardServletException() {
        assertThrowsExactly(IOException.class, () -> {
            RequestDispatcher rd = createMock(RequestDispatcher.class);

            expect(response.isCommitted()).andReturn(false);
            expect(request.getRequestDispatcher("/my/path")).andReturn(rd);
            rd.forward(request, response);
            expectLastCall().andThrow(new ServletException());

            replay(applicationContext, request, response, rd);
            try {
                req.doForward("/my/path");
            } finally {
                verify(applicationContext, request, response, rd);
            }
        });
    }

    /**
     * Test method for {@link ServletRequest#doForward(String)}.
     */
    @Test
    public void testDoForwardInclude() {
        assertDoesNotThrow(() -> {
            RequestDispatcher rd = createMock(RequestDispatcher.class);

            expect(response.isCommitted()).andReturn(true);
            expect(request.getRequestDispatcher("/my/path")).andReturn(rd);
            rd.include(request, response);

            replay(applicationContext, request, response, rd);
            req.doForward("/my/path");
            verify(applicationContext, request, response, rd);
        });
    }

    /**
     * Test method for {@link ServletRequest#doInclude(String)}.
     */
    @Test
    public void testDoInclude() {
        assertDoesNotThrow(() -> {
            RequestDispatcher rd = createMock(RequestDispatcher.class);

            expect(request.getRequestDispatcher("/my/path")).andReturn(rd);
            rd.include(request, response);

            replay(applicationContext, request, response, rd);
            req.doInclude("/my/path");
            verify(applicationContext, request, response, rd);
        });
    }

    /**
     * Test method for {@link ServletRequest#doInclude(String)}.
     */
    @Test
    public void testDoIncludeNoDispatcher() {
        assertThrowsExactly(IOException.class, () -> {
            expect(request.getRequestDispatcher("/my/path")).andReturn(null);

            replay(applicationContext, request, response);
            try {
                req.doInclude("/my/path");
            } finally {
                verify(applicationContext, request, response);
            }
        });
    }

    /**
     * Test method for {@link ServletRequest#doInclude(String)}.
     */
    @Test
    public void testDoIncludeServletException() {
        assertThrowsExactly(IOException.class, () -> {
            RequestDispatcher rd = createMock(RequestDispatcher.class);

            expect(request.getRequestDispatcher("/my/path")).andReturn(rd);
            rd.include(request, response);
            expectLastCall().andThrow(new ServletException());

            replay(applicationContext, request, response, rd);
            try {
                req.doInclude("/my/path");
            } finally {
                verify(applicationContext, request, response, rd);
            }
        });
    }

    /**
     * Test method for {@link ServletRequest#getHeader()}.
     */
    @Test
    public void testGetHeader() {
        assertInstanceOf(ReadOnlyEnumerationMap.class, req.getHeader());
    }

    /**
     * Test method for {@link ServletRequest#getHeader()}.
     */
    @Test
    public void testGetResponseHeaders() {
        assertInstanceOf(HeaderExtractor.class, req.getResponseHeaders() );
    }

    /**
     * Test method for {@link ServletRequest#getHeaderValues()}.
     */
    @Test
    public void testGetHeaderValues() {
        assertInstanceOf(HeaderValuesMap.class, req.getHeaderValues());
    }

    /**
     * Test method for {@link ServletRequest#getParam()}.
     */
    @Test
    public void testGetParam() {
        assertInstanceOf(ReadOnlyEnumerationMap.class, req.getParam());
    }

    /**
     * Test method for {@link ServletRequest#getParamValues()}.
     */
    @Test
    public void testGetParamValues() {
        Map<String, String[]> paramMap = createMock(Map.class);

        expect(request.getParameterMap()).andReturn(paramMap);

        replay(applicationContext, request, response, paramMap);
        assertEquals(paramMap, req.getParamValues());
        verify(applicationContext, request, response, paramMap);
    }

    /**
     * Test method for {@link ServletRequest#getRequestScope()}.
     */
    @Test
    public void testGetRequestScope() {
        assertInstanceOf(ScopeMap.class, req.getRequestScope());
    }

    /**
     * Test method for {@link ServletRequest#getSessionScope()}.
     */
    @Test
    public void testGetSessionScope() {
        assertInstanceOf(ScopeMap.class, req.getSessionScope());
    }

    /**
     * Test method for {@link ServletRequest#getOutputStream()}.
     */
    @Test
    public void testGetOutputStream() {
        assertDoesNotThrow(() -> {
            ServletOutputStream os = createMock(ServletOutputStream.class);

            expect(response.getOutputStream()).andReturn(os);

            replay(applicationContext, request, response, os);
            assertEquals(req.getOutputStream(), os);
            verify(applicationContext, request, response, os);
        });
    }

    /**
     * Test method for {@link ServletRequest#getWriter()}.
     */
    @Test
    public void testGetWriter() {
        assertDoesNotThrow(() -> {
            PrintWriter os = createMock(PrintWriter.class);

            expect(response.getWriter()).andReturn(os);

            replay(applicationContext, request, response, os);
            assertEquals(req.getWriter(), os);
            verify(applicationContext, request, response, os);
        });
    }

    /**
     * Test method for {@link ServletRequest#getPrintWriter()}.
     */
    @Test
    public void testGetPrintWriter() {
        assertDoesNotThrow(() -> {
            PrintWriter os = createMock(PrintWriter.class);

            expect(response.getWriter()).andReturn(os);

            replay(applicationContext, request, response, os);
            assertEquals(req.getPrintWriter(), os);
            verify(applicationContext, request, response, os);
        });
    }

    /**
     * Test method for {@link ServletRequest#isResponseCommitted()}.
     */
    @Test
    public void testIsResponseCommitted() {
        expect(response.isCommitted()).andReturn(true);

        replay(applicationContext, request, response);
        assertTrue(req.isResponseCommitted());
        verify(applicationContext, request, response);
    }

    /**
     * Test method for {@link ServletRequest#setContentType(String)}.
     */
    @Test
    public void testSetContentType() {
        response.setContentType("text/html");

        replay(applicationContext, request, response);
        req.setContentType("text/html");
        verify(applicationContext, request, response);
    }

    /**
     * Test method for {@link ServletRequest#getRequestLocale()}.
     */
    @Test
    public void testGetRequestLocale() {
        Locale locale = Locale.ITALY;

        expect(request.getLocale()).andReturn(locale);

        replay(applicationContext, request, response);
        assertEquals(locale, req.getRequestLocale());
        verify(applicationContext, request, response);
    }

    /**
     * Test method for {@link ServletRequest#getRequest()}.
     */
    @Test
    public void testGetRequest() {
        replay(applicationContext, request, response);
        assertEquals(request, req.getRequest());
        verify(applicationContext, request, response);
    }

    /**
     * Test method for {@link ServletRequest#getResponse()}.
     */
    @Test
    public void testGetResponse() {
        replay(applicationContext, request, response);
        assertEquals(response, req.getResponse());
        verify(applicationContext, request, response);
    }

    /**
     * Test method for {@link ServletRequest#isUserInRole(String)}.
     */
    @Test
    public void testIsUserInRole() {
        expect(request.isUserInRole("myrole")).andReturn(true);

        replay(applicationContext, request, response);
        assertTrue(req.isUserInRole("myrole"));
        verify(applicationContext, request, response);
    }
}
