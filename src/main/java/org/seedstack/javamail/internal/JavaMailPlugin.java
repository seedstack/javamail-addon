/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.internal;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.core.AbstractPlugin;
import org.apache.commons.configuration.Configuration;
import org.seedstack.javamail.spi.SessionConfigurer;
import org.seedstack.seed.core.spi.configuration.ConfigurationProvider;
import org.seedstack.seed.core.utils.SeedReflectionUtils;

import javax.mail.Session;
import java.util.Collection;
import java.util.Map;

/**
 * This plugin is responsible for providing injectable JavaMail @see (javax.mail.Session) sessions based on the different
 * protocol providers configured in the configuration files a number of instances will be created.
 *
 * @author aymen.benhmida@ext.mpsa.com
 */
public class JavaMailPlugin extends AbstractPlugin {
    public static final String CONFIGURATION_PREFIX = "org.seedstack.seed.mail";

    private final Map<String, Session> sessions = Maps.newHashMap();

    @Override
    public String name() {
        return "seed-mail-plugin";
    }

    @Override
    public InitState init(InitContext initContext) {
        Configuration mailSessionsConfiguration = initContext.dependency(ConfigurationProvider.class)
                .getConfiguration().subset(JavaMailPlugin.CONFIGURATION_PREFIX);

        SessionConfigurer configurer = new PropertyFileSessionConfigurer(mailSessionsConfiguration);
        sessions.putAll(configurer.doConfigure());

        return InitState.INITIALIZED;
    }

    @Override
    public Object nativeUnitModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                install(new JavaMailModule(sessions));

                if (SeedReflectionUtils.isClassPresent("org.subethamail.wiser.Wiser")) {
                    install(new JavaMailITModule());
                }
            }
        };
    }

    @Override
    public Collection<Class<?>> requiredPlugins() {
        return Lists.<Class<?>>newArrayList(ConfigurationProvider.class);
    }
}
