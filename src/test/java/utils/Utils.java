package utils;

import base.BaseTest;
import static io.restassured.RestAssured.given;

public class Utils extends BaseTest {

    public static void delete (String param){
        given()
                .spec(reqSpec)
                .when()
                .delete(param)
                .then()
                .statusCode(200);
    }

}
