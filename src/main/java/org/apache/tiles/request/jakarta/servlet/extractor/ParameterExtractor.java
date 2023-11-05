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

import jakarta.servlet.http.HttpServletRequest;

import org.apache.tiles.request.attribute.HasKeys;

/**
 * Extract parameters from the request.
 *
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for
 * Jakarta EE 9.</p>
 */
public class ParameterExtractor implements HasKeys<String> {

    /**
     * The servlet request.
     */
    private HttpServletRequest request;

    /**
     * Constructor.
     *
     * @param request The servlet request.
     */
    public ParameterExtractor(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * The enumeration of the keys in the stored attributes.
     *
     * @return The keys.
     */
    @Override
    public Enumeration<String> getKeys() {
        return request.getParameterNames();
    }

    /**
     * Returns the value of the attribute with the given key.
     *
     * @param key The key of the attribute.
     *
     * @return The value.
     */
    @Override
    public String getValue(String key) {
        return request.getParameter(key);
    }
}
