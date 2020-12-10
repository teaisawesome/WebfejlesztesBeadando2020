package hu.unideb.webdev.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@Entity
@Table(name = "dept_emp", schema = "employees")
//@IdClass(DeptEmpCompositeKeys.class)
public class DeptEmployeeEntity
{
    @EmbeddedId
    private Key key;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Embeddable
    public static class Key implements Serializable
    {
        @ManyToOne
        @JoinColumn(name="emp_no")
        private EmployeeEntity employee;

        @ManyToOne
        @JoinColumn(name="dept_no")
        private DepartmentEntity department;
    }

    @Column(name="from_date")
    private Date fromDate;

    @Column(name="to_date")
    private Date toDate;

}
