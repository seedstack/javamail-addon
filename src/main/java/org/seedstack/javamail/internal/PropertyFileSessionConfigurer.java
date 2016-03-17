/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.internal;

import com.google.common.collect.Maps;
import org.apache.commons.configuration.Configuration;
import org.seedstack.javamail.spi.SessionConfigurer;

import javax.mail.Session;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

class PropertyFileSessionConfigurer implements SessionConfigurer {
    private final Configuration configuration;

    PropertyFileSessionConfigurer(Configuration mailSessionsConfiguration) {
        this.configuration = mailSessionsConfiguration;
    }

    @Override
    public Map<String, Session> doConfigure() {
        Map<String, Session> sessions = Maps.newHashMap();
        String mailProviders[] = configuration.getStringArray("providers");

        if (mailProviders != null) {
            for (String provider : mailProviders) {
                Configuration mailProviderConfig = configuration.subset(String.format("provider.%s.property", provider));

                Properties mailProviderProperties = new Properties();
                Iterator<String> keys = mailProviderConfig.getKeys();
                while (keys.hasNext()) {
                    String configuredProperty = keys.next();
                    mailProviderProperties.put(configuredProperty, mailProviderConfig.getProperty(configuredProperty));
                }

                Session session = Session.getInstance(mailProviderProperties);
                sessions.put(provider, session);
            }
        }

        return sessions;
    }
}
