# Description
This project is based on spring boot, and uses log4j for logging and Apache commons math for checking the number. Every
dependency for this project should be added to the .war during the packaging phase so there shouldnt be any problems
running it on another system.

The project itself consists of four main parts, the controllers and the math logic, the controllers consist of one rest
controller, one MVC controller and one Exception controller, the ExceptionHandlerController makes sure the exceptions
gets logged to the correct logger. Both the Rest and Mvc controller logs to a class local Logger object, which the
appender RequestLog. Beans are configured in applicationContext.xml and not with a java-based config

## RestPrimeCheckerController
The RestPrimeCheckerController class works like a normal REST api and returns a
PrimeResult enum. This isnt really required but in the beginning I just wanted to make a Rest controller, but since I've
already used it before I ended up trying thymeleaf and just kept the RestController.

## MvcPrimeCheckerController
The MvcPrimeCheckerController sets the result and passes it to the
main/webapp/WEB-INF/templates/primecheck.html template, which then formats the text using the TextFormatter class(format
can be configured in locale.properties). Everything is started in the PrimeWebApplication class.

## ExceptionHandlerController
The ExceptionHandlerController is a very simple controller that logs any Throwable caught and logs it.

# Tests
I only have tests for the Math logic as the rest of the code doesnt have any real logic behind it and almost only rely
on spring magic

# Requirements
* Java 1.8
* Maven

# Compile
To compile this program simply run ```mvn clean package```.

If you just want to run this solution without having
a full Java EE server running you can just run the command ```mvn spring-boot:run```.

If you want to build the entire project with javadoc and maven-site you can issue the command ```mvn clean package site```

# Generating javadoc and maven site
To generate the javadoc and maven site execute the goal "site" with the command ```mvn clean site```