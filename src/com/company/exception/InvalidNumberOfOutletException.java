package com.company.exception;

/*
    Exception for the case when input contains invalid inputs
 */
public class InvalidNumberOfOutletException extends Exception {
    public InvalidNumberOfOutletException(int outletCount){
        super( "Invalid input: Number of outlets can't be " + outletCount + ".");
    }
}
