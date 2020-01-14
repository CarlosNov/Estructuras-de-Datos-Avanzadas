package usecase;


import material.maps.HashTableMapDH;

import java.util.*;

public class FlightManager {

    private HashTableMapDH<Flight, HashSet<Passenger>> flights = new HashTableMapDH<>();
    private ArrayList<Flight> flightsInOrderEntry = new ArrayList<>();

    public Flight addFlight(String company, int flightCode, int year, int month, int day) {
        Flight f = new Flight();
        f.setCompany(company);
        f.setFlightCode(flightCode);
        f.setDate(year, month, day);
        flights.put(f, new HashSet<>());
        flightsInOrderEntry.add(f);
        return f;
    }

    public Flight addFlight(String company, int flightCode, int year, int month, int day, String destination) {
        Flight f = new Flight();
        f.setCompany(company);
        f.setFlightCode(flightCode);
        f.setDate(year, month, day);
        f.setDestination(destination);
        flights.put(f, new HashSet<>());
        flightsInOrderEntry.add(f);
        return f;
    }

    public Flight getFlight(String company, int flightCode, int year, int month, int day) {
        Flight f = new Flight();
        f.setCompany(company);
        f.setFlightCode(flightCode);
        f.setDate(year, month, day);

        for(Flight flight : flights.keys())
        {
            if(flight.equals(f))
            {
                return flight;
            }
        }
        throw new RuntimeException();
    }

    public void updateFlight(String company, int flightCode, int year, int month, int day, Flight updatedFlightInfo) {
        Flight f = new Flight();
        f.setCompany(company);
        f.setFlightCode(flightCode);
        f.setDate(year, month, day);

        for(Flight flight : flights.keys())
        {
            if(flight.equals(f))
            {
                flight.setCompany(updatedFlightInfo.getCompany());
                flight.setFlightCode(updatedFlightInfo.getFlightCode());
                flight.setDate(updatedFlightInfo.getYear(), updatedFlightInfo.getMonth(), updatedFlightInfo.getDay());
                flight.setOrigin(updatedFlightInfo.getOrigin());
                flight.setDestination(updatedFlightInfo.getDestination());
                flight.setCapacity(updatedFlightInfo.getCapacity());
                flight.setDelay(updatedFlightInfo.getDelay());
                for(String s : updatedFlightInfo.getAllAttributes())
                {
                    flight.setProperty(s,updatedFlightInfo.getProperty(s));
                }
                return;
            }
        }
        throw new RuntimeException();
    }

    public void addPassenger(String dni, String name, String surname, Flight flight) {
        Passenger p = new Passenger();
        p.setDNI(dni);
        p.setName(name);
        p.setSurname(surname);

        if(flights.get(flight).contains(p))
        {
            flights.get(flight).remove(p);
            flights.get(flight).add(p);
        }
    }


    public Iterable<Passenger> getPassengers(String company, int flightCode, int year, int month, int day) {
        Flight f = new Flight();
        f.setCompany(company);
        f.setFlightCode(flightCode);
        f.setDate(year, month, day);

        return flights.get(f);
    }

    public Iterable<Flight> flightsByDate(int year, int month, int day) {
        ArrayList arrayList = new ArrayList();
        for(Flight f : flights.keys())
        {
            if(f.getYear() == year && f.getMonth() == month && f.getDay() == day)
            {
                arrayList.add(f);
            }
        }
        return arrayList;
    }

    public Iterable<Flight> getFlightsByPassenger(Passenger passenger) {
        ArrayList arrayList = new ArrayList();
        for(Flight f : flights.keys())
        {
            for (HashSet set : flights.values())
            {
                if(set.contains(passenger))
                {
                    arrayList.add(f);
                }
            }
        }
        return arrayList;
    }

    public Iterable<Flight> getFlightsByDestination(String destination, int year, int month, int day) {
        ArrayList arrayList = new ArrayList();

        return Collections.unmodifiableCollection(arrayList);
    }

    public Iterable<Flight> getFlightsByDestination(String destination) {
        LinkedList<Flight> flightsList = new LinkedList<>();
        for(Flight f: flights.keys())
            if (f.getDestination().startsWith(destination))
                flightsList.add(f);
        return flightsList;
    }

    public Iterable<Flight> getFlightsByCompany(String company) {
        LinkedList<Flight> flightsList = new LinkedList<>();
        for(Flight f: flights.keys())
            if (f.getCompany().startsWith(company))
                flightsList.add(f);
        return flightsList;
    }

    public Iterable<Flight> getAllFlights(){
        LinkedList<Flight> flightsList = new LinkedList<>();
        for(Flight f: flights.keys())
            flightsList.add(f);
        return Collections.unmodifiableCollection(flightsList);
    }

    public Iterable<Flight> getAllFlightsOrdered(){
        return Collections.unmodifiableCollection(flightsInOrderEntry);
    }
}