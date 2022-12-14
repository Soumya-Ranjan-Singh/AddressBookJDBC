package com.addressbook.test;

import com.addressbook.main.AddressBookException;
import com.addressbook.main.AddressBookMain;
import com.addressbook.main.ContactPerson;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddressBookTest {
    AddressBookMain addressBookFunction;
    List<ContactPerson> contacts;

    @Before
    public void init() {
        addressBookFunction=new AddressBookMain();
        contacts = addressBookFunction.readContactData();
    }

    @Test
    public void givenAddressBook_WhenRetrieved_ShouldMatchCount() {
        assertEquals(5, contacts.size());
    }

    @Test
    public void givenNewAddressForRecord_WhenUpdated_ShouldSyncWithDatabase() throws AddressBookException {
        addressBookFunction.updateRecord("Soumya","8908641811", "PhoneNo");
        boolean result=addressBookFunction.checkAddressBookInSyncWithDB("Soumya");
        assertTrue(result);
    }

    @Test
    public void givenDateRangeForRecord_WhenRetrieved_ShouldReturnProperData() {
        LocalDate startDate = LocalDate.of(2018,01,01);
        LocalDate endDate = LocalDate.of(2019,12,12);
        List<ContactPerson> readDataInGivenDateRange = addressBookFunction.readContactDataForDateRange(startDate, endDate);
        assertEquals(3, readDataInGivenDateRange.size());
    }

    @Test
    public void givenCityOrState_WhenRetrieved_ShouldReturnProperData() {
        List<ContactPerson> readDataByCityOrState = addressBookFunction.getContactsByCityOrState("Cuttack", "Karnataka");
        assertEquals(3, readDataByCityOrState.size());
    }

    @Test
    public void givenNewContact_WhenAdded_ShouldSyncWithDB() throws AddressBookException {
        addressBookFunction.addContactToDatabase("Nigam", "Jena", "Nakhara", "BBSR", "Odisha",
                "742005", "9987555666", "nigamj@gmail.com", "SimContact", "Friends", LocalDate.now());
        boolean result = addressBookFunction.checkAddressBookInSyncWithDB("Nigam");
        assertTrue(result);
    }
}
