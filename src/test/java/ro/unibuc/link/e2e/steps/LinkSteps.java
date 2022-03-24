package ro.unibuc.link.e2e.steps;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.dto.IsAvailableDTO;
import ro.unibuc.link.dto.UrlDeleteDTO;
import ro.unibuc.link.dto.UrlShowDTO;
import ro.unibuc.link.e2e.util.HeaderSetup;
import ro.unibuc.link.e2e.util.ResponseErrorHandler;
import ro.unibuc.link.e2e.util.ResponseResults;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest()
public class LinkSteps {

    public static ResponseResults latestResponse = null;
    public static Object latestPostResponse = null;
    private static UrlEntity urlEntity = new UrlEntity("fb", "https://www.facebook.com", "pass");

    @Autowired
    protected RestTemplate restTemplate;

    @When("the client calls short-link\\/check\\/fb")
    public void the_client_issues_GET_availability() {
        executeGet("http://localhost:8080/short-link/check/fb");
    }

    @When("the client calls short-link\\/redirect\\/fb")
    public void the_client_issues_GET_link() {
        executeGet("http://localhost:8080/short-link/redirect/fb");
    }


    @When("the client calls collection\\/check\\/fb")
    public void the_client_issues_GET_availability_collection() {
        executeGet("http://localhost:8080/collection/check/fb");
    }


    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @And("^the client receives response that url is available$")
    public void the_client_receives_response() throws JsonProcessingException {
        String latestResponseBody = latestResponse.getBody();
        IsAvailableDTO availability = new ObjectMapper().readValue(latestResponseBody, IsAvailableDTO.class);
        assertTrue(availability.isAvailable(), "Response received is incorrect");
    }

    @And("^the client receives response that url is not available$")
    public void the_client_receives_response_not_available() throws JsonProcessingException {
        String latestResponseBody = latestResponse.getBody();
        IsAvailableDTO availability = new ObjectMapper().readValue(latestResponseBody, IsAvailableDTO.class);
        assertFalse(availability.isAvailable(), "Response received is incorrect");
    }

    public void executeGet(String url) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSetup requestCallback = new HeaderSetup(headers);
        final ResponseErrorHandler errorHandler = new ResponseErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.getHadError()) {
                return (errorHandler.getResults());
            } else {
                return (new ResponseResults(response));
            }
        });
    }

    @When("the client calls delete short-link")
    public void the_client_issues_DELETE() {
        executeDelete("http://localhost:8080/short-link");
    }


    public void executeDelete(String url) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSetup requestCallback = new HeaderSetup(headers);
        final ResponseErrorHandler errorHandler = new ResponseErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        restTemplate.exchange(URI.create(url), HttpMethod.DELETE, new HttpEntity<>(new UrlDeleteDTO(urlEntity.getInternalUrl(), urlEntity.getDeleteWord())), UrlShowDTO.class);
    }

    @When("the client calls short-link\\/set")
    public void the_client_issues_POST_url() {
        executePostUrl("http://localhost:8080/short-link/set");
    }

    @Then("the client receives the url he posted")
    public void the_client_receives_the_url_he_posted() {
        assertEquals(new UrlShowDTO(urlEntity), latestPostResponse, "Response received is incorrect");
    }

    public void executePostUrl(String url) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSetup requestCallback = new HeaderSetup(headers);
        final ResponseErrorHandler errorHandler = new ResponseErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestPostResponse = restTemplate
                .postForObject(url, urlEntity, UrlShowDTO.class);
    }

}
