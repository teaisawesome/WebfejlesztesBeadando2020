package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.EmployeeEntity;
import hu.unideb.webdev.exception.CannotRecordSalaryException;
import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.model.Employee;
import hu.unideb.webdev.model.Salary;
import hu.unideb.webdev.model.Title;

import java.util.Collection;

public interface EmployeeDao
{
    Collection<Employee> readAll();

    void createEmployee(Employee employee);

    EmployeeEntity findEmployeeById(int id);

    void deleteEmployee(Employee employees) throws UnknownEmployeeException;

    void updateEmployee(Employee oldEmployeeData, Employee newEmployeeData);

    Collection<Title> getEmployeeTitles(Employee employee) throws UnknownEmployeeException;

    void removeEmployeeTitles(Employee employee) throws UnknownEmployeeException;

    void createNewTitle(Employee employee, Title title) throws UnknownEmployeeException;

    Collection<Salary> listEmployeeSalary(Employee employee) throws UnknownEmployeeException;

    Salary listEmployeeCurrentSalary(Employee employee) throws UnknownEmployeeException, InvalidRequestException;

    void removeEmployeeSalary(Employee employee) throws UnknownEmployeeException, InvalidRequestException;

    void createNewSalary(Employee employee, int amount) throws UnknownEmployeeException, CannotRecordSalaryException;
}
