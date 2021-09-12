package com.demo.starwars.controller;

import com.demo.starwars.StarwarsApplication;
import com.demo.starwars.entity.Species;
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
public class SpeciesControllerTest {

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
                    case "/species":
                        path = "src/test/resources/jsonResponse/species/SpeciesResponse.json";
                        break;
                    case "/species/1":
                        path = "src/test/resources/jsonResponse/species/OneSpeciesResponse.json";
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
    public void getAllSpeciesTest() {
        Response<List<Species>> response = client.get()
                .uri("/species")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<Species>>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertTrue(response.getData().size() > 0);
        assertEquals("Human", response.getData().get(0).getName());
        assertEquals("Droid", response.getData().get(1).getName());
    }

    @Test
    public void getSpeciesByIdTest() {
        Response<Species> response = client.get()
                .uri("/species/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Species>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("Human", response.getData().getName());
        assertEquals("mammal", response.getData().getClassification());
    }
}
