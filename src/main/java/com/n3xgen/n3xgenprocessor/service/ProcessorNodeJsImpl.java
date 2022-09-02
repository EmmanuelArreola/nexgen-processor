package com.n3xgen.n3xgenprocessor.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.n3xgen.n3xgenprocessor.bean.ExceptionPath;

public class ProcessorNodeJsImpl implements ProcessorNodeJs {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProcessorNodeJsImpl.class);

	@Override
	public String nodeJsToJSONprocessor(String payload, String expression) {
		// Create a stream to hold the output
		ByteArrayOutputStream finalData = new ByteArrayOutputStream();
		log.info("Starting expression");
		try {
			File tempFile = File.createTempFile("node", ".js");
			FileOutputStream fos = new FileOutputStream(tempFile);
			log.info(tempFile.getAbsolutePath());
//			Add this: \"/usr/local/lib/node_modules/jsonata\" so the app can work on docker image or add: \"jsonata\" so the app work on local
			StringBuilder expressionBuilder = new StringBuilder("var jsonata = require(\"jsonata\");");
			expressionBuilder.append(System.lineSeparator());
			expressionBuilder.append("var data=" + payload + ";");
			expressionBuilder.append(System.lineSeparator());
			expressionBuilder.append("var expression = jsonata(\'" + expression + "\');");
			expressionBuilder.append(System.lineSeparator());
			expressionBuilder.append("var data1 = expression.evaluate(data);");
			expressionBuilder.append(System.lineSeparator());
			expressionBuilder.append("console.log(JSON.stringify(data1));");
			fos.write(expressionBuilder.toString().getBytes());
			fos.close();
			fos.flush();
			log.info("It creates the temp file");
			log.info(tempFile.getAbsolutePath());
			PrintStream ps = new PrintStream(finalData);
			// IMPORTANT: Save the old System.out!
			PrintStream old = System.out;
			// Tell Java to use your special stream
			System.setOut(ps);
			// Execute temp file
			Process pb = Runtime.getRuntime().exec("node " + tempFile.getAbsolutePath());
			// Saves and print final JSON
			try (InputStream in = pb.getInputStream();) {
				byte[] bytes = new byte[2048];
				int len;
				while ((len = in.read(bytes)) != -1) {
					System.out.write(bytes, 0, len);
				}
			}
			// Put things back
			System.out.flush();
			System.setOut(old);
			// Lock the thread so don't stuck
			pb.waitFor();
			log.info("state : " + pb.exitValue());
			// Show what happened
			if (pb.exitValue() == 1) {
				BufferedReader br = new BufferedReader(new InputStreamReader(pb.getErrorStream()));
				String line = null;
				System.out.println("<ERROR>");
				while ((line = br.readLine()) != null)
					System.out.println(line);
				System.out.println("</ERROR>");
			}
			System.out.println("Final data: " + finalData.toString());
			tempFile.deleteOnExit();
		} catch (FileNotFoundException e) {
			throw new ExceptionPath("File do not exist or is unreacheable: " + e.getMessage() + e.getStackTrace());
		} catch (IOException e) {
			throw new ExceptionPath("Input or Output excpetion thrown by: " + e.getMessage());
		} catch (InterruptedException e) {
			throw new ExceptionPath("Thread interrupted" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionPath("Error on: " + e.getMessage());
		}
		return finalData.toString();
	};
}
