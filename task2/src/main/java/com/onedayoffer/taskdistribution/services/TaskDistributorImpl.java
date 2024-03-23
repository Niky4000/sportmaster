/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.DTO.EmployeeDTO;
import com.onedayoffer.taskdistribution.DTO.TaskDTO;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author me
 */
@Service
public class TaskDistributorImpl implements TaskDistributor {

    private static final int MIN_LEAD_TIME = 360;
    private static final int MAX_LEAD_TIME = 420;

    /**
     *
     * @param employees
     * @param tasks - тут останутся нераспределённые задания
     */
    @Override
    public void distribute(List<EmployeeDTO> employees, List<TaskDTO> tasks) {
        Collections.sort(tasks, Comparator.comparing(TaskDTO::getPriority).thenComparing(Comparator.comparing(TaskDTO::getLeadTime).reversed()));
        Deque<EmployeeDTO> employeesQueue = new LinkedList<>(employees);
        Map<EmployeeDTO, Set<TaskDTO>> incompatibleTasks = new HashMap<>();
        while (!employeesQueue.isEmpty()) {
            Iterator<TaskDTO> tasksIterator = tasks.iterator();
            while (tasksIterator.hasNext() && !employeesQueue.isEmpty()) {
                TaskDTO task = tasksIterator.next();
                if (task == null || employeesQueue.isEmpty()) {
                    break;
                }
                boolean firstIteration = true;
                EmployeeDTO firstEmployee = employeesQueue.peekFirst(); // Это необходимо для предотвращения зацикливания
                while (true) {
                    EmployeeDTO employee = employeesQueue.pollFirst();
                    if (employee == null || (firstEmployee == employee && !firstIteration)) { // Если это не первая итерация и мы опять пробуем первого сотрудника, то выходим из цикла.
                        if (!(incompatibleTasks.containsKey(employee) && incompatibleTasks.get(employee).contains(task))) { // Если мы не пытались дать сотруднику это задание, то добавим этого сотрудника в конец очереди.
                            employeesQueue.addLast(employee);
                        }
                        incompatibleTasks.compute(employee, (k, v) -> { // Это задание мы уже пытались дать сотруднику.
                            if (v == null) {
                                v = new HashSet<>();
                            }
                            v.add(task);
                            return v;
                        });
                        break;
                    }
                    firstIteration = false;
                    Integer totalLeadTime = employee.getTotalLeadTime();
                    if (totalLeadTime + task.getLeadTime() <= MAX_LEAD_TIME) {
                        employee.getTasks().add(task); // Отдаём задание сотруднику.
                        tasksIterator.remove(); // Удаляем задание.
                        if (totalLeadTime + task.getLeadTime() < MAX_LEAD_TIME) {
                            employeesQueue.addLast(employee); // Если сотрудник может взять ещё, то помещаем его в конец очереди.
                        }
                        break;
                    } else if (totalLeadTime < MAX_LEAD_TIME) { // Если сотрудник может взять ещё, то помещаем его в конец очереди.
                        employeesQueue.addLast(employee);
                    }
                }
            }
        }
    }
}
