package com.example.demo.service;

import com.example.demo.bean.product.NewChoiceBean;
import com.example.demo.bean.product.NewQuestionBean;
import com.example.demo.bean.product.UpdateChoiceBean;
import com.example.demo.domain.Choice;
import com.example.demo.domain.Question;
import com.example.demo.exception.DataFormatException;
import com.example.demo.repository.impl.ChoiceRepositoryService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoiceService {
    @Autowired
    private ChoiceRepositoryService choiceRepositoryService;

    public ObjectNode addNewChoice(NewChoiceBean body , Question question){
        Choice choice = new Choice();
        choice.setHtml(body.getHtml());
        choice.setCorrect(body.isCorrect());
        choice.setQuestions(question);
        try{
            choice = choiceRepositoryService.save(choice);
            return createProductDetailJson(choice);
        }catch (DataFormatException e){
            throw new DataFormatException("create choice fail");
        }
    }

    public ArrayNode showAllChoice(){
        List<Choice> choices = choiceRepositoryService.findAll();

        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);

        if(choices.size() > 0){
            for(Choice choice : choices){
                arrayNode.add(createProductDetailJson(choice));
            }
        }

        return arrayNode;
    }

    public ObjectNode showChoiceById(Long id) {
        Choice choice = choiceRepositoryService.find(id);
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        if (choice != null) {
            jsonNodes = createProductDetailJson(choice);
        }

        return jsonNodes;
    }

    public ObjectNode updateQuestion(Long id , UpdateChoiceBean body , Question question){
        Choice choice = choiceRepositoryService.find(id);
        if(question != null){
            try{
                choice.setHtml(body.getHtml());
                choice.setCorrect(body.isCorrect());
                choice.setQuestions(question);
                choiceRepositoryService.update(choice);
                return createProductDetailJson(choice);
            }catch(DataFormatException e){
                throw new DataFormatException("update choice fail");
            }
        }else {
            throw new DataFormatException("invalid choice id");
        }
    }

    public void deleteChoice(Long id){
        Choice choice = choiceRepositoryService.find(id);
        if(choice != null){
            try{
                choiceRepositoryService.delete(id);
            }catch (DataFormatException e){
                throw new DataFormatException("delele choice fail");
            }
        }else {
            throw new DataFormatException("invalid choice id");
        }
    }

    private ObjectNode createProductDetailJson(Choice choice){
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("id",choice.getId());
        jsonNodes.put("html",choice.getHtml());
        jsonNodes.put("isCorrect",choice.isCorrect());
        jsonNodes.put("choice_id",choice.getQuestions().getQuestionId());

        return jsonNodes;
    }
}
