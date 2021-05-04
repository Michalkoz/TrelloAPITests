package organization;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Utils;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class OrganizationTest extends Utils {

    String fakeFunnyDisplayName = faker.funnyName().name();
    long fakeNumber = faker.number().randomNumber();

    @Test
    public void createNewOrganizationTest() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", fakeFunnyDisplayName)
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String organizationId = jsonPath.getString("id");

        delete(BASE_URL + "/" + ORGANIZATION + "/" + organizationId);
    }

    @Test
    public void createOrganizationWithEmptyDisplayName() {

        given()
                .spec(reqSpec)
                .queryParam("displayName", "")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(400);
    }

    @Test
    public void createNewOrganizationWithOneLetterInDisplayNameTest() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", "f")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String organizationId = jsonPath.getString("id");

        delete(BASE_URL + "/" + ORGANIZATION + "/" + organizationId);
    }

    @Test
    public void createNewOrganizationWithOneNumberAndSpecificSignInDisplayNameTest() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", "1$")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String organizationId = jsonPath.getString("id");

        delete(BASE_URL + "/" + ORGANIZATION + "/" + organizationId);
    }

    @Test
    public void createNewOrganizationWith257SignsInDisplayNameTest() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkkkkkkkkllllllllllmmmmmmmmmmaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkkkkkkkkllllllllllmmmmmmm")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String organizationId = jsonPath.getString("id");

        delete(BASE_URL + "/" + ORGANIZATION + "/" + organizationId);
    }

    @Test
    public void createNewOrganizationWith101SignsInDisplayNameTest() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhjjjjjjjjjjkkkkkkkkkkl")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String organizationId = jsonPath.getString("id");

        delete(BASE_URL + "/" + ORGANIZATION + "/" + organizationId);
    }

    @Test
    public void createNewOrganizationWith100SignsInDisplayNameTest() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhjjjjjjjjjjkkkkkkkkkk")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String organizationId = jsonPath.getString("id");

        delete(BASE_URL + "/" + ORGANIZATION + "/" + organizationId);
    }

    @Test
    public void createNewOrganizationWithUniqueNameTest() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", fakeFunnyDisplayName)
                .queryParam("name", "organization_test_name_042021")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String organizationId = jsonPath.getString("id");
        Assertions.assertThat(jsonPath.getString("name")).isEqualTo("organization_test_name_042021");

        delete(BASE_URL + "/" + ORGANIZATION + "/" + organizationId);
    }

    @Test
    public void createNewOrganizationWithUppercaseLettersOfNameTest() {

        given()
                .spec(reqSpec)
                .queryParam("displayName", fakeFunnyDisplayName)
                .queryParam("name", "Organization_Test_Name_042021")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(400);
    }

    @Test
    public void createNewOrganizationWithSpacesBetweenCharsOfNameTest() {

        given()
                .spec(reqSpec)
                .queryParam("displayName", fakeFunnyDisplayName)
                .queryParam("name", "organization test name 042021")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(400);
    }

    @Test
    public void createNewOrganizationWithTwoSignsOfNameTest() {

        given()
                .spec(reqSpec)
                .queryParam("displayName", fakeFunnyDisplayName)
                .queryParam("name", "ur")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(400);
    }

    @Test
    public void createNewOrganizationWithSpecificStringOfNameTest() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", fakeFunnyDisplayName)
                .queryParam("name", "request")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String organizationId = jsonPath.getString("id");
        Assertions.assertThat(jsonPath.getString("name")).isEqualTo("request");

        delete(BASE_URL + "/" + ORGANIZATION + "/" + organizationId);
    }

    @Test
    public void createNewOrganizationWithHTTPWebsiteParameterTest() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", fakeFunnyDisplayName)
                .queryParam("website", "http://michal.kozakiewicz.pl")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String organizationId = jsonPath.getString("id");
        Assertions.assertThat(jsonPath.getString("website")).isEqualTo("http://michal.kozakiewicz.pl");

        delete(BASE_URL + "/" + ORGANIZATION + "/" + organizationId);
    }

    @Test
    public void createNewOrganizationWithHTTPSWebsiteParameterTest() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", fakeFunnyDisplayName)
                .queryParam("website", "https://michal.kozakiewicz.pl")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String organizationId = jsonPath.getString("id");
        Assertions.assertThat(jsonPath.getString("website")).isEqualTo("https://michal.kozakiewicz.pl");

        delete(BASE_URL + "/" + ORGANIZATION + "/" + organizationId);
    }

    @Test
    public void createNewOrganizationWithoutProtocolOfWebsiteParameterTest() {

        given()
                .spec(reqSpec)
                .queryParam("displayName", fakeFunnyDisplayName)
                .queryParam("website", "www.michal.kozakiewicz.pl")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(400);
    }

    @Test
    public void createNewOrganizationWithFullListOfParametersTest() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", fakeFunnyDisplayName)
                .queryParam("desc", "FFDN")
                .queryParam("name", "organization_test_name_" + fakeNumber)
                .queryParam("website", "http://michal.kozakiewicz.pl")
                .when()
                .post(BASE_URL + "/" + ORGANIZATION)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String organizationId = jsonPath.getString("id");
        Assertions.assertThat(jsonPath.getString("displayName")).isEqualTo(fakeFunnyDisplayName);
        Assertions.assertThat(jsonPath.getString("desc")).isEqualTo("FFDN");
        Assertions.assertThat(jsonPath.getString("name")).isEqualTo("organization_test_name_" + fakeNumber);
        Assertions.assertThat(jsonPath.getString("website")).isEqualTo("http://michal.kozakiewicz.pl");

        delete(BASE_URL + "/" + ORGANIZATION + "/" + organizationId);
    }
}
