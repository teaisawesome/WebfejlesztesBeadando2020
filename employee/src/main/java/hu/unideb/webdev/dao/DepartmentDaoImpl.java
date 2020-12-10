package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.DepartmentEntity;
import hu.unideb.webdev.dao.entity.DeptEmployeeEntity;
import hu.unideb.webdev.dao.entity.DeptManagerEntity;
import hu.unideb.webdev.dao.entity.EmployeeEntity;
import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UniqueDepartmentException;
import hu.unideb.webdev.exception.UnknownDepartmentException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentDaoImpl implements DepartmentDao
{
    private final DepartmentRepository departmentRepository;

    private final DeptEmployeeRepository deptEmployeeRepository;

    private final DeptManagerRepository deptManagerRepository;

    private final EmployeeRepository employeeRepository;

    @Override
    public Collection<Department> readAll()
    {
        return StreamSupport.stream(departmentRepository.findAll().spliterator(), false)
                .map(entity -> new Department(
                        entity.getDepartmentNumber(),
                        entity.getDepartmentName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void createDepartment(Department department) throws UniqueDepartmentException
    {
        Optional<DepartmentEntity> isDeparmentExists = Optional.ofNullable(departmentRepository.findDepartmentEntityByDepartmentNumberOrDepartmentName(department.getDepartmentNumber(), department.getDepartmentName()));

        if(isDeparmentExists.isPresent())
        {
            throw new UniqueDepartmentException(String.format("Department already exits! Department Has To Be Unique %s", department), department);
        }

        DepartmentEntity departmentEntity;

        departmentEntity = DepartmentEntity.builder()
                .departmentNumber(department.getDepartmentNumber())
                .departmentName(department.getDepartmentName())
                .build();

        departmentRepository.save(departmentEntity);

        log.info("Department is created!");
    }

    @Transactional
    @Override
    public void deleteDepartment(Department department) throws UnknownDepartmentException
    {
        DepartmentEntity departmentEntity = getDepartmentEntity(department);

        departmentRepository.deleteByDepartmentName(departmentEntity.getDepartmentName());
    }

    @Override
    public void updateDepartment(Department department, String newDepartmentName) throws UnknownDepartmentException
    {
        DepartmentEntity departmentEntity = getDepartmentEntity(department);

        departmentEntity.setDepartmentName(newDepartmentName);

        departmentRepository.save(departmentEntity);
    }

    @Override
    public Collection<Employee> readAllEmployeeInDepartment(Department department) throws UnknownDepartmentException
    {
        DepartmentEntity departmentEntity = getDepartmentEntity(department);

        Collection<EmployeeEntity> employeeEntities;

        employeeEntities = StreamSupport.stream(deptEmployeeRepository.findAllByKey_DepartmentAndToDateIsGreaterThanEqual(departmentEntity, Date.valueOf("9999-01-01"))
                .spliterator(), false)
                .map(entity -> new EmployeeEntity(
                        entity.getKey().getEmployee().getId(),
                        entity.getKey().getEmployee().getBirthDate(),
                        entity.getKey().getEmployee().getFirstName(),
                        entity.getKey().getEmployee().getLastName(),
                        entity.getKey().getEmployee().getGender(),
                        entity.getKey().getEmployee().getHireDate()
                        )
                )
                .collect(Collectors.toList());

        return StreamSupport.stream(employeeEntities.spliterator(), false)
                .map(entity -> new Employee(
                        entity.getId(),
                        entity.getBirthDate(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getGender(),
                        entity.getHireDate()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Department getEmployeeDepartment(Employee employee) throws UnknownEmployeeException
    {
        EmployeeEntity employeeEntity = getEmployeeEntity(employee);


        Optional<DeptEmployeeEntity> isEmployeeHasDepartment = Optional.ofNullable(deptEmployeeRepository.findByKey_EmployeeAndToDateGreaterThanEqual(employeeEntity, Date.valueOf("9999-01-01")));

        DeptEmployeeEntity deptEmployeeEntity = deptEmployeeRepository.findByKey_EmployeeAndToDateGreaterThanEqual(employeeEntity, Date.valueOf("9999-01-01"));

        if(!isEmployeeHasDepartment.isPresent())
        {
            throw new UnknownEmployeeException("Employee Has No Department ", employee);
        }

        DepartmentEntity departmentEntity = isEmployeeHasDepartment.get().getKey().getDepartment();

        return new Department(
                departmentEntity.getDepartmentNumber(),
                departmentEntity.getDepartmentName()
        );
    }

    @Override
    public void addEmployeeToDepartment(DeptEmployee deptEmployee) throws UnknownEmployeeException, UnknownDepartmentException, InvalidRequestException
    {

        EmployeeEntity employeeEntity = employeeRepository.findById(Integer.parseInt(deptEmployee.getEmployee()));

        Optional<DeptEmployeeEntity> isEmployeeHasDepartment;

        isEmployeeHasDepartment = Optional.ofNullable(deptEmployeeRepository.findByKey_EmployeeAndToDateGreaterThanEqual(employeeEntity, Date.valueOf("9999-01-01")));

        long millis = System.currentTimeMillis();

        Date date = new Date(millis);

        if(isEmployeeHasDepartment.isPresent())
        {
            if(isEmployeeHasDepartment.get().getKey().getDepartment().getDepartmentNumber().equals(deptEmployee.getDepartmentNumber()))
                throw new InvalidRequestException("Employee Is Already In Department!");

            isEmployeeHasDepartment.get().setToDate(date);

            DeptEmployeeEntity oldDeptEmployeeEntity = isEmployeeHasDepartment.get();

            deptEmployeeRepository.save(oldDeptEmployeeEntity);
        }

        DeptEmployeeEntity deptEmployeeEntity = DeptEmployeeEntity.builder()
            .key(new DeptEmployeeEntity.Key(queryEmployeeNumber(Integer.parseInt(deptEmployee.getEmployee())), queryDepartmentNumber(deptEmployee.getDepartmentNumber())))
                .fromDate(date)
                .toDate(Date.valueOf("9999-01-01"))
            .build();

        deptEmployeeRepository.save(deptEmployeeEntity);
    }

    @Override
    public void addManagerToDepartment(DeptManager deptManager) throws UnknownEmployeeException, UnknownDepartmentException, InvalidRequestException
    {
        Optional<DeptManagerEntity> isManagerHasDepartment;

        isManagerHasDepartment = Optional.ofNullable(deptManagerRepository.findByKey_EmployeeAndToDateGreaterThanEqual(queryEmployeeNumber(Integer.parseInt(deptManager.getEmployee())), Date.valueOf("9999-01-01")));

        long millis = System.currentTimeMillis();

        Date date = new Date(millis);

        if(isManagerHasDepartment.isPresent())
        {
            deptManagerRepository.delete(isManagerHasDepartment.get());

            DeptManagerEntity deptManagerEntity = DeptManagerEntity.builder()
                    .key(new DeptManagerEntity.Key(queryEmployeeNumber(Integer.parseInt(deptManager.getEmployee())), queryDepartmentNumber(deptManager.getDepartmentNumber())))
                    .fromDate(date)
                    .toDate(Date.valueOf("9999-01-01"))
                    .build();

            deptManagerRepository.save(deptManagerEntity);
        }
        else
        {
            DeptManagerEntity deptManagerEntity = DeptManagerEntity.builder()
                    .key(new DeptManagerEntity.Key(queryEmployeeNumber(Integer.parseInt(deptManager.getEmployee())), queryDepartmentNumber(deptManager.getDepartmentNumber())))
                    .fromDate(date)
                    .toDate(Date.valueOf("9999-01-01"))
                    .build();

            deptManagerRepository.save(deptManagerEntity);
        }
    }

    @Override
    public Collection<Employee> readAllDepartmentManager()
    {
        return StreamSupport.stream(deptManagerRepository.findAll().spliterator(), false)
                .map(entity -> new Employee(
                        entity.getKey().getEmployee().getId(),
                        entity.getKey().getEmployee().getBirthDate(),
                        entity.getKey().getEmployee().getFirstName(),
                        entity.getKey().getEmployee().getLastName(),
                        entity.getKey().getEmployee().getGender(),
                        entity.getKey().getEmployee().getHireDate()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteManager(Employee employee) throws UnknownEmployeeException, InvalidRequestException
    {
        EmployeeEntity employeeEntity = getEmployeeEntity(employee);

        Optional<DeptManagerEntity> isManagerHasDepartment;

        isManagerHasDepartment = Optional.ofNullable(deptManagerRepository.findByKey_Employee(employeeEntity));

        if(isManagerHasDepartment.isPresent())
        {
            deptManagerRepository.delete(isManagerHasDepartment.get());
        }
        else
        {
            throw new InvalidRequestException("Employee Is Not A Manager!");
        }
    }

    @Override
    public void deleteEmployeeDepartmentRelation(Employee employee) throws UnknownEmployeeException, InvalidRequestException
    {
        EmployeeEntity employeeEntity = getEmployeeEntity(employee);

        Optional<DeptEmployeeEntity>  deptEmployeeEntity = StreamSupport.stream(deptEmployeeRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return employee.getFirstName().equals(entity.getKey().getEmployee().getFirstName())  &&
                            employee.getLastName().equals(entity.getKey().getEmployee().getLastName()) &&
                            employee.getBirthDate().equals(entity.getKey().getEmployee().getBirthDate()) &&
                            employee.getGender().equals(entity.getKey().getEmployee().getGender()) &&
                            employee.getHireDate().equals(entity.getKey().getEmployee().getHireDate());
                }
        ).findAny();

        if(deptEmployeeEntity.isPresent())
        {
            Collection<DeptEmployeeEntity> departmentEntities = deptEmployeeRepository.findAllByKey_Employee(employeeEntity);

            deptEmployeeRepository.deleteAll(departmentEntities);
        }
        else
        {
            throw new InvalidRequestException("Employee Is Not Working In Any Department!");
        }
    }

    protected EmployeeEntity queryEmployeeNumber(int id) throws UnknownEmployeeException
    {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findAllById(id).stream().filter(
                entity -> entity.getId() == id
        ).findFirst();

        if(!employeeEntity.isPresent())
        {
            throw new UnknownEmployeeException(String.format("Employee Not Found"));
        }

        return employeeEntity.get();
    }

    protected DepartmentEntity queryDepartmentNumber(String number) throws UnknownDepartmentException
    {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findByDepartmentNumber(number).stream().filter(
                entity -> entity.getDepartmentNumber().equals(number)
        ).findFirst();

        if(!departmentEntity.isPresent())
        {
            throw new UnknownDepartmentException(String.format("Department Not Found"));
        }

        return departmentEntity.get();
    }


    protected DepartmentEntity getDepartmentEntity(Department department) throws UnknownDepartmentException
    {
        Optional<DepartmentEntity> departmentEntity = StreamSupport.stream(departmentRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return department.getDepartmentName().equals(entity.getDepartmentName());
                }
        ).findAny();

        if(departmentEntity.isPresent())
        {
            return departmentEntity.get();
        }
        else
        {
            throw new UnknownDepartmentException(String.format("Department Not Found %s", department), department);
        }
    }

    protected EmployeeEntity getEmployeeEntity(Employee employee) throws UnknownEmployeeException
    {
        Optional<EmployeeEntity>  employeeEntity = StreamSupport.stream(employeeRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return employee.getFirstName().equals(entity.getFirstName())  &&
                            employee.getLastName().equals(entity.getLastName()) &&
                            employee.getBirthDate().equals(entity.getBirthDate()) &&
                            employee.getGender().equals(entity.getGender()) &&
                            employee.getHireDate().equals(entity.getHireDate());
                }
        ).findAny();

        if(employeeEntity.isPresent())
        {
            return employeeEntity.get();
        }
        else
        {
            throw new UnknownEmployeeException(String.format("Employee Not Found %s", employee), employee);
        }
    }
}
