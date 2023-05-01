package me.edwardosei.departmentservice.controller;

import lombok.AllArgsConstructor;
import me.edwardosei.departmentservice.dto.DepartmentDto;
import me.edwardosei.departmentservice.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/departments")
@AllArgsConstructor
public class DepartmentController {
    private DepartmentService departmentService;

    // Build create department REST API
    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto savedDepartmentDto = departmentService.saveDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartmentDto, HttpStatus.CREATED);
    }

    // Build get department by ID REST API
    @GetMapping("id/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") Long departmentId) {
        DepartmentDto departmentDto = departmentService.getDepartmentById(departmentId);
        return  new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }

    // Build get department by department code REST API
    @GetMapping("{department-code}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("department-code") String departmentCode) {
        DepartmentDto departmentDto = departmentService.getDepartment(departmentCode);
        return  new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }
}
