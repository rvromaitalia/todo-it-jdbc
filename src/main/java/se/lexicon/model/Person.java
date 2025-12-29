package se.lexicon.model;

import java.util.Objects;

public class Person {
    private int personId;
    private String firstName;
    private String lastName;

    public Person(){} //Public constructor

    //USe this constructor as  a setter for 'Person' class fields
    public Person(int personId, String firstName, String lastName){
        this.personId = personId;
        this.lastName = lastName;
        this.firstName = firstName;
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
