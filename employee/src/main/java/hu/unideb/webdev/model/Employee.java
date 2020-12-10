package hu.unideb.webdev.model;


import lombok.*;

import java.sql.Date;


@AllArgsConstructor
@ToString
@Getter
@Builder
@EqualsAndHashCode
public class Employee
{
    private int id;

    private Date birthDate;

    private String firstName;

    private String lastName;

    private Gender gender;

    private Date hireDate;

    public Employee(String birthDate, String firstName, String lastName, String gender, String hireDate)
    {
        this.birthDate = Date.valueOf(birthDate);
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = Gender.valueOf(gender);
        this.hireDate = Date.valueOf(hireDate);
    }
}
