/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.internal;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.seedstack.javamail.test.MailMessagesAssertions;
import org.seedstack.javamail.test.MessageRetriever;
import org.subethamail.wiser.Wiser;

class JavaMailITModule extends AbstractModule {
    @Override
    protected void configure() {
        requestStaticInjection(MailMessagesAssertions.class);

        bind(JavaMailITClassRule.class);
        bind(Wiser.class).in(Scopes.SINGLETON);
        bind(MessageRetriever.class).to(WiserMessageRetriever.class).in(Scopes.SINGLETON);
    }
}
