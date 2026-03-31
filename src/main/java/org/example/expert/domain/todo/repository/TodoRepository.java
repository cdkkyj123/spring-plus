package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 각각의 기준이 null이라면 넘기고 null이 아니라면 조건으로서 조회
    @Query("""
            SELECT t
            FROM Todo t
            LEFT JOIN FETCH t.user u
            WHERE (:weather IS NULL OR :weather = '' OR t.weather = :weather)
            AND (:start IS NULL OR t.modifiedAt >= :start)
            AND (:end IS NULL OR t.modifiedAt <= :end)
            ORDER BY t.modifiedAt DESC""")
    Page<Todo> findAllByWeatherAndBetweenOrderByModifiedAtDesc(
            Pageable pageable,
            @Param("weather") String weather, // 날씨 조건 추가
            @Param("start") LocalDateTime start, // 수정일 기준 검색 시작
            @Param("end") LocalDateTime end // 수정일 기준 검색 끝
    );

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
