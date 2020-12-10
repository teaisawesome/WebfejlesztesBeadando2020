package hu.unideb.webdev.exception;

import hu.unideb.webdev.model.Department;

public class UniqueDepartmentException extends Exception
{
    private Department department;

    public UniqueDepartmentException(String message, Department department)
    {
        super(message);
        this.department = department;
    }
}
