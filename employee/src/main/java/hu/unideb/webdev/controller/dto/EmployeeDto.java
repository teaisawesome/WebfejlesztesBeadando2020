package hu.unideb.webdev.controller.dto;

import hu.unideb.webdev.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto
{
    protected String id;

    protected String birthDate;

    protected String firstName;

    protected String lastName;

    protected String gender;

    protected String hireDate;
}
