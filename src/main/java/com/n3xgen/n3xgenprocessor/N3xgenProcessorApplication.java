package com.n3xgen.n3xgenprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.n3xgen.n3xgenprocessor.bean.ProcessorStream;
import com.n3xgen.n3xgenprocessor.service.ProcessorNodeJsImpl;


@SpringBootApplication
public class N3xgenProcessorApplication {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(N3xgenProcessorApplication.class);
	public static void main(String[] args) {
		log.info("Main method starting");
		 SpringApplication.run(N3xgenProcessorApplication.class, args);
		 String JSONataexpression = "{\r\n"
		 		+ "  \"name\": FirstName & \" \" & Surname,\r\n"
		 		+ "  \"mobile\": Phone[type = \"mobile\"].number\r\n"
		 		+ "}";
		 String payload = "{\r\n"
		 		+ "  \"FirstName\": \"Fred\",\r\n"
		 		+ "  \"Surname\": \"Smith\",\r\n"
		 		+ "  \"Age\": 28,\r\n"
		 		+ "  \"Address\": {\r\n"
		 		+ "    \"Street\": \"Hursley Park\",\r\n"
		 		+ "    \"City\": \"Winchester\",\r\n"
		 		+ "    \"Postcode\": \"SO21 2JN\"\r\n"
		 		+ "  },\r\n"
		 		+ "  \"Phone\": [\r\n"
		 		+ "    {\r\n"
		 		+ "      \"type\": \"home\",\r\n"
		 		+ "      \"number\": \"0203 544 1234\"\r\n"
		 		+ "    },\r\n"
		 		+ "    {\r\n"
		 		+ "      \"type\": \"office\",\r\n"
		 		+ "      \"number\": \"01962 001234\"\r\n"
		 		+ "    },\r\n"
		 		+ "    {\r\n"
		 		+ "      \"type\": \"office\",\r\n"
		 		+ "      \"number\": \"01962 001235\"\r\n"
		 		+ "    },\r\n"
		 		+ "    {\r\n"
		 		+ "      \"type\": \"mobile\",\r\n"
		 		+ "      \"number\": \"077 7700 1234\"\r\n"
		 		+ "    }\r\n"
		 		+ "  ],\r\n"
		 		+ "  \"Email\": [\r\n"
		 		+ "    {\r\n"
		 		+ "      \"type\": \"office\",\r\n"
		 		+ "      \"address\": [\r\n"
		 		+ "        \"fred.smith@my-work.com\",\r\n"
		 		+ "        \"fsmith@my-work.com\"\r\n"
		 		+ "      ]\r\n"
		 		+ "    },\r\n"
		 		+ "    {\r\n"
		 		+ "      \"type\": \"home\",\r\n"
		 		+ "      \"address\": [\r\n"
		 		+ "        \"freddy@my-social.com\",\r\n"
		 		+ "        \"frederic.smith@very-serious.com\"\r\n"
		 		+ "      ]\r\n"
		 		+ "    }\r\n"
		 		+ "  ],\r\n"
		 		+ "  \"Other\": {\r\n"
		 		+ "    \"Over 18 ?\": true,\r\n"
		 		+ "    \"Misc\": null,\r\n"
		 		+ "    \"Alternative.Address\": {\r\n"
		 		+ "      \"Street\": \"Brick Lane\",\r\n"
		 		+ "      \"City\": \"London\",\r\n"
		 		+ "      \"Postcode\": \"E1 6RF\"\r\n"
		 		+ "    }\r\n"
		 		+ "  }\r\n"
		 		+ "}";
		 ProcessorNodeJsImpl processorstream = new ProcessorNodeJsImpl();
		 log.info(processorstream.nodeJsToJSONprocessor(JSONataexpression, payload));
	}
}
