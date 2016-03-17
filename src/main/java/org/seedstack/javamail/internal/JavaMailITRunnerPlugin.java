/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.javamail.internal;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;
import org.seedstack.javamail.test.WithMailServer;
import org.seedstack.seed.it.spi.ITKernelMode;
import org.seedstack.seed.it.spi.ITRunnerPlugin;

import java.util.List;
import java.util.Map;

/**
 * This plugin is responsible for providing the testing infrastructure for application that
 * used the mail plugin for configuring and sending emails
 *
 * @author aymen.benhmida@ext.mpsa.com
 */
public class JavaMailITRunnerPlugin implements ITRunnerPlugin {

    @Override
    public List<Class<? extends TestRule>> provideTestRulesToApply(TestClass testClass, Object target) {
        return Lists.newArrayList();
    }

    @Override
    public List<Class<? extends MethodRule>> provideMethodRulesToApply(TestClass testClass, Object target) {
        return Lists.newArrayList();
    }

    @Override
    public Map<String, String> provideDefaultConfiguration(TestClass testClass, FrameworkMethod method) {
        return Maps.newHashMap();
    }

    @Override
    public ITKernelMode kernelMode(TestClass testClass) {
        return ITKernelMode.PER_TEST_CLASS;
    }

    @Override
    public List<Class<? extends TestRule>> provideClassRulesToApply(TestClass testClass) {
        List<Class<? extends TestRule>> rule = Lists.newArrayList();
        if (testClass.getJavaClass().isAnnotationPresent(WithMailServer.class)) {
            rule.add(JavaMailITClassRule.class);
        }
        return rule;
    }
}
