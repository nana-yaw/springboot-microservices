package me.edwardosei.employeeservice.service;

import me.edwardosei.employeeservice.dto.ApiResponseDto;
import me.edwardosei.employeeservice.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    ApiResponseDto getEmployeeById(Long employeeId);
}
