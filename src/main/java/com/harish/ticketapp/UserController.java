package com.harish.ticketapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private SeatService seatService;
    @DeleteMapping("/users")
    public User removeUser(@RequestParam String userEmailAddress) {
        Ticket ticket = ticketService.getTicketForUser(userEmailAddress);
        User user = ticket.user();
        ticketService.cancelTicket(ticket.id());
        seatService.makeSeatAvailable(ticket.seat());
        return user;
    }
}
