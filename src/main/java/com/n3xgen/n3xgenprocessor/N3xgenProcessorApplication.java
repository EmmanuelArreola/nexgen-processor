package com.n3xgen.n3xgenprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class N3xgenProcessorApplication {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(N3xgenProcessorApplication.class);
	public static void main(String[] args) {
		log.info("Main method starting");
		 SpringApplication.run(N3xgenProcessorApplication.class, args);
	}
}
