package com.oludayo.service;
import com.oludayo.models.User;
import com.oludayo.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.oludayo.repo.TaskRepository;
import com.oludayo.dto.TaskDTO;
import com.oludayo.models.Task;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public TaskDTO createTask(TaskDTO taskDTO, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Task task = Task.builder()
            .title(taskDTO.title())
            .description(taskDTO.description())
            .dueDate(taskDTO.dueDate())
            .status(taskDTO.status())
            .user(user)
            .build();

        return convertToDTO(taskRepository.save(task));
    }

    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        existingTask.setTitle(taskDTO.title());
        existingTask.setDescription(taskDTO.description());
        existingTask.setDueDate(taskDTO.dueDate());
        existingTask.setStatus(taskDTO.status());

        return convertToDTO(taskRepository.save(existingTask));
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    private TaskDTO convertToDTO(Task task) {
        return TaskDTO.builder()
            .id(task.getTaskId())
            .title(task.getTitle())
            .description(task.getDescription())
            .dueDate(task.getDueDate())
            .status(task.getStatus())
            .userId(task.getUser().getId())
            .build();
    }
}