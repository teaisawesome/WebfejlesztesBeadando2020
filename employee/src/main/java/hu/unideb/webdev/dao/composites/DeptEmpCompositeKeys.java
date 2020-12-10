package hu.unideb.webdev.dao.composites;

import hu.unideb.webdev.dao.entity.DepartmentEntity;
import hu.unideb.webdev.dao.entity.EmployeeEntity;

import java.io.Serializable;

public class DeptEmpCompositeKeys implements Serializable
{
    private EmployeeEntity employee;

    private DepartmentEntity department;
}
