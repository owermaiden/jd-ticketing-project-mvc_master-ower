package com.cybertek.implementation;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.service.ProjectService;
import com.cybertek.utils.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String> implements ProjectService {

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    @Override
    public ProjectDTO findById(String projectCode) {
        return super.findById(projectCode);
    }

    @Override
    public ProjectDTO save(ProjectDTO object) {
        return super.save(object.getProjectCode(), object);
    }

    @Override
    public void delete(ProjectDTO object) {
        super.delete(object);
    }

    @Override
    public void deleteById(String projectCode) {
        super.deleteById(projectCode);
    }

    @Override
    public void update(ProjectDTO object) {

        ProjectDTO newProject = findById(object.getProjectCode());

        if(object.getProjectStatus() == null){
            object.setProjectStatus(newProject.getProjectStatus());
        }
        super.update(object.getProjectCode(), object);
    }

    @Override
    public void complete(ProjectDTO project) {
        project.setProjectStatus(Status.COMPLETE);
        super.save(project.getProjectCode(), project);  // Dont forget to save your change to Map....
    }

}
