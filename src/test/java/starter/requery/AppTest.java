package starter.requery;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

import org.jooby.test.JoobyRule;
import org.junit.ClassRule;
import org.junit.Test;

public class AppTest {

  @ClassRule
  public static JoobyRule app = new JoobyRule(new App());

  @Test
  public void listPeople() {
    get("/")
        .then()
        .assertThat()
        .body(equalTo(
            "[{\"id\":1,\"name\":\"Pedro Picapiedra\",\"email\":\"pedrookok@picapiedra.com\",\"age\":42}]"))
        .statusCode(200)
        .contentType("application/json;charset=UTF-8");
  }

  @Test
  public void getOne() {
    get("/1")
        .then()
        .assertThat()
        .body(equalTo(
            "{\"id\":1,\"name\":\"Pedro Picapiedra\",\"email\":\"pedrookok@picapiedra.com\",\"age\":42}"))
        .statusCode(200)
        .contentType("application/json;charset=UTF-8");
  }

}
