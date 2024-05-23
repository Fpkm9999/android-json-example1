package org.example.javaspringbootjpa1.repository;

import org.example.javaspringbootjpa1.domain.OneBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OneRepository extends JpaRepository<OneBoard, Long> {
    OneBoard findByEmail(String email);

    @Query("SELECT u FROM OneBoard u where u.name = :name")
    List<OneBoard> findByName(@Param("name") String name);
}
