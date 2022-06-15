package com.n3xgen.n3xgenprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.n3xgen.n3xgenprocessor.service.ProcessorNodeJs;
import com.n3xgen.n3xgenprocessor.service.ProcessorNodeJsImpl;

@SpringBootApplication
public class N3xgenProcessorApplication {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(N3xgenProcessorApplication.class);
	public static void main(String[] args) {
		log.info("Main method starting");
		 SpringApplication.run(N3xgenProcessorApplication.class, args);
//		String data = "{\"state\":\"Karnataka\",\n"
//				+ "\"code\":\"KA\",\n"
//				+ "\"country\":\"India\"}";
//		String expression = "{ 'age': state, 'name': code}";
//		ProcessorNodeJs transformer = new ProcessorNodeJsImpl();
//		transformer.nodeJsToJSONprocessor(data, expression);
	}
}
