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

import jakarta.servlet.ServletContext;

import org.apache.tiles.request.attribute.AttributeExtractor;

/**
 * Extract attributes from application scope.
 *
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for
 * Jakarta EE 9.</p>
 */
public class ApplicationScopeExtractor implements AttributeExtractor {

    /**
     * The servlet context.
     */
    private ServletContext context;

    /**
     * Constructor.
     *
     * @param context The servlet context.
     */
    public ApplicationScopeExtractor(ServletContext context) {
        this.context = context;
    }

    /**
     * Sets a value for the given key.
     *
     * @param name  The key of the attribute.
     * @param value The value of the attribute.
     */
    @Override
    public void setValue(String name, Object value) {
        context.setAttribute(name, value);
    }

    /**
     * Removes an attribute.
     *
     * @param name The key of the attribute to remove.
     */
    @Override
    public void removeValue(String name) {
        context.removeAttribute(name);
    }

    /**
     * The enumeration of the keys of the stored attributes.
     *
     * @return The keys.
     */
    @Override
    public Enumeration<String> getKeys() {
        return context.getAttributeNames();
    }

    /**
     * Returns the value of the attribute with the given key.
     *
     * @param key The key of the attribute.
     *
     * @return The value.
     */
    @Override
    public Object getValue(String key) {
        return context.getAttribute(key);
    }
}
