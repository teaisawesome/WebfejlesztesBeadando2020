package hu.unideb.webdev.dao.entity;

import hu.unideb.webdev.dao.composites.DepartmentCompositeKeys;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
@ToString
@Entity
@Table(name = "departments", schema = "employees")
//@IdClass(DepartmentCompositeKeys.class)
public class DepartmentEntity
{
    @Id
    @Column(name = "dept_no")
    private String departmentNumber;

    @Column(name="dept_name")
    private String departmentName;

}
