package com.n3xgen.n3xgenprocessor.bean;

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
		return payload -> {
			log.info("Starting Application");

			ProcessorNodeJs processor = new ProcessorNodeJsImpl();
			return processor.nodeJsToJSONprocessor(new String((byte[]) payload.getPayload()),
					processorProperties.getExpression());

		};
	}
}
