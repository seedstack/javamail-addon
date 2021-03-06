/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.internal;

import com.google.inject.PrivateModule;
import com.google.inject.name.Names;

import javax.mail.Session;
import java.util.Map;

class JavaMailModule extends PrivateModule {
    private final Map<String, Session> sessions;

    JavaMailModule(Map<String, Session> sessions) {
        this.sessions = sessions;
    }

    protected void configure() {
        for (Map.Entry<String, Session> mailSession : sessions.entrySet()) {
            bind(Session.class).annotatedWith(Names.named(mailSession.getKey())).toInstance(mailSession.getValue());
            expose(Session.class).annotatedWith(Names.named(mailSession.getKey()));
        }
    }

}
