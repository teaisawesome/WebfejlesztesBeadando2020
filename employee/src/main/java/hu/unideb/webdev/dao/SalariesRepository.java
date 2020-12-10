package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.EmployeeEntity;
import hu.unideb.webdev.dao.entity.SalaryEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Collection;

public interface SalariesRepository extends CrudRepository<SalaryEntity, Integer>
{
    Collection<SalaryEntity> findByEmployee(EmployeeEntity employeeEntity);

    SalaryEntity findSalaryEntityByEmployeeAndToDateGreaterThanEqual(EmployeeEntity employeeEntity, Date toDate);

    void deleteAllByEmployee(EmployeeEntity employeeEntity);

    SalaryEntity findSalaryEntityByFromDateIs(Date fromDate);

    Collection<SalaryEntity> findAllByEmployee(EmployeeEntity employeeEntity);
}
