package hu.unideb.webdev.exception;

import hu.unideb.webdev.model.Employee;

public class UnknownEmployeeException extends Exception
{
    private Employee employee;

    public UnknownEmployeeException(Employee employee)
    {
        this.employee = employee;
    }

    public UnknownEmployeeException(String message)
    {
        super(message);
    }

    public UnknownEmployeeException(String message, Employee employee)
    {
        super(message);
        this.employee = employee;
    }
}
