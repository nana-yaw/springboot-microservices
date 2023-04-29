package me.edwardosei.departmentservice.service.impl;

import lombok.AllArgsConstructor;
import me.edwardosei.departmentservice.dto.DepartmentDto;
import me.edwardosei.departmentservice.entity.Department;
import me.edwardosei.departmentservice.repository.DepartmentRepository;
import me.edwardosei.departmentservice.service.DepartmentService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        // convert department dto to department jpa entity
        Department department = new Department(
                departmentDto.getId(),
                departmentDto.getDepartmentName(),
                departmentDto.getDepartmentDescription(),
                departmentDto.getDepartmentCode()
        );
        Department savedDepartment = departmentRepository.save(department);
        // convert department jpa entity to department dto
        return new DepartmentDto(
                savedDepartment.getId(),
                savedDepartment.getDepartmentName(),
                savedDepartment.getDepartmentDescription(),
                savedDepartment.getDepartmentCode()
        );
    }
}
