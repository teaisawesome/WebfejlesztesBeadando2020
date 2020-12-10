package hu.unideb.webdev.service;

import hu.unideb.webdev.dao.EmployeeDao;
import hu.unideb.webdev.exception.CannotRecordSalaryException;
import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.exception.EmployeeHasNoTitleException;
import hu.unideb.webdev.model.Employee;
import hu.unideb.webdev.model.Gender;
import hu.unideb.webdev.model.Salary;
import hu.unideb.webdev.model.Title;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest
{
    @InjectMocks
    private EmployeeServiceImpl service;

    @Mock
    private EmployeeDao dao;

    @Test
    public void testRecordEmployee()
    {
        Employee employee = getEmployee();

        service.recordEmployee(employee);

        verify(dao, times(1)).createEmployee(employee);
    }

    @Test
    void testReadAllEmployees()
    {
        when(dao.readAll()).thenReturn(getDefaultEmployees());

        Collection<Employee> actual = service.getAllEmployee();

        assertThat(getDefaultEmployees(), is(actual));
    }

    @Test
    void testReadEmployeeTitles() throws UnknownEmployeeException
    {
        when(dao.getEmployeeTitles(getEmployee())).thenReturn(getTitles());

        Collection<Title> actual = service.getEmployeeTitle(getEmployee());

        assertThat(getTitles(), is(actual));
    }

    @Test
    void testRemoveEmployeeTitles() throws EmployeeHasNoTitleException, UnknownEmployeeException
    {
        when(dao.getEmployeeTitles(getEmployee())).thenReturn(getTitles());

        Employee employee = getEmployee();

        service.removeEmployeeTitles(employee);

        verify(dao, times(1)).removeEmployeeTitles(employee);
    }

    @Test
    void testUnknownEmployee() throws UnknownEmployeeException {
        doThrow(UnknownEmployeeException.class).when(dao).deleteEmployee(any());

        assertThrows(UnknownEmployeeException.class, ()->{
            service.deleteEmployee(getEmployee());
        });
    }

    @Test
    void testRecordNewTitleForEmployee() throws UnknownEmployeeException {
        Employee employee = getEmployee();
        Title title = getTitle();

        service.recordTitle(employee, title);

        verify(dao, times(1)).createNewTitle(employee, title);
    }

    @Test
    void testListEmployeeSalaries() throws UnknownEmployeeException {
        when(dao.listEmployeeSalary(getEmployee())).thenReturn(getSalaries());

        Collection<Salary> actual = service.listEmployeeSalaries(getEmployee());

        assertThat(getSalaries(), is(actual));
    }

    @Test
    void testRemoveEmployeeSalary() throws UnknownEmployeeException, InvalidRequestException
    {
        Employee employee = getEmployee();

        service.removeEmployeeSalary(employee);

        verify(dao, times(1)).removeEmployeeSalary(employee);
    }

    @Test
    void testRecordNewSalaryForEmployee() throws UnknownEmployeeException, CannotRecordSalaryException {
        Employee employee = getEmployee();
        int salary = 50;

        service.recordSalary(employee, salary);

        verify(dao, times(1)).createNewSalary(employee, salary);
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

    private Title getTitle()
    {
        return new Title(
                3,
                "Prime Minister",
                Date.valueOf("2020-12-06"),
                Date.valueOf("9999-11-20")
        );
    }
    private Collection<Title> getTitles()
    {
        return Arrays.asList(
                new Title(
                        4,
                "VEZETO FEJLESZTO",
                    Date.valueOf("2020-12-06"),
                    Date.valueOf("9999-11-20")
                )
        );
    }

    private Collection<Salary> getSalaries()
    {
        return Arrays.asList(
                new Salary(
                        4,
                        125000,
                        Date.valueOf("2020-12-06"),
                        Date.valueOf("9999-11-20")
                )
        );
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
}
