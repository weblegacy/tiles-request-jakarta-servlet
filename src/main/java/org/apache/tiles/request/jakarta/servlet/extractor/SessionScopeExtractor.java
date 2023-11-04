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

import java.util.Enumeration;
import java.util.Collections;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.tiles.request.attribute.AttributeExtractor;

/**
 * Extract attributes from session scope.
 * <p>Copied from Apache tiles-request-servlet 1.0.7
 * and adapted for Jakarta EE 9 </p>
 *
 * @version $Rev$ $Date$
 */
public class SessionScopeExtractor implements AttributeExtractor {

    /**
     * The servlet request.
     */
    private HttpServletRequest request;

    /**
     * Constructor.
     *
     * @param request The servlet request.
     */
    public SessionScopeExtractor(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Sets a value for the given key.
     *
     * @param name The key of the attribute.
     * @param value The value of the attribute.
     */
    @Override
    public void setValue(String name, Object value) {
        request.getSession().setAttribute(name, value);
    }

    /**
     * Removes an attribute.
     *
     * @param name The key of the attribute to remove.
     */
    @Override
    public void removeValue(String name) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(name);
        }
    }

    /**
     * The enumeration of the keys in the stored attributes.
     *
     * @return The keys.
     */
    @Override
    public Enumeration<String> getKeys() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getAttributeNames();
        }
        return Collections.enumeration(Collections.<String>emptySet());
    }

    /**
     * Returns the value of the attribute with the given key.
     *
     * @param key The key of the attribute.
     * @return The value.
     */
    @Override
    public Object getValue(String key) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }
}
