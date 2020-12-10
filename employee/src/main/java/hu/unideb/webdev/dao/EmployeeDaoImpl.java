package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.EmployeeEntity;
import hu.unideb.webdev.dao.entity.SalaryEntity;
import hu.unideb.webdev.dao.entity.TitleEntity;
import hu.unideb.webdev.exception.CannotRecordSalaryException;
import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.model.Employee;
import hu.unideb.webdev.model.Salary;
import hu.unideb.webdev.model.Title;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeDaoImpl implements EmployeeDao
{
    private final EmployeeRepository employeeRepository;

    private final TitleRepository titleRepository;

    private final SalariesRepository salariesRepository;

    @Override
    public Collection<Employee> readAll()
    {
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
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
    public void createEmployee(Employee employee)
    {
        EmployeeEntity employeeEntity;

        employeeEntity = EmployeeEntity.builder()
                .birthDate(employee.getBirthDate())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .gender(employee.getGender())
                .hireDate(employee.getHireDate())
                .build();

        employeeRepository.save(employeeEntity);

        log.info("Employee is created!");
    }


    @Override
    public void deleteEmployee(Employee employee) throws UnknownEmployeeException
    {
        Optional<EmployeeEntity> employeeEntity = StreamSupport.stream(employeeRepository.findAll().spliterator(), false).filter(
                entity -> {
                    return employee.getFirstName().equals(entity.getFirstName()) &&
                            employee.getLastName().equals(entity.getLastName()) &&
                            employee.getBirthDate().equals(entity.getBirthDate()) &&
                            employee.getGender().equals(entity.getGender()) &&
                            employee.getHireDate().equals(entity.getHireDate());
                }
        ).findAny();

        if (!employeeEntity.isPresent()) {
            throw new UnknownEmployeeException(String.format("Employee Not Found %s", employee), employee);
        }

        employeeRepository.delete(employeeEntity.get());
    }

    @Override
    public EmployeeEntity findEmployeeById(int id)
    {
        Optional<EmployeeEntity> employeesEntity = StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                .filter(entity -> entity.getId() == id)
                .findFirst();

        if (!employeesEntity.isPresent()) {
            log.info("Nincsen ilyen EMP");
        }

        return employeesEntity.get();
    }

    @Override
    public void updateEmployee(Employee oldEmployeeData, Employee newEmployeeData)
    {
        Optional<EmployeeEntity> employeesEntity = StreamSupport.stream(employeeRepository.findAll().spliterator(), false).filter(
                entity -> {
                    return oldEmployeeData.getFirstName().equals(entity.getFirstName()) &&
                            oldEmployeeData.getLastName().equals(entity.getLastName()) &&
                            oldEmployeeData.getBirthDate().equals(entity.getBirthDate()) &&
                            oldEmployeeData.getGender().equals(entity.getGender()) &&
                            oldEmployeeData.getHireDate().equals(entity.getHireDate());
                }
        ).findAny();

        if (!employeesEntity.isPresent()) {
            log.info("error occured");
        }

        // update to new employee datas
        employeesEntity.get().setBirthDate(newEmployeeData.getBirthDate());
        employeesEntity.get().setFirstName(newEmployeeData.getFirstName());
        employeesEntity.get().setLastName(newEmployeeData.getLastName());
        employeesEntity.get().setGender(newEmployeeData.getGender());
        employeesEntity.get().setHireDate(newEmployeeData.getHireDate());

        System.out.println(employeesEntity.get());
        employeeRepository.save(employeesEntity.get());
        log.info("Update was successfull!");
    }

    @Override
    public Collection<Title> getEmployeeTitles(Employee employee) throws UnknownEmployeeException
    {
        EmployeeEntity employeeEntity = getEmployeeEntity(employee);

        return StreamSupport.stream(titleRepository.findByEmployee(employeeEntity).spliterator(), false)
                .map(entity -> new Title(
                        entity.getTitle(),
                        entity.getFromDate(),
                        entity.getToDate()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void removeEmployeeTitles(Employee employee) throws UnknownEmployeeException
    {
        EmployeeEntity employeeEntity = getEmployeeEntity(employee);

        titleRepository.deleteAllByEmployee(employeeEntity);
    }

    @Override
    public void createNewTitle(Employee employee, Title title) throws UnknownEmployeeException
    {
        EmployeeEntity employeeEntity = getEmployeeEntity(employee);

        long millis = System.currentTimeMillis();

        Date date = new Date(millis);

        // Régi Title beállítása
        if (!getEmployeeTitles(employee).isEmpty()) {
            TitleEntity oldTitleEntity = titleRepository.findTitleEntityByEmployeeAndToDateGreaterThanEqual(employeeEntity, Date.valueOf("9999-01-01"));

            oldTitleEntity.setToDate(date);

            titleRepository.save(oldTitleEntity);
        }

        // Új title létrehozása
        EmployeeEntity newEmployeeEntity = getEmployeeEntity(employee);

        TitleEntity newTitleEntity = TitleEntity.builder()
                .employee(newEmployeeEntity)
                .title(title.getTitle())
                .fromDate(title.getFromDate())
                .toDate(Date.valueOf("9999-01-01"))
                .build();

        titleRepository.save(newTitleEntity);
    }

    @Override
    public Collection<Salary> listEmployeeSalary(Employee employee) throws UnknownEmployeeException
    {
        EmployeeEntity employeeEntity = getEmployeeEntity(employee);

        return StreamSupport.stream(salariesRepository.findByEmployee(employeeEntity).spliterator(), false)
                .map(entity -> new Salary(
                        entity.getSalary(),
                        entity.getFromDate(),
                        entity.getToDate()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Salary listEmployeeCurrentSalary(Employee employee) throws UnknownEmployeeException, InvalidRequestException
    {
        EmployeeEntity employeeEntity = getEmployeeEntity(employee);

        Collection<SalaryEntity> isSalaryExits = salariesRepository.findAllByEmployee(employeeEntity);

        if(isSalaryExits.isEmpty())
        {
            throw new InvalidRequestException("Employee Has No Salary!");
        }

        SalaryEntity salaryEntity = salariesRepository.findSalaryEntityByEmployeeAndToDateGreaterThanEqual(employeeEntity, Date.valueOf("9999-01-01"));

        return new Salary(
                salaryEntity.getSalary(),
                salaryEntity.getFromDate(),
                salaryEntity.getToDate()
        );
    }

    @Transactional
    @Override
    public void removeEmployeeSalary(Employee employee) throws UnknownEmployeeException, InvalidRequestException
    {
        EmployeeEntity employeeEntity = getEmployeeEntity(employee);

        Collection<SalaryEntity> isSalaryExits = salariesRepository.findAllByEmployee(employeeEntity);

        if(isSalaryExits.isEmpty())
        {
            throw new InvalidRequestException("Employee Has No Salary! Delete Salary Is Invalid Operation!");
        }

        salariesRepository.deleteAllByEmployee(employeeEntity);
    }

    @Override
    public void createNewSalary(Employee employee, int salary) throws UnknownEmployeeException, CannotRecordSalaryException
    {
        EmployeeEntity employeeEntity = getEmployeeEntity(employee);

        long millis = System.currentTimeMillis();

        Date date = new Date(millis);

        SalaryEntity oldSalaryEntity;

        Optional<SalaryEntity> isSubmitable = Optional.ofNullable(salariesRepository.findSalaryEntityByFromDateIs(date));

        // Régi Salary beállítása
        if (!listEmployeeSalary(employee).isEmpty())
        {
                if(isSubmitable.isPresent())
                {
                    throw new CannotRecordSalaryException("Cannot record salary at now! You have already raised salary today.");
                }

                oldSalaryEntity = salariesRepository.findSalaryEntityByEmployeeAndToDateGreaterThanEqual(employeeEntity, Date.valueOf("9999-01-01"));

                oldSalaryEntity.setToDate(date);

                salariesRepository.save(oldSalaryEntity);

        }

        // Új Salary létrehozása
        EmployeeEntity newEmployeeEntity = getEmployeeEntity(employee);

        SalaryEntity newSalaryEntity = SalaryEntity.builder()
                .employee(newEmployeeEntity)
                .salary(salary)
                .fromDate(date)
                .toDate(Date.valueOf("9999-01-01"))
                .build();

        salariesRepository.save(newSalaryEntity);
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
