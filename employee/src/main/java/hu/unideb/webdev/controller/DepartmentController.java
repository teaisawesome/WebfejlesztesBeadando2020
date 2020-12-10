package hu.unideb.webdev.controller;

import hu.unideb.webdev.controller.dto.*;
import hu.unideb.webdev.exception.InvalidRequestException;
import hu.unideb.webdev.exception.UniqueDepartmentException;
import hu.unideb.webdev.exception.UnknownDepartmentException;
import hu.unideb.webdev.exception.UnknownEmployeeException;
import hu.unideb.webdev.model.Department;
import hu.unideb.webdev.model.DeptEmployee;
import hu.unideb.webdev.model.DeptManager;
import hu.unideb.webdev.model.Employee;
import hu.unideb.webdev.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DepartmentController
{
    private final DepartmentService departmentService;

    @GetMapping("/department")
    public Collection<DepartmentRecordRequestDto> listDepartments()
    {
        return departmentService.getAllDepartment()
                .stream()
                .map(model -> DepartmentRecordRequestDto.builder()
                        .departmentId(model.getDepartmentNumber())
                        .departmentName(model.getDepartmentName())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/department")
    public void recordDepartment(@RequestBody DepartmentRecordRequestDto requestDto)
    {
        try
        {
            departmentService.recordDepartment(new Department(
                    requestDto.getDepartmentId(),
                    requestDto.getDepartmentName()
            ));
        }
        catch(UniqueDepartmentException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/department")
    public void removeDepartment(@RequestBody DepartmentDto requestDto) {
        try
        {
            departmentService.removeDepartment(
                    new Department(
                            requestDto.getDepartmentName()
                    )
            );
        }
        catch(UnknownDepartmentException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/department")
    public void updateDepartmentName(@RequestBody DepartmentUpdateRequestDto requestDto)
    {
        try
        {
            departmentService.updateDepartmentName(
                    new Department(
                            requestDto.getDepartmentName()
                    ),
                    requestDto.getNewDepartmentName()
            );
        }
        catch(UnknownDepartmentException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/findEmployeesByDepartment")
    public Collection<EmployeeDto> listEmployeesInDepartment(@RequestBody DepartmentDto requestDto)
    {
        try
        {
            Department department = Department.builder()
                    .departmentName(requestDto.getDepartmentName())
                    .build();

            return departmentService.findAllEmployeeInDepartment(department)
                    .stream()
                    .map(model -> EmployeeDto.builder()
                            .id(String.valueOf(model.getId()))
                            .birthDate(String.valueOf(model.getBirthDate()))
                            .firstName(model.getFirstName())
                            .lastName(model.getLastName())
                            .hireDate(String.valueOf(model.getHireDate()))
                            .build())
                    .collect(Collectors.toList());
        }
        catch(UnknownDepartmentException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/findDepartmentsByEmployee")
    public DepartmentRecordRequestDto getEmployeeDepartment(@RequestBody EmployeeRecordRequestDto requestDto)
    {
        try
        {
            Department department = departmentService.getEmployeeDepartment(
                    new Employee(
                            requestDto.getBirthDate(),
                            requestDto.getFirstName(),
                            requestDto.getLastName(),
                            requestDto.getGender(),
                            requestDto.getHireDate()
                    )
            );

            return DepartmentRecordRequestDto.builder()
                    .departmentId(department.getDepartmentNumber())
                    .departmentName(department.getDepartmentName())
                    .build();
        }
        catch (UnknownEmployeeException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/addDepartmentToEmployee")
    public void updateDepartmentToEmployee(@RequestBody AddDepartmentToEmployeeRequestDto requestDto)
    {
        try
        {
            DeptEmployee deptEmployee = DeptEmployee.builder()
                    .employee(requestDto.getId())
                    .departmentNumber(requestDto.getNumber())
                    .fromDate(Date.valueOf("1111-01-01"))
                    .toDate(Date.valueOf("1111-01-01"))
                    .build();

            departmentService.createEmployeeDepartment(deptEmployee);
        }
        catch(UnknownDepartmentException | UnknownEmployeeException | InvalidRequestException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/addDepartmentToManager")
    public void updateDepartmentToManager(@RequestBody AddDepartmentToEmployeeRequestDto requestDto)
    {
        try
        {
            DeptManager deptManager = DeptManager.builder()
                    .employee(requestDto.getId())
                    .departmentNumber(requestDto.getNumber())
                    .fromDate(Date.valueOf("1111-01-01"))
                    .toDate(Date.valueOf("1111-01-01"))
                    .build();

            departmentService.createManagerDepartment(deptManager);
        }
        catch(UnknownDepartmentException | UnknownEmployeeException | InvalidRequestException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("listAllManager")
    public Collection<EmployeeDto> listAllManager()
    {
        return departmentService.getAllManager()
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
    @DeleteMapping("/manager")
    public void removeManager(@RequestBody EmployeeRecordRequestDto requestDto)
    {
        try
        {
            departmentService.removeManager(new Employee(
                            requestDto.getBirthDate(),
                            requestDto.getFirstName(),
                            requestDto.getLastName(),
                            requestDto.getGender(),
                            requestDto.getHireDate()
                    )
            );
        }
        catch(UnknownEmployeeException | InvalidRequestException  e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/employeeDepartment")
    public void removeEmployeeDepartmentRelation(@RequestBody EmployeeRecordRequestDto requestDto)
    {
        try
        {
            departmentService.removeEmployeeDepartmentRelation(new Employee(
                            requestDto.getBirthDate(),
                            requestDto.getFirstName(),
                            requestDto.getLastName(),
                            requestDto.getGender(),
                            requestDto.getHireDate()
                    )
            );
        }
        catch(UnknownEmployeeException | InvalidRequestException  e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
