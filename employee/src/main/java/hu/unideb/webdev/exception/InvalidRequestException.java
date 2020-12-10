package hu.unideb.webdev.exception;

import hu.unideb.webdev.model.Department;

public class InvalidRequestException extends Exception
{
    public InvalidRequestException(String message)
    {
        super(message);
    }
}
