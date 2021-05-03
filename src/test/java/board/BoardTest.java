package board;


import config.Configuration;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest extends Utils {

    private final String key = Configuration.KEY;
    private final String token = Configuration.TOKEN;

    @Test
    public void createNewBoard() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "My second board")
                .when()
                .post("https://api.trello.com/1/boards/")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("My second board", json.get("name"));

        String boardId = json.get("id");

        delete("https://api.trello.com/1/boards/" + boardId);

    }

    @Test
    public void createBoardWithEmptyBoardName() {
        Response response = given()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("name", "")
                .contentType(ContentType.JSON)
                .when()
                .post("https://api.trello.com/1/boards/")
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
                .post("https://api.trello.com/1/boards/")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Board without default lists", json.getString("name"));
        String boardId = json.getString("id");

        Response responseGet = given()
                .spec(reqSpec)
                .when()
                .get("https://api.trello.com/1/boards/" + boardId + "/lists")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = responseGet.jsonPath();
        List<String> idList = jsonPath.getList("id");
        assertEquals(0, idList.size());
    }

    @Test
    public void createBoardWithDefaultLists() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "Board with default lists")
                .queryParam("defaultLists", true)
                .when()
                .post("https://api.trello.com/1/boards/")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Board with default lists", json.getString("name"));
//        System.out.println(response.prettyPrint());
        String boardId = json.getString("id");

        Response responseGet = given()
                .spec(reqSpec)
                .when()
                .get("https://api.trello.com/1/boards/" + boardId + "/lists")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonGet = responseGet.jsonPath();
        List<String> idLists = jsonGet.getList("id");
        assertEquals(3, idLists.size());

        System.out.println(jsonGet.prettyPrint());
        List<String> nameList = jsonGet.getList("name");
        assertEquals("To Do", nameList.get(0));
        assertEquals("Doing", nameList.get(1));
        assertEquals("Done", nameList.get(2));
    }
}
