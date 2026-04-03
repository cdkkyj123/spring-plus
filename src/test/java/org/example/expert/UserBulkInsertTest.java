package org.example.expert;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.example.expert.domain.user.enums.UserRole.USER;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class UserBulkInsertTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final int TOTAL = 5_000_000;
    private static final int BATCH_SIZE = 1_000;

    @Test
    void bulkInsert() {

        List<User> users = new ArrayList<>();

        // 1. for문으로 TOTAL만큼 반복
        for (int i = 1; i <= TOTAL; i++) {

            // 2. BATCH_SIZE만큼 리스트에 담아서
            User user = new User(
                    "user" + i + "@test.com",
                    "password",
                    UserRole.USER,
                    // 기존 8글자 방식은 유니크하지 않을 수 있기에 수정
                    UUID.randomUUID().toString().replace("-", "")
            );
            users.add(user);

            // 3. batchUpdate() 호출
            if (i % BATCH_SIZE == 0) {
                jdbcTemplate.batchUpdate(
                        "INSERT INTO users (email, password, user_role, nickname) VALUES (?, ?, ?, ?)",
                        new BatchPreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement ps, int index) throws SQLException {
                                // i는 현재 배치 내의 인덱스
                                ps.setString(1, users.get(index).getEmail());
                                ps.setString(2, users.get(index).getPassword());
                                ps.setString(3, String.valueOf(users.get(index).getUserRole()));
                                ps.setString(4, users.get(index).getNickname());
                            }

                            @Override
                            public int getBatchSize() {
                                return users.size();
                            }
                        }
                );
                // 4. 리스트 초기화 후 반복
                users.clear();
            }
        }
    }
}