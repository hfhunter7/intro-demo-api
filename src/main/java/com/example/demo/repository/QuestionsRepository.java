package com.example.demo.repository;

import com.example.demo.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository extends JpaRepository<Question, Long>{
}
