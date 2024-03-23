package com.onedayoffer.taskdistribution.utils;

import com.onedayoffer.taskdistribution.DTO.EmployeeDTO;
import com.onedayoffer.taskdistribution.DTO.TaskDTO;
import com.onedayoffer.taskdistribution.DTO.TaskStatus;
import com.onedayoffer.taskdistribution.DTO.TaskType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class DataGenerator {

    public List<TaskDTO> getTasks() {
        return taskPack1();
    }

    public List<EmployeeDTO> getEmployees() { return employeePack1(); }

    private int[] leadTimeDirectory = {30, 60, 90, 120, 240};

    private int[][] initArray = {{1, 30}, {9, 120}, {10, 60}, {6, 60}, {8, 60},
            {10, 30}, {4, 180}, {8, 30}, {10, 30}, {10, 120}, {4, 120}, {9, 120},
            {7, 120}, {3, 30}, {2, 180}, {3, 180}, {1, 60}, {8, 120}, {9, 60},
            {2, 120}, {3, 120}, {5, 60}, {7, 30}, {2, 60}, {8, 60}, {10, 30}};

    private RandomEnumGenerator<TaskType> taskTypeGenerator = new RandomEnumGenerator<>(TaskType.class);

    private List<TaskDTO> randomTasksGenerator() {
        Random rn = new Random();
        ArrayList<TaskDTO> tasks = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int priority = rn.nextInt(10) + 1;
            int leadTime = leadTimeDirectory[rn.nextInt(5)];
            tasks.add(new TaskDTO(null, "task" + (i + 1),
                    taskTypeGenerator.randomEnum(),
                    TaskStatus.APPOINTED,
                    priority,
                    leadTime));
        }
        return tasks;
    }

    private List<TaskDTO> taskPack1() {
        List<TaskDTO> tasks = new ArrayList<>();
        for (int i = 0; i<initArray.length-1; i++) {
            tasks.add(
                    new TaskDTO(null, "task" + (i + 1),
                            taskTypeGenerator.randomEnum(),
                            TaskStatus.APPOINTED,
                            initArray[i][0], initArray[i][1])
            );
        }
        return tasks;
    }

    private List<TaskDTO> taskPack2() {
        return List.of(
                new TaskDTO(null, "task1", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 1, 60),
                new TaskDTO(null, "task2", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 1, 94),
                new TaskDTO(null, "task3", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 3, 70),
                new TaskDTO(null, "task4", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 3, 95),
                new TaskDTO(null, "task5", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 3, 40),
                new TaskDTO(null, "task6", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 3, 90),
                new TaskDTO(null, "task7", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 4, 30),
                new TaskDTO(null, "task8", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 5, 90),
                new TaskDTO(null, "task9", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 6, 60),
                new TaskDTO(null, "task10", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 7, 65),
                new TaskDTO(null, "task11", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 7, 90),
                new TaskDTO(null, "task12", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 2, 60),
                new TaskDTO(null, "task13", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 2, 68),
                new TaskDTO(null, "task14", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 1, 95),
                new TaskDTO(null, "task15", taskTypeGenerator.randomEnum(), TaskStatus.APPOINTED, 1, 90)
        );
    }

    private List<EmployeeDTO> employeePack1() {
        return List.of(
                new EmployeeDTO("Andrey", "hall manager"),
                new EmployeeDTO("Svetlana", "hall cleaner"),
                new EmployeeDTO("Mariya", "hall employee"),
                new EmployeeDTO("Dmitry", "hall employee"),
                new EmployeeDTO("David", "hall employee"));
    }

    public boolean equalLists(List<String> one, List<String> two) {
        if (one == null && two == null) {
            return true;
        }

        if ((one == null && two != null)
                || one != null && two == null
                || one.size() != two.size()) {
            return false;
        }

        one = new ArrayList<>(one);
        two = new ArrayList<>(two);

        Collections.sort(one);
        Collections.sort(two);
        return one.equals(two);
    }

    private static class RandomEnumGenerator<T extends Enum<T>> {
        private static final Random PRNG = new Random();
        private final T[] values;

        public RandomEnumGenerator(Class<T> e) {
            values = e.getEnumConstants();
        }

        public T randomEnum() {
            return values[PRNG.nextInt(values.length)];
        }
    }
}