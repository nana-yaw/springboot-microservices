package me.edwardosei.departmentservice.service;

import me.edwardosei.departmentservice.dto.DepartmentDto;

import java.util.Optional;

public interface DepartmentService {
    DepartmentDto saveDepartment(DepartmentDto departmentDto);
    DepartmentDto getDepartmentById(Long departmentId);

    DepartmentDto getDepartment(String departmentCode);
}
