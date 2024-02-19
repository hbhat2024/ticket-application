package com.harish.ticketapp;

public record Ticket(long id, String sourceLocation, String destinationLocation, User user, double price, Seat seat) {
}
