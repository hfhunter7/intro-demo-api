package com.example.demo.repository.impl;

import com.example.demo.domain.Exam;
import com.example.demo.repository.ExamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamsRepositoryService {

    @Autowired
    private ExamsRepository examsRepository;

    public ExamsRepositoryService() {

    }

    public Exam save(Exam exam) {
        return examsRepository.save(exam);
    }

    public Exam find(Long id) {
        return examsRepository.findOne(id);
    }

    public List<Exam> findAll() {
        return examsRepository.findAll();
    }

    public void update(Exam exam) {
        examsRepository.save(exam);
    }

    public void delete(Long id) {
        examsRepository.delete(id);
    }
}
