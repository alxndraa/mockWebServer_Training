package com.demo.starwars.controller;

import com.demo.starwars.StarwarsApplication;
import com.demo.starwars.entity.Vehicle;
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
public class VehicleControllerTest {

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
                    case "/vehicles":
                        path = "src/test/resources/jsonResponse/vehicle/VehiclesResponse.json";
                        break;
                    case "/vehicles/1":
                        path = "src/test/resources/jsonResponse/vehicle/OneVehicleResponse.json";
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
    public void getAllVehiclesTest() {
        Response<List<Vehicle>> response = client.get()
                .uri("/vehicles")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<Vehicle>>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertTrue(response.getData().size() > 0);
        assertEquals("Sand Crawler", response.getData().get(0).getName());
        assertEquals("T-16 skyhopper", response.getData().get(1).getName());
    }

    @Test
    public void getVehiclesByIdTest() {
        Response<Vehicle> response = client.get()
                .uri("/vehicles/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Vehicle>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("Sand Crawler", response.getData().getName());
        assertEquals("Digger Crawler", response.getData().getModel());
    }
}
