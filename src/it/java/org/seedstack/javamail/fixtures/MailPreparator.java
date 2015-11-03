/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.fixtures;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public class MailPreparator {
    private Session session;

    public MailPreparator(Session session) {
        this.session = session;
    }

    public Message prepareMessageToBeSent(String to, String from, String subject, String text) throws MessagingException {
        Message m = new MimeMessage(session);
        m.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        m.setFrom(new InternetAddress(from));
        m.setSubject(subject);
        m.setText(text);
        // m.setContent("Test Content","plain/text");
        m.setSentDate(new Date());
        return m;
    }
}
