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

# Log4j2
Log4j2 is configured with three loggers, a root logger that catches everything Spring spits out, the root logger is
configured to catch any messages with a higher level then INFO. There is a logger which catches all logged messages with
a level higher then ERROR from the ExceptionHandlerController and writes them to a rolling file named "exception.log".
The third logger logs every http request to the mapped http paths and adds additional useful info and writes them to a
rolling file named "access.log". Both of the rolling file loggers does not write anything to the parent loggers.

# Tests
I only have tests for the Math logic as the rest of the code doesnt have any real logic behind it and almost only rely
on spring magic

# Maven site configuration
I've set up maven site to only generate the things that are relevant for this project, this makes the site generation
time way shorter. I wanted to use markdown as I think its a better syntax then the "Almost plain text"(APT) maven uses
by default and it comes with a side-by-side editor for IntelliJ so I can see the result without bulding the entire site.

# Requirements
* Java 1.8
* Maven

# Compile
To compile this program simply run ```mvn clean package```.

If you just want to run this solution without having
a full Java EE server running you can just run the command ```mvn spring-boot:run```.

If you want to build the entire project with javadoc and maven-site you can issue the command ```mvn clean package site```

To generate a javadoc jar append "javadoc:jar to the command"

# Generating javadoc and maven site
To generate the javadoc and maven site execute the goal "site" with the command ```mvn clean site```

# Sources
* https://maven.apache.org/plugins/maven-javadoc-plugin/
* https://maven.apache.org/plugins/maven-site-plugin/
* https://maven.apache.org/skins/
* https://docs.spring.io/spring-boot/docs/current/reference/html/howto-logging.html#howto-configure-log4j-for-logging