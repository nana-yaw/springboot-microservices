package me.edwardosei.organizationservice.service.impl;

import lombok.AllArgsConstructor;
import me.edwardosei.organizationservice.dto.OrganizationDto;
import me.edwardosei.organizationservice.entity.Organization;
import me.edwardosei.organizationservice.mapper.OrganizationMapper;
import me.edwardosei.organizationservice.repository.OrganizationRepository;
import me.edwardosei.organizationservice.service.OrganizationService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
        Organization organization = OrganizationMapper.mapToOrganization(organizationDto);
        Organization savedOrganization = organizationRepository.save(organization);
        return OrganizationMapper.mapToOrganizationDto(savedOrganization);
    }

    @Override
    public OrganizationDto getOrganizationByCode(String organizationCode) {
        Organization organization = organizationRepository.findByOrganizationCode(organizationCode);
        return OrganizationMapper.mapToOrganizationDto(organization);
    }
}
