package com.addressbook.test;

import com.addressbook.main.ContactPerson;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookJDBCService {

    private PreparedStatement addressBookStatement;
    private static AddressBookJDBCService addressBookJDBCService;

    AddressBookJDBCService() {
    }

    public static AddressBookJDBCService getInstance() {
        if (addressBookJDBCService == null)
            addressBookJDBCService = new AddressBookJDBCService();
        return addressBookJDBCService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service";
        String userName = "root";
        String password = "Soumya@42558";
        Connection con;
        System.out.println("Connecting to database: "+jdbcURL);
        con = DriverManager.getConnection(jdbcURL,userName,password);
        System.out.println("Connection is successful!!"+con);
        return con;
    }

    public List<ContactPerson> readAddressBook() {
        String sqlQuery = "select * from addressbook";
        return this.getAddressBookDataUsingDB(sqlQuery);
    }

    private List<ContactPerson> getAddressBookDataUsingDB(String sqlQuery) {
        List<ContactPerson> addressBookDataList = new ArrayList<>();
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            addressBookDataList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookDataList;
    }

    private List<ContactPerson> getAddressBookData(ResultSet resultSet) {
        List<ContactPerson> addressBookDataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String address = resultSet.getString("Address");
                String city = resultSet.getString("City");
                String state = resultSet.getString("State");
                String zip = resultSet.getString("Zip");
                String phoneNo = resultSet.getString("PhoneNo");
                String email = resultSet.getString("Email");
                addressBookDataList.add(new ContactPerson(firstName, lastName,address,city, state, zip, phoneNo, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookDataList;
    }
}
