package hello;

import static com.jayway.restassured.RestAssured.when;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)   // 1
@SpringApplicationConfiguration(classes = Application.class)   // 2
@WebAppConfiguration   // 3
@IntegrationTest("server.port:0")   // 4
public class GreetingControllerTest {

	@Value("${local.server.port}")   // 6
	int port;

	Greeting helloWorld;

	@Before
	public void setUp() {
		helloWorld = new Greeting(1L, "Hello, World!");
		// 9
		RestAssured.port = port;
	}

	// 10
    @Test
    public void canFetchFirstHelloWorldGreeting() {
        when().
                get("/greeting").
        then().
                statusCode(HttpStatus.SC_OK).
                body("id", Matchers.is(1)).
                body("content", Matchers.is("Hello, World!"));
    }

}
