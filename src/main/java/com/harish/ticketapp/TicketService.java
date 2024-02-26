package com.harish.ticketapp;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private static List<Ticket> tickets = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public Ticket generateTicket(TicketPurchaseRequest ticketPurchaseRequest, Seat seat) {
        Ticket newTicket = new Ticket(counter.incrementAndGet(), ticketPurchaseRequest.sourceLocation(), ticketPurchaseRequest.destinationLocation(), ticketPurchaseRequest.user(), ticketPurchaseRequest.price(), seat);
        tickets.add(newTicket);
        return newTicket;
    }

    public Ticket getTicketForUser(String userEmailAddress) {
        return tickets.stream().filter(ticket -> ticket.user().emailAddress().equals(userEmailAddress)).map(Optional::ofNullable).findFirst().flatMap(Function.identity()).orElse(null);
    }

    public List<SeatAllocation> getSeatAllocation(String section) {
        return tickets.stream().filter(ticket -> ticket.seat().section().equals(section)).map(ticket -> new SeatAllocation(ticket.user(), ticket.seat())).toList();
    }

    public void cancelTicket(long id) {
        tickets = tickets.stream().filter(ticket -> ticket.id() != id).collect(Collectors.toList());
    }

    public Ticket modifySeatAllocation(String userEmailAddress, Seat newSeat) {
        Ticket ticket = tickets.stream().filter(t -> t.user().emailAddress().equals(userEmailAddress)).findFirst().orElseGet(null);
        Ticket newTicket = new Ticket(ticket.id(), ticket.sourceLocation(), ticket.destinationLocation(), ticket.user(), ticket.price(), newSeat);
        tickets.remove(ticket);
        tickets.add(newTicket);
        return newTicket;
    }
}
