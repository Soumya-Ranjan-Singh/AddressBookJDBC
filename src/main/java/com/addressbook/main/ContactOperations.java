//Operating class

package com.addressbook.main;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactOperations implements Serializable {
    static Scanner scan = new Scanner(System.in);

    //Creating an array list that contains all the contact persons
    private final ArrayList<ContactPerson> contactDetails;
    public boolean check;

    //Constructor
    public ContactOperations() {
        this.contactDetails = new ArrayList<>();
    }

    //Adding to list (Considering no duplicate occurs in the list)
    public void addToList(ContactPerson obj) {
        if (!checkList())
        {
            contactDetails.add(obj);
            check = true;
        }
        else
        {
            boolean flag = contactDetails.stream().anyMatch((ContactPerson)
                    -> ContactPerson.getFirstName().equalsIgnoreCase(obj.getFirstName()));

//            boolean flag = false;
//            for (int i = 0; i < contactDetails.size(); i++)
//            {
//                if (contactDetails.get(i).getFirstName().equalsIgnoreCase(obj.getFirstName()))
//                {
//                    flag = true;
//                    break;
//                }
//            }
            if (flag)
            {
                System.out.println("Already this contact details : "+obj.getFirstName()+" is present in the list");
                check = false;
            }
            else
            {
                contactDetails.add(obj);
                check = true;
            }
        }
    }

    //For adding contact
    public void addContact()
    {
        System.out.println("Enter the contact details.............");
        System.out.println("Enter the First name:");
        String firstName = scan.nextLine();
        System.out.println("Enter the Last name:");
        String lastName = scan.nextLine();
        System.out.println("Enter the Address:");
        String address = scan.nextLine();
        System.out.println("Enter the City:");
        String city = scan.nextLine();
        System.out.println("Enter the State:");
        String state = scan.nextLine();
        System.out.println("Enter the Zip code:");
        String zip = scan.nextLine();
        System.out.println("Enter the Phone no:");
        String phoneNumber = scan.nextLine();
        System.out.println("Enter the Email:");
        String email = scan.nextLine();

        //Calling Contact person class
        ContactPerson details = new ContactPerson(firstName, lastName, address, city, state,zip,phoneNumber,email);
        addToList(details);
    }

    //Adding some contact cards
    public void sharedContactCards()
    {
        ContactPerson a = new ContactPerson("Soumya", "Singh", "Jobra", "Cuttack",
                "Odisha", "753007", "8908641811", "soumyars675@gmail.com");
        ContactPerson b = new ContactPerson("Sambit", "Behera", "Chowdwar", "Cuttack",
                "Odisha", "754021", "7008565646", "sbehera@gmail.com");
        ContactPerson c = new ContactPerson("Nigam", "Jena", "Nakhara", "Bbsr",
                "Odisha", "724001", "9937585846", "njena50@gmail.com");
        addToList(a);
        addToList(b);
        addToList(c);
    }

    public void readContactData (AddressBookSystemMain.IOService ioService) {
        if (ioService.equals(AddressBookSystemMain.IOService.FILE_IO)) {
            System.out.println("1.Want to read contact list by means of Buffer reader?");
            System.out.println("2.Want to read contact list by means of Object input stream?");
            int wrtChoice = scan.nextInt();
            if (wrtChoice == 2) {
                try {
                    String name = "PhoneBookObject.txt";
                    FileInputStream fis = new FileInputStream(name);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    ContactPerson obj1 = (ContactPerson) ois.readObject();
                    ContactPerson obj2 = (ContactPerson) ois.readObject();
                    ContactPerson obj3 = (ContactPerson) ois.readObject();
                    contactDetails.add(obj1);
                    contactDetails.add(obj2);
                    contactDetails.add(obj3);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (wrtChoice == 1) {
                try {
                    Files.readAllLines(new File("PhoneBook.txt").toPath()).stream().map(String::trim)
                            .forEach(line -> {
                                String[] ch = line.split("=");
                                String firstName = ch[1].split("'")[1].trim();
                                String lastName = ch[2].split("'")[1].trim();
                                String address = ch[3].split("'")[1].trim();
                                String city = ch[4].split("'")[1].trim();
                                String state = ch[5].split("'")[1].trim();
                                String zip = ch[6].split("'")[1].trim();
                                String phoneNo = ch[7].split("'")[1].trim();
                                String email = ch[8].split("'")[1].trim();
                                contactDetails.add(new ContactPerson(firstName, lastName, address, city, state, zip, phoneNo, email));
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (ioService.equals(AddressBookSystemMain.IOService.CSV_IO)) {
            try {
                FileReader filereader = new FileReader("PhoneBook.csv");
                List<String[]> allData;
                try (CSVReader csvReader = new CSVReaderBuilder(filereader)
                        .withSkipLines(1)
                        .build()) {
                    allData = csvReader.readAll();
                } catch (IOException | CsvException e) {
                    throw new RuntimeException(e);
                }
                for (String[] row : allData) {
                    contactDetails.add(new ContactPerson(row[0],row[1],row[2],row[3],row[4],row[5],row[6],row[7]));
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else if (ioService.equals(AddressBookSystemMain.IOService.JSON_IO)) {
            try {
                Gson gson = new Gson();
                //Read the Contact.json file
                BufferedReader br = new BufferedReader(
                        new FileReader("PhoneBook.json"));
                //convert the json to  Java object (ContactPerson)
                ContactOperations contactPerson = gson.fromJson(br, ContactOperations.class);
                contactDetails.addAll(contactPerson.contactDetails);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //For editing contact
    public boolean editContact()
    {
        int flag = 0;
        if (checkList())
        {
            System.out.println("Enter the Person First name to edit details: ");
            String name = scan.next();
            for (ContactPerson contact : contactDetails) {
                if (contact.getFirstName().equalsIgnoreCase(name)) {
                    System.out.println("Enter the detail which needs to be updated:\nChoose the option.");
                    System.out.println("1. Edit First Name");
                    System.out.println("2. Edit Last Name");
                    System.out.println("3. Edit Address");
                    System.out.println("4. Edit City");
                    System.out.println("5. Edit State");
                    System.out.println("6. Edit Zip");
                    System.out.println("7. Edit Phone Number");
                    System.out.println("8. Edit Email");

                    int choice = scan.nextInt();
                    switch (choice) {
                        case 1 -> {
                            System.out.println("Enter First Name: ");
                            String firstName = scan.next();
                            contact.setFirstName(firstName);
                        }
                        case 2 -> {
                            System.out.println("Enter Last name: ");
                            String lastName = scan.next();
                            contact.setLastName(lastName);
                        }
                        case 3 -> {
                            System.out.println("Enter Address: ");
                            String address = scan.next();
                            contact.setAddress(address);
                        }
                        case 4 -> {
                            System.out.println("Enter City: ");
                            String city = scan.next();
                            contact.setCity(city);
                        }
                        case 5 -> {
                            System.out.println("Enter State: ");
                            String state = scan.next();
                            contact.setState(state);
                        }
                        case 6 -> {
                            System.out.println("Enter Zip Code: ");
                            String zip = scan.next();
                            contact.setZip(zip);
                        }
                        case 7 -> {
                            System.out.println("Enter Phone Number:");
                            String phoneNumber = scan.next();
                            contact.setPhoneNumber(phoneNumber);
                        }
                        case 8 -> {
                            System.out.println("Enter Email: ");
                            String email = scan.next();
                            contact.setEmail(email);
                        }
                    }

                    flag = 1;
                    break;
                }
            }
        }
        return flag == 1;
    }

    //For deleting contact
    public boolean deleteContact() {
        int flag = 0;
        if (checkList())
        {
            System.out.println("Enter the Contact to be deleted:");
            String name = scan.next();
            for (ContactPerson contact : contactDetails) {
                if (contact.getFirstName().equalsIgnoreCase(name)) {
                    contactDetails.remove(contact);
                    flag = 1;
                    break;
                }
            }
        }
        return flag == 1;
    }

    //Check if array list is empty
    public boolean checkList()
    {
        return !contactDetails.isEmpty();
    }

    //Printing contactDetails
    public void printContact() {
        if(checkList())
        {
            System.out.println("Contact details are below.....\n");
            for (ContactPerson contactDetail : contactDetails) {
                System.out.println(contactDetail);
            }
        }
        else
            System.out.println("Contact list is empty.");
    }

    //Method to get objects of list
    public ArrayList<ContactPerson> getContact() {
        if(checkList())
        {
            return contactDetails;
        }
        return null;
    }

    //Creating a To string method
    @Override
    public String toString() {
        return "ContactOperations{" +
                "contactDetails=" + contactDetails +
                '}';
    }
}
