package hu.unideb.webdev.exception;

import hu.unideb.webdev.model.Employee;
import hu.unideb.webdev.model.Gender;

public class UnknownGenderException extends Exception
{
    private Gender gender;

    public UnknownGenderException(String message, Gender gender)
    {
        super(message);
        this.gender = gender;
    }
}
