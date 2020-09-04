/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.azure;

import org.springframework.data.annotation.Id;
import com.microsoft.azure.spring.data.cosmosdb.core.mapping.PartitionKey;

public class User {

    @Id
    @PartitionKey
    public String id;

    public String name;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("User[id=%s, name='%s']", id, name);
    }

}

