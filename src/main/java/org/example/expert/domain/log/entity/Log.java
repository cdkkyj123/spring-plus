package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;

@Getter
@Entity
@Table(name = "log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Log extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long managerId;
    private Long todoId;
    private String status;

    public Log(Long userId, Long managerId, Long todoId, String status) {
        this.userId = userId;
        this.managerId = managerId;
        this.todoId = todoId;
        this.status = status;
    }
}
