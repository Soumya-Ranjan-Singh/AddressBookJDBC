//Encapsulated class

package com.addressbook.main;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class ContactPerson implements Serializable {

    //Declaring variables for Address Book System
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String email;

    //Creating one parameterized constructor
    public ContactPerson(String firstName, String lastName, String address,
                         String city, String state, String zip, String phoneNumber, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setCity(city);
        setState(state);
        setZip(zip);
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    //Create setter methods for all instance variables.
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Creating getter method.
    public String getFirstName() {
        return firstName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    //Grab to a toString method
    @Override
    public String toString() {
        return "ContactPerson{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ContactPerson)) return false;

        ContactPerson that = (ContactPerson) o;

        return new EqualsBuilder().append(getFirstName(), that.getFirstName()).append(getLastName(), that.getLastName()).append(getAddress(), that.getAddress()).append(getCity(), that.getCity()).append(getState(), that.getState()).append(getZip(), that.getZip()).append(getPhoneNumber(), that.getPhoneNumber()).append(getEmail(), that.getEmail()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getFirstName()).append(getLastName()).append(getAddress()).append(getCity()).append(getState()).append(getZip()).append(getPhoneNumber()).append(getEmail()).toHashCode();
    }
}

