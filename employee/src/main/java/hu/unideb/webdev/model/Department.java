package hu.unideb.webdev.model;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Department
{
    private String departmentNumber;

    private String departmentName;

    public Department(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public Department(String departmentNumber, String departmentName)
    {
        this.departmentNumber = departmentNumber;
        this.departmentName = departmentName;
    }

    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getDepartmentNumber() {
        return departmentNumber;
    }
}
