package com.addressbook.main;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.addressbook.main.AddressBookSystemMain.IOService.*;
import static com.addressbook.main.ContactOperations.scan;
import com.addressbook.main.AddressBookSystemMain.IOService;

public class AddressBookSystemIOService {

    //Method to print all the address book
    public void printAddressBooks(Map<String, ContactOperations> addressBookDictionary) {
        for (Map.Entry<String, ContactOperations> entry : addressBookDictionary.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    //Method to print all the address book sorting by names
    public void printBySortingNames(Map<String, ContactOperations> addressBookDictionary) {
        for (Map.Entry<String, ContactOperations> entry : addressBookDictionary.entrySet()) {
            System.out.println(entry.getKey());
            List<ContactPerson> list = entry.getValue().getContact().stream().
                    sorted(Comparator.comparing(ContactPerson::getFirstName)).collect(Collectors.toList());
            System.out.println(list);
        }
    }

    //Method to print all the address book sorting by city
    public void printBySortingCity(Map<String, ContactOperations> addressBookDictionary) {
        for (Map.Entry<String, ContactOperations> entry : addressBookDictionary.entrySet()) {
            System.out.println(entry.getKey());
            List<ContactPerson> list = entry.getValue().getContact().stream().
                    sorted(Comparator.comparing(ContactPerson::getCity)).collect(Collectors.toList());
            System.out.println(list);
        }
    }

    //Method to print all the address book sorting by state
    public void printBySortingState(Map<String, ContactOperations> addressBookDictionary) {
        for (Map.Entry<String, ContactOperations> entry : addressBookDictionary.entrySet()) {
            System.out.println(entry.getKey());
            List<ContactPerson> list = entry.getValue().getContact().stream().
                    sorted(Comparator.comparing(ContactPerson::getState)).collect(Collectors.toList());
            System.out.println(list);
        }
    }

    //Method to print all the address book sorting by zip code
    public void printBySortingZipCodes(Map<String, ContactOperations> addressBookDictionary) {
        for (Map.Entry<String, ContactOperations> entry : addressBookDictionary.entrySet()) {
            System.out.println(entry.getKey());
            List<ContactPerson> list = entry.getValue().getContact().stream().
                    sorted(Comparator.comparing(ContactPerson::getZip)).collect(Collectors.toList());
            System.out.println(list);
        }
    }

    //Method to get Print as per user wish
    public void printMap(Map<String, ContactOperations> addressBookDictionary) {
        System.out.println("Enter your choice like how do you want to see your Address Books");
        System.out.println("1. Print by means of sorting names");
        System.out.println("2. Print by means of sorting city");
        System.out.println("3. Print by means of sorting state");
        System.out.println("4. Print by means of sorting zip codes");
        int choice = scan.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("Before sorting");
                printAddressBooks(addressBookDictionary);
                System.out.println("\n" + "After sorting");
                printBySortingNames(addressBookDictionary);
            }
            case 2 -> {
                System.out.println("Before sorting");
                printAddressBooks(addressBookDictionary);
                System.out.println("\n" + "After sorting");
                printBySortingCity(addressBookDictionary);
            }
            case 3 -> {
                System.out.println("Before sorting");
                printAddressBooks(addressBookDictionary);
                System.out.println("\n" + "After sorting");
                printBySortingState(addressBookDictionary);
            }
            case 4 -> {
                System.out.println("Before sorting");
                printAddressBooks(addressBookDictionary);
                System.out.println("\n" + "After sorting");
                printBySortingZipCodes(addressBookDictionary);
            }
            default -> System.out.println("Wrong choice. Please try again");
        }
    }

    public void writeAddressBookData(IOService ioService, Map<String, ContactOperations> addressBookDictionary) throws IOException {
        if (ioService.equals(CONSOLE_IO))
            printMap(addressBookDictionary);
        else if (ioService.equals(FILE_IO)) {
            System.out.println("Enter your choice");
            System.out.println("1.Want to print the complete Address book?");
            System.out.println("2.Want to print the Contact List?");
            int choice = scan.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter your choice");
                    System.out.println("1.Want to print whole address book by means of Buffer writer?");
                    System.out.println("2.Want to print whole address book by means of Object output stream?");
                    int wrtChoice = scan.nextInt();
                    if (wrtChoice == 1) {
                        File file = new File("AddressBook.txt");
                        try (BufferedWriter bf = new BufferedWriter(new FileWriter(file))) {
                            for (Map.Entry<String, ContactOperations> entry : addressBookDictionary.entrySet()) {
                                bf.write(entry.getKey());
                                bf.newLine();
                                entry.getValue().getContact().forEach(contactPerson -> {
                                    String contactPersonString = contactPerson.toString().concat("\n");
                                    try {
                                        bf.write(contactPersonString);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                bf.newLine();
                            }

                            bf.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (wrtChoice == 2) {
                        FileOutputStream fos = new FileOutputStream("AddressBookObject.txt");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        for (Map.Entry<String, ContactOperations> entry : addressBookDictionary.entrySet()) {
                            oos.writeObject(entry.getKey() + ":" + entry.getValue());
                        }
                        oos.flush();
                        oos.close();
                        fos.close();
                    } else {
                        System.out.println("Wrong choice");
                    }
                }
                case 2 -> {
                    System.out.println("Enter your choice");
                    System.out.println("1.Want to print contact list by means of Buffer writer?");
                    System.out.println("2.Want to print contact list by means of Object output stream?");
                    int wrtChoice = scan.nextInt();
                    if (wrtChoice == 1) {
                        for (Map.Entry<String, ContactOperations> entry : AddressBookSystemMain.addressBookDictionary.entrySet()) {
                            String fileName = entry.getKey() + ".txt";
                            StringBuffer contactBuffer = new StringBuffer();
                            entry.getValue().getContact().forEach(contactPerson -> {
                                String contactPersonString = contactPerson.toString().concat("\n");
                                contactBuffer.append(contactPersonString);
                            });
                            try {
                                Files.write(Paths.get(fileName), contactBuffer.toString().getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (wrtChoice == 2) {
                        for (Map.Entry<String, ContactOperations> entry : AddressBookSystemMain.addressBookDictionary.entrySet()) {
                            String fileName = entry.getKey() + "Object.txt";
                            FileOutputStream fos = new FileOutputStream(fileName);
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            entry.getValue().getContact().forEach(contactPerson -> {
                                try {
                                    oos.writeObject(contactPerson);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            oos.flush();
                            oos.close();
                            fos.close();
                        }
                    } else {
                        System.out.println("Wrong Choice.");
                    }
                }
                default -> System.out.println("Wrong Choice");
            }
        } else if (ioService.equals(CSV_IO)) {
            for (Map.Entry<String, ContactOperations> entry : AddressBookSystemMain.addressBookDictionary.entrySet()) {
                String addressBookName = entry.getKey() + ".csv";
                CSVWriter writer = new CSVWriter(new FileWriter(addressBookName));
                String[] header = {"Firstname", "Lastname", "Address", "City", "State", "Zip", "PhoneNo.", "Email"};
                writer.writeNext(header);
                entry.getValue().getContact().forEach(contactPerson -> {
                    String[] contacts = {contactPerson.getFirstName(), contactPerson.getLastName(),
                            contactPerson.getAddress(), contactPerson.getCity(), contactPerson.getState(), contactPerson.getZip(),
                            contactPerson.getPhoneNumber(), contactPerson.getEmail()};
                    writer.writeNext(contacts);
                });
                writer.flush();
                writer.close();
            }
        } else if (ioService.equals(JSON_IO)) {
            try {
                for (Map.Entry<String, ContactOperations> entry : AddressBookSystemMain.addressBookDictionary.entrySet()) {
                    String addressBookName = entry.getKey() + ".json";
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(entry.getValue());
                    FileWriter fileWriter;
                    fileWriter = new FileWriter(addressBookName);
                    fileWriter.write(jsonString);
                    fileWriter.close();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void readAddressBookData (IOService ioService, Map<String, ContactOperations> addressBookDictionary) {
        try {
            String name = "PhoneBookObject.txt";
            FileInputStream fis = new FileInputStream(name);
            ObjectInputStream ois = new ObjectInputStream(fis);
            String addressBookName = name.split("Object.txt")[0].trim();
            Object obj = ois.readObject();
            addressBookDictionary.put(addressBookName, (ContactOperations) obj);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
