/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.internal;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import org.seedstack.javamail.JavaMailConfig;
import org.seedstack.javamail.spi.SessionConfigurer;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.shed.reflect.Classes;

import javax.mail.Session;
import java.util.Map;

/**
 * This plugin is responsible for providing injectable JavaMail @see (javax.mail.Session) sessions based on the different
 * protocol providers configured in the configuration files a number of instances will be created.
 */
public class JavaMailPlugin extends AbstractSeedPlugin {
    private final Map<String, Session> sessions = Maps.newHashMap();

    @Override
    public String name() {
        return "seed-mail-plugin";
    }

    @Override
    public InitState initialize(InitContext initContext) {
        JavaMailConfig javaMailConfig = getConfiguration(JavaMailConfig.class);

        SessionConfigurer configurer = new InternalSessionConfigurer(javaMailConfig);
        sessions.putAll(configurer.doConfigure());

        return InitState.INITIALIZED;
    }

    @Override
    public Object nativeUnitModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                install(new JavaMailModule(sessions));

                if (Classes.optional("org.subethamail.wiser.Wiser").isPresent()) {
                    install(new JavaMailITModule());
                }
            }
        };
    }
}
