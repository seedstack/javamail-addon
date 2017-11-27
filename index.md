---
title: "JavaMail"
addon: "JavaMail"
repo: "https://github.com/seedstack/javamail-addon"
author: Aymen BENHMIDA
description: "Integrates JavaMail (JSR 919) for sending and receiving of e-mail messages over POP3, IMAP and SMTP."
zones:
    - Addons
noMenu: true    
---

The JavaMail add-on integrates JavaMail API (JSR 919) with SeedStack.<!--more--> 

## Dependency

{{< dependency g="org.seedstack.addons.javamail" a="javamail" >}}

You also need to add the JavaMail API itself:

{{< dependency g="javax.mail" a="mail" >}}

Note that you need to specify the scope as `provided` if your runtime environment already provides the JavaMail API 
(such as JEE servers).

## Configuration

Configuration is done by declaring one or more mail providers:

{{% config p="javamail" %}}
```yaml
javamail:
  # Configured mail providers with the name of the cache as key
  providers:
    provider1:
      # Providers are fully configured with JavaMail session properties
      property1: value1
```
{{% /config %}}   

{{% callout ref %}}
Note that providers are fully configured by specifying [JavaMail session properties](https://javamail.java.net/nonav/docs/api/).
{{% /callout %}}


### SMTP

To configure an SMTP provider, use the following configuration:

```yaml
javamail:
  providers:
    smtpProvider:
      mail.transport.protocol: smtp
      mail.smtp.host: ...
      mail.smtp.port: ...
```

### IMAP

To configure an IMAP provider, use the following configuration:

```yaml
javamail:
  providers:
    imapProvider:
      mail.store.protocol: imap
      mail.imap.user: ...
      mail.imap.host: ...
      mail.imap.port: ...
```

### POP3

To configure a POP3 provider, use the following configuration:

```yaml
javamail:
  providers:
    imapProvider:
      mail.store.protocol: pop3
      mail.pop3.user: ...
      mail.pop3.host: ...
      mail.pop3.port: ...
```

## Usage

The configured providers will be injectable as a fully-configured JavaMail session:
 
```java
public class SomeClass {
    @Inject
    @Named("provider1")
    private Session session;
}
``` 

{{% callout ref %}}
Use the [JavaMail API documentation](https://javamail.java.net/nonav/docs/api/) to know more about usage.
{{% /callout %}}

## Testing

JavaMail add-on provides testing fixtures which enable to emulate an SMTP server and easily assert that your sent mails
are valid. Consider the following test configuration:
 
```yaml
javamail:
  providers:
    smtpTest:
      mail.transport.protocol: smtp
      mail.smtp.host: localhost
      mail.smtp.port: 6457        
```  

You can then use the {{< java "org.seedstack.javamail.test.WithMailServer" "@" >}} annotation and the 
{{< java "org.seedstack.javamail.test.MessageRetriever" >}} class in your tests:

```java
@WithMailServer(host = "localhost", port = 6457)
public class SmtpSendingIT extends AbstractSeedIT {
    @Inject
    @Named("smtpTest")
    private Session smtpSession;

    @Inject
    private MessageRetriever retriever;

    @Test
    public void testSend() throws MessagingException {
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
            MockMailServerAssertions.assertThat(message)
                .hasRecipients(Message.RecipientType.TO);
            MockMailServerAssertions.assertThat(message)
                .recipientEqualsTo(Message.RecipientType.TO,
                        InternetAddress.parse(TestConstantsValues.DEFAULT_RECIPIENT));
        }
    }
}
```
