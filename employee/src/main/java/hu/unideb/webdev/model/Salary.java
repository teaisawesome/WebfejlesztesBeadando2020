package hu.unideb.webdev.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Salary
{
    private int employeeId;

    private int salary;

    private Date fromDate;

    private Date toDate;

    public Salary(int salary, Date fromDate, Date toDate)
    {
        this.salary = salary;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
}
