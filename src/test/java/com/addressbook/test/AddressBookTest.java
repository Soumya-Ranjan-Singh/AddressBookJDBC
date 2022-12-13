package com.addressbook.test;

import com.addressbook.main.AddressBookException;
import com.addressbook.main.AddressBookMain;
import com.addressbook.main.ContactPerson;
import org.junit.Before;
import org.junit.Test;

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
    public void givenAddressBook_WhenRetrieved_ShouldMatchCount() throws AddressBookException {
        assertEquals(5, contacts.size());
    }

    @Test
    public void givenNewAddressForRecord_WhenUpdated_ShouldSyncWithDatabase() throws AddressBookException {
        addressBookFunction.updateRecord("Soumya","8908641811", "PhoneNo");
        boolean result=addressBookFunction.checkAddressBookInSyncWithDB("Soumya");
        assertTrue(result);
    }
}
