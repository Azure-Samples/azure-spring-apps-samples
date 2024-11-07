/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.azure.spring.sample.service;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.util.Arrays;
import java.util.List;

public class MemoryTools {

    public static void printMemory() {
        Runtime runtime = Runtime.getRuntime();
        double freeMemory = (double) runtime.freeMemory() / (1024 * 1024);
        double totalMemory = (double) runtime.totalMemory() / (1024 * 1024);
        double usedMemory = totalMemory - freeMemory;

        System.out.println("Total Memory:" + totalMemory);
        System.out.println("Free Memory:" + freeMemory);
        System.out.println("Used Memory:" + usedMemory);

    }


    public static int printDirectMemory(boolean printInfo) {
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean bean : memoryPoolMXBeans) {
            System.out.println(bean.getName() + "： " + bean.getUsage());
        }
        List<MemoryManagerMXBean> memoryManagerMXBeans = ManagementFactory.getMemoryManagerMXBeans();
        for (MemoryManagerMXBean bean : memoryManagerMXBeans) {
            System.out.println(bean.getName() + "： " + Arrays.asList(bean.getMemoryPoolNames()));
        }
        System.out.println();
        return 0;

    }
}