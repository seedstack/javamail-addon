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

    <dependency>
        <groupId>org.seedstack.addons</groupId>
        <artifactId>javamail</artifactId>
    </dependency>
    
You may also need to add the JavaMail API (depending on your runtime environment):
    
    <dependency>
        <groupId>javax.mail</groupId>
        <artifactId>mail</artifactId>
    </dependency>
