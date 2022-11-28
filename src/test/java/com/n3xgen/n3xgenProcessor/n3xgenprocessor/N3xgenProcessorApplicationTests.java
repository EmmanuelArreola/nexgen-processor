package com.n3xgen.n3xgenProcessor.n3xgenprocessor;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.n3xgex.jsonSchemaValidator.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.n3xgen.n3xgenprocessor.service.ProcessorNodeJs;
import com.n3xgen.n3xgenprocessor.service.ProcessorNodeJsImpl;

import java.util.List;

@SpringBootTest
class N3xgenProcessorApplicationTests {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProcessorNodeJsImpl.class);
	ProcessorNodeJs testingMain = new ProcessorNodeJsImpl();

//	Sample data
	String sampleData = " {\r\n" +
		                  "\"FirstName\": \"Emmanuel\",\r\n"+
		                  "\"SecondName\": \"Arreola\",\r\n" +
		                  "\"Age\": \"25 years\",\r\n" +
		                  "\"LastName\": \"Perez\"\r\n" +
		                   "}";
    String  in_schema="{}";
	@Test
	@DisplayName("Testing main method")
	void testNodeJsToJSONprocessor() {

        JsonSchemaValidator wrapperFromLocalResources = new JsonSchemaValidator();

        List<JsonNode> a =  wrapperFromLocalResources.jsonValidator(in_schema,sampleData);
        System.out.println(a.size() == 0 ? "true" : a);


		String dataSample = testingMain.nodeJsToJSONprocessor(sampleData,
				"$substring(FirstName, 2, 3) & $lowercase(SecondName) & $replace(LastName, \"Perez\", \"Cortes\") & $substringBefore(Age, \" years\")");
	//	log.info(dataSample);
		assertEquals("\"manarreolaCortes25\"", dataSample.trim());
	}

}
