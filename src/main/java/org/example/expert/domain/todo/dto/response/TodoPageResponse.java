package org.example.expert.domain.todo.dto.response;

public record TodoPageResponse(
        String title,
        Long managerCount,
        Long commentCount
) {

}
