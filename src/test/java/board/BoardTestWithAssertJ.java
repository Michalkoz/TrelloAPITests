package board;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class BoardTestWithAssertJ extends Utils {

    @Test
    public void createNewBoard() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "My second board")
                .when()
                .post(BASE_URL + "/" + BOARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
//        Assertions.assertEquals("My second board", json.get("name"));
        assertThat(json.getString("name")).isEqualTo("My second board");
        String boardId = json.get("id");

        delete(BASE_URL + "/" + BOARDS + "/" + boardId);
    }

    @Test
    public void createBoardWithEmptyBoardName() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "")
                .when()
                .post(BASE_URL + "/" + BOARDS)
                .then()
                .statusCode(400)
                .extract()
                .response();
    }

    @Test
    public void createBoardWithoutDefaultLists() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "Board without default lists")
                .queryParam("defaultLists", false)
                .when()
                .post(BASE_URL + "/" + BOARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
//        Assertions.assertEquals("Board without default lists", json.getString("name"));
        assertThat(json.getString("name")).isEqualTo("Board without default lists");
        String boardId = json.getString("id");

        Response responseGet = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + BOARDS + "/" + boardId + "/" + LISTS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = responseGet.jsonPath();
        List<String> idList = jsonPath.getList("id");
        assertThat(jsonPath.getList("id")).hasSize(0);
//        Assertions.assertEquals(0, idList.size());

        delete(BASE_URL + "/" + BOARDS + "/" + boardId);

    }

    @Test
    public void createBoardWithDefaultLists() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "Board with default lists")
                .queryParam("defaultLists", true)
                .when()
                .post(BASE_URL + "/" + BOARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Board with default lists");
        String boardId = json.getString("id");

        Response responseGet = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + BOARDS + "/" + boardId + "/" + LISTS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonGet = responseGet.jsonPath();
        List<String> idLists = jsonGet.getList("id");
        assertThat(idLists).hasSize(3);

        System.out.println(jsonGet.prettyPrint());
        List<String> nameList = jsonGet.getList("name");
        assertThat(nameList).contains("To Do", "Doing", "Done").hasSize(3);

        delete(BASE_URL + "/" + BOARDS + "/" + boardId);

    }
}
