---
title: "Overview"
addon: "JavaMail"
repo: "https://github.com/seedstack/javamail-addon"
author: "SeedStack"
description: "Integrates JavaMail (JSR 919) with SeedStack. Allows sending and receiving of e-mail messages over POP3, IMAP and SMTP. Provides testing tools."
min-version: "15.7+"
menu:
    JavaMailAddon:
        weight: 10
---

The JavaMail add-on integrates JavaMail API (JSR 919) with SeedStack. To add the JavaMail add-on to your project, use
the following dependency snippet:

<{{< dependency "org.seedstack.addons.javamail" "javamail" >}}

You may also need to add the JavaMail API (depending on your runtime environment):
    
       <dependency>
            <groupId>javax.mail</groupId>
            <artifactId>mail</artifactId>
            <version>1.4.7</version>
            <scope>provided</scope>
        </dependency>

# Configuration

You can configure mail providers by using the following configuration:

    org.seedstack.seed.mail.providers = myProvider1, myProvider2, myProvider3

## SMTP

To configure a provider as an SMTP one, use the following configuration:

    [org.seedstack.seed.mail.provider.myProvider1.property]
    mail.transport.protocol = smtp
    mail.smtp.host = ...
    mail.smtp.port = ...
    mail.smtp.auth = ...
    mail.smtp.user = ...
    mail.smtp.password = ...
    ...

Any property specified here will be used to configure the corresponding JAVA mail session.

## IMAP

To configure a provider as an IMAP one, use the following configuration:

    [org.seedstack.seed.mail.provider.myProvider2.property]
    mail.store.protocol = imap
    mail.imap.user = ...
    mail.imap.host = ...
    mail.imap.port = ...
    mail.imap.auth.login.disable = ...
    mail.imap.auth.plain.disable = ...
    ...

Any property specified here will be used to configure the corresponding JAVA mail session.

## POP3

To configure a provider as a POP3 one, use the following configuration:

    [org.seedstack.seed.mail.provider.myProvider3.property]
    mail.store.protocol = pop3
    mail.pop3.user = ...
    mail.pop3.host = ...
    mail.pop3.port = ...
    ...

Any property specified here will be used to configure the corresponding JAVA mail session.

# Testing

JavaMail add-on provides testing fixtures which enable to emulate an SMTP server and easily assert that your sent mails
are valid. You can then use the {{< java "org.seedstack.javamail.test.WithMailServer" "@" >}} annotation and the
{{< java "org.seedstack.javamail.test.MessageRetriever" >}} in your tests:

    @WithMailServer(host = "localhost", port = 6457)
    public class SmtpIT extends AbstractSeedIT {
        @Inject
        @Named("smtp-test")
        Session smtpSession;

        @Inject
        MessageRetriever retriever;

        @Test
        public void test_send() throws MessagingException {
            Transport transport = null;
            try {
                Message message = new MimeMessage(smtpSession);
                message.setRecipient(Message.RecipientType.TO, new InternetAddress("..."));
                message.setFrom(new InternetAddress("..."));
                message.setSubject("...");
                message.setText("...");
                message.setSentDate(new Date());

                transport = smtpSession.getTransport();
                transport.connect();
                transport.sendMessage(message, message.getAllRecipients());
            } finally {
                if (transport != null) {
                    transport.close();
                }
            }

            for (Message message : retriever.getSentMessages()) {
                MockMailServerAssertions.assertThat(message).hasRecipients(Message.RecipientType.TO);
                MockMailServerAssertions.assertThat(message).recipientEqualsTo(Message.RecipientType.TO, InternetAddress.parse(TestConstantsValues.DEFAULT_RECIPIENT));
            }
        }
    }

The following configuration is needed to define the `smtp-test` session to the corresponding mock mail server:

    [org.seedstack.seed]
    mail.providers= smtp-test

    [org.seedstack.seed.mail.provider.smtp-test.property]
    mail.transport.protocol = smtp
    mail.smtp.host = localhost
    mail.smtp.port = 6457
