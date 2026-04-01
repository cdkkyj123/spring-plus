package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.request.TodoSearchRequest;
import org.example.expert.domain.todo.dto.response.TodoPageResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(long todoId) {

        return Optional.ofNullable(queryFactory
                .selectFrom(todo)
                .leftJoin(todo.user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne());
    }

    @Override
    public Page<TodoPageResponse> findByMultiCondition(TodoSearchRequest request, Pageable pageable, LocalDateTime startOfDay, LocalDateTime endOfDay) {

        BooleanBuilder builder = new BooleanBuilder();

        // 검색 조건
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            builder.and(todo.title.containsIgnoreCase(request.getTitle()));
        }
        if (request.getStart() != null) {
            builder.and(todo.createdAt.goe(startOfDay));
        }
        if (request.getEnd() != null) {
            builder.and(todo.createdAt.loe(endOfDay));
        }
        if (request.getManagerNickname() != null && !request.getManagerNickname().isBlank()) {
            builder.and(todo.managers.any().user.nickname.containsIgnoreCase(request.getManagerNickname()));
        }

        // 실제 데이터 값
        List<TodoPageResponse> result = queryFactory
                .select(Projections.constructor(TodoPageResponse.class,
                        todo.title,
                        // 매니저 수 서브쿼리
                        JPAExpressions.select(manager.count())
                                .from(manager)
                                .where(manager.todo.eq(todo)),
                        // 댓글 수 서브쿼리
                        JPAExpressions.select(comment.count())
                                .from(comment)
                                .where(comment.todo.eq(todo))
                ))
                .from(todo)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(todo.createdAt.desc())
                .fetch();

        // 전체 데이터 갯수
        Long total =queryFactory
                .select(todo.count())
                .from(todo)
                .where(builder)
                .fetchOne();

        // 전체 갯수가 null인 경우 방지
        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(result, pageable, total);
    }
}
