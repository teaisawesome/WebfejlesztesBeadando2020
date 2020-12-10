package hu.unideb.webdev.service;

import hu.unideb.webdev.dao.EmployeeDao;
import hu.unideb.webdev.exception.CannotRecordSalaryException;
import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.exception.EmployeeHasNoTitleException;
import hu.unideb.webdev.model.Employee;
import hu.unideb.webdev.model.Salary;
import hu.unideb.webdev.model.Title;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService
{
    private final EmployeeDao employeeDao;


    @Override
    public Collection<Employee> getAllEmployee()
    {
        return employeeDao.readAll();
    }

    @Override
    public void recordEmployee(Employee employee)
    {
        employeeDao.createEmployee(employee);
    }

    @Override
    public void deleteEmployee(Employee employee) throws UnknownEmployeeException
    {
        employeeDao.deleteEmployee(employee);
    }

    @Override
    public Collection<Title> getEmployeeTitle(Employee employee) throws UnknownEmployeeException
    {
        return employeeDao.getEmployeeTitles(employee);
    }

    @Override
    public void removeEmployeeTitles(Employee employee) throws UnknownEmployeeException, EmployeeHasNoTitleException
    {
        if(getEmployeeTitle(employee).isEmpty())
            throw new EmployeeHasNoTitleException(String.format("Employee Has No Title %s", employee), employee);

        employeeDao.removeEmployeeTitles(employee);
    }

    @Override
    public void recordTitle(Employee employee, Title title) throws UnknownEmployeeException
    {
        employeeDao.createNewTitle(employee, title);
    }

    @Override
    public Collection<Salary> listEmployeeSalaries(Employee employee) throws UnknownEmployeeException
    {
        return employeeDao.listEmployeeSalary(employee);
    }

    @Override
    public Salary listEmployeeCurrentSalary(Employee employee) throws UnknownEmployeeException, InvalidRequestException
    {
        return employeeDao.listEmployeeCurrentSalary(employee);
    }

    @Override
    public void removeEmployeeSalary(Employee employee) throws UnknownEmployeeException, InvalidRequestException
    {
        employeeDao.removeEmployeeSalary(employee);
    }

    @Override
    public void recordSalary(Employee employee, int salary) throws UnknownEmployeeException, CannotRecordSalaryException
    {
        employeeDao.createNewSalary(employee, salary);
    }
}
