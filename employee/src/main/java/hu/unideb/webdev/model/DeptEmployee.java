package hu.unideb.webdev.model;

import hu.unideb.webdev.dao.entity.EmployeeEntity;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
@Builder
public class DeptEmployee
{
    private String employee;

    private String departmentNumber;

    private Date fromDate;

    private Date toDate;
}
