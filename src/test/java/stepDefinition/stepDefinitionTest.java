package stepDefinition;

import io.cucumber.java.en.Given;
import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.util.Map;

public class stepDefinitionTest extends baseStepDefinition {

    @Given("the User can create a new user")
    public void the_user_can_create_a_new_user() {

        try {

            String fullUrl = baseStepDefinition.setupEnvironment("createEndPoint");

            Map<String, String> headers = getHeaders();

            String requestBody = getRequestBody("src/test/java/payLoads/requestBody.json");

            String Response = sendPostRequest(fullUrl, headers, requestBody);
            System.out.println("Response of POST" + Response);

            JsonPath js = new JsonPath(Response);
            String messageId = js.getString("message");
            System.out.println("message  is " + messageId);

            if (messageId == null || !messageId.equals("ok")) {

                throw new AssertionError("Unexpected Message: " + messageId);
            }

        } catch (IOException e) {
            System.err.println("Error reading request body: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An error occurred during user creation: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Given("the User can fetch the created user {string}")
    public void the_user_can_fetch_the_created_user(String username) {

        try {

            String endpoint = baseStepDefinition.setupEnvironment("getEndPoint");

        String fullUrl = endpoint.replace("{username}",username);

            Map<String, String> headers = getHeaders();

        String Response = sendGetRequest(fullUrl,headers, SCHEMA_PATH);

        System.out.println("Response Body: " +Response);

        JsonPath js = new JsonPath(Response);
        String getusername = js.getString("username");
        System.out.println("Message in Response : " + getusername);

            if (getusername == null || !getusername.trim().equals(username.trim())) {
                throw new AssertionError("Actual Username: " + getusername + "Expected"  +username);
            }

        } catch (IOException e) {
            System.err.println("Error reading request body: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("error occurred during fetching user: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
