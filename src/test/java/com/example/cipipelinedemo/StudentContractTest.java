
package com.example.cipipelinedemo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

@PactConsumerTest
@PactTestFor(
    providerName = "StudentAPI",
    pactMethod = "studentContract"
)
class StudentContractTest {

    @Pact(consumer = "StudentFrontend")
    public V4Pact studentContract(PactBuilder builder) {
        return builder
                .usingLegacyDsl()
                .given("student 1 exists")
                .uponReceiving("a request for student 1")
                .path("/students/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(java.util.Map.of("Content-Type", "application/json"))
                .body("{\"id\":1,\"name\":\"Alice\"}")
                .toPact(V4Pact.class);
    }

    @Test
    void testStudentContract(MockServer server) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server.getUrl() + "/students/1"))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("\"id\":1"));
        assertTrue(response.body().contains("\"name\":\"Alice\""));
    }
}
