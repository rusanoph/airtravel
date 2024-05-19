package ideaplatform.rusanoph.model;

import java.util.List;
import java.util.stream.Collectors;

public class TicketList {
    
    List<Ticket> tickets;

    public TicketList(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Ticket getMinimalTimeTravel(String origin, String destination) {
        return this.tickets.stream()
            .filter(t -> t.getOrigin().equals(origin) && t.getDestination().equals(destination))
            .min((t1, t2) -> t1.getTimeTravel().compareTo(t2.getTimeTravel()))
            .get();
    }

    public double getMeanPrice(String origin, String destination) {
        return this.tickets.stream()
            .filter(t -> t.getOrigin().equals(origin) && t.getDestination().equals(destination))
            .mapToDouble(Ticket::getPrice)
            .average()
            .getAsDouble();
    }

    public double getMedianPrice(String origin, String destination) {
        double[] sortedTicketPrices = tickets.stream()
            .filter(ticket -> ticket.getOrigin().equals("VVO") && ticket.getDestination().equals("TLV"))
            .mapToDouble(Ticket::getPrice)
            .sorted()
            .toArray();
        
        int size = sortedTicketPrices.length;
        double medianTicketPrice = size % 2 == 0 
            ? (sortedTicketPrices[size / 2 - 1] + sortedTicketPrices[size / 2]) / 2.0
            : sortedTicketPrices[size / 2];

        return medianTicketPrice;
    }

}
