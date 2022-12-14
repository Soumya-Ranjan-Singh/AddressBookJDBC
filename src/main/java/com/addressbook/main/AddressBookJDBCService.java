package com.addressbook.main;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.addressbook.main.AddressBookException.ExceptionType.UPDATE_FAIL;

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

    public List<ContactPerson> getAddressBookData(String name) {
        List<ContactPerson> addressBookDataList = null;
        if (this.addressBookStatement == null)
            this.prepareStatementForAllData();
        try {
            addressBookStatement.setString(1 , name);
            ResultSet resultSet = addressBookStatement.executeQuery();
            addressBookDataList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookDataList;
    }

    private void prepareStatementForAllData() {
        try {
            Connection connection = this.getConnection();
            String sqlQuery = "select * from addressbook where FirstName = ?";
            addressBookStatement = connection.prepareStatement(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateAddressBookData(String name, String data, String columnName) throws AddressBookException {
        return this.updateAddressBookDataUsingStatement(name , data , columnName);
    }

    private int updateAddressBookDataUsingStatement(String firstName, String data, String columnName) throws  AddressBookException{
        String sqlQuery = String.format("update addressbook set %s = '%s' where FirstName = '%s';",columnName,data,firstName);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ContactPerson> getContactDataForDateRange(LocalDate startDate, LocalDate endDate) {
        String sqlQuery = String.format("select * from addressbook where Date_added between '%s' and '%s';",
                Date.valueOf(startDate),Date.valueOf(endDate));
        return this.getAddressBookDataUsingDB(sqlQuery);
    }

    public List<ContactPerson> getContactsByCityOrState(String city, String state) {
        String sqlQuery = String.format("select * from addressbook where City = '%s' or State = '%s';",city,state);
        return this.getAddressBookDataUsingDB(sqlQuery);
    }
}
