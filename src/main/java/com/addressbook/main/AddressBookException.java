package com.addressbook.main;

import java.sql.SQLException;

public class AddressBookException extends SQLException {
    public enum ExceptionType {UPDATE_FAIL,INSERTION_FAIL}

    public ExceptionType type;

    public AddressBookException(ExceptionType type,String message) {
        super(message);
        this.type = type;
    }
}
