package com.n3xgen.n3xgenprocessor.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.n3xgen.n3xgenprocessor.bean.ExceptionPath;

public class ProcessorNodeJsImpl implements ProcessorNodeJs {

	@Override
	public String nodeJsToJSONprocessor(String payload, String expression) {
		StringBuffer outputPayload = new StringBuffer();
		try {
			File tempFile = File.createTempFile("node", ".js");
			FileOutputStream fos = new FileOutputStream(tempFile);
			StringBuilder expressionBuilder = new StringBuilder("var jsonata = require(\"jsonata\");");
			expressionBuilder.append(System.lineSeparator());
			expressionBuilder.append("var data=" + payload + ";");
			expressionBuilder.append(System.lineSeparator());
			expressionBuilder.append("var expression = jsonata(\"" + expression + "\");");
			expressionBuilder.append(System.lineSeparator());
			expressionBuilder.append("var data1 = expression.evaluate(data);");
			expressionBuilder.append(System.lineSeparator());
			expressionBuilder.append("console.log(JSON.stringify(data1));");
			fos.write(expressionBuilder.toString().getBytes());
			fos.close();
			fos.flush();
			Process pb = Runtime.getRuntime().exec("node " + tempFile.getAbsolutePath());
			if (null != pb.getErrorStream()) {
				BufferedReader error = new BufferedReader(new InputStreamReader(pb.getErrorStream()));
				StringBuffer errorMsg = new StringBuffer();
				String str;
				while ((str = error.readLine()) != null)
					errorMsg.append(str);
				error.close();
				throw new Exception(errorMsg.toString());
			}
			BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
			String line;
			while ((line = input.readLine()) != null)
				outputPayload.append(line);
			input.close();
			tempFile.deleteOnExit();
		} catch (FileNotFoundException e) {
			throw new ExceptionPath("File do not exist or is unreacheable: " + e.getMessage());
		} catch (IOException e) {
			throw new ExceptionPath("Input or Output excpetion thrown by: " + e.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			throw new ExceptionPath("Error on: " );
		}
		return outputPayload.toString();
	};
}
