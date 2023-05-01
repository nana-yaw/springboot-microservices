package me.edwardosei.employeeservice.service.impl;

import lombok.AllArgsConstructor;
import me.edwardosei.employeeservice.dto.ApiResponseDto;
import me.edwardosei.employeeservice.dto.DepartmentDto;
import me.edwardosei.employeeservice.dto.EmployeeDto;
import me.edwardosei.employeeservice.entity.Employee;
import me.edwardosei.employeeservice.exception.EmailAlreadyExistsException;
import me.edwardosei.employeeservice.exception.ResourceNotFoundException;
import me.edwardosei.employeeservice.mapper.AutoEmployeeMapper;
import me.edwardosei.employeeservice.repository.EmployeeRepository;
import me.edwardosei.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    private RestTemplate restTemplate;

    private ModelMapper modelMapper;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Optional<Employee> employeeEmail = employeeRepository.findByEmail(employeeDto.getEmail());
        if (employeeEmail.isPresent()){
            throw new EmailAlreadyExistsException("Email exists already");
        }
        System.out.println(employeeDto.getDepartmentCode());
        // convert employee dto to employee jpa entity
//        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee employee = AutoEmployeeMapper.MAPPER.mapToEmployee(employeeDto);
        System.out.println(employee.getDepartmentCode());
        Employee savedEmployee = employeeRepository.save(employee);
        return AutoEmployeeMapper.MAPPER.mapToEmployeeDto(savedEmployee);
    }

    // Get an employee record with its respective department record.
    @Override
    public ApiResponseDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );
        EmployeeDto employeeDto = AutoEmployeeMapper.MAPPER.mapToEmployeeDto(employee);

        ResponseEntity<DepartmentDto> responseEntity =
                restTemplate.getForEntity(
                        "http://localhost:8080/api/departments/" + employee.getDepartmentCode(),
                        DepartmentDto.class);

        DepartmentDto departmentDto = responseEntity.getBody();
        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);

        return apiResponseDto;
    }
}
