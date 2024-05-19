package ideaplatform.rusanoph.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {
    private String origin;
    private String destination;

    private LocalDate departure_date;
    private LocalTime departure_time;
    private LocalDate arrival_date;
    private LocalTime arrival_time;

    private double price;

    public Duration getTimeTravel() {
        return Duration.between(
            LocalDateTime.of(departure_date, departure_time), 
            LocalDateTime.of(arrival_date, arrival_time)
        );
    }

    public String getTimeTravelAsString() {
        return String.format("%s days %s hours %s minutes", 
            getTimeTravel().toDays(), 
            getTimeTravel().toHours() % 24, 
            getTimeTravel().toMinutes() % 60
        );
    }
    
    //#region Getters
    public String getOrigin() {
        return origin;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public LocalDate getDepartureDate() {
        return departure_date;
    }
    
    public LocalTime getDepartureTime() {
        return departure_time;
    }
    
    public LocalDate getArrivalDate() {
        return arrival_date;
    }
    
    public LocalTime getArrivalTime() {
        return arrival_time;
    }

    public double getPrice() {
        return price;
    }
    //#endregion

    //#region Setters
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDeparture_date(LocalDate departure_date) {
        this.departure_date = departure_date;
    }

    public void setDeparture_time(LocalTime departure_time) {
        this.departure_time = departure_time;
    }

    public void setArrival_date(LocalDate arrival_date) {
        this.arrival_date = arrival_date;
    }

    public void setArrival_time(LocalTime arrival_time) {
        this.arrival_time = arrival_time;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    //#endregion
    
}
