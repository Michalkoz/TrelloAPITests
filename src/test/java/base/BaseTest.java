package base;

import com.github.javafaker.Faker;
import config.Configuration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static final String BASE_URL = "https://api.trello.com/1";
    protected static final String BOARDS = "boards";
    protected static final String LISTS = "lists";
    protected static final String ORGANIZATION = "organizations";
    protected static final String CARDS = "cards";
    protected static final String KEY = Configuration.KEY;
    protected static final String TOKEN = Configuration.TOKEN;


    protected static RequestSpecBuilder reqBuilder;
    protected static RequestSpecification reqSpec;
    public static Faker faker;

    @BeforeAll
    public static void beforeAll() {
        reqBuilder = new RequestSpecBuilder();
        reqBuilder.addQueryParam("key", KEY);
        reqBuilder.addQueryParam("token", TOKEN);
        reqBuilder.setContentType(ContentType.JSON);

        reqSpec = reqBuilder.build();

        faker = new Faker();
    }
}
