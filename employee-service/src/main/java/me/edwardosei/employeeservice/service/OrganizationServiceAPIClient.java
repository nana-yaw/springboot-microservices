package me.edwardosei.employeeservice.service;

import me.edwardosei.employeeservice.dto.OrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ORGANIZATION-SERVICE")
public interface OrganizationServiceAPIClient {
    // Build get an organization
    @GetMapping("api/organizations/{organization-code}")
    OrganizationDto getOrganization(@PathVariable("organization-code") String organizationCode);
}

