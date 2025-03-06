# test-rest-service
test pet store on rest-assured framework

Project Name: Rest-Assured Framework to Create user and fetch user

**Description:**

The purpose of this framework is to create a user through API services and 
validate whether the user is added to the services.

**Features:**

Create new users through API
Fetch Created user details
Validate API responses using JSON Schema
Supports testing in different environments (dev, qa)

**Prerequisites:**

Java: Ensure you have JDK 8+ installed.
Maven: Used for building the project.
Git: To clone the repository.
IntelliJ for IDE

**Setup**

1. Clone the repository

git clone https://github.com/praveenathi/test-rest-service.git

2. Navigate to the project directory

cd projectdirectory

3. Install the required dependencies

mvn clean install

4. Configure environment settings

Make sure the config.properties file is properly setup with environment configurations (baseURI, endpoints)

**Run Tests in Different Environments** (default to dev environment)
Use the terminal run below commands

mvn test -Denv=dev
mvn test -Denv=qa

Use the IntelliJ IDEA

Right-click on the feature file and select Run to execute tests

**Test Results**

Test results are displayed under the target folder as cucumber-html-reports
Open the explorer and view the reports through browser (chrome, firefox)

**Folder Structure**

1. config.properties src/test/resources/config.properties
2. featurefile src/test/resources/features
3. stepdefinition src/test/java/stepDefinition
4. payLoads src/test/java/payLoads
5. headerfiles src/test/java/headerFiles
6. reports target/cucumber-html-reports







