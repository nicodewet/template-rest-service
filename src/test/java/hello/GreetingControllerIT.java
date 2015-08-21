package hello;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class GreetingControllerIT {

	@Autowired
	GreetingController greetingController;

	@Value("${local.server.port}")
	int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
		ReflectionTestUtils.setField(greetingController, "counter", new AtomicLong());
	}

    @Test
    public void canFetchFirstHelloWorldGreeting() {
        when().
                get("/greeting").
        then().
                statusCode(HttpStatus.SC_OK).
                body("id", Matchers.is(1)).
                body("content", Matchers.is("Hello, World!"));
    }

    @Test
    public void canFetchFirstNameEchoGreeting() {
    	given().
        	param("name", "Nico").
    	when().
        	get("/greeting").
        then().
        	statusCode(HttpStatus.SC_OK).
        	body("id", Matchers.is(1)).
        	body("content", Matchers.is("Hello, Nico!"));
    }

    @Test
    public void canFetchFirstAndSecondNameEchoGreeting() {
    	given().
        	param("name", "John").
    	when().
        	get("/greeting").
        then().
        	statusCode(HttpStatus.SC_OK).
        	body("id", Matchers.is(1)).
        	body("content", Matchers.is("Hello, John!"));

    	given().
    		param("name", "Mike").
    	when().
    		get("/greeting").
    	then().
    		statusCode(HttpStatus.SC_OK).
    		body("id", Matchers.is(2)).
    		body("content", Matchers.is("Hello, Mike!"));
    }

}
