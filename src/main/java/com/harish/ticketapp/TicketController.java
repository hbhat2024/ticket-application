package com.harish.ticketapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private SeatService seatService;

    @PostMapping("/ticket")
    public Ticket generateTicket(@RequestBody TicketPurchaseRequest ticketPurchaseRequest) {
        Seat seat = seatService.retrieveAvailableSeat();
        return ticketService.generateTicket(ticketPurchaseRequest, seat);
    }

    @GetMapping("/ticket")
    public Ticket showTicket(@RequestParam String userEmailAddress) {
        return ticketService.getTicketForUser(userEmailAddress);
    }
}
