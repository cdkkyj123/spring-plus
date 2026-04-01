package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.request.TodoSearchRequest;
import org.example.expert.domain.todo.dto.response.TodoPageResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

    public interface TodoCustomRepository {

    Optional<Todo> findByIdWithUser(long todoId);

    Page<TodoPageResponse> findByMultiCondition(TodoSearchRequest request, Pageable pageable, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
