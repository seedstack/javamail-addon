---
title: "Testing"
addon: "JavaMail"
repo: "https://github.com/seedstack/javamail-addon"
menu:
    JavaMailAddon:
        weight: 30
---

JavaMail add-on provides testing fixtures which enable to emulate an SMTP server and easily assert that your sent mails
are valid. To add the testing tools to your project, use the following dependency snippet:

    <dependency>
        <groupId>org.seedstack</groupId>
        <artifactId>seed-javamail-test</artifactId>
        <scope>test</scope>
    </dependency>
    
You can then use the `@WithMailServer` annotation and the `MessageRetriever` in your tests:

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
