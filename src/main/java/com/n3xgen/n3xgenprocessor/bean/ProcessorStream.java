package com.n3xgen.n3xgenprocessor.bean;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.function.Function;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import com.n3xgen.n3xgenprocessor.service.ProcessorNodeJs;
import com.n3xgen.n3xgenprocessor.service.ProcessorNodeJsImpl;



@EnableConfigurationProperties(ProcessorProperties.class)
@Configuration
public class ProcessorStream {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProcessorStream.class);
	private ProcessorProperties processorProperties;

	public ProcessorStream(ProcessorProperties processorProperties) {
		this.processorProperties = processorProperties;
	}

	@Bean
	public Function<Message<?>, String> processTransformation() {
		return payload ->  {
			log.info("Starting Application");
			log.info(payload.getHeaders().get("contentType").toString());


			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			try {
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(payload.getPayload());
				oos.flush();
				oos.close();
			} catch (IOException InputOutputException) {
				
				throw new ExceptionPath("Input or Output excpetion thrown by: " + InputOutputException.getMessage() 
				+ InputOutputException.getStackTrace());
			}

		    InputStream is = new ByteArrayInputStream(baos.toByteArray());
			
			BufferedReader  data = new BufferedReader(new InputStreamReader( is ));
						
			StringBuffer sb = new StringBuffer();
			String inputLine = "";
		    try {
		    	data.readLine();
				while ((inputLine = data.readLine()) != null) {
					log.info(inputLine);
				    sb.append(inputLine);
				}
			} catch (IOException InputOutputException) {
				throw new ExceptionPath("Input or Output excpetion thrown by: " + InputOutputException.getMessage() 
				+ InputOutputException.getStackTrace());
			}

			log.info(sb.toString());
			
			ProcessorNodeJs processor = new ProcessorNodeJsImpl();
			return processor.nodeJsToJSONprocessor(sb.toString(), processorProperties.getExpression());


		};
	}
}
