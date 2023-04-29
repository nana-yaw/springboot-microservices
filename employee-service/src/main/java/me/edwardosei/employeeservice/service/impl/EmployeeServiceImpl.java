package me.edwardosei.employeeservice.service.impl;

import lombok.AllArgsConstructor;
import me.edwardosei.employeeservice.dto.EmployeeDto;
import me.edwardosei.employeeservice.entity.Employee;
import me.edwardosei.employeeservice.repository.EmployeeRepository;
import me.edwardosei.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    private ModelMapper modelMapper;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        // convert employee dto to employee jpa entity
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto getEmployeeByEmail(String employeeEmail) {
        Employee employee = employeeRepository.findByEmail(employeeEmail);
        return modelMapper.map(employee, EmployeeDto.class);
    }
}
