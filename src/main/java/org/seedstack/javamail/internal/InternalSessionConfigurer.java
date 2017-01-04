/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.internal;

import org.seedstack.javamail.JavaMailConfig;
import org.seedstack.javamail.spi.SessionConfigurer;

import javax.mail.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class InternalSessionConfigurer implements SessionConfigurer {
    private final JavaMailConfig javaMailConfig;

    InternalSessionConfigurer(JavaMailConfig javaMailConfig) {
        this.javaMailConfig = javaMailConfig;
    }

    @Override
    public Map<String, Session> doConfigure() {
        Map<String, Session> sessions = new HashMap<>();
        for (Map.Entry<String, Properties> providerEntry : javaMailConfig.getProviders().entrySet()) {
            sessions.put(providerEntry.getKey(), Session.getInstance(providerEntry.getValue()));
        }
        return sessions;
    }
}
