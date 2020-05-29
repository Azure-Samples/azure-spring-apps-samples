/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.mynotes.spring.cloud.service;

public class Product {
	
	private String name;
	
	private String description;
	
	private String detailsLink;
	
	public Product(String name, String description, String detailsLink) {
		super();
		this.name = name;
		this.description = description;
		this.detailsLink = detailsLink;
	}

	public Product() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetailsLink() {
		return detailsLink;
	}

	public void setDetailsLink(String detailsLink) {
		this.detailsLink = detailsLink;
	}
	
	

}
