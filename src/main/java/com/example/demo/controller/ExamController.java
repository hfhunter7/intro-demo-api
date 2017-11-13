package com.example.demo.controller;

import com.example.demo.bean.product.NewExamBean;
import com.example.demo.bean.product.UpdateExamBean;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.ExamService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
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
@RequestMapping(value = "/v1/exam")
@Api(value = "exam", description = "Exam API")
public class ExamController extends AbstractRestHandler implements Serializable {
    private static final long serialVersionUID = 1687642145151571702L;
    private static final Logger log = LoggerFactory.getLogger(ExamController.class);

    @Autowired
    private ExamService examService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Show All Exam.", notes = "แสดงการสอบทั้งหมด")
    public @ResponseBody
    ResponseEntity<?> showAllExam(
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try{
            ArrayNode responseBean = examService.showAllExam();
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Show Exam By id", notes = "แสดงการสอบด้วยไอดี")
    public @ResponseBody
    ResponseEntity<?> showExamById(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try{
            ObjectNode responseBean = examService.showExamById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add New Exam.", notes = "สร้างการสอบ")
    public @ResponseBody ResponseEntity<?> addNewExam(
            @RequestBody NewExamBean body,
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try{
            ObjectNode responseBean = examService.addNewExam(body);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Exam.", notes = "อัพเดทการสอบ")
    public @ResponseBody ResponseEntity<?> updateExam(
            @PathVariable Long id,
            @RequestBody UpdateExamBean body,
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try{
            ObjectNode responseBean = examService.updateExam(id,body);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Exam.", notes = "ลบการสอบ")
    public @ResponseBody ResponseEntity<?> deleteExam(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletRequest response) throws Exception {
        try{
            examService.deleteExam(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseSuccessMessage());
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
