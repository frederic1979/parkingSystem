package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.DateConverter;

import java.time.temporal.ChronoUnit;

import static com.parkit.parkingsystem.util.DateConverter.*;

public class FareCalculatorService {

    public long durationInMinutes(Ticket ticket){
        return ChronoUnit.MINUTES.between(convertDateToLocalDateTime(ticket.getInTime()),
                convertDateToLocalDateTime(ticket.getOutTime()));
    }

    public long durationInHours(Ticket ticket){
        return ChronoUnit.HOURS.between(convertDateToLocalDateTime(ticket.getInTime()),
                convertDateToLocalDateTime(ticket.getOutTime()));
    }

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        long durationHours = durationInHours(ticket);
        long durationMinutes = durationInMinutes(ticket);

        if (durationHours == 0) {
            if (durationMinutes <= 30) {
                ticket.setPrice(0);
            } else {
                durationHours = 1;
            }
        }

            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR: {
                    ticket.setPrice(durationHours * Fare.CAR_RATE_PER_HOUR);
                    break;
                }
                case BIKE: {
                    ticket.setPrice(durationHours * Fare.BIKE_RATE_PER_HOUR);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unkown Parking Type");
            }
        }
}