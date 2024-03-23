package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.DTO.EmployeeDTO;
import com.onedayoffer.taskdistribution.DTO.TaskDTO;
import com.onedayoffer.taskdistribution.DTO.TaskStatus;
import com.onedayoffer.taskdistribution.repositories.EmployeeRepository;
import com.onedayoffer.taskdistribution.repositories.TaskRepository;
import com.onedayoffer.taskdistribution.repositories.entities.Employee;
import com.onedayoffer.taskdistribution.repositories.entities.Task;
import jakarta.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public List<EmployeeDTO> getEmployees(@Nullable String sortDirection) {
        List<Employee> employees;
        if (sortDirection != null) {
            employees = employeeRepository.findAllAndSort(Sort.by(Sort.Direction.fromString(sortDirection), "fio"));
        } else {
            employees = employeeRepository.findAll();
        }
        Type listType = new TypeToken<List<EmployeeDTO>>() {
        }.getType();
        List<EmployeeDTO> employeeDTOS = modelMapper.map(employees, listType);
        return employeeDTOS;
        // if sortDirection.isPresent() ..
        // Sort.Direction direction = ...
        // employees = employeeRepository.findAllAndSort(Sort.by(direction, "fio"))
        // employees = employeeRepository.findAll()
        // Type listType = new TypeToken<List<EmployeeDTO>>() {}.getType()
        // List<EmployeeDTO> employeeDTOS = modelMapper.map(employees, listType)
    }

    @Transactional
    public EmployeeDTO getOneEmployee(Integer id) {
        Optional<EmployeeDTO> employeeById = getEmployeeById(id);
        return employeeById.orElse(null);
    }

    private Optional<EmployeeDTO> getEmployeeById(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        Optional<EmployeeDTO> employeeById = employee.map(e -> {
            Type type = new TypeToken<EmployeeDTO>() {
            }.getType();
            EmployeeDTO employeeDTO = modelMapper.map(e, type);
            return employeeDTO;
        });
        return employeeById;
    }

    public List<TaskDTO> getTasksByEmployeeId(Integer id) {
        return getEmployeeById(id).map(EmployeeDTO::getTasks).orElse(Collections.emptyList());
    }

    @Transactional
    public void changeTaskStatus(Integer taskId, TaskStatus status) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(status);
            taskRepository.saveAndFlush(task);
        }
    }

    @Transactional
    public void postNewTask(Integer employeeId, TaskDTO newTask) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            Type type = new TypeToken<Task>() {
            }.getType();
            Task task = modelMapper.map(newTask, type);
            employee.addTask(task);
            task.setEmployee(employee);
        }
    }
}
