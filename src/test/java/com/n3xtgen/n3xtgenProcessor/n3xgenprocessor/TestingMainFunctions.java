package com.n3xtgen.n3xtgenProcessor.n3xgenprocessor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Base64;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.n3xgen.n3xgenprocessor.service.ProcessorNodeJs;
import com.n3xgen.n3xgenprocessor.service.ProcessorNodeJsImpl;



class TestingMainFunctions {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProcessorNodeJsImpl.class);
	ProcessorNodeJs testingMain = new ProcessorNodeJsImpl();

//	Sample data
	String sampleData = " {\r\n"
			+ "            \"FirstName\": \"Emmanuel\",\r\n"
			+ "            \"SecondName\": \"Arreola\",\r\n"
			+ "            \"Age\": \"25 years\",\r\n"
			+ "            \"LastName\": \"Perez\"\r\n"
			+ "        }";
//	Transforming sample data to base64
	byte[] encodedBytes = Base64.getEncoder().encode(sampleData.getBytes());
	
	String expressionSample = testingMain.nodeJsToJSONprocessor(sampleData, "$substring(FirstName, 2, 3) & $lowercase(SecondName) & $replace(LastName, \"Perez\", \"Cortes\") & $substringBefore(Age, \" years\")");
	
	@BeforeEach
	void setUp() throws Exception {
	}
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testNodeJsToJSONprocessor() {
		
		String dataSample = testingMain.nodeJsToJSONprocessor(sampleData, "$substring(FirstName, 2, 3) & $lowercase(SecondName) & $replace(LastName, \"Perez\", \"Cortes\") & $substringBefore(Age, \" years\")");
		log.info(dataSample);
		//byte[] decodedString = Base64.getDecoder().decode(dataSample);
		//String payload = new String(decodedString);
		assertEquals("\"manarreolaCortes25\"" , dataSample.trim());
	}
	
//	@Test
//	void testProcessTransformation() {
//		
//		ProcessorProperties properties = new ProcessorProperties();
//		properties.setExpression(expressionSample);
//		ProcessorStream stream = new ProcessorStream(properties);
//		
//		
//		stream.processTransformation();
//	}
	
	
	

}