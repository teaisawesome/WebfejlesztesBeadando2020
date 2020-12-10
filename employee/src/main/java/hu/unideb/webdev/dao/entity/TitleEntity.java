package hu.unideb.webdev.dao.entity;

import hu.unideb.webdev.dao.composites.CompositKeys;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@Entity
@Table(name = "titles", schema = "employees")
@IdClass(CompositKeys.class)
public class TitleEntity
{

    @Id
    @ManyToOne
    @JoinColumn(name="emp_no")
    private EmployeeEntity employee;

    @Id
    @Column
    private String title;

    @Id
    @Column(name="from_date")
    private Date fromDate;

    @Column(name="to_date")
    private Date toDate;
}
