/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.internal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.seedstack.coffig.Coffig;
import org.seedstack.coffig.provider.JacksonProvider;
import org.seedstack.javamail.JavaMailConfig;
import org.seedstack.javamail.spi.SessionConfigurer;
import org.seedstack.shed.ClassLoaders;

import javax.mail.Session;
import java.net.URL;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BlockJUnit4ClassRunner.class)
public class InternalSessionConfigurerTest {
    private Map<String, Session> sessionsConfig;

    @Before
    public void setUp() throws Exception {
        JacksonProvider jacksonProvider = new JacksonProvider();
        URL testConfig = ClassLoaders.findMostCompleteClassLoader(InternalSessionConfigurerTest.class).getResource("test.yaml");
        if (testConfig == null) {
            throw new IllegalStateException("Unable to find test config");
        }
        jacksonProvider.addSource(testConfig);
        Coffig coffig = Coffig.builder().withProviders(jacksonProvider).build();
        SessionConfigurer configurer = new InternalSessionConfigurer(coffig.get(JavaMailConfig.class));
        this.sessionsConfig = configurer.doConfigure();
    }

    @Test
    public void testDoConfigure() throws Exception {
        assertThat(sessionsConfig).isNotNull();
        assertThat(sessionsConfig).isNotEmpty();
        assertThat(sessionsConfig).hasSize(4);
    }


    @Test
    public void test_smtp_session_configuration_is_present() {
        assertThat(sessionsConfig).containsKey("smtp");
        final Session smtp = sessionsConfig.get("smtp");
        assertThat(smtp).isNotNull();
        assertThat(smtp.getProperty("mail.smtp.host")).isEqualTo("testserver");
        assertThat(smtp.getProperty("mail.smtp.auth")).isEqualTo("true");
        assertThat(smtp.getProperty("mail.smtp.user")).isEqualTo("testuser");
        assertThat(smtp.getProperty("mail.smtp.password")).isEqualTo("testpw");
    }

    @Test
    public void test_smtp2_session_configuration_is_present() {
        assertThat(sessionsConfig).containsKey("smtp2");
        final Session smtp2 = sessionsConfig.get("smtp2");
        assertThat(smtp2).isNotNull();
        assertThat(smtp2.getProperty("mail.smtp.host")).isEqualTo("testserver2");
        assertThat(smtp2.getProperty("mail.smtp.auth")).isEqualTo("true");
        assertThat(smtp2.getProperty("mail.smtp.user")).isEqualTo("testuser2");
        assertThat(smtp2.getProperty("mail.smtp.password")).isEqualTo("testpw2");
    }

    @Test
    public void test_imap_session_configuration_is_present() {
        assertThat(sessionsConfig).containsKey("imap");
        final Session imap = sessionsConfig.get("imap");
        assertThat(imap).isNotNull();
        assertThat(imap.getProperty("mail.imap.user")).isEqualTo("toto_user@ext.mpsa.com");
        assertThat(imap.getProperty("mail.imap.host")).isEqualTo("testserver3");
        assertThat(imap.getProperty("mail.imap.auth.login.disable")).isEqualTo(Boolean.FALSE.toString());
        assertThat(imap.getProperty("mail.imap.auth.plain.disable")).isEqualTo(Boolean.TRUE.toString());
    }

    @Test
    public void test_pop3_session_configuration_is_present() {
        assertThat(sessionsConfig).containsKey("pop3");
        final Session pop3 = sessionsConfig.get("pop3");
        assertThat(pop3.getProperty("mail.pop3.host")).isEqualTo("testserver4");
        assertThat(pop3).isNotNull();
    }
}