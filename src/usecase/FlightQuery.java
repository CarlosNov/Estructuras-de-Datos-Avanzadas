package usecase;


import java.sql.Time;
import java.time.LocalDate;
import java.util.*;

public class FlightQuery {
    /*
     * En cada caso solo se ordena por las claves especificadas.
     *
     * Al buscar por (date1, date1) se deberia devolver Vuelo2 pero al ser la misma fecha se compara
     * por codigo de vuelo y se excluye.
     *
     * Vuelo( date1, code=550) > Vuelo2 (date1, code=0)
     */

    private FlightManager flightManager = new FlightManager();

    public void addFlight(Flight flight) {
        flightManager.addFlight(flight.getCompany(), flight.getFlightCode(), flight.getYear(), flight.getMonth(), flight.getDay(), flight.getDestination());
    }


    public Iterable<Flight> searchByDates(int start_year, int start_month, int start_day, int end_year, int end_month, int end_day) throws RuntimeException {
        LinkedList<Flight> flights = new LinkedList<>();
        LocalDate date1 = LocalDate.of(start_year, start_month, start_day);
        LocalDate date2 = LocalDate.of(end_year, end_month, end_day);

        if (date1.isAfter(date2)){
            throw new RuntimeException("Invalid range. (min>max)");
        } else{
            Iterable<Flight> it = flightManager.getAllFlights();

            for(Flight f : flightManager.getAllFlightsOrdered()) {
                LocalDate flightDate = f.getDate();
                if(date1.isBefore(flightDate) && date2.isAfter(flightDate) || date1.equals(flightDate) ||date2.equals(flightDate)) {
                    flights.add(f);
                }
            }
            Collections.sort(flights);
        }
        return flights;
    }

    public Iterable<Flight> searchByDestinations(String start_destination, String end_destination) throws RuntimeException {
        LinkedList<Flight> flights = new LinkedList<>();

        if (start_destination.compareToIgnoreCase(end_destination) > 0){
            throw new RuntimeException("Invalid range. (min>max)");
        } else if (start_destination.compareToIgnoreCase(end_destination) == 0){
            for (Flight f: flightManager.getFlightsByDestination(start_destination)){
                flights.add(f);
            }
        } else {
            for (Flight f: flightManager.getAllFlightsOrdered()){
                if (f.getDestination().compareToIgnoreCase(start_destination)>= 0 && f.getDestination().compareToIgnoreCase(end_destination) <= 0){
                    flights.add(f);
                }
            }
        }
        Collections.sort(flights, new Comparator<Flight>(){
            public int compare(Flight s1,Flight s2){
                if(s1.getDestination().compareTo(s2.getDestination()) == 0) {
                    if (s1.getFlightCode() == s2.getFlightCode()) {
                        if (s1.getDate().isBefore(s2.getDate()))
                            return -1;
                        else
                            return 1;
                    }
                }
                return s1.getDestination().compareTo(s2.getDestination());
            }});
        return flights;
    }


    public Iterable<Flight> searchByCompanyAndFLightCode(String start_company, int start_flightCode, String end_company, int end_flightCode) {
        LinkedList<Flight> flights = new LinkedList<>();

        int strComp = start_company.compareToIgnoreCase(end_company);

        if (strComp > 0 || start_flightCode > end_flightCode){
            throw new RuntimeException("Invalid range. (min>max)");
        } else if (strComp == 0){
            for (Flight f: flightManager.getFlightsByCompany(start_company)){
                if (f.getFlightCode() >= start_flightCode && f.getFlightCode() <= end_flightCode)
                    flights.add(f);
            }
        } else {
            for (Flight f: flightManager.getAllFlightsOrdered()){
                if ((f.getCompany().compareToIgnoreCase(start_company) >= 0) && (f.getCompany().compareToIgnoreCase(end_company) <= 0)
                        && (f.getFlightCode() >= start_flightCode) && (f.getFlightCode() <= end_flightCode)){
                    flights.add(f);
                }
            }
        }
        Collections.sort(flights, new Comparator<Flight>(){
            public int compare(Flight s1,Flight s2){
                if(s1.getCompany().compareTo(s2.getCompany()) == 0) {
                    if (s1.getFlightCode() == s2.getFlightCode()) {
                        if (s1.getDate().isBefore(s2.getDate()))
                            return -1;
                        else
                            return 1;
                    }
                    return s1.getFlightCode() - s2.getFlightCode();
                }
                return s1.getCompany().compareTo(s2.getCompany());
            }});
        return flights;
    }
}
