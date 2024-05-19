package ideaplatform.rusanoph;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import ideaplatform.rusanoph.model.Ticket;
import ideaplatform.rusanoph.model.TicketList;

public class App {
    public static void main( String[] args ) {
        
        Path ticketPath = Paths.get("json", "tickets.json");

        try {
            TicketList ticketList = TicketList.fromJson(ticketPath);

            System.out.println("=== Minimal Time Travel For Each Carrier ===");
            for (String carrier : ticketList.getCarriers()) {
                Ticket minTimeTravel = ticketList.getMinimalTimeTravel(carrier, "VVO", "TLV");
                System.out.println(carrier + " - " + minTimeTravel.getTimeTravelAsString());
            }

            System.out.println("=== Difference between Mean and Median price ===");
            double meanTicketPrice = ticketList.getMeanPrice("VVO", "TLV");
            double medianTicketPrice = ticketList.getMedianPrice("VVO", "TLV");
            System.out.println("Mean - Median = " + (meanTicketPrice - medianTicketPrice));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}   
