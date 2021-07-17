package com.example.task1crud.service;

import com.example.task1crud.entity.RoleType;
import com.example.task1crud.entity.Task;
import com.example.task1crud.entity.User;
import com.example.task1crud.payload.ApiResponse;
import com.example.task1crud.payload.TaskDto;
import com.example.task1crud.repository.TaskRepository;
import com.example.task1crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository repository;
    @Autowired
    UserRepository userRepository;

    public ApiResponse getAllTasksByUSerId(Integer id) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRollar().getRoleType() != RoleType.ROLE_ADMIN) {
            return new ApiResponse("siz admin emassiz", false);
        }
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("user not found", false);
        List<Task> allByUsers_id = repository.findAllByUsers_Id(id);
        if (!allByUsers_id.isEmpty()) return new ApiResponse("success", true, allByUsers_id);
        return new ApiResponse("faild", false);
    }

    public ApiResponse getOneTask(Integer id) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> byId = repository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("faild", false);

        if (!byId.get().getUsers().getUsername().equals(principal.getUsername()) ||
                !principal.getRollar().getRoleType().equals(RoleType.ROLE_ADMIN))
            return new ApiResponse("bu task sizning taskingiz emas", false);


        return new ApiResponse("success", true, byId);
    }

    public ApiResponse getMyTasks() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> byId = repository.findById(principal.getId());
        if (!byId.isPresent()) return new ApiResponse("faild", false);

        if (!byId.get().getUsers().getUsername().equals(principal.getUsername()) ||
                !principal.getRollar().getRoleType().equals(RoleType.ROLE_ADMIN))
            return new ApiResponse("bu task sizning taskingiz emas", false);


        return new ApiResponse("success", true, byId);
    }

    public ApiResponse addTask(TaskDto task1) {
        if (task1.getText() == null || task1.getTitle() == null) {
            return new ApiResponse("fail, malumotlaringizda xatolik", false);
        }
        Task task = new Task();
        task.setText(task1.getText());
        task.setTitle(task1.getTitle());
        Task save1 = repository.save(task);
        return new ApiResponse("success", true, save1);
    }

    public ApiResponse updateTask(TaskDto dto, Integer taskId) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (dto.getText() == null || dto.getTitle() == null) {
            return new ApiResponse("fail, malumotlaringizda xatolik", false);
        }
        Optional<Task> byId = repository.findById(taskId);
        if (!byId.isPresent()) return new ApiResponse("fail. id not found", false);
        if (!byId.get().getUsers().getUsername().equals(principal.getUsername()) ||
                !principal.getRollar().getRoleType().equals(RoleType.ROLE_ADMIN))
            return new ApiResponse("bu task sizning taskingiz emas", false);

        Task task = byId.get();
        task.setText(dto.getText());
        task.setTitle(dto.getTitle());
        Task save = repository.save(task);
        return new ApiResponse("success", true, save);
    }

    public ApiResponse deleteByID(Integer id) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Task> byId = repository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("fail. id not found", false);
        Task task = byId.get();
        if (!task.getUsers().getUsername().equals(principal.getUsername()) ||
                !principal.getRollar().getRoleType().equals(RoleType.ROLE_ADMIN))
            return new ApiResponse("bu task sizning taskingiz emas", false);
        repository.delete(task);
        return new ApiResponse("deleted", true, task);
    }
}
