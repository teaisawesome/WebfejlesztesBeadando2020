package hu.unideb.webdev.dao.composites;

import hu.unideb.webdev.dao.entity.EmployeeEntity;

import java.io.Serializable;
import java.sql.Date;

public class CompositKeys implements Serializable
{
    //private EmployeeEntity employee;

    private String title;

    private Date fromDate;
}
