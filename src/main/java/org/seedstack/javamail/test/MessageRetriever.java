/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.test;

import javax.mail.Message;

/**
 * This a helper interface for retrieving messages that where sent to the Wiser Server
 * its only purpose is alleviate the user from knowing about the MockServer used.
 */
public interface MessageRetriever {
    /**
     * @return the sent messages.
     */
    java.util.Collection<Message> getSentMessages();
}
