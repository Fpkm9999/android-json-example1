package org.example.javaspringbootjpa1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 이 애노테이션을 추가합니다.
public class JavaSpringBootJpa1Application {

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringBootJpa1Application.class, args);
    }

}
