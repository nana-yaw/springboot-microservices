package me.edwardosei.employeeservice.service.impl;

import lombok.AllArgsConstructor;
import me.edwardosei.employeeservice.dto.EmployeeDto;
import me.edwardosei.employeeservice.entity.Employee;
import me.edwardosei.employeeservice.repository.EmployeeRepository;
import me.edwardosei.employeeservice.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        // convert employee dto to employee jpa entity
        Employee employee = new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );
        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDto(
                savedEmployee.getId(),
                savedEmployee.getFirstName(),
                savedEmployee.getLastName(),
                savedEmployee.getEmail()
        );
    }
}
