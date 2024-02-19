package com.harish.ticketapp;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SeatService {
    private static Map<Integer, Boolean> sectionASeatAvailability = new HashMap<>();
    private static Map<Integer, Boolean> sectionBSeatAvailability = new HashMap<>();
    private static final int SECTION_A_SEAT_COUNT = 100;
    private static final int SECTION_B_SEAT_COUNT = 50;

    static {
        for (int seatNumber = 1; seatNumber <= SECTION_A_SEAT_COUNT; seatNumber++) {
            sectionASeatAvailability.put(seatNumber, true);
        }
        for (int seatNumber = 1; seatNumber <= SECTION_B_SEAT_COUNT; seatNumber++) {
            sectionBSeatAvailability.put(seatNumber, true);
        }
    }

    public Seat retrieveAvailableSeat() {
        Seat availableSeat = null;
        String section = "A";
        for (Integer seatNumber: sectionASeatAvailability.keySet()) {
            if (sectionASeatAvailability.get(seatNumber)) {
                availableSeat = new Seat(section, seatNumber);
                sectionASeatAvailability.put(seatNumber, false);
                return availableSeat;
            }
        }
        section = "B";
        for (Integer seatNumber: sectionBSeatAvailability.keySet()) {
            if (sectionASeatAvailability.get(seatNumber)) {
                availableSeat = new Seat(section, seatNumber);
                sectionASeatAvailability.put(seatNumber, false);
                return availableSeat;
            }
        }
        return availableSeat;
    }

    public void makeSeatAvailable(Seat seat) {
        switch (seat.section()) {
            case "A":
                sectionASeatAvailability.put(seat.seatNumber(), true);
                break;
            case "B":
                sectionBSeatAvailability.put(seat.seatNumber(), true);
                break;
        }
    }

    public void makeSeatUnavailable(Seat seat) {
        switch (seat.section()) {
            case "A":
                sectionASeatAvailability.put(seat.seatNumber(), false);
                break;
            case "B":
                sectionBSeatAvailability.put(seat.seatNumber(), false);
                break;
        }
    }

    public boolean isSeatAvailable(Seat seat) {
        return switch (seat.section()) {
            case "A" -> sectionASeatAvailability.get(seat.seatNumber());
            case "B" -> sectionBSeatAvailability.get(seat.seatNumber());
            default -> false;
        };
    }
}
