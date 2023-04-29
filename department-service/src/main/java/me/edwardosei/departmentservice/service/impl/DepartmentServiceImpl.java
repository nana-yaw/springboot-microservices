package me.edwardosei.departmentservice.service.impl;

import lombok.AllArgsConstructor;
import me.edwardosei.departmentservice.dto.DepartmentDto;
import me.edwardosei.departmentservice.entity.Department;
import me.edwardosei.departmentservice.mapper.AutoDepartmentMapper;
import me.edwardosei.departmentservice.repository.DepartmentRepository;
import me.edwardosei.departmentservice.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepository departmentRepository;

    private ModelMapper modelMapper;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        // convert department dto to department jpa entity
//        Department department = modelMapper.map(departmentDto, Department.class);
        Department department = AutoDepartmentMapper.MAPPER.mapToDepartment(departmentDto);
        Department savedDepartment = departmentRepository.save(department);
        // convert department jpa entity to department dto
//        return modelMapper.map(savedDepartment, DepartmentDto.class);
        return AutoDepartmentMapper.MAPPER.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
        Department department = departmentRepository.findByDepartmentCode(departmentCode);
        return AutoDepartmentMapper.MAPPER.mapToDepartmentDto(department);
    }
}
