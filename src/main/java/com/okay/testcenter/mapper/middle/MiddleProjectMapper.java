package com.okay.testcenter.mapper.middle;

import com.okay.testcenter.domain.middle.MiddleProject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MiddleProjectMapper {

    public void insertMiddleProject(MiddleProject middleProject);

    public void deleteMiddleProject(int id);

    public void updateMiddleProject(MiddleProject middleProject);

    public MiddleProject findMiddleProjectById(int id);

    public MiddleProject findMiddleProjectByName(String name);

    public List<MiddleProject> findMiddleProjectList();


}
