package com.addressbook.test;

import com.addressbook.main.ContactPerson;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AddressBookTest {
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        AddressBookJDBCService addressBookJDBCService = new AddressBookJDBCService();
        List<ContactPerson> contactPersonList = addressBookJDBCService.readAddressBook();
        assertEquals(5 , contactPersonList.size());
    }
}
