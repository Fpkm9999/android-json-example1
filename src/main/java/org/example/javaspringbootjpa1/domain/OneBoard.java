package org.example.javaspringbootjpa1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

// OneBoard 엔티티 클래스 정의
@Entity // 이 클래스를 데이터베이스 테이블과 매핑하기 위해 사용하는 애노테이션
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OneBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    public void setEmail(String mail) {
        this.email = email;
    }

    // getters and setters
}
