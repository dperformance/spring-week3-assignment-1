package com.codesoom.assignment.application;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TaskServiceTest {
    private TaskService taskService;
    private static final String TASK_TITLE = "Test";
    private static final String UPDATE_POSTFIX = "!!!";

    @BeforeEach
    void setUp() {
        taskService = new TaskService();

        Task task = new Task();
        task.setTitle(TASK_TITLE);

        taskService.createTask(task);
    }

    @Test
    void getTasks() {
        List<Task> tasks = taskService.getTasks();

        assertThat(taskService.getTasks()).hasSize(1);
        assertThat(taskService.getTasks()).isNotEmpty();
//        Task task = taskService.getTasks().get(0);        List<Task>로 선언하여 사용한다.
        Task task = tasks.get(0);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);
    }

    @Test
    void getTaskWithValidId() {
        assertThat(taskService.getTask(1L).getTitle()).isEqualTo(TASK_TITLE);
    }

    @Test
    void getTaskWithInvalidId() {
        assertThatThrownBy(() -> taskService.getTask(100L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void createTask() {
        int oldSize = taskService.getTasks().size();

        Task task = new Task();
        task.setTitle(TASK_TITLE);
        taskService.createTask(task);

        int newSize = taskService.getTasks().size();

        assertThat(newSize - oldSize).isEqualTo(1);
    }

    @Test
    void updateTaskWithExistedId() {
        Task source = new Task();
        source.setTitle(TASK_TITLE + UPDATE_POSTFIX);
        taskService.updateTask(1L, source);

        Task task = taskService.getTask(1L);

        assertThat(task.getTitle()).isEqualTo(TASK_TITLE + UPDATE_POSTFIX);
    }

    @Test
    void updateTaskNotExistedId() {
        Task source = new Task();
        source.setTitle(TASK_TITLE + UPDATE_POSTFIX);

        assertThatThrownBy(() -> taskService.updateTask(100L, source))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void deleteTaskWithExistedId() {
        int oldSize = taskService.getTasks().size();

        taskService.deleteTask(1L);

        int newSize = taskService.getTasks().size();

        assertThat(oldSize - newSize).isEqualTo(1);
    }

    @Test
    void deleteTaskWithNotExistedId() {
        assertThatThrownBy(() -> taskService.deleteTask(100L))
                .isInstanceOf(TaskNotFoundException.class);
    }

}
