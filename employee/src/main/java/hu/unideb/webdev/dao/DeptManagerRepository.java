package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.DepartmentEntity;
import hu.unideb.webdev.dao.entity.DeptEmployeeEntity;
import hu.unideb.webdev.dao.entity.DeptManagerEntity;
import hu.unideb.webdev.dao.entity.EmployeeEntity;
import hu.unideb.webdev.model.DeptManager;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Collection;

public interface DeptManagerRepository extends CrudRepository<DeptManagerEntity, Integer>
{
    //Collection<DeptEmployeeEntity> findAllByKey_DepartmentAndToDateIsGreaterThanEqual(DepartmentEntity departmentEntity, Date date);
    Collection<DeptManagerEntity> findAllByKey_DepartmentAndToDateIsGreaterThanEqual(DepartmentEntity departmentEntity, Date date);

    //DeptEmployeeEntity findByKey_EmployeeAndToDateGreaterThanEqual(EmployeeEntity employeeEntity, Date date);
    DeptManagerEntity findByKey_EmployeeAndToDateGreaterThanEqual(EmployeeEntity employeeEntity, Date date);

    DeptManagerEntity findByKey_Employee(EmployeeEntity employeeEntity);
}
