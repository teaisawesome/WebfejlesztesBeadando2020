package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.DepartmentEntity;
import hu.unideb.webdev.dao.entity.DeptManagerEntity;
import hu.unideb.webdev.dao.entity.EmployeeEntity;
import hu.unideb.webdev.exception.UniqueDepartmentException;
import hu.unideb.webdev.exception.UnknownDepartmentException;
import hu.unideb.webdev.model.*;
import lombok.extern.slf4j.Slf4j;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class DepartmentDaoImplTest
{
    @Spy
    @InjectMocks
    private DepartmentDaoImpl dao;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DeptEmployeeRepository deptEmployeeRepository;

    @Mock
    private DeptManagerRepository deptManagerRepository;

    @Test
    public void testCreateDepartment() throws UniqueDepartmentException
    {
        dao.createDepartment(getDepartment());

        verify(departmentRepository, times(1)).save(any());
    }

    @Test
    public void testReadAllDepartment()
    {
        when(departmentRepository.findAll()).thenReturn(getDefaultDepartmentEntitys());

        Collection<Department> actual = dao.readAll();

        assertThat(getDefaultDepartments(), is(actual));
    }

    @Test
    public void testDeleteDepartment() throws UnknownDepartmentException
    {
        doReturn(getDepartmentEntity()).when(dao).getDepartmentEntity(any());

        dao.deleteDepartment(any());

        verify(departmentRepository, times(1)).deleteByDepartmentName(any());
    }

    @Test
    public void testUpdateDepartmentName() throws UnknownDepartmentException
    {
        doReturn(getDepartmentEntity()).when(dao).getDepartmentEntity(any());

        dao.updateDepartment(getDepartment(), "Head Council");

        verify(departmentRepository, times(1)).save(any());
    }

    @Test
    public void testReadAllDepartmentManager()
    {
        when(deptManagerRepository.findAll()).thenReturn(getDefaultManagerEntites());

        Collection<Employee> actual = dao.readAllDepartmentManager();

        log.info(String.valueOf(dao.readAll()));

        assertThat(getDefaultEmployees(), is(actual));
    }

    private Department getDepartment()
    {
        return Department.builder()
                .departmentNumber("d005")
                .departmentName("Security Operation Center")
                .build();
    }

    private DepartmentEntity getDepartmentEntity()
    {
        return DepartmentEntity.builder()
                .departmentNumber("d001")
                .departmentName("Help Desk")
                .build();
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

    private Collection<DepartmentEntity> getDefaultDepartmentEntitys()
    {
        return Arrays.asList(
                DepartmentEntity.builder()
                        .departmentNumber("d005")
                        .departmentName("Security Operation Center")
                        .build(),
                DepartmentEntity.builder()
                        .departmentNumber("d003")
                        .departmentName("Development")
                        .build()
        );
    }

    private Collection<Department> getDefaultDepartments()
    {
        return Arrays.asList(
                Department.builder()
                        .departmentNumber("d005")
                        .departmentName("Security Operation Center")
                        .build(),
                Department.builder()
                        .departmentNumber("d003")
                        .departmentName("Development")
                        .build()
        );
    }

    private Collection<DeptManagerEntity> getDefaultManagerEntites()
    {
        DeptManagerEntity.Key key = DeptManagerEntity.Key.builder()
                .employee(new EmployeeEntity(
                        2,
                        Date.valueOf("1997-03-10"),
                        "Kálmán",
                        "Beka",
                        Gender.valueOf("M"),
                        Date.valueOf("1997-03-10")
                ))
                .department(getDepartmentEntity())
                .build();

        DeptManagerEntity.Key key2 = DeptManagerEntity.Key.builder()
                .employee(new EmployeeEntity(
                        4,
                        Date.valueOf("2020-11-20"),
                        "Róbert",
                        "Dékány",
                        Gender.valueOf("M"),
                        Date.valueOf("2020-11-21")
                ))
                .department(getDepartmentEntity())
                .build();

        return Arrays.asList(
                DeptManagerEntity.builder()
                        .key(key)
                        .fromDate(Date.valueOf("2020-11-20"))
                        .toDate(Date.valueOf("9999-11-20"))
                        .build(),
                DeptManagerEntity.builder()
                        .key(key2)
                        .fromDate(Date.valueOf("1965-11-20"))
                        .toDate(Date.valueOf("9999-11-20"))
                        .build()
        );
    }

    private Collection<Employee> getDefaultEmployees() {
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
