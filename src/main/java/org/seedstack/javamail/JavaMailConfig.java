/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail;

import org.seedstack.coffig.Config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Config("javamail")
public class JavaMailConfig {
    private Map<String, Properties> providers = new HashMap<>();

    public Map<String, Properties> getProviders() {
        return Collections.unmodifiableMap(providers);
    }

    public JavaMailConfig addProviders(String name, Properties properties) {
        this.providers = providers;
        return this;
    }
}
