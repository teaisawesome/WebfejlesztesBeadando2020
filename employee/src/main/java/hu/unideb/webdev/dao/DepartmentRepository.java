package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.DepartmentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Integer>
{
    DepartmentEntity findDepartmentEntityByDepartmentNumberOrDepartmentName(String departmentNumber, String departmentName);

    void deleteByDepartmentName(String departmentName);

    DepartmentEntity findByDepartmentName(String departmentName);

    Collection<DepartmentEntity> findByDepartmentNumber(String departmentNumber);
}
