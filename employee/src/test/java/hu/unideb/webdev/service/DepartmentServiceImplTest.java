package hu.unideb.webdev.service;

import hu.unideb.webdev.dao.DepartmentDao;
import hu.unideb.webdev.dao.EmployeeDao;
import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UniqueDepartmentException;
import hu.unideb.webdev.exception.UnknownDepartmentException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest
{
    @InjectMocks
    private DepartmentServiceImpl service;

    @Mock
    private DepartmentDao dao;

    @Test
    public void testReadAllDepartment()
    {
        when(dao.readAll()).thenReturn(getDefaultDepartments());

        Collection<Department> actual = service.getAllDepartment();

        assertThat(getDefaultDepartments(), is(actual));
    }

    @Test
    public void testRecordNewDepartment() throws UniqueDepartmentException
    {
        Department department = getDepartment();

        service.recordDepartment(department);

        verify(dao, times(1)).createDepartment(department);
    }

    @Test
    void testDepartmentHasToBeUnique() throws UniqueDepartmentException
    {
        doThrow(UniqueDepartmentException.class).when(dao).createDepartment(any());

        assertThrows(UniqueDepartmentException.class, ()->{
            service.recordDepartment(getDepartment());
        });
    }

    @Test
    void testDeleteDepartment() throws UnknownDepartmentException
    {
        Department department = getDepartment();

        service.removeDepartment(department);

        verify(dao, times(1)).deleteDepartment(department);
    }

    @Test
    void testUpdateDepartment() throws UnknownDepartmentException
    {
        Department department = getDepartment();

        service.updateDepartmentName(department, "Action Center");

        verify(dao, times(1)).updateDepartment(department, "Action Center");
    }

    @Test
    void testUnknownDepartmentException() throws UnknownDepartmentException
    {
        doThrow(UnknownDepartmentException.class).when(dao).deleteDepartment(any());

        assertThrows(UnknownDepartmentException.class, ()->{
            service.removeDepartment(getDepartment());
        });
    }

    @Test
    void testFindAllEmployeeInSpecificDepartment() throws UnknownDepartmentException
    {
        Collection<Employee> employees= getDefaultEmployees();

        when(dao.readAllEmployeeInDepartment(getDepartment())).thenReturn(employees);

        Collection<Employee> actual = service.findAllEmployeeInDepartment(getDepartment());

        assertThat(getDefaultEmployees(), is(actual));
    }

    @Test
    void testGetDepartmentWhereTheEmployeeIsWorkin() throws UnknownEmployeeException
    {
        Department department = getDepartment();

        when(dao.getEmployeeDepartment(getEmployee())).thenReturn(department);

        Department actual = service.getEmployeeDepartment(getEmployee());

        assertThat(department, is(actual));
    }

    @Test
    void testUnknownEmployeeException() throws UnknownEmployeeException
    {
        doThrow(UnknownEmployeeException.class).when(dao).getEmployeeDepartment(any());

        assertThrows(UnknownEmployeeException.class, ()->{
            service.getEmployeeDepartment(getEmployee());
        });
    }

    @Test
    void testRecordAnEmployeeIntoADepartment() throws UnknownDepartmentException, InvalidRequestException, UnknownEmployeeException
    {
        DeptEmployee deptEmployee = getDeptEmployee();

        service.createEmployeeDepartment(deptEmployee);

        verify(dao, times(1)).addEmployeeToDepartment(deptEmployee);
    }

    @Test
    void testInvalidRequestException() throws InvalidRequestException, UnknownDepartmentException, UnknownEmployeeException
    {
        doThrow(InvalidRequestException.class).when(dao).addEmployeeToDepartment(any());

        assertThrows(InvalidRequestException.class, ()->{
            service.createEmployeeDepartment(getDeptEmployee());
        });
    }

    @Test
    void testRecordAManagerIntoADepartment() throws UnknownDepartmentException, InvalidRequestException, UnknownEmployeeException
    {
        DeptManager deptManager = getDeptManager();

        service.createManagerDepartment(deptManager);

        verify(dao, times(1)).addManagerToDepartment(deptManager);
    }

    @Test
    void testfindAllManager()
    {
        when(dao.readAllDepartmentManager()).thenReturn(getDefaultEmployees());

        Collection<Employee> actual = service.getAllManager();

        assertThat(getDefaultEmployees(), is(actual));
    }

    @Test
    void testDeleteManager() throws InvalidRequestException, UnknownEmployeeException
    {
        Employee employee = getEmployee();

        service.removeManager(employee);

        verify(dao, times(1)).deleteManager(employee);
    }

    @Test
    void testDeleteRelationBetweenDepartmentAndEmployee() throws InvalidRequestException, UnknownEmployeeException
    {
        Employee employee = getEmployee();

        service.removeEmployeeDepartmentRelation(employee);

        verify(dao, times(1)).deleteEmployeeDepartmentRelation(employee);
    }

    private DeptEmployee getDeptEmployee()
    {
        return DeptEmployee.builder()
                .employee("1")
                .departmentNumber("d005")
                .fromDate(Date.valueOf("2020-11-20"))
                .toDate(Date.valueOf("9999-01-01"))
                .build();
    }

    private DeptManager getDeptManager()
    {
        return DeptManager.builder()
                .employee("2")
                .departmentNumber("d005")
                .fromDate(Date.valueOf("2020-11-20"))
                .toDate(Date.valueOf("9999-01-01"))
                .build();
    }

    private Collection<Employee> getDefaultEmployees()
    {
        return Arrays.asList(
                new Employee(
                        2,
                        Date.valueOf("1997-03-10"),
                        "Kálmán",
                        "Beka",
                        Gender.valueOf("M"),
                        Date.valueOf("1997-03-10")
                ),
                new Employee(
                        4,
                        Date.valueOf("2020-11-20"),
                        "Róbert",
                        "Dékány",
                        Gender.valueOf("M"),
                        Date.valueOf("2020-11-21")
                )
        );
    }

    private Employee getEmployee()
    {
        return new Employee(
                "2020-11-20",
                "Róbert",
                "Dékány",
                "M",
                "2020-11-21"
        );
    }

    private Department getDepartment()
    {
        return Department.builder()
                .departmentNumber("d001")
                .departmentName("Security Operation Center")
                .build();
    }

    private Collection<Department> getDefaultDepartments()
    {
        return Arrays.asList(
                new Department(
                        "d001",
                        "SOC"
                ),
                new Department(
                        "d002",
                        "Security"
                )
        );
    }
}
