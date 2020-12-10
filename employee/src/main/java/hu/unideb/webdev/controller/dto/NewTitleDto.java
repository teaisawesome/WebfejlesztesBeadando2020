package hu.unideb.webdev.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NewTitleDto
{
    private String birthDate;

    private String firstName;

    private String lastName;

    private String gender;

    private String hireDate;


    private String title;

    private String fromDate;
}
