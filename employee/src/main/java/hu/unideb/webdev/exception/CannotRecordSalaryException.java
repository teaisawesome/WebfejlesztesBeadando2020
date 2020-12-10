package hu.unideb.webdev.exception;

import hu.unideb.webdev.model.Employee;

public class CannotRecordSalaryException extends Exception
{
    public CannotRecordSalaryException(String message)
    {
        super(message);
    }
}
