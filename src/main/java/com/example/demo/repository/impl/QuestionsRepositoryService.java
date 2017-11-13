package com.example.demo.repository.impl;

import com.example.demo.domain.Question;
import com.example.demo.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionsRepositoryService {
    @Autowired
    private QuestionsRepository questionsRepository;

    public QuestionsRepositoryService() {

    }

    public Question save(Question question) {
        return questionsRepository.save(question);
    }

    public Question find(Long id){
        return questionsRepository.findOne(id);
    }

    public List<Question> findAll(){
        return questionsRepository.findAll();
    }

    public void update(Question question){
        questionsRepository.save(question);
    }

    public void delete(Long id){
        questionsRepository.delete(id);
    }
}
