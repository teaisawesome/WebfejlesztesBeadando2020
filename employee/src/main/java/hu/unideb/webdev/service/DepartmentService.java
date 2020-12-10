package hu.unideb.webdev.service;

import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UniqueDepartmentException;
import hu.unideb.webdev.exception.UnknownDepartmentException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.model.Department;
import hu.unideb.webdev.model.DeptEmployee;
import hu.unideb.webdev.model.DeptManager;
import hu.unideb.webdev.model.Employee;

import java.util.Collection;

public interface DepartmentService
{
    Collection<Department> getAllDepartment();

    void recordDepartment(Department department) throws UniqueDepartmentException;

    void removeDepartment(Department department) throws UnknownDepartmentException;

    void updateDepartmentName(Department department, String newDepartmentName) throws UnknownDepartmentException;

    Collection<Employee> findAllEmployeeInDepartment(Department department) throws UnknownDepartmentException;

    Department getEmployeeDepartment(Employee employee) throws UnknownEmployeeException;

    void createEmployeeDepartment(DeptEmployee deptEmployee) throws UnknownEmployeeException, UnknownDepartmentException, InvalidRequestException;

    void createManagerDepartment(DeptManager deptManager) throws UnknownEmployeeException, UnknownDepartmentException, InvalidRequestException;

    Collection<Employee> getAllManager();

    void removeManager(Employee employee) throws UnknownEmployeeException, InvalidRequestException;

    void removeEmployeeDepartmentRelation(Employee employee) throws UnknownEmployeeException, InvalidRequestException;
}
