package hu.unideb.webdev.dao;

import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UniqueDepartmentException;
import hu.unideb.webdev.exception.UnknownDepartmentException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.model.Department;
import hu.unideb.webdev.model.DeptEmployee;
import hu.unideb.webdev.model.DeptManager;
import hu.unideb.webdev.model.Employee;

import java.util.Collection;

public interface DepartmentDao
{
    Collection<Department> readAll();

    void createDepartment(Department department) throws UniqueDepartmentException;

    void deleteDepartment(Department department) throws UnknownDepartmentException;

    void updateDepartment(Department department, String newDepartmentName) throws UnknownDepartmentException;

    Collection<Employee> readAllEmployeeInDepartment(Department department) throws UnknownDepartmentException;

    Department getEmployeeDepartment(Employee employee) throws UnknownEmployeeException;

    void addEmployeeToDepartment(DeptEmployee deptEmployee) throws UnknownEmployeeException, UnknownDepartmentException, InvalidRequestException;

    void addManagerToDepartment(DeptManager deptManager) throws UnknownEmployeeException, UnknownDepartmentException, InvalidRequestException;

    Collection<Employee> readAllDepartmentManager();

    void deleteManager(Employee employee) throws UnknownEmployeeException, InvalidRequestException;

    void deleteEmployeeDepartmentRelation(Employee employee) throws UnknownEmployeeException, InvalidRequestException;
}
