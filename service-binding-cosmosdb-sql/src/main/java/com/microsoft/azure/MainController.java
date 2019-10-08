/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MainController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping(path="/all")
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping(path="/hello")
	public String hello() {
		return "hello!";
	}
}
