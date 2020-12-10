package hu.unideb.webdev;

import hu.unideb.webdev.dao.*;
import hu.unideb.webdev.model.Department;
import hu.unideb.webdev.model.DeptEmployee;
import hu.unideb.webdev.model.Employee;
import hu.unideb.webdev.model.Gender;
import hu.unideb.webdev.service.DepartmentService;
import hu.unideb.webdev.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.Date;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@RequiredArgsConstructor
public class App implements CommandLineRunner
{
    //private final DepartmentService employeeService;

    @Autowired
    ApplicationContext context;

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception { }
}
