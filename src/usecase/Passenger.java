package usecase;


import java.util.Objects;

public class Passenger {

    private String DNI;
    private String name;
    private String surname;

    public String getDNI() {
        return this.getDNI();
    }

    public void setDNI(String dni) {
        this.DNI = dni;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return DNI.equals(passenger.DNI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(DNI);
    }
}
