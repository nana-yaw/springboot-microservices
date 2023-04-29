package me.edwardosei.employeeservice.controller;

import lombok.AllArgsConstructor;
import me.edwardosei.employeeservice.dto.EmployeeDto;
import me.edwardosei.employeeservice.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    // Create an employee REST API endpoint
    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployeeDto = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployeeDto, HttpStatus.CREATED);
    }

    // Get an employee REST API endpoint
    @GetMapping("{employee-email}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("employee-email") String employeeEmail) {
        EmployeeDto employeeDto = employeeService.getEmployeeByEmail(employeeEmail);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }
}
