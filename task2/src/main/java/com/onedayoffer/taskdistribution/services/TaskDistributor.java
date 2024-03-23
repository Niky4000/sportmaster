package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.DTO.EmployeeDTO;
import com.onedayoffer.taskdistribution.DTO.TaskDTO;

import java.util.List;

public interface TaskDistributor {
    void distribute(List<EmployeeDTO> employees, List<TaskDTO> tasks);
}
