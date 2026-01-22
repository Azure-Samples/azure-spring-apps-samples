/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.azure.spring.sample.service;

public class LongString {

    private String value;

    public LongString(String value) {
        for (int i = 0; i < 100; i++) {
            this.value += value;
        }
    }
}
