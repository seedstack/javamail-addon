/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.test;

import javax.mail.Message;
import javax.mail.Session;

/**
 * Assertion class over mail server.
 */
public final class MockMailServerAssertions {
    private MockMailServerAssertions() {
    }

    /**
     * Creates an assertion over a message.
     *
     * @param message the message to assert.
     * @return the assertion.
     */
    public static MailMessagesAssertions assertThat(Message message) {
        return new MailMessagesAssertions(message);
    }

    /**
     * Creates an assertion over a java mail session.
     *
     * @param <S>     The type of java mail session.
     * @param session the session to assert.
     * @return the assertion.
     */
    public static <S extends Session> MailSessionAssertions assertThat(S session) {
        return new MailSessionAssertions<S>(session);
    }
}
