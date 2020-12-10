package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.EmployeeEntity;
import hu.unideb.webdev.dao.entity.TitleEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Collection;

public interface TitleRepository extends CrudRepository<TitleEntity, Integer> {

    Collection<TitleEntity> findByEmployee(EmployeeEntity employeeEntity);
    //TitlesEntity findByEmployeeAndFromDateAfterAndToDateBefore(EmployeeEntity employeeEntity, Date date);

    // Ha már nem dolgozik itt.
    void deleteAllByEmployee(EmployeeEntity employeeEntity);

    TitleEntity findTitleEntityByEmployeeAndToDateGreaterThanEqual(EmployeeEntity employeeEntity, Date toDate);
}
