package hu.unideb.webdev.model;

import hu.unideb.webdev.dao.entity.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Title
{
    private int id;

    private String title;

    private Date fromDate;

    private Date toDate;

    public Title(String title, Date fromDate, Date toDate)
    {
        this.title = title;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
}
