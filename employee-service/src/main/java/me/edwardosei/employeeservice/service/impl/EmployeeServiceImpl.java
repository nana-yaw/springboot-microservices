package me.edwardosei.employeeservice.service.impl;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import me.edwardosei.employeeservice.dto.ApiResponseDto;
import me.edwardosei.employeeservice.dto.DepartmentDto;
import me.edwardosei.employeeservice.dto.EmployeeDto;
import me.edwardosei.employeeservice.dto.OrganizationDto;
import me.edwardosei.employeeservice.entity.Employee;
import me.edwardosei.employeeservice.exception.EmailAlreadyExistsException;
import me.edwardosei.employeeservice.exception.ResourceNotFoundException;
import me.edwardosei.employeeservice.mapper.AutoEmployeeMapper;
import me.edwardosei.employeeservice.repository.EmployeeRepository;
import me.edwardosei.employeeservice.service.DepartmentServiceAPIClient;
import me.edwardosei.employeeservice.service.EmployeeService;
import me.edwardosei.employeeservice.service.OrganizationServiceAPIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private EmployeeRepository employeeRepository;

//    private RestTemplate restTemplate;

//    private ModelMapper modelMapper;

//    private WebClient webClient;
    private DepartmentServiceAPIClient departmentServiceApiClient;

    private OrganizationServiceAPIClient organizationServiceAPIClient;

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
//    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartmentAndOrg")
    public ApiResponseDto getEmployeeById(Long employeeId) {
        LOGGER.info("inside getEmployeeById method");

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );
        EmployeeDto employeeDto = AutoEmployeeMapper.MAPPER.mapToEmployeeDto(employee);

//        ResponseEntity<DepartmentDto> responseEntity =
//                restTemplate.getForEntity(
//                        "http://localhost:8080/api/departments/" + employee.getDepartmentCode(),
//                        DepartmentDto.class);

//        DepartmentDto departmentDto = webClient.get()
//                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDto.class)
//                .block();

        DepartmentDto departmentDto = departmentServiceApiClient.getDepartment(employeeDto.getDepartmentCode());
        OrganizationDto organizationDto = organizationServiceAPIClient
                .getOrganization(employeeDto.getOrganizationCode());

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setOrganization(organizationDto);

        return apiResponseDto;
    }

    public ApiResponseDto getDefaultDepartmentAndOrg(Long employeeId, Exception exception) {
        LOGGER.info("inside getDefaultDepartmentAndOrg fallback method");
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );
        EmployeeDto employeeDto = AutoEmployeeMapper.MAPPER.mapToEmployeeDto(employee);

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("R&D");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research & Development");

        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setOrganizationCode("ABC_ORG");
        organizationDto.setOrganizationName("ABC");
        organizationDto.setOrganizationDescription("ABC Company Ltd");

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setOrganization(organizationDto);

        return apiResponseDto;
    }
}
