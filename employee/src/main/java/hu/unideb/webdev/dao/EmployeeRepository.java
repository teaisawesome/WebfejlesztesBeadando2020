package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer>
{
    EmployeeEntity findById(int id);

    Collection<EmployeeEntity> findAllById(int id);
}
