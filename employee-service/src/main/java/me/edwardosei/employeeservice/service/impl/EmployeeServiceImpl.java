package me.edwardosei.employeeservice.service.impl;

import lombok.AllArgsConstructor;
import me.edwardosei.employeeservice.dto.EmployeeDto;
import me.edwardosei.employeeservice.entity.Employee;
import me.edwardosei.employeeservice.exception.EmailAlreadyExistsException;
import me.edwardosei.employeeservice.exception.ResourceNotFoundException;
import me.edwardosei.employeeservice.mapper.AutoEmployeeMapper;
import me.edwardosei.employeeservice.repository.EmployeeRepository;
import me.edwardosei.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    private ModelMapper modelMapper;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Optional<Employee> employeeEmail = employeeRepository.findByEmail(employeeDto.getEmail());
        if (employeeEmail.isPresent()){
            throw new EmailAlreadyExistsException("Email exists already");
        }
        // convert employee dto to employee jpa entity
//        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee employee = AutoEmployeeMapper.MAPPER.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return AutoEmployeeMapper.MAPPER.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );
        return AutoEmployeeMapper.MAPPER.mapToEmployeeDto(employee);
    }
}
