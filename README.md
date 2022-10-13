[![N|Solid](https://cloudgensys.com/cg-demo/wp-content/uploads/2019/05/CG-Logo-01.png)](https://www.cloudgensys.com/)
# Nexgen-Processor  (Mapping Processor)
## 1. Introduction
Processor application developed to apply JSONata Lenguaje query expressions on JSON payloads by creating a temp javascript file and using the JSONata package.

Application can be builded with Maven or following the next steps with Docker.

**Note**: It is important to mention that the Node.js and JSONata packages need to be installed on the machine to run it locally.

- Maven: **`clean -DskipTests package`**
- Docker: **`mvnw clean install spring-boot:build-image -DskipTests=true`** after creating the image needs to create another one using the DockerFile localized on root **` docker build -t n3xtgencloud/nextgenprocessor:0.0.1 .`**

## 2. Library Objects and Methods

This application only use Javascript for the creation of the temporary file.

- ## 2.1 Methods
  **processTransformation:** Method that will take the message send it by the application that comes before wheter it be a Source app or another processor on the stream. It will start the nodeJsToJSONprocessor by selecting only the payload and casting it to String object.
  **nodeJsToJSONprocessor:** Method that will take the payload and the JSONata expression passed as propertie and will build the content of the Js file to be executed, also hear the result wheter it be an error or the final JSON and will return it as String.

- ## 2.2 Objects and properties
  The following are the objects and properties need it to work properly.
  - ## 2.2.1 Objects
  - **processProperties:** Contains the properties, in this case it will only be the JSONata expression defined at the moment of the creation of the SCDF stream.
  - **payload:** Contains the data that will be evaluated using the JSONata expression on the temp js. file.
  - **expressionBuilder:** StringBuilder object used for build the data sended to Js. file. Consume processProperties and payload object, also contains the path for JSONata package.
  - **ExceptionPath:** Extends from RuntimeException to easily throw expections on nodeJsToJSONprocessor function.
  -  ## 2.2.2 Properties
  - **expression:** Contains the JSONata expression used to query on payload each time it receives data. String type variable.
    > Note: JSON expression need to use simple quotation marks or it will cause error even if the expression is right. An example below: 

    ```
    {'name': FirstName & ' ' & Surname,'mobile':Phone[type = 'mobile'].number}
    ```
    > Note: To know how to construct JSONata expressions please refer to offical documentation:  https://jsonata.org/).
    
## 3. Examples
Data for Spring Cloud Dataflow interaction method will be obtained by two surces, payload from Source or processor and Expression from properties at the start of the app.

- **Args:**
1. Payload will be obtained from Message object and cast it to String from byte array.
2. Expression will be obtained at the moment of the application to start, this propertie can be set through the SCDF dashboard or by API call.
   ```
    mapping-processor --expression="{'name': FirstName & ' ' & Surname,'mobile': Phone[type = 'mobile'].number}"
    ```
    ## 3.1 processTransformation

- **Description:** This method will take and transform the incoming message and cast it to String and also will instantiate the main transform method.  
- **Object and properties:** To use this method, the user need to pass a Spring Message with a correct JSON as payload. Also nee to instantiate the object processorProperties with a JSONata expression.
- **Samples:**
    For this method it is necessary to get Spring Cloud Dataflow(SCDF) server running one Skipper server and one message broker, for a complete guide plase refer to the offical SCDF documentation: https://dataflow.spring.io/docs/
**Steps to follow**
1.- Build .jar or upload Docker image and upload the application to SCDF server.
2.- Build following stream: 
    ```
    http | mapping-processor --expression="{'name': FirstName & ' ' & Surname,'mobile': Phone[type = 'mobile'].number}" | log
    ```
    3.- Send sample JSON as payload on http POST
    ```JSON
    { "FirstName": "Fred",
  "Surname": "Smith",
  "Age": 28,
  "Address": {
    "Street": "Hursley Park",
    "City": "Winchester",
    "Postcode": "SO21 2JN"
  },
  "Phone": [
    {
      "type": "home",
      "number": "0203 544 1234"
    },
    {
      "type": "office",
      "number": "01962 001234"
    },
    {
      "type": "office",
      "number": "01962 001235"
    },
    {
      "type": "mobile",
      "number": "077 7700 1234"
    }
  ],
  "Email": [
    {
      "type": "office",
      "address": [
        "fred.smith@my-work.com",
        "fsmith@my-work.com"
      ]
    },
    {
      "type": "home",
      "address": [
        "freddy@my-social.com",
        "frederic.smith@very-serious.com"
      ]
    }
  ],
  "Other": {
    "Over 18 ?": true,
    "Misc": null,
    "Alternative.Address": {
      "Street": "Brick Lane",
      "City": "London",
      "Postcode": "E1 6RF"
    }
  } } 
  ```
    4.- Final result on Log application will be like the following:
    ```
    { "name": "Fred Smith", "mobile": "077 7700 1234" }
    ```
    
    ## 3.2 nodeJsToJSONprocessor
- Description: This method will take payload and JSONata expression and create and execute a Javascript file take the result and print wheter is an correct JSON or an error.
- Object and Properties: To use this method is necessary to pass two valid JSON structures one for payload and one JSONata expression.
- Samples
On this second example use an instance of the class ProcessorNodeJsImpl and use the method nodeJsToJSONprocessor.
Also for the method we will use the same data sample just like the below example:
    -1. First step: Instanciate ProcessorNodeJsImpl class
    ```java
        ProcessorNodeJsImpl processorstream = new ProcessorNodeJsImpl();
    ```
    -2.-Create variables with the sample data
    ```java
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
    ```
    -3.- Next we call for the method **nodeJsToJSONprocessor** and pass our sample data.
    ```java
    log.info(processorstream.nodeJsToJSONprocessor(JSONataexpression, payload));
    ```
    -4.- Finally we get our result like this:
    ```
        { "name": "Fred Smith", "mobile": "077 7700 1234" }
    ```
You can use the JSONata official tool to validate your expression and your result on this page: https://try.jsonata.org/# nexgen-processor