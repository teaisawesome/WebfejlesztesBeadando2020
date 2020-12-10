package hu.unideb.webdev.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NewSalaryDto
{
    private String birthDate;

    private String firstName;

    private String lastName;

    private String gender;

    private String hireDate;

    private String salary;
}
