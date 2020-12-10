package hu.unideb.webdev.service;

import hu.unideb.webdev.exception.CannotRecordSalaryException;
import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.exception.EmployeeHasNoTitleException;
import hu.unideb.webdev.model.Employee;
import hu.unideb.webdev.model.Salary;
import hu.unideb.webdev.model.Title;

import java.util.Collection;

public interface EmployeeService
{
    Collection<Employee> getAllEmployee();

    void recordEmployee(Employee employee);

    void deleteEmployee(Employee employee) throws UnknownEmployeeException;

    Collection<Title> getEmployeeTitle(Employee employee) throws UnknownEmployeeException;

    void removeEmployeeTitles(Employee employee) throws  UnknownEmployeeException,EmployeeHasNoTitleException;

    void recordTitle(Employee employee, Title title) throws UnknownEmployeeException;

    Collection<Salary> listEmployeeSalaries(Employee employee) throws UnknownEmployeeException;

    Salary listEmployeeCurrentSalary(Employee employee) throws UnknownEmployeeException, InvalidRequestException;

    void removeEmployeeSalary(Employee employee) throws UnknownEmployeeException, InvalidRequestException;

    void recordSalary(Employee employee, int salary) throws UnknownEmployeeException, CannotRecordSalaryException;
}
