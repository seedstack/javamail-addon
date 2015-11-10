/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.internal;

import com.google.common.collect.Lists;
import io.nuun.kernel.api.plugin.context.InitContext;
import org.apache.commons.configuration.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.seedstack.seed.core.spi.configuration.ConfigurationProvider;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JavaMailPluginTest {

    private static JavaMailPlugin mailPlugin;

    @Mock
    private InitContext initContext;

    @Mock
    private Configuration configuration;

    @Mock
    private ConfigurationProvider configurationProvider;

    @BeforeClass
    public static void setUp() throws Exception {
        mailPlugin = new JavaMailPlugin();
    }

    @Test
    public void test_init() throws Exception {
        configureMocks();
        mailPlugin.init(initContext);
    }

    @Test
    public void test_Required_Plugins() throws Exception {
        final Collection<Class<?>> plugins = mailPlugin.requiredPlugins();
        assertThat(plugins).containsOnly(ConfigurationProvider.class);
    }

    private void configureMocks() {
        when(initContext.dependency(ConfigurationProvider.class)).thenReturn(configurationProvider);
        when(configurationProvider.getConfiguration()).thenReturn(configuration);
        when(configuration.getList(anyString())).thenReturn(Lists.newArrayList());
        when(configuration.subset(anyString())).thenReturn(configuration);
    }
}