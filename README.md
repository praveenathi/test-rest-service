# Rest-Assured Framework
***************************************************************************************
# Introduction

The purpose of this framework is to create a user then fetch user through API services.
RestAssured implementation with Cucumber, Hamcrest assertions and cucumber-html-reports

# Features

Create new users through API
Fetch Created user details
Validate responses and status codes and response bodies using JSON Schema
Supports testing in different environments (dev, qa)

# Prerequisites

* IDE : Developed using Intellij 2023.3.8
* Java : Frame work developed with Java therefore it requires JDK installed in machine
* Maven : Project is built in using Apache Maven for dependency management

# Installation 

1. Clone the repository to the Local drive

* git clone https://github.com/praveenathi/test-rest-service.git

2. Navigate to the project directory
* cd projectdirectory

3. Open the project through IntelliJ IDE

4. Install the required dependencies to download
* mvn clean install

# Run Tests in Different Environments (default to dev environment)

Use the terminal and run below commands.
* mvn test -Denv=dev
* mvn test -Denv=qa

Right-click TestRunner and Run TestRunner 

# Test Results & Reports

* Test results are displayed under the target folder as cucumber-html-reports
* Open the explorer and view the reports through browser (chrome, firefox) or can view through built in browser

# Folder Structure

1. config.properties src/test/resources/config.properties
2. featurefile src/test/resources/features
3. stepdefinition src/test/java/stepDefinition
4. payLoads src/test/java/payLoads
5. headerfiles src/test/java/headerFiles
6. reports target/cucumber-reports/cucumber.html

# Sugesstions to improve

1. Avoide hardcoding of json payloads , can be read it from config.properties to more flexible
2. Single Json file for valid and invalid payloads
3. Replace System.out.println with log4j2 for better logging capability
4. Add extent reports to capture Performance time and provide detailed Test summary report

***********************************************************************************************

