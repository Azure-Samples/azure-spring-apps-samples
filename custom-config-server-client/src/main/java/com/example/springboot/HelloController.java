package com.example.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	/**
	* This is the example that user can read the properties from Config Server.
	*/
	@Value("${hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds}")
	private int timeout;

	@GetMapping("/config")
	public String getConfig() {
		return String.valueOf(timeout);
	}
}
