/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.sample;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class StorageController {
    private static final String TEMP = "/tmp";
    private static final String PERSISTENT = "/persistent";

    @PutMapping(value = "/persistent/{name}")
    public void writePersistent(@PathVariable String name, @RequestParam String content) throws IOException {
        Files.write(Paths.get(PERSISTENT + "/" + name), content.getBytes());
    }

    @GetMapping(value = "/persistent/{name}")
    public String readPersistent(@PathVariable String name) throws IOException {
        return new String(Files.readAllBytes(Paths.get(PERSISTENT + "/" + name)));
    }

    @PutMapping(value = "/tmp/{name}")
    public void writeTemporary(@PathVariable String name, @RequestParam String content) throws IOException {
        Files.write(Paths.get(TEMP + "/" + name), content.getBytes());
    }

    @GetMapping(value = "/tmp/{name}")
    public String readTemporary(@PathVariable String name) throws IOException {
        return new String(Files.readAllBytes(Paths.get(TEMP + "/" + name)));
    }
}
