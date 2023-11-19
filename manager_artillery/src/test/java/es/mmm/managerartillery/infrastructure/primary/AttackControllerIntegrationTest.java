package es.mmm.managerartillery.infrastructure.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.mmm.managerartillery.application.dto.AttackRequestMother;
import es.mmm.managerartillery.infrastructure.dto.AttackRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AttackControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private final CountDownLatch serverStartedLatch = new CountDownLatch(1);
    private List<ClientAndServer> clientAndServerList;


    @BeforeAll
    void setUp() {
        clientAndServerList = new ArrayList<>();
        new Thread(() -> {
            createServer("1", 3500,"{\"casualties\": 10, \"generation\": 1}");
            createServer("2", 1500,"{\"casualties\": 10, \"generation\": 2}");
            createServer("3", 2500,"{\"casualties\": 10, \"generation\": 3}");
            serverStartedLatch.countDown();
        }).start();
    }


    @AfterAll
    void tearDown() {
        clientAndServerList.forEach(ClientAndServer::stop);
    }


    private void createServer(String id, int delayMilliseconds, String response) {
        ClientAndServer clientAndServer = ClientAndServer.startClientAndServer(Integer.parseInt("300"+id));
        MockServerClient mockServerClient = new MockServerClient("localhost", Integer.parseInt("300"+id));
        mockServerClient
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/fire")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                                .withBody(response)
                                .withDelay(TimeUnit.MILLISECONDS, delayMilliseconds)
                );
        mockServerClient
                .when(
                        request()
                                .withMethod("get")
                                .withPath("/status")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                                .withBody("{\"generation\": "+id+",\"available\":true}")
                                .withDelay(TimeUnit.MILLISECONDS, delayMilliseconds)
                );
        clientAndServerList.add(clientAndServer);
    }


    private void serversCountDown() {
        try {
            serverStartedLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for server to start");
        }
    }

    @Test
    void execute() throws Exception {
        serversCountDown();
        AttackRequest attackRequest = AttackRequestMother.valid();
        String expected = "{\"target\":{\"x\":5,\"y\":5},\"casualties\":10,\"generation\":1}";

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult result = mockMvc.perform(
                        post("/attack")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(attackRequest))
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(expected);
    }

}
