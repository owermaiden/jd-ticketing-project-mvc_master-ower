package com.cybertek.implementation;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.entity.Project;
import com.cybertek.enums.Status;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.mapper.UserMapper;
import com.cybertek.repository.ProjectRepository;
import com.cybertek.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;
    private UserMapper userMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserMapper userMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ProjectDTO getByProjectCode(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        return projectMapper.convertToDto(project);
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(obj -> projectMapper.convertToDto(obj)).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {
        dto.setProjectStatus(Status.OPEN);
        Project project = projectMapper.convertToEntity(dto);
        // project.setAssignedManager(userMapper.convertToEntity(dto.getAssignedManager()));   mapper convert this also we dont need this line actually
        projectRepository.save(project);
    }

    @Override
    public ProjectDTO update(ProjectDTO dto) {
        //Find current project from database
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());  // project from database
        // Map update project dto to entity object
        Project convertedProject = projectMapper.convertToEntity(dto);                // project updated by smbdy
        //set id to the converted object
        convertedProject.setId(project.getId());                                      // get id from old version to updated one
        // update the status again...
        convertedProject.setProjectStatus(project.getProjectStatus());                // get Status from old version to updated one
        //save updated project
        projectRepository.save(convertedProject);                                     // save new version to database

        return getByProjectCode(dto.getProjectCode());
    }

    @Override
    public void delete(String code) {
        Project project = projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);
        projectRepository.save(project);
    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }
}
