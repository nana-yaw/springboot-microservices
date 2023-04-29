package me.edwardosei.employeeservice.service;

import me.edwardosei.employeeservice.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployee(String employeeEmail);
}
