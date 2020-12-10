package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.EmployeeEntity;
import hu.unideb.webdev.dao.entity.SalaryEntity;
import hu.unideb.webdev.exception.CannotRecordSalaryException;
import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeDaoImplTest
{
    @Spy
    @InjectMocks
    private EmployeeDaoImpl dao;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TitleRepository titleRepository;

    @Mock
    private SalariesRepository salariesRepository;

    @Test
    public void testCreateEmployee()
    {
        dao.createEmployee(getEmployee());

        verify(employeeRepository, times(1)).save(any());
    }

    @Test
    public void testCreateTitle() throws UnknownEmployeeException
    {
        doReturn(getEmployeeEntity()).when(dao).getEmployeeEntity(any());

        dao.createNewTitle(getEmployee(), getTitle());

        verify(titleRepository, times(1)).save(any());
    }

    @Test
    public void testCreateSalary() throws UnknownEmployeeException, CannotRecordSalaryException
    {
        doReturn(getEmployeeEntity()).when(dao).getEmployeeEntity(any());

        when(salariesRepository.findSalaryEntityByFromDateIs(any())).thenReturn(getSalaryEntity());

        dao.createNewSalary(getEmployee(), 50500);

        verify(salariesRepository, times(1)).save(any());
    }

    @Test
    public void testReadAllEmployee()
    {
        when(employeeRepository.findAll()).thenReturn(getDefaultEmployeeEntitys());

        Collection<Employee> actual = dao.readAll();

        assertThat(getDefaultEmployees(), is(actual));
    }

    @Test
    public void testListEmployeeCurrentSalary() throws UnknownEmployeeException, InvalidRequestException
    {
        doReturn(getEmployeeEntity()).when(dao).getEmployeeEntity(getEmployee());

        when(salariesRepository.findAllByEmployee(any())).thenReturn(getSalaryEntities());

        when(salariesRepository.findSalaryEntityByEmployeeAndToDateGreaterThanEqual(getEmployeeEntity(), Date.valueOf("9999-01-01")))
                .thenReturn(getSalaryEntity());

        Salary actual = dao.listEmployeeCurrentSalary(getEmployee());

        assertThat(getSalary(), is(actual));
    }


    private EmployeeEntity getEmployeeEntity()
    {
        return new EmployeeEntity(
                3,
                Date.valueOf("2020-11-20"),
                "Róbert",
                "Dékány",
                Gender.valueOf("M"),
                Date.valueOf("2020-11-21")
        );
    }

    private Employee getEmployee()
    {
        return new Employee(
                3,
                Date.valueOf("2020-11-20"),
                "Róbert",
                "Dékány",
                Gender.valueOf("M"),
                Date.valueOf("2020-11-21")
        );
    }
    private SalaryEntity getSalaryEntity()
    {
        return SalaryEntity.builder()
                .employee(getEmployeeEntity())
                .salary(55000)
                .fromDate(Date.valueOf("2020-12-06"))
                .toDate(Date.valueOf("9999-11-20"))
                .build();
    }

    private Collection<SalaryEntity> getSalaryEntities()
    {
        return Arrays.asList(
                SalaryEntity.builder()
                        .employee(getEmployeeEntity())
                        .salary(40500)
                        .fromDate(Date.valueOf("2020-11-06"))
                        .toDate(Date.valueOf("9999-01-01"))
                        .build()
        );
    }

    private Salary getSalary()
    {
        return new Salary(
                55000,
                Date.valueOf("2020-12-06"),
                Date.valueOf("9999-11-20")
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

    private Collection<EmployeeEntity> getDefaultEmployeeEntitys()
    {
        return Arrays.asList(
                new EmployeeEntity(
                        2,
                        Date.valueOf("1997-03-10"),
                        "Kálmán",
                        "Beka",
                        Gender.valueOf("M"),
                        Date.valueOf("1997-03-10")
                ),
                new EmployeeEntity(
                        4,
                        Date.valueOf("2020-11-20"),
                        "Róbert",
                        "Dékány",
                        Gender.valueOf("M"),
                        Date.valueOf("2020-11-21")
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
