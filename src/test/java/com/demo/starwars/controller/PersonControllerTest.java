package com.demo.starwars.controller;

import com.demo.starwars.StarwarsApplication;
import com.demo.starwars.entity.Person;
import com.demo.starwars.web.model.Response;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = StarwarsApplication.class)
@AutoConfigureWebTestClient
public class PersonControllerTest {

    @Autowired
    WebTestClient client;

    static MockWebServer mockWebServer;

    @BeforeAll
    static void beforeAll() throws Exception {
        //Set Up MockWebServer
        mockWebServer = new MockWebServer();
        mockWebServer.start(10001); //start in port 10001

        mockWebServer.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest recordedRequest) throws InterruptedException {
                MockResponse mockResponse = new MockResponse();
                mockResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                mockResponse.setResponseCode(200);

                String path = "";

                switch (recordedRequest.getPath()){
                    case "/people":
                        path = "src/test/resources/jsonResponse/person/PeopleResponse.json";
                        break;
                    case "/people/1":
                        path = "src/test/resources/jsonResponse/person/OnePersonResponse.json";
                        break;
                }

                try{
                    FileInputStream fileInputStream = new FileInputStream(path);
                    String content = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8.name());
                    mockResponse.setBody(content);
                } catch (Exception e) {
                    System.out.println("ERROR!" + e.getMessage());
                }
                return mockResponse;
            }
        });
    }

    @AfterAll
    static void afterAll() throws Exception{
        mockWebServer.shutdown();
    }

    @Test
    public void getAllPersonsTest() {
        Response<List<Person>> response = client.get()
                .uri("/people")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<Person>>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertTrue(response.getData().size() > 0);
        assertEquals("Luke Skywalker", response.getData().get(0).getName());
        //assertEquals("C-3PO", response.getData().get(1).getName());
    }

    @Test
    public void getPersonsByIdTest() {
        Response<Person> response = client.get()
                .uri("/people/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Person>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("Luke Skywalker", response.getData().getName());
        assertEquals("19BBY", response.getData().getBirth_year());
    }
}
