package hu.unideb.webdev.dao.composites;

import hu.unideb.webdev.dao.entity.EmployeeEntity;

import java.io.Serializable;

public class DeptManCompositeKeys implements Serializable
{
    private EmployeeEntity employee;

    private String departmentNumber;
}
