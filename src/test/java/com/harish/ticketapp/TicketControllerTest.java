package com.harish.ticketapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TicketController.class)
public class TicketControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TicketService ticketService;
    @MockBean
    private SeatService seatService;

    @Test
    public void givenTicketPurchaseRequest_whenPostRequest_thenReturnJsonReceipt() throws Exception {
        User mockUser = new User("John", "Doe", "jdoe@example.com");
        TicketPurchaseRequest mockTicketPurchaseRequest = new TicketPurchaseRequest("London", "Paris", mockUser, 20.0);
        Seat mockSeat = new Seat("A", 1);
        Ticket mockTicket = new Ticket(1L, "London", "Paris", mockUser, 20.0, mockSeat);
        String mockContent = """
                {
                    "sourceLocation": "London",
                    "destinationLocation": "Paris",
                    "user": {
                        "firstName": "John",
                        "lastName": "Doe",
                        "emailAddress": "jdoe@example.com"
                    },
                    "price": 20.0
                }""";
        given(seatService.retrieveAvailableSeat()).willReturn(mockSeat);
        given(ticketService.generateTicket(mockTicketPurchaseRequest, mockSeat)).willReturn(mockTicket);

        mvc.perform(post("/ticket").contentType(APPLICATION_JSON).content(mockContent)).andExpect(status().isOk()).andExpect(jsonPath("$.user.emailAddress", is(mockUser.emailAddress())));


    }
}
