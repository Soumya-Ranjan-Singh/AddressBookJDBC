package com.addressbook.main;

public class AddressBookException extends Throwable {
    public enum ExceptionType
    {UPDATE_FAIL,INSERTION_FAIL}

    public ExceptionType type;

    public AddressBookException(ExceptionType type,String message) {
        super(message);
        this.type = type;
    }
}
