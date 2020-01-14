package usecase;


import material.maps.HashTableMapDH;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Flight implements Comparable{

    private String company;
    private int flightCode;
    private LocalDate date;
    private int minutes;
    private int hours;
    private String origin;
    private String destination;
    private int capacity;
    private int delay;
    private HashTableMapDH<String, String> properties;

    public Flight(){
        this.company = "";
        this.flightCode = -1;
        this.date = LocalDate.of(1,1,1);
        this.hours = 0;
        this.minutes = 0;
        this.origin = "";
        this.destination = "";
        this.capacity = 0;
        this.delay = 0;
        this.properties = new HashTableMapDH<>();
    }

    public void setTime(int hours, int minutes) {
        this.minutes = minutes;
        this.hours = hours;
    }

    public int getHours() {
        return this.hours;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(int flightCode){
        this.flightCode = flightCode;
    }

    public void setDate(int year, int month, int day) {
        this.date = LocalDate.of(year,month,day);
    }

    public int getYear() {
        return this.date.getYear();
    }

    public int getMonth() { return this.date.getMonthValue(); }

    public int getDay() {
        return this.date.getDayOfMonth();
    }

    public LocalDate getDate() { return this.date; }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setProperty(String attribute, String value) {
        this.properties.put(attribute, value);
    }

    public String getProperty(String attribute) {
        return this.properties.get(attribute);
    }

    public Iterable<String> getAllAttributes() {
        return this.properties.keys();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return flightCode == flight.flightCode &&
                company.equals(flight.company) &&
                date.equals(flight.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, flightCode, date);
    }

    @Override
    public int compareTo(Object o) {
        Flight f = (Flight) o;
        int month = f.getMonth();
        if(month == 0) month = 1;

        int day = f.getDay();
        if(day == 0) day = 1;

        int thisMonth = this.getMonth();
        if(thisMonth == 0) thisMonth = 1;

        int thisDay = this.getDay();
        if(thisDay == 0) thisDay = 1;

        LocalDate date1 = LocalDate.of(this.getYear(), thisMonth, thisDay);
        LocalDate date2 = LocalDate.of(f.getYear(), month, day);
        if (date1.equals(date2)){
            return 0;
        } else if (date1.isBefore(date2))
            return -1;
        else
            return 1;
    }

    @Override
    public String toString() {
        return getDay() + "-" + getMonth() + "-" + getYear() + "\t" +
                getCompany()+getFlightCode() + "\t" + destination;
    }
}
