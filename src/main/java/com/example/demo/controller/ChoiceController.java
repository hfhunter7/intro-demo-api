package com.example.demo.controller;


import com.example.demo.bean.product.NewChoiceBean;
import com.example.demo.bean.product.UpdateChoiceBean;
import com.example.demo.domain.Question;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.impl.ChoiceRepositoryService;
import com.example.demo.service.ChoiceService;
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
@RequestMapping(value = "/v1/choice")
@Api(value = "choice", description = "Choice API")
public class ChoiceController extends AbstractRestHandler implements Serializable {
    private static final long serialVersionUID = 5932470568850770644L;
    private static final Logger log = LoggerFactory.getLogger(ChoiceController.class);

    @Autowired
    private ChoiceService choiceService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Show All Choice.", notes = "แสดงตัวเลือกทั้งหมด")
    public @ResponseBody
    ResponseEntity<?> showAllChoice(
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try {
            ArrayNode responseBean = choiceService.showAllChoice();
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Show Choice By id", notes = "แสดงตัวเลือกด้วยไอดี")
    public @ResponseBody
    ResponseEntity<?> showChoiceById(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try {
            ObjectNode responseBean = choiceService.showChoiceById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{questionId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add New Choice.", notes = "สร้างตัวเลือก")
    public @ResponseBody
    ResponseEntity<?> addNewChoice(
            @RequestBody NewChoiceBean body,
            @PathVariable Long questionId,
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try {
            Question question = questionService.getQuestion(questionId);
            ObjectNode responseBean = choiceService.addNewChoice(body, question);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id},{questionId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Choice.", notes = "อัพเดทตัวเลือก")
    public @ResponseBody
    ResponseEntity<?> updateChoice(
            @PathVariable Long id, Long questionId,
            @RequestBody UpdateChoiceBean body,
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try {
            Question question = questionService.getQuestion(questionId);
            ObjectNode responseBean = choiceService.updateQuestion(id, body, question);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Choice.", notes = "ลบตัวเลือก")
    public @ResponseBody
    ResponseEntity<?> deleteChoice(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try {
            choiceService.deleteChoice(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseSuccessMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
