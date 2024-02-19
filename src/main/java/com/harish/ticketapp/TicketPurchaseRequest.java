package com.harish.ticketapp;

public record TicketPurchaseRequest(String sourceLocation, String destinationLocation, User user, double price) {
}
