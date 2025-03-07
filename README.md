# Rest-Assured Framework

# Introduction

The purpose of this framework is to create a user then fetch user through API services.
RestAssured implementation with Cucumber, Hamcrest assertions and cucumber-html-reports

# Features

Create new users through API
Fetch Created user details
Validate status codes and response using JSON Schema
Supports testing in different environments (dev, qa)

# Prerequisites

Developed using Intellij
Frame work developed with Java therefore it requires JDK installed in machine
Project is built in using Apache Maven

# Installation 

1. Clone the repository

git clone https://github.com/praveenathi/test-rest-service.git

2. Navigate to the project directory

cd projectdirectory

3. Install the required dependencies

mvn clean install

# Run Tests in Different Environments (default to dev environment)

Use the terminal and run below commands

mvn test -Denv=dev
mvn test -Denv=qa

# Test Results

Test results are displayed under the target folder as cucumber-html-reports
Open the explorer and view the reports through browser (chrome, firefox)

# Folder Structure

1. config.properties src/test/resources/config.properties
2. featurefile src/test/resources/features
3. stepdefinition src/test/java/stepDefinition
4. payLoads src/test/java/payLoads
5. headerfiles src/test/java/headerFiles
6. reports target/cucumber-html-reports







