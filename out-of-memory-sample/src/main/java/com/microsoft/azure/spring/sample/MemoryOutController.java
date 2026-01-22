/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.azure.spring.sample;

import com.microsoft.azure.spring.sample.service.DirectMemoryOut;
import com.microsoft.azure.spring.sample.service.HeapMemoryOut;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoryOutController {

    @RequestMapping("/")
    public String home() throws Exception {
        return "This is the demo for azure spring cloud OOM analysis.";
    }

    @RequestMapping("/heap-out")
    public String heapOut() throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    HeapMemoryOut heapMemoryOut = new HeapMemoryOut();
                    heapMemoryOut.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100);
        return "Now run the out of heap memory problem";
    }

    @RequestMapping("/direct-out")
    public String directJvmOut() throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    DirectMemoryOut directMemoryOut = new DirectMemoryOut();
                    directMemoryOut.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100);
        return "Now run the out of direct memory problem";
    }

}
