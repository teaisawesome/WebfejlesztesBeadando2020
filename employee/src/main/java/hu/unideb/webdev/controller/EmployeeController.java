package hu.unideb.webdev.controller;


import hu.unideb.webdev.controller.dto.*;
import hu.unideb.webdev.exception.CannotRecordSalaryException;
import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.exception.EmployeeHasNoTitleException;
import hu.unideb.webdev.model.Employee;
import hu.unideb.webdev.model.Salary;
import hu.unideb.webdev.model.Title;
import hu.unideb.webdev.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class EmployeeController
{
    private final EmployeeService employeeService;

    @GetMapping("/employee")
    public Collection<EmployeeDto> listAddresses()
    {
        return employeeService.getAllEmployee()
                .stream()
                .map(model -> EmployeeDto.builder()
                        .id(String.valueOf(model.getId()))
                        .birthDate(String.valueOf(model.getBirthDate()))
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .gender(String.valueOf(model.getGender()))
                        .hireDate(String.valueOf(model.getHireDate()))
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/employee")
    public void record(@RequestBody EmployeeRecordRequestDto requestDto)
    {
            employeeService.recordEmployee(new Employee(
                    requestDto.getBirthDate(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getGender(),
                    requestDto.getHireDate()
            ));
    }

    @DeleteMapping("/employee")
    public void deleteEmployee(@RequestBody EmployeeRecordRequestDto requestDto)
    {
        try
        {
            employeeService.deleteEmployee(
                    new Employee(
                            requestDto.getBirthDate(),
                            requestDto.getFirstName(),
                            requestDto.getLastName(),
                            requestDto.getGender(),
                            requestDto.getHireDate()
                    )
            );
        }
        catch(UnknownEmployeeException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/listEmployeeTitles")
    public Collection<TitleDto> listEmployeeTitles(@RequestBody EmployeeRecordRequestDto requestDto)
    {
        try {
            return employeeService.getEmployeeTitle(
                    new Employee(
                            requestDto.getBirthDate(),
                            requestDto.getFirstName(),
                            requestDto.getLastName(),
                            requestDto.getGender(),
                            requestDto.getHireDate()
                    )
            )
                    .stream()
                    .map(model -> TitleDto.builder()
                            .title(model.getTitle())
                            .fromDate(String.valueOf(model.getFromDate()))
                            .toDate(String.valueOf(model.getToDate()))
                            .build())
                    .collect(Collectors.toList());
        }
        catch( UnknownEmployeeException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/employeetitles")
    public void removeEmployeeTitles(@RequestBody EmployeeRecordRequestDto requestDto) {
        try {
            employeeService.removeEmployeeTitles(
                    new Employee(
                            requestDto.getBirthDate(),
                            requestDto.getFirstName(),
                            requestDto.getLastName(),
                            requestDto.getGender(),
                            requestDto.getHireDate()
                    )
            );
        }
        catch (EmployeeHasNoTitleException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        catch(UnknownEmployeeException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/newtitle")
    void recordNewTitleForEmployee(@RequestBody NewTitleDto requestDto)
    {
        try
        {
            employeeService.recordTitle(
                    new Employee(
                            requestDto.getBirthDate(),
                            requestDto.getFirstName(),
                            requestDto.getLastName(),
                            requestDto.getGender(),
                            requestDto.getHireDate()
                    ),
                    new Title(
                            requestDto.getTitle(),
                            Date.valueOf(requestDto.getFromDate()),
                            Date.valueOf("9999-01-01")
                    )
            );
        }
        catch(UnknownEmployeeException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/listEmployeeSalary")
    public Collection<SalaryDto> listEmployeeSalaries(@RequestBody EmployeeRecordRequestDto requestDto)
    {
        try {
            return employeeService.listEmployeeSalaries(
                    new Employee(
                            requestDto.getBirthDate(),
                            requestDto.getFirstName(),
                            requestDto.getLastName(),
                            requestDto.getGender(),
                            requestDto.getHireDate()
                    )
            )
                    .stream()
                    .map(model -> SalaryDto.builder()
                            .salary(String.valueOf(model.getSalary()))
                            .fromDate(String.valueOf(model.getFromDate()))
                            .toDate(String.valueOf(model.getToDate()))
                            .build())
                    .collect(Collectors.toList());
        }
        catch( UnknownEmployeeException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/listEmployeeCurrentSalary")
    public SalaryDto listEmployeeCurrentSalary(@RequestBody EmployeeRecordRequestDto requestDto)
    {
        try
        {
            Salary salary = employeeService.listEmployeeCurrentSalary(new Employee(
                    requestDto.getBirthDate(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getGender(),
                    requestDto.getHireDate()
            ));

            return SalaryDto.builder()
                            .salary(String.valueOf(salary.getSalary()))
                            .fromDate(String.valueOf(salary.getFromDate()))
                            .toDate(String.valueOf(salary.getToDate()))
                            .build();
        }
        catch (UnknownEmployeeException | InvalidRequestException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/employeesalary")
    public void removeEmployeeSalary(@RequestBody EmployeeRecordRequestDto requestDto) {
        try {
            employeeService.removeEmployeeSalary(
                    new Employee(
                            requestDto.getBirthDate(),
                            requestDto.getFirstName(),
                            requestDto.getLastName(),
                            requestDto.getGender(),
                            requestDto.getHireDate()
                    )
            );
        }
        catch(UnknownEmployeeException | InvalidRequestException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/newsalary")
    public void recordNewSalary(@RequestBody NewSalaryDto requestDto)
    {
        try
        {
            employeeService.recordSalary(
                    new Employee(
                            requestDto.getBirthDate(),
                            requestDto.getFirstName(),
                            requestDto.getLastName(),
                            requestDto.getGender(),
                            requestDto.getHireDate()
                    ),
                    Integer.parseInt(requestDto.getSalary())
            );
        }
        catch(CannotRecordSalaryException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
            catch(UnknownEmployeeException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
