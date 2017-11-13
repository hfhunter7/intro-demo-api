package com.example.demo.controller;


import com.example.demo.bean.product.NewQuestionBean;
import com.example.demo.bean.product.UpdateQuestionBean;
import com.example.demo.domain.Exam;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.ExamService;
import com.example.demo.service.QuestionService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@RestController
@RequestMapping(value = "/v1/question")
@Api(value = "question", description = "Question API")
public class QuestionController extends AbstractRestHandler implements Serializable{
    private static final long serialVersionUID = -1789954434168482369L;
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamService examService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Show All Question.", notes = "แสดงคำถามทั้งหมด")
    public @ResponseBody
    ResponseEntity<?> showAllQuestion(
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try{
            ArrayNode responseBean = questionService.showAllQuestion();
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Show Question By id", notes = "แสดงคำถามด้วยไอดี")
    public @ResponseBody
    ResponseEntity<?> showQuestionById(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try{
            ObjectNode responseBean = questionService.showQuestionById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{examId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add New Question.", notes = "สร้างคำถาม")
    public @ResponseBody ResponseEntity<?> addNewQuestion(
            @RequestBody NewQuestionBean body,
            @PathVariable Long examId,
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try{
            Exam exam = examService.getExam(examId);
            ObjectNode responseBean = questionService.addNewQuestion(body , exam);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id},{examId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Question.", notes = "อัพเดทคำถาม")
    public @ResponseBody ResponseEntity<?> updateQuestion(
            @PathVariable Long id,Long examId,
            @RequestBody UpdateQuestionBean body,
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try{
            Exam exam = examService.getExam(examId);
            ObjectNode responseBean = questionService.updateQuestion(id,body,exam);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Question.", notes = "ลบคำถาม")
    public @ResponseBody ResponseEntity<?> deleteQuestion(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try{
            questionService.deleteQuestion(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseSuccessMessage());
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
