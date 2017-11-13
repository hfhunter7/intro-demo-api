package com.example.demo.repository;

import com.example.demo.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamsRepository extends JpaRepository<Exam, Long> {
}
