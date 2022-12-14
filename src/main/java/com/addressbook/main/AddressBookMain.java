package com.addressbook.main;

import java.time.LocalDate;
import java.util.List;

public class AddressBookMain {
    public List<ContactPerson> contacts;
    private AddressBookJDBCService addressBookDBService;


    public AddressBookMain() {
        addressBookDBService=AddressBookJDBCService.getInstance();
    }

    public List<ContactPerson> readContactData() {
        contacts = addressBookDBService.readAddressBook();
        return contacts;
    }

    public void updateRecord(String firstName, String data, String columnName) throws AddressBookException {
        int result = addressBookDBService.updateAddressBookData(firstName,data,columnName);
        if(result == 0)
            return;
        ContactPerson contactData=this.getContactDataByName(firstName);
        if (contactData!=null){
            contactData.setPhoneNumber(data);
        }
    }

    public boolean checkAddressBookInSyncWithDB(String firstName) {
        List<ContactPerson> contactPersonList = addressBookDBService.getAddressBookData(firstName);
        return contactPersonList.get(0).equals(getContactDataByName(firstName));
    }

    private ContactPerson getContactDataByName(String firstName) {
        ContactPerson contactData = this.contacts.stream()
                .filter(contact->contact.getFirstName().equals(firstName))
                .findFirst()
                .orElse(null);
        return contactData;
    }

    public List<ContactPerson> readContactDataForDateRange(LocalDate startDate, LocalDate endDate) {
        return addressBookDBService.getContactDataForDateRange(startDate,endDate);
    }

    public List<ContactPerson> getContactsByCityOrState(String city, String state) {
        List<ContactPerson> contactPersonList = addressBookDBService.getContactsByCityOrState(city, state);
        return contactPersonList;
    }

    public void addContactToDatabase(String firstName, String lastName, String address, String city, String state, String zip,
                                     String phoneNo, String email, String name, String type, LocalDate date) throws AddressBookException{
        contacts.add(addressBookDBService.addContactToAddressBook(firstName,lastName,address,city,state,zip,
                                                                        phoneNo,email,name,type,date));
    }
}
