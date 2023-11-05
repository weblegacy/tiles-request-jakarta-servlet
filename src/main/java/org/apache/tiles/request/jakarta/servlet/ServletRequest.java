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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.tiles.request.AbstractClientRequest;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.attribute.Addable;
import org.apache.tiles.request.collection.HeaderValuesMap;
import org.apache.tiles.request.collection.ReadOnlyEnumerationMap;
import org.apache.tiles.request.collection.ScopeMap;
import org.apache.tiles.request.jakarta.servlet.extractor.HeaderExtractor;
import org.apache.tiles.request.jakarta.servlet.extractor.ParameterExtractor;
import org.apache.tiles.request.jakarta.servlet.extractor.RequestScopeExtractor;
import org.apache.tiles.request.jakarta.servlet.extractor.SessionScopeExtractor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet-based implementation of the TilesApplicationContext interface.
 *
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for
 * Jakarta EE 9.</p>
 */
public class ServletRequest extends AbstractClientRequest {

    /**
     * The native available scopes: request, session and application.
     */
    private static final List<String> SCOPES
            = Collections.unmodifiableList(Arrays.asList(
                    REQUEST_SCOPE, "session", APPLICATION_SCOPE));

    /**
     * The request object to use.
     */
    private HttpServletRequest request;

    /**
     * The response object to use.
     */
    private HttpServletResponse response;

    /**
     * The response output stream, lazily initialized.
     */
    private OutputStream outputStream;

    /**
     * The response writer, lazily initialized.
     */
    private PrintWriter writer;

    /**
     * The lazily instantiated {@code Map} of header name-value combinations
     * (immutable).
     */
    private Map<String, String> header = null;

    /**
     * The lazily instantiated {@code Map} of header name-value combinations
     * (write-only).
     */
    private Addable<String> responseHeaders = null;

    /**
     * The lazily instantiated {@code Map} of header name-values combinations
     * (immutable).
     */
    private Map<String, String[]> headerValues = null;

    /**
     * The lazily instantiated {@code Map} of request parameter name-value.
     */
    private Map<String, String> param = null;

    /**
     * The lazily instantiated {@code Map} of request scope attributes.
     */
    private Map<String, Object> requestScope = null;

    /**
     * The lazily instantiated {@code Map} of session scope attributes.
     */
    private Map<String, Object> sessionScope = null;

    /**
     * Creates a new instance of ServletTilesRequestContext.
     *
     * @param applicationContext The application context.
     * @param request            The request object.
     * @param response           The response object.
     */
    public ServletRequest(
            ApplicationContext applicationContext,
            HttpServletRequest request, HttpServletResponse response) {

        super(applicationContext);
        this.request = request;
        this.response = response;
    }

    /**
     * Return an immutable Map that maps header names to the first (or only)
     * header value (as a String).
     *
     * @return The header map.
     */
    @Override
    public Map<String, String> getHeader() {
        if (header == null && request != null) {
            header = new ReadOnlyEnumerationMap<String>(
                    new HeaderExtractor(request, null));
        }

        return header;
    }

    /**
     * Return an add-able object that can be used to write headers to the
     * response.
     *
     * @return An add-able object.
     */
    @Override
    public Addable<String> getResponseHeaders() {
        if (responseHeaders == null && response != null) {
            responseHeaders = new HeaderExtractor(null, response);
        }

        return responseHeaders;
    }

    /**
     * Return an immutable Map that maps header names to the set of all values
     * specified in the request (as a String array). Header names must be
     * matched in a case-insensitive manner.
     *
     * @return The header values map.
     */
    @Override
    public Map<String, String[]> getHeaderValues() {
        if (headerValues == null && request != null) {
            headerValues = new HeaderValuesMap(
                    new HeaderExtractor(request, response));
        }

        return headerValues;
    }

    /**
     * Return an immutable Map that maps request parameter names to the first
     * (or only) value (as a String).
     *
     * @return The parameter map.
     */
    @Override
    public Map<String, String> getParam() {
        if (param == null && request != null) {
            param = new ReadOnlyEnumerationMap<String>(
                    new ParameterExtractor(request));
        }

        return param;
    }

    /**
     * Return an immutable Map that maps request parameter names to the set of
     * all values (as a String array).
     *
     * @return The parameter values map.
     */
    @Override
    public Map<String, String[]> getParamValues() {
        return request.getParameterMap();
    }

    /**
     * Returns a context map, given the scope name.
     *
     * <p>This method always return a map for all the scope names returned by
     * {@link #getAvailableScopes()}. That map may be writable, or immutable,
     * depending on the implementation.
     *
     * @param scope The name of the scope.
     *
     * @return The context.
     */
    @Override
    public Map<String, Object> getContext(String scope) {
        if (REQUEST_SCOPE.equals(scope)) {
            return getRequestScope();
        } else if ("session".equals(scope)) {
            return getSessionScope();
        } else if (APPLICATION_SCOPE.equals(scope)) {
            return getApplicationScope();
        }

        throw new IllegalArgumentException(scope + " does not exist. "
                + "Call getAvailableScopes() first to check.");
    }

    /**
     * Returns the context map from request scope.
     *
     * @return the context map from request scope
     */
    public Map<String, Object> getRequestScope() {
        if (requestScope == null && request != null) {
            requestScope = new ScopeMap(new RequestScopeExtractor(request));
        }

        return requestScope;
    }

    /**
     * Returns the context map from session scope.
     *
     * @return the context map from session scope
     */
    public Map<String, Object> getSessionScope() {
        if (sessionScope == null && request != null) {
            sessionScope = new ScopeMap(new SessionScopeExtractor(request));
        }

        return sessionScope;
    }

    /**
     * Returns all available scopes.
     *
     * <p>The scopes are ordered according to their lifetime, the innermost,
     * shorter lived scope appears first, and the outermost, longer lived scope
     * appears last. Besides, the scopes "request" and "application" always
     * included in the list.</p>
     *
     * @return All the available scopes.
     */
    @Override
    public List<String> getAvailableScopes() {
        return SCOPES;
    }

    /**
     * Forwards to a path.
     *
     * @param path The path to forward to.
     *
     * @throws IOException If something goes wrong when forwarding.
     */
    @Override
    public void doForward(String path) throws IOException {
        if (response.isCommitted()) {
            doInclude(path);
        } else {
            forward(path);
        }
    }

    /**
    * Includes the content of a resource (servlet, JSP page, HTML file) in the
    * response. In essence, this method enables programmatic server-side includes.
    *
    * @param path a {@code String} specifying the pathname to the resource. If
    *        it is relative, it must be relative against the current servlet.
    *
    * @throws IOException if the included resource throws this exception
    *
    * @see RequestDispatcher#include(jakarta.servlet.ServletRequest, ServletResponse)
    */
    public void doInclude(String path) throws IOException {
        RequestDispatcher rd = request.getRequestDispatcher(path);

        if (rd == null) {
            throw new IOException("No request dispatcher returned for path '"
                    + path + "'");
        }

        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            throw ServletUtil.wrapServletException(
                    ex, "ServletException including path '" + path + "'.");
        }
    }

    /**
     * Forwards to a path.
     *
     * @param path The path to forward to.
     *
     * @throws IOException If something goes wrong during the operation.
     */
    private void forward(String path) throws IOException {
        RequestDispatcher rd = request.getRequestDispatcher(path);

        if (rd == null) {
            throw new IOException("No request dispatcher returned for path '"
                    + path + "'");
        }

        try {
            rd.forward(request, response);
        } catch (ServletException ex) {
            throw ServletUtil.wrapServletException(
                    ex, "ServletException including path '" + path + "'.");
        }
    }

    /**
     * Returns a {@link jakarta.servlet.ServletOutputStream} suitable for
     * writing binary data in the response. The servlet container does not
     * encode the binary data.
     *
     * @return a {@link jakarta.servlet.ServletOutputStream} for writing binary
     *         data
     *
     * @throws IllegalStateException if the {@code getWriter} method has
     *                               been called on this response
     * @throws IOException           if an input or output exception occurred
     *
     * @see HttpServletResponse#getOutputStream()
     */
    public OutputStream getOutputStream() throws IOException {
        if (outputStream == null) {
            outputStream = response.getOutputStream();
        }

        return outputStream;
    }

    /**
     * Returns a {@code Writer} object that can send character text to the
     * client. The {@code Writer} uses the character encoding returned by
     * {@link HttpServletResponse#getCharacterEncoding()}. If the response's
     * character encoding has not been specified as described in
     * {@code getCharacterEncoding} (i.e., the method just returns the default
     * value {@code ISO-8859-1}), {@code getWriter} updates it to
     * {@code ISO-8859-1}.
     *
     * @return a {@code Writer} object that can return character data to the
     *         client
     *
     * @throws java.io.UnsupportedEncodingException if the character encoding
     *                               returned by {@code getCharacterEncoding}
     *                               cannot be used
     * @throws IllegalStateException if the {@code getOutputStream} method has
     *                               already been called for this response
     *                               object
     * @throws IOException           if an input or output exception occurred
     *
     * @see #getPrintWriter()
     * @see HttpServletResponse#getWriter()
     */
    public Writer getWriter() throws IOException {
        return getPrintWriter();
    }

    /**
     * Returns a {@code PrintWriter} object that can send character text to the
     * client. The {@code PrintWriter} uses the character encoding returned by
     * {@link HttpServletResponse#getCharacterEncoding()}. If the response's
     * character encoding has not been specified as described in
     * {@code getCharacterEncoding} (i.e., the method just returns the default
     * value {@code ISO-8859-1}), {@code getWriter} updates it to
     * {@code ISO-8859-1}.
     *
     * @return a {@code PrintWriter} object that can return character data to
     *         the client
     *
     * @throws java.io.UnsupportedEncodingException if the character encoding
     *                               returned by {@code getCharacterEncoding}
     *                               cannot be used
     * @throws IllegalStateException if the {@code getOutputStream} method has
     *                               already been called for this response
     *                               object
     * @throws IOException           if an input or output exception occurred
     *
     * @see HttpServletResponse#getWriter()
     */
    public PrintWriter getPrintWriter() throws IOException {
        if (writer == null) {
            writer = response.getWriter();
        }

        return writer;
    }

    /**
     * Returns a boolean indicating if the response has been committed. A
     * committed response has already had its status code and headers written.
     *
     * @return a boolean indicating if the response has been committed
     *
     * @see HttpServletResponse#isCommitted()
     */
    public boolean isResponseCommitted() {
        return response.isCommitted();
    }

    /**
     * Sets the content type of the response being sent to the client, if the
     * response has not been committed yet. The given content type may include
     * a character encoding specification, for example,
     * {@code>text/html;charset=UTF-8}. The response's character encoding is
     * only set from the given content type if this method is called before
     * {@code getWriter} is called.
     *
     * @param contentType a {@code String} specifying the MIME type of the
     *                    content
     *
     * @see HttpServletResponse#setContentType(String)
     */
    public void setContentType(String contentType) {
        response.setContentType(contentType);
    }

    /**
     * Returns the preferred {@code Locale} that the client will accept content
     * in, based on the Accept-Language header. If the client request doesn't
     * provide an Accept-Language header, this method returns the default
     * locale for the server.
     *
     * @return the preferred {@code Locale} for the client
     *
     * @see HttpServletRequest#getLocale()
     */
    public Locale getRequestLocale() {
        return request.getLocale();
    }

    /**
     * Returns the request object to use.
     *
     * @return the request object to use
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Returns the response object to use.
     *
     * @return the response object to use
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Returns a boolean indicating whether the authenticated user is included
     * in the specified logical "role". Roles and role membership can be
     * defined using deployment descriptors. If the user has not been
     * authenticated, the method returns {@code false}.
     *
     * @param role a {@code String} specifying the name of the role
     *
     * @return a {@code boolean} indicating whether the user making this
     *         request belongs to a given role; {@code false} if the user has
     *         not been authenticated
     *
     * @see HttpServletRequest#isUserInRole(String)
     */
    public boolean isUserInRole(String role) {
        return request.isUserInRole(role);
    }
}
