package hu.unideb.webdev.dao.entity;

import hu.unideb.webdev.dao.composites.SalariesCompositeKeys;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@Entity
@Table(name = "salaries", schema = "employees")
@IdClass(SalariesCompositeKeys.class)
public class SalaryEntity
{
    @Id
    @ManyToOne
    @JoinColumn(name="emp_no")
    private EmployeeEntity employee;

    @Column
    private int salary;

    @Id
    @Column(name = "from_date")
    private Date fromDate;

    @Column(name="to_date")
    private Date toDate;
}
