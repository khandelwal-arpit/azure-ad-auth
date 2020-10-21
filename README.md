# Authentication Using Azure Active Directory

## Overview
This micro-service intends to provide a jump start solution for all those who are looking to do authenticaation using Azure Active Directory.

## Technology Stack

* [Spring Boot v2.3](https://spring.io/projects/spring-boot)
* [Azure Active Directory](https://azure.microsoft.com/en-in/services/active-directory)
* [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

## Solution
The solution provided here assumes that you have an existing Azure AD with users enrolled in it. The next requirement is to create a client app registered with this active directory. Steps to do the same are as follows:

<img src="https://github.com/khandelwal-arpit/azure-ad-auth/blob/master/src/main/resources/static/MS-AD-CLIENT-STEPS.png" alt="client app">  

We make use of the following two important ADAL classes for this solution:
* [AuthenticationContext](https://docs.microsoft.com/en-us/java/api/com.microsoft.aad.adal.authenticationcontext?view=azure-java-stable)
* [AuthenticationResult](https://docs.microsoft.com/en-us/java/api/com.microsoft.aad.adal4j.authenticationresult?view=azure-java-stable)

#### Steps Involved

* Create context with address of authority, throws exception if url incorrect:

``` java
AuthenticationContext context = new AuthenticationContext(authority_url, false, service);
```

* Acquire a security token from the authority using a username/password flow:

``` java
Future<AuthenticationResult> future = context.acquireToken(graphURL, clientID, email, password, null);
```

#### Environment Variables
Please set the following env variables for this micro-service to work properly:

1. **PORT** : Port for tomcat server
2. **CLIENT_ID** : Client ID obtained from the registered client app
3. **TENANT_ID** : Tenant ID regisred with the Azure AD 

## Running the server locally
To be able to run this Spring Boot micro-service you will need to first build it. To build and package a Spring Boot app into a single executable Jar file with a Maven, use the below command. You will need to run it from the project folder which contains the pom.xml file.

```
maven package
```
or you can also use

```
mvn install
```

To run the Spring Boot app from a command line in a Terminal window you can you the java -jar command. This is provided your Spring Boot app was packaged as an executable jar file.

```
java -jar target/azure-ad-auth-1.0.jar
```

You can also use Maven plugin to run the app. Use the below example to run your Spring Boot app with Maven plugin :

```
mvn spring-boot:run
```

If you do not have a mongo instance running and still just want to create the JAR, then please use the following command:

```
mvn install -DskipTests
```

This will skip the test cases and won't check the availability of a mongodb instance and allow you to create the JAR.

You can follow any/all the above commands, or simply use the run configuration provided by your favorite IDE and run/debug the app from there for development purposes. Once the server is setup you should be able to access the REST API at the following path:

- http://localhost:8080/api/login (HTTP:POST)

The input body in the post request should look something like:

``` javascript
{
    "email" : "arpit.khandelwal@email-domain.com",
    "password" : "Y2hhbmdlaXQ="
}
```

In case you are wondering about the password not being in plain-text format, it is needed in **Base64** encoded format as the input in request body.

If everything goes as planned, you should see the following response from the api:

``` javascript
{
    "status": 200,
    "user-name": "Arpit",
    "user-email": "arpit.khandelwal@email-domain.com",
    "timestamp": "2020-10-21T16:26:49.734+00:00",
    "family-name": "Khandelwal",
    "tenant-id": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
}
```

## Contributors ##
[Arpit Khandelwal](https://www.linkedin.com/in/arpitkhandelwal1984/)

## License ##
This project is licensed under the terms of the MIT license.