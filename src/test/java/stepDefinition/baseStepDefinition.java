package stepDefinition;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import utils.configReader;
import utils.jsonReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class baseStepDefinition {

    //Path to JSOn Schema for validation
    public static final String SCHEMA_PATH = configReader.getProperty("schemaPath");

    //ENvironment setup by reading baseURI from config.properties

    public static String setupEnvironment(String endpointKey) {
        try {

            //default environment is dev

            String env = System.getProperty("env", "dev");

            RestAssured.baseURI = configReader.getProperty(env + ".baseURI");
            System.out.println("Selected environment:" + env);
            System.out.println("Base URI :" + RestAssured.baseURI);

            if (endpointKey == null || endpointKey.isEmpty()) {
                throw new IllegalArgumentException("EndpointKey is missing from config.properties");
            }

            //Get endpoint from config.properties

            String endpoint = configReader.getProperty(endpointKey);

            //combine baseURI with endpoint

            String fullUrl = RestAssured.baseURI + endpoint;
            System.out.println("FullURL : " + fullUrl);
            return fullUrl;
        } catch (IllegalArgumentException e) {
            System.err.println("Error in setting up environment: " + e.getMessage());
            throw e;
        }
    }

    //Read headers from header.json file

    public static Map<String, String> getHeaders() throws IOException {
        try {
            String headersFilePath = "src/test/java/headerFiles/headers.json";
            return jsonReader.readJsonAsMap(headersFilePath);
        } catch (IOException e) {
            System.err.println("Error reading headers file: " + e.getMessage());
            throw e;
        }
    }

    //Read requestbody from file path

    public static String getRequestBody(String filepath) throws IOException {
        try {

            Path requestBodyPath = Paths.get(filepath);
            if (!Files.exists(requestBodyPath)) {

                throw new IOException("Request body file not found" + requestBodyPath);
            }
            return new String(Files.readAllBytes(requestBodyPath));
        } catch (IOException e) {
            System.err.println("Error reading request body file: " + e.getMessage());
            throw e;
        }
    }

    //Post Requests
    public static String sendPostRequest(String url, Map<String, String> headers, String body, int expectedStatusCode) throws IOException {

        long responseTime = 0;

        try {

            Response response = given().log().all().headers(headers).body(body).when().post(url).then().
                    assertThat().statusCode(expectedStatusCode).time(lessThan(5000L)).
                    extract().response();

            responseTime = given().log().all().headers(headers).
                    body(body).when().post(url).then().extract().time();

            System.out.println("POST Request Response Time: " + responseTime + "ms");

            int actualStatusCode = response.getStatusCode();

            if (actualStatusCode != expectedStatusCode) {
                throw new AssertionError("Expected Status Code : " + expectedStatusCode);
            }

            return response.asString();
        } catch (Exception e) {
            System.err.println("Error during POST request: " + e.getMessage());
            throw e;
        }

    }

    //Get Requests

    public static String sendGetRequest(String url, Map<String, String> headers, String schemaPath, int expectedStatusCode) throws IOException {
        long responseTime = 0;

        try {

            Response response = given().log().all().headers(headers).when().get(url).then().
                    assertThat().statusCode(expectedStatusCode).time(lessThan(5000L)).
                    body(JsonSchemaValidator.matchesJsonSchema(new File(SCHEMA_PATH))).
                    extract().response();

            responseTime = given().log().all().headers(headers).
                    when().get(url).then().extract().time();

            System.out.println("Get Request Response Time: " + responseTime + "ms");

            int actualStatusCode = response.getStatusCode();

            if (actualStatusCode != expectedStatusCode) {
                throw new AssertionError("Expected Status Code : " + expectedStatusCode);
            }
            System.out.println("JSOn Schema Validation Passed");

            return response.asString();
        } catch (Exception e) {
            System.err.println("JSOn Schema Validation Failed");
            System.err.println("Error during POST request: " + e.getMessage());
            throw e;
        }

    }

}
