package hu.unideb.webdev.dao.entity;

import hu.unideb.webdev.dao.composites.EmployeeCompositeKeys;
import hu.unideb.webdev.model.Gender;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Setter
@Getter
@ToString
@Entity
@Table(name = "employees", schema = "employees")
//@IdClass(EmployeeCompositeKeys.class)
public class EmployeeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="emp_no")
    private Integer id;

    @Column(name="birth_date")
    private Date birthDate;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column(name="hire_date")
    private Date hireDate;
}
