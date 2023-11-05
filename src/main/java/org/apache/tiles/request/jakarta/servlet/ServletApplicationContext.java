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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.ApplicationResource;
import org.apache.tiles.request.collection.ReadOnlyEnumerationMap;
import org.apache.tiles.request.collection.ScopeMap;
import org.apache.tiles.request.jakarta.servlet.extractor.ApplicationScopeExtractor;
import org.apache.tiles.request.jakarta.servlet.extractor.InitParameterExtractor;
import org.apache.tiles.request.locale.URLApplicationResource;

import jakarta.servlet.ServletContext;

/**
 * Servlet-based implementation of the TilesApplicationContext interface.
 *
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for
 * Jakarta EE 9.</p>
 */
public class ServletApplicationContext implements ApplicationContext {

    /**
     * The servlet context to use.
     */
    private ServletContext servletContext;

    /**
     * The lazily instantiated {@code Map} of application scope attributes.
     */
    private Map<String, Object> applicationScope = null;

    /**
     * The lazily instantiated {@code Map} of context initialization parameters.
     */
    private Map<String, String> initParam = null;

    /**
     * Creates a new instance of ServletTilesApplicationContext.
     *
     * @param servletContext The servlet context to use.
     */
    public ServletApplicationContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * Returns the servlet context to use.
     *
     * @return the servlet context to use
     */
    public Object getContext() {
        return servletContext;
    }

    /**
     * Returns the context map from application scope.
     *
     * @return the context map from application scope
     */
    public Map<String, Object> getApplicationScope() {

        if ((applicationScope == null) && (servletContext != null)) {
            applicationScope = new ScopeMap(
                    new ApplicationScopeExtractor(servletContext));
        }
        return (applicationScope);

    }

    /**
     * Return an immutable Map that maps context application initialization
     * parameters to their values.
     *
     * @return initialization parameters
     */
    public Map<String, String> getInitParams() {

        if ((initParam == null) && (servletContext != null)) {
            initParam = new ReadOnlyEnumerationMap<String>(
                    new InitParameterExtractor(servletContext));
        }
        return (initParam);

    }

    /**
     * Return the application resource mapped to the specified path.
     *
     * @param localePath path to the desired resource, including the Locale
     *                   suffix.
     *
     * @return the first located resource which matches the given path or null
     *         if no such resource exists.
     */
    public ApplicationResource getResource(String localePath) {
        try {
            URL url = servletContext.getResource(localePath);
            if (url != null) {
                return new URLApplicationResource(localePath, url);
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Return a localized version of an ApplicationResource.
     *
     * @param base   the ApplicationResource.
     * @param locale the desired Locale.
     *
     * @return the first located resource which matches the given path or null
     *         if no such resource exists.
     */
    public ApplicationResource getResource(ApplicationResource base, Locale locale) {
        try {
            URL url = servletContext.getResource(base.getLocalePath(locale));
            if (url != null) {
                return new URLApplicationResource(base.getPath(), locale, url);
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Return the application resources mapped to the specified path.
     *
     * @param path to the desired resource.
     *
     * @return all resources which match the given path.
     */
    public Collection<ApplicationResource> getResources(String path) {
        ArrayList<ApplicationResource> resources = new ArrayList<ApplicationResource>();
        resources.add(getResource(path));
        return resources;
    }
}
