package hu.unideb.webdev.exception;

import hu.unideb.webdev.model.Employee;

public class EmployeeHasNoTitleException extends Exception
{
    private Employee employee;

    public EmployeeHasNoTitleException(String message, Employee employee)
    {
        super(message);
        this.employee = employee;
    }
}
