/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.spi;

import javax.mail.Session;
import java.util.Map;

/**
 * Interface for handling session configuration. Must be implemented by classes wanting to provide configured mail
 * sessions.
 */
public interface SessionConfigurer {
    /**
     * This method must return the mail sessions to configure.
     *
     * @return a map of sessions with provider name as key and the given session as value
     */
    Map<String, Session> doConfigure();
}
