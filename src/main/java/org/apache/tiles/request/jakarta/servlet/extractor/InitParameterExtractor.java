/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tiles.request.jakarta.servlet.extractor;

import java.util.Enumeration;

import jakarta.servlet.ServletContext;

import org.apache.tiles.request.attribute.HasKeys;

/**
 * Extract initialization parameters from the servlet context.
 * <p>Copied from Apache tiles-request-servlet 1.0.7 and adapted for Jakarta EE 9 </p>
 *
 * @version $Rev$ $Date$
 */
public class InitParameterExtractor implements HasKeys<String> {

    /**
     * The servlet context.
     */
    private ServletContext context;

    /**
     * Constructor.
     *
     * @param context The servlet context.
     */
    public InitParameterExtractor(ServletContext context) {
        this.context = context;
    }

    /**
     * The enumeration of the keys in the stored attributes.
     *
     * @return The keys.
     */
    @Override
    public Enumeration<String> getKeys() {
        return context.getInitParameterNames();
    }

    /**
     * Returns the value of the attribute with the given key.
     *
     * @param key The key of the attribute.
     * @return The value.
     */
    @Override
    public String getValue(String key) {
        return context.getInitParameter(key);
    }

}
