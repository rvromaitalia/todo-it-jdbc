package se.lexicon.model;

import java.util.Objects;

public class Person {
    private int personId;
    private String firstName;
    private String lastName;

    // Required by frameworks, JDBC mapping, and for creating empty objects
    // Used when an object is populated later via setters or ResultSet mapping
    public Person(){} //Public constructor

    // Used when creating a Person object from database data
    // Typically called inside DAO implementations when mapping ResultSet → Person
    public Person(int personId, String firstName, String lastName){
        this.personId = personId;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    // Used when creating a new Person before it is persisted to the database
    // The ID is set to 0 and will be replaced with a generated value on INSERT
    public Person(String firstName, String lastName) {
        this(0, firstName, lastName);
    }


    public int getPersonId() { return personId;}
    public void setPersonId(int personId) {this.personId = personId;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personId == person.personId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId);
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
