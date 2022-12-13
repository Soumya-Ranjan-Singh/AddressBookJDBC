//This program is to display Welcome to Address Book System in main class.
//Use Case 1 is to Create one contact in Address Book System.
//Use Case 2 is to Add another contact in Address Book System.
//Use Case 3 is to Edit existing contact in Address Book System.
//Use Case 4 is to Delete a contact in Address Book System.
//Use Case 5 is to Add multiple persons at a time in Address Book System.
//Use Case 6 is to Add multiple Address Book to the System but condition is that each Address Book has a unique name.
//Use Case 7 is to Ensure that no duplicate entry of the same person in a particular address book.
//Use Case 8 is to Search person in a city or state across the multiple address book.
//Use Case 9 is to Search persons by city or state.
//Use Case 10 is to View persons by city or state.
//Use Case 11 is to Sort the entries in the address book alphabetically by Person's name.
//Use Case 12 is to Sort the entries in the address book by city, state or zip.
//Use Case 13 is to read or write a person contact into a file using file io.
//Use Case 14 is to read or write a person contact into a csv file using csv io.
//Use Case 15 is to read or write a person contact into a json file using json io.


package com.addressbook.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.addressbook.main.AddressBookSystemMain.IOService.*;

public class AddressBookSystemMain {

    public enum IOService {CONSOLE_IO, FILE_IO, CSV_IO, JSON_IO}

    //Declaring one hashmap containing all the address book
    public static Map<String, ContactOperations> addressBookDictionary = new HashMap<>();


    //Default Constructor
    public AddressBookSystemMain() {
        System.out.println("Welcome to Address Book Program !!!");
    }

    Scanner scan = new Scanner(System.in);
    ContactOperations addressBook;

    //Performing some contact operation
    public void addressBookOperation() {

        boolean flag = true;

        while (flag) {
            System.out.println();
            System.out.println("1.Add Contact");
            System.out.println("2.Add Multiple Contact");
            System.out.println("3.Edit Contact");
            System.out.println("4.Delete Contact");
            System.out.println("5.Show Contacts");
            System.out.println("6.Exit");
            System.out.println();
            System.out.println("Enter Choice: ");

            String option = scan.next();

            switch (option) {
                case "1":
                    addressBook.addContact();
                    if (addressBook.check) {
                        System.out.println(" Contact added successfully");
                    } else {
                        System.out.println("Sorry!!! Contact can't be added");
                    }
                    break;

                case "2":
                    System.out.println("1.You want to add multiple contacts from console");
                    System.out.println("2.You want to add multiple contacts from the contact cards");
                    System.out.println("3.You want to add multiple contacts from the files");
                    System.out.println("What you want?");
                    System.out.println("Enter your choice.");
                    int choice = scan.nextInt();
                    if (choice == 1) {
                        System.out.println("Enter how many contacts you want to add at a time.");
                        int numOfContacts = scan.nextInt();
                        for (int i = 1; i <= numOfContacts; i++) {
                            addressBook.addContact();
                            if (addressBook.check) {
                                System.out.println(i + " Contact added successfully");
                            } else {
                                System.out.println("Sorry!!! Contact can't be added");
                                System.out.println("Please input another one");
                                i--;
                            }
                        }
                    } else if (choice == 2) {
                        addressBook.sharedContactCards();
                        System.out.println("Contact cards added successfully");
                    }
                    else if (choice == 3){
                        System.out.println("Which io stream you want to use");
                        System.out.println("1.File IO?");
                        System.out.println("2.CSV IO?");
                        System.out.println("3.JSON IO?");
                        int wish = scan.nextInt();
                        if (wish == 1)
                            addressBook.readContactData(FILE_IO);
                        else if (wish == 2)
                            addressBook.readContactData(CSV_IO);
                        else
                            addressBook.readContactData(JSON_IO);
                    }
                    else
                        System.out.println("Enter valid choice");
                    break;

                case "3":
                    if (addressBook.checkList()) {
                        boolean b = addressBook.editContact();
                        if (b) {
                            System.out.println("Details Updated");
                        } else {
                            System.out.println("Contact Not Found");
                        }
                    } else
                        System.out.println("Nothing in the contact list.\nPlease create one");
                    break;

                case "4":
                    if (addressBook.checkList()) {
                        boolean listDeleted = addressBook.deleteContact();
                        if (listDeleted) {
                            System.out.println("Details Deleted");
                        } else {
                            System.out.println("Cannot be Deleted");
                        }
                    } else
                        System.out.println("Nothing in the contact list.\nPlease create one");
                    break;

                case "5":
                    addressBook.printContact();
                    break;
                case "6":
                    flag = false;
                    break;
                default:
                    System.out.println("Enter a valid input.");
                    break;
            }
        }
    }

    //Method to save the address book
    public void saveAddressBook(String a) {
        addressBook = new ContactOperations();
        addressBookOperation();
        System.out.println("Do you want to save this address book?");
        String choice = scan.next();
        if (choice.equalsIgnoreCase("y")) {
            addressBookDictionary.put(a, addressBook);
        } else {
            System.out.println("Address book is not saved");
        }
    }

    //Method to check if that given address book name is present or in the address book dictionary
    public boolean checkBookName(String a) {
        boolean flag = true;
        for (Map.Entry<String, ContactOperations> entry : addressBookDictionary.entrySet()) {
            String s = entry.getKey();
            if (s.equalsIgnoreCase(a)) {
                System.out.println("This name : " + a + " is already present in Address Book Dictionary\nGive a new name");
                flag = false;
                break;
            }
        }
        return flag;
    }

    //Method to search and print persons by means of city and state
    public void searchPersons() {
        System.out.println("1. Search by city name");
        System.out.println("2. Search by state name");
        System.out.println("Enter your choice by means of that you want to search");
        int choice = scan.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("Enter city name by means of which you want to search");
                String searchCity = scan.next();
                long count = 0;
                for (Map.Entry<String, ContactOperations> entry : addressBookDictionary.entrySet()) {
                    System.out.println(entry.getKey());
                    List<ContactPerson> list = entry.getValue().getContact().stream().filter(ContactPerson ->
                            ContactPerson.getCity().equalsIgnoreCase(searchCity)).collect(Collectors.toList());
                    long cnt = entry.getValue().getContact().stream().filter(ContactPerson ->
                            ContactPerson.getCity().equalsIgnoreCase(searchCity)).count();
                    System.out.println(list);
                    count = count + cnt;
                }
                System.out.println(count);
            }
/*                Iterator<Map.Entry<String , ContactOperations>> itr = addressBookDictionary.entrySet().iterator();
//                while (itr.hasNext())
//                {
//                    Map.Entry<String, ContactOperations> entry = itr.next();
//                    System.out.println(entry.getKey());
//                    for (int i = 0; i < entry.getValue().getContact().size(); i++)
//                    {
//                        if (entry.getValue().getContact().get(i).getCity().equalsIgnoreCase(searchCity))
//                        {
//                            System.out.println(entry.getValue().getContact().get(i));
//                        }
//                    }
               }*/
            case 2 -> {
                System.out.println("Enter State name by means of which you want to search");
                String searchState = scan.next();
                long count1 = 0;
                for (Map.Entry<String, ContactOperations> entry : addressBookDictionary.entrySet()) {
                    System.out.println(entry.getKey());
                    List<ContactPerson> list1 = entry.getValue().getContact().stream().filter(ContactPerson ->
                            ContactPerson.getState().equalsIgnoreCase(searchState)).collect(Collectors.toList());
                    System.out.println(list1);
                    long cnt1 = entry.getValue().getContact().stream().filter(ContactPerson ->
                            ContactPerson.getCity().equalsIgnoreCase(searchState)).count();
                    count1 = count1 + cnt1;
                }
                System.out.println(count1);
            }
            default -> {
                System.out.println("Wrong entry. Please try again\n");
                searchPersons();
            }
        }
    }

    public static void main(String[] args) throws IOException {

        //Initialize Object
        AddressBookSystemMain obj = new AddressBookSystemMain();
        AddressBookSystemIOService temp = new AddressBookSystemIOService();

        //Now saving the address book
        System.out.println("How many address book you want to save?");
        int books = obj.scan.nextInt();
        for (int i = 1; i <= books; i++) {
            System.out.println("Give one address book name");
            String a = obj.scan.next();
            if (obj.checkBookName(a)) {
                System.out.println("For " + a);
                obj.saveAddressBook(a);
            } else
                i--;
        }
//        temp.readAddressBookData(FILE_IO, addressBookDictionary);
        //print all address book
        temp.printMap(addressBookDictionary);

        //search persons by means of city or state in whole address books
        obj.searchPersons();

        temp.writeAddressBookData(FILE_IO,addressBookDictionary);
    }
}

