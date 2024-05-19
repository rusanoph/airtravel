package ideaplatform.rusanoph.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import ideaplatform.rusanoph.util.LocalDateDeserializer;
import ideaplatform.rusanoph.util.LocalTimeDeserializer;

public class TicketList {
    
    List<Ticket> tickets;

    public static TicketList fromJson(Path jsonPath) throws IOException {
        File jsonFile = new File(jsonPath.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule()
            .addDeserializer(LocalDate.class, new LocalDateDeserializer())
            .addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        objectMapper.registerModule(module);

        JsonNode rootNode = objectMapper.readTree(jsonFile);
        JsonNode ticketsNode = rootNode.get("tickets");  // null if tickets not found

        List<Ticket> tickets = objectMapper.convertValue(ticketsNode, new TypeReference<List<Ticket>>() {});

        return new TicketList(tickets);
    }

    public TicketList(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<String> getCarriers() {
        return tickets.stream()
            .map(Ticket::getCarrier)
            .distinct()
            .collect(Collectors.toList());
    }

    public Ticket getMinimalTimeTravel(String carrier, String origin, String destination) {
        return this.tickets.stream()
            .filter(t -> 
                t.getCarrier().equals(carrier) && 
                t.getOrigin().equals(origin) && 
                t.getDestination().equals(destination)
            )
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
