package com.n3xgen.n3xgenprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class N3xgenProcessorApplication {

	public static void main(String[] args) {
		 SpringApplication.run(N3xgenProcessorApplication.class, args);
//		String data = "{\n"
//				+ "  example: [\n"
//				+ "    {value: 4},\n"
//				+ "    {value: 7},\n"
//				+ "    {value: 13}\n"
//				+ "  ]\n"
//				+ "}";
//		String expression = "$sum(example.value)";
//		ProcessorNodeJs transformer = new ProcessorNodeJsImpl();
//		transformer.nodeJsToJSONprocessor(data, expression);
	}
}
