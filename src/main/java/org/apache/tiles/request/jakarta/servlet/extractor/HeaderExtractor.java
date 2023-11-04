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
import jakarta.servlet.http.HttpServletResponse;

import org.apache.tiles.request.attribute.EnumeratedValuesExtractor;

/**
 * Extract header values from an HTTP request.
 * <p>Copied from Apache tiles-request-servlet 1.0.7
 * and adapted for Jakarta EE 9 </p>
 *
 * @version $Rev$ $Date$
 */
public class HeaderExtractor implements EnumeratedValuesExtractor {

    /**
     * The request.
     */
    private HttpServletRequest request;

    /**
     * The response.
     */
    private HttpServletResponse response;

    /**
     * Constructor.
     *
     * @param request The request.
     * @param response The response.
     */
    public HeaderExtractor(HttpServletRequest request,
            HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * The enumeration of the keys in the stored attributes.
     *
     * @return The keys.
     */
    @Override
    public Enumeration<String> getKeys() {
        return request.getHeaderNames();
   }

    /**
     * Returns the value of the attribute with the given key.
     *
     * @param key The key of the attribute.
     * @return The value.
     */
    @Override
    public String getValue(String key) {
        return request.getHeader(key);
    }

    /**
     * Returns the values stored at the given key.
     *
     * @param key The key of the attribute.
     * @return The values of the attribute.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Enumeration<String> getValues(String key) {
        return request.getHeaders(key);
    }

    /**
     * Sets a value for the given key.
     *
     * @param key The key of the attribute.
     * @param value The value of the attribute.
     */
    @Override
    public void setValue(String key, String value) {
        response.setHeader(key, value);
    }
}
