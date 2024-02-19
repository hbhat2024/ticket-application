package com.harish.ticketapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeatAllocationController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private SeatService  seatService;
    @GetMapping("/seat-allocation")
    public List<SeatAllocation> getSeatAllocation(@RequestParam String section) {
      return ticketService.getSeatAllocation(section);
    }

    @PatchMapping("/seat-allocation")
    public SeatAllocation modifySeatAllocation(@RequestParam String userEmailAddress, @RequestBody Seat newSeat) {
        if(seatService.isSeatAvailable(newSeat)) {
            Ticket ticket = ticketService.getTicketForUser(userEmailAddress);
            seatService.makeSeatAvailable(ticket.seat());
            Ticket newTicket = ticketService.modifySeatAllocation(userEmailAddress, newSeat);
            seatService.makeSeatUnavailable(newSeat);
            return new SeatAllocation(newTicket.user(), newTicket.seat());
        }
        return null;
    }

}
