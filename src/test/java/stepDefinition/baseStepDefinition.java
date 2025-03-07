package stepDefinition;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
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

    public static final String SCHEMA_PATH = configReader.getProperty("schemaPath");
    public static final int EXPECTED_STATUS_CODE = Integer.parseInt(configReader.getProperty("expectedStatuscode"));


    public static String setupEnvironment(String endpointKey) {
        try {

            String env = System.getProperty("env", "dev");

            RestAssured.baseURI = configReader.getProperty(env + ".baseURI");
            System.out.println("Selected environment:" + env);
            System.out.println("Base URI :" + RestAssured.baseURI);

            if (endpointKey == null || endpointKey.isEmpty()) {
                throw new IllegalArgumentException("EndpointKey is missing from config.properties");
            }
            String endpoint = configReader.getProperty(endpointKey);

            String fullUrl = RestAssured.baseURI + endpoint;
            System.out.println("FullURL : " + fullUrl);
            return fullUrl;
        } catch (IllegalArgumentException e) {
            System.err.println("Error in setting up environment: " + e.getMessage());
            throw e;
        }
    }

    public static Map<String, String> getHeaders() throws IOException {
        try {
            String headersFilePath = "src/test/java/headerFiles/headers.json";
            return jsonReader.readJsonAsMap(headersFilePath);
        } catch (IOException e) {
            System.err.println("Error reading headers file: " + e.getMessage());
            throw e;
        }
    }

    public static String getRequestBody(String filepath) throws IOException {
        try {

            Path requestBodyPath = Paths.get(filepath);
            if (!Files.exists(requestBodyPath)) {

                throw new IOException("Request body file not found" + requestBodyPath);
            }
            return new String(Files.readAllBytes(requestBodyPath));
        } catch (IOException e) {
            System.err.println("Error reading request body file: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    public static String sendPostRequest(String url, Map<String, String> headers, String body) throws IOException {

        long responseTime = 0;
        String Response = null;

        try {

            responseTime = given().log().all().headers(headers).
                    body(body).when().post(url).then().extract().time();

            System.out.println("POST Request Response Time: " + responseTime + "ms");

            Response = given().log().all().headers(headers).body(body).when().post(url).then().
                    assertThat().statusCode(EXPECTED_STATUS_CODE).time(lessThan(5000L)).
                    extract().response().asString();

            int statusCode = given().log().all().headers(headers).body(body).when().post(url)
                    .then().extract().statusCode();
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("POST Request failed with HTTP Status Code: " + statusCode);
                throw new IOException("POST Request failed with status code " + statusCode);
            }

            return Response;
        } catch (IOException e) {
            System.err.println("Error during POST request: " + e.getMessage());
            throw e;
        }

    }

    public static String sendGetRequest(String url, Map<String, String> headers, String schemaPath) throws IOException {
        long responseTime = 0;
        String Response = null;

        System.out.println("Schema Path is : " + schemaPath);

        try {

            responseTime = given().log().all().headers(headers).
                    when().get(url).then().extract().time();

            System.out.println("Get Request Response Time: " + responseTime + "ms");

            Response = given().log().all().headers(headers).when().get(url).then().
                    assertThat().statusCode(EXPECTED_STATUS_CODE).time(lessThan(5000L)).
                    body(JsonSchemaValidator.matchesJsonSchema(new File(SCHEMA_PATH))).
                    extract().response().asString();

            int statusCode = given().log().all().headers(headers).when().get(url)
                    .then().extract().statusCode();

            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("POST Request failed with HTTP Status Code: " + statusCode);
                throw new IOException("POST Request failed with status code " + statusCode);
            }
            System.out.println("JSOn Schema Validation Passed");

            return Response;
        } catch (IOException e) {
            System.err.println("JSOn Schema Validation Failed");
            System.err.println("Error during POST request: " + e.getMessage());
            throw e;
        }

    }

}
