package ideaplatform.rusanoph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ideaplatform.rusanoph.model.Ticket;
import ideaplatform.rusanoph.model.TicketList;
import ideaplatform.rusanoph.util.LocalDateDeserializer;
import ideaplatform.rusanoph.util.LocalTimeDeserializer;

public class App {
    public static void main( String[] args ) {
        
        Path ticketPath = Paths.get("json", "tickets.json");

        try {
            TicketList ticketList = readTicketsFromJson(ticketPath);

            System.out.println("=== Minimal Time Travel ===");
            Ticket minTimeTravel = ticketList.getMinimalTimeTravel("VVO", "TLV");
            System.out.println("Minimal time travel is " + minTimeTravel.getTimeTravelAsString());

            System.out.println("=== Difference between Mean and Median price ===");
            double meanTicketPrice = ticketList.getMeanPrice("VVO", "TLV");
            double medianTicketPrice = ticketList.getMedianPrice("VVO", "TLV");
            System.out.println("Mean - Median = " + (meanTicketPrice - medianTicketPrice));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TicketList readTicketsFromJson(Path jsonPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        objectMapper.registerModule(module);

        File jsonFile = new File(jsonPath.toString());

        JsonNode rootNode = objectMapper.readTree(jsonFile);
        JsonNode ticketsNode = rootNode.get("tickets");  // null if tickets not found

        List<Ticket> tickets = objectMapper.convertValue(ticketsNode, new TypeReference<List<Ticket>>() {});

        return new TicketList(tickets);
    }
}   
