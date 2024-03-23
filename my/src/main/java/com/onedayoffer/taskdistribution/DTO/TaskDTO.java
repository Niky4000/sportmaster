package com.onedayoffer.taskdistribution.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Integer id;
    private String name;
    private TaskType taskType;
    private TaskStatus status;
    private Integer priority;
    private Integer leadTime;
}
