package com.example.demo.repository.impl;

import com.example.demo.domain.Choice;
import com.example.demo.repository.ChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoiceRepositoryService {
    @Autowired
    private ChoiceRepository choiceRepository;

    public ChoiceRepositoryService(){

    }

    public Choice save(Choice choice){
        return choiceRepository.save(choice);
    }

    public Choice find(Long id){
        return choiceRepository.findOne(id);
    }

    public List<Choice> findAll(){
        return choiceRepository.findAll();
    }

    public void update(Choice choice){
        choiceRepository.save(choice);
    }

    public void delete(Long id){
        choiceRepository.delete(id);
    }
}
