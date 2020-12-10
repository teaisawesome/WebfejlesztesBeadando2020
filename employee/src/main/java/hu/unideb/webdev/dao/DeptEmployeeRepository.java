package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.DepartmentEntity;
import hu.unideb.webdev.dao.entity.DeptEmployeeEntity;
import hu.unideb.webdev.dao.entity.DeptManagerEntity;
import hu.unideb.webdev.dao.entity.EmployeeEntity;
import hu.unideb.webdev.model.Department;
import hu.unideb.webdev.model.DeptEmployee;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Collection;

public interface DeptEmployeeRepository extends CrudRepository<DeptEmployeeEntity, Integer>
{
    Collection<DeptEmployeeEntity> findAllByKey_DepartmentAndToDateIsGreaterThanEqual(DepartmentEntity departmentEntity, Date date);

    DeptEmployeeEntity findByKey_EmployeeAndToDateGreaterThanEqual(EmployeeEntity employeeEntity, Date date);

    DeptEmployeeEntity findByKey_Employee(EmployeeEntity employeeEntity);

    Collection<DeptEmployeeEntity> findAllByKey_Employee(EmployeeEntity employeeEntity);
}
