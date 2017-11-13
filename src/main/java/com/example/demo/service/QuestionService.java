package com.example.demo.service;

import com.example.demo.bean.product.NewQuestionBean;
import com.example.demo.bean.product.UpdateQuestionBean;
import com.example.demo.domain.Exam;
import com.example.demo.domain.Question;
import com.example.demo.exception.DataFormatException;
import com.example.demo.repository.impl.QuestionsRepositoryService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionsRepositoryService questionsRepositoryService;

    public Question getQuestion(Long id){
        Question question = questionsRepositoryService.find(id);
        return question;
    }

    public ObjectNode addNewQuestion(NewQuestionBean body , Exam exam){
        Question question = new Question();
        question.setHtml(body.getHtml());
        question.setExams(exam);
        try{
            question = questionsRepositoryService.save(question);
            return createProductDetailJson(question);
        }catch (DataFormatException e){
            throw new DataFormatException("create exam fail");
        }
    }

    public ArrayNode showAllQuestion(){
        List<Question> questions = questionsRepositoryService.findAll();

        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);

        if(questions.size() > 0){
            for(Question question : questions){
                arrayNode.add(createProductDetailJson(question));
            }
        }

        return arrayNode;
    }

    public ObjectNode showQuestionById(Long id) {
        Question question = questionsRepositoryService.find(id);
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        if (question != null) {
            jsonNodes = createProductDetailJson(question);
        }

        return jsonNodes;
    }

    public ObjectNode updateQuestion(Long id , UpdateQuestionBean body , Exam exam){
        Question question = questionsRepositoryService.find(id);

        if(question != null){
            try{
                question.setHtml(body.getHtml());
                question.setExams(exam);
                questionsRepositoryService.update(question);
                return createProductDetailJson(question);
            }catch(DataFormatException e){
                throw new DataFormatException("update question fail");
            }
        }else {
            throw new DataFormatException("invalid question id");
        }
    }

    public void deleteQuestion(Long id){
        Question question = questionsRepositoryService.find(id);
        if(question != null){
            try{
                questionsRepositoryService.delete(id);
            }catch (DataFormatException e){
                throw new DataFormatException("delele question fail");
            }
        }else {
            throw new DataFormatException("invalid question id");
        }
    }

    private ObjectNode createProductDetailJson(Question question){
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("id",question.getQuestionId());
        jsonNodes.put("html",question.getHtml());
        jsonNodes.put("exam_id",question.getExams().getExamId());

        return jsonNodes;
    }
}
