package hu.unideb.webdev.service;

import hu.unideb.webdev.dao.DepartmentDao;
import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UniqueDepartmentException;
import hu.unideb.webdev.exception.UnknownDepartmentException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.model.Department;
import hu.unideb.webdev.model.DeptEmployee;
import hu.unideb.webdev.model.DeptManager;
import hu.unideb.webdev.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService
{
    private final DepartmentDao departmentDao;

    @Override
    public Collection<Department> getAllDepartment()
    {
        return departmentDao.readAll();
    }

    @Override
    public void recordDepartment(Department department) throws UniqueDepartmentException
    {
        departmentDao.createDepartment(department);
    }

    @Override
    public void removeDepartment(Department department) throws UnknownDepartmentException
    {
        departmentDao.deleteDepartment(department);
    }

    @Override
    public void updateDepartmentName(Department department, String newDepartmentName) throws UnknownDepartmentException
    {
        departmentDao.updateDepartment(department, newDepartmentName);
    }

    @Override
    public Collection<Employee> findAllEmployeeInDepartment(Department department) throws UnknownDepartmentException
    {
        return departmentDao.readAllEmployeeInDepartment(department);
    }

    @Override
    public Department getEmployeeDepartment(Employee employee) throws UnknownEmployeeException
    {
        return departmentDao.getEmployeeDepartment(employee);
    }

    @Override
    public void createEmployeeDepartment(DeptEmployee deptEmployee) throws UnknownEmployeeException, UnknownDepartmentException, InvalidRequestException
    {
        departmentDao.addEmployeeToDepartment(deptEmployee);
    }

    @Override
    public void createManagerDepartment(DeptManager deptManager) throws UnknownEmployeeException, UnknownDepartmentException, InvalidRequestException
    {
        departmentDao.addManagerToDepartment(deptManager);
    }

    @Override
    public Collection<Employee> getAllManager()
    {
        return departmentDao.readAllDepartmentManager();
    }

    @Override
    public void removeManager(Employee employee) throws UnknownEmployeeException, InvalidRequestException
    {
        departmentDao.deleteManager(employee);
    }

    @Override
    public void removeEmployeeDepartmentRelation(Employee employee) throws UnknownEmployeeException, InvalidRequestException
    {
        departmentDao.deleteEmployeeDepartmentRelation(employee);
    }
}
