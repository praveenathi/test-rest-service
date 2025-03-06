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


    @Given("the User can fetch the created user")
    public void the_user_can_fetch_the_created_user() {

        try {

        String fullUrl = baseStepDefinition.setupEnvironment("getEndPoint");

            Map<String, String> headers = getHeaders();

        String Response = sendGetRequest(fullUrl,headers, "schemas/user-schema.json");

        System.out.println("Response Body: " +Response);

        JsonPath js = new JsonPath(Response);
        String username = js.getString("username");
        System.out.println("Message in Response : " +username);

            if (username == null || !username.equals("Devonen")) {
                throw new AssertionError("Expected username 'Devonen', but got: " + username);
            }

        } catch (IOException e) {
            System.err.println("Error reading request body: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An error occurred during fetching user details: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
