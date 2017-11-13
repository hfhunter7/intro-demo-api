package com.example.demo.service;

import com.example.demo.bean.product.NewExamBean;
import com.example.demo.bean.product.UpdateExamBean;
import com.example.demo.domain.Exam;
import com.example.demo.exception.DataFormatException;
import com.example.demo.repository.impl.ExamsRepositoryService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

@Service
public class ExamService {
    public static final Locale LOCALE_TH = new Locale("TH", "TH");
    @Autowired
    private ExamsRepositoryService examsRepositoryService;

    public ObjectNode addNewExam(NewExamBean body){
        Timestamp timestamp = new Timestamp(new GregorianCalendar(LOCALE_TH).getTime().getTime());
        Exam exam = new Exam();
        exam.setTitle(body.getTitle());
        exam.setDescription(body.getDescription());
        exam.setCreate_by(body.getCreate_by());
        exam.setCreate_date(timestamp);
        exam.setModify_date(timestamp);

        try{
            exam = examsRepositoryService.save(exam);
            return createProductDetailJson(exam);
        }catch (DataFormatException e){
            throw new DataFormatException("create exam fail");
        }
    }

    public ObjectNode updateExam(Long id , UpdateExamBean body){
        Timestamp timestamp = new Timestamp(new GregorianCalendar(LOCALE_TH).getTime().getTime());
        Exam exam = examsRepositoryService.find(id);

        if(exam != null){
            try{
                exam.setTitle(body.getTitle());
                exam.setDescription(body.getDescription());
                exam.setCreate_by(body.getCreate_by());
                exam.setCreate_date(timestamp);
                exam.setModify_date(timestamp);
                examsRepositoryService.update(exam);

                return createProductDetailJson(exam);
            }catch(DataFormatException e){
                throw new DataFormatException("update exam fail");
            }
        }else {
            throw new DataFormatException("invalid exam id");
        }
    }

    public ArrayNode showAllExam(){
        List<Exam> exams = examsRepositoryService.findAll();

        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);

        if(exams.size() > 0){
            for(Exam exam : exams){
                arrayNode.add(createProductDetailJson(exam));
            }
        }

        return arrayNode;
    }

    public ObjectNode showExamById(Long id) {
        Exam exam = examsRepositoryService.find(id);
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        if (exam != null) {
            jsonNodes = createProductDetailJson(exam);
        }

        return jsonNodes;
    }

    public Exam getExam(Long id){
        Exam exam = examsRepositoryService.find(id);
        return exam;
    }

    public void deleteExam(Long id){
        Exam exam = examsRepositoryService.find(id);

        if(exam != null){
            try{
                examsRepositoryService.delete(id);
            }catch (DataFormatException e){
                throw new DataFormatException("delele exam fail");
            }
        }else {
            throw new DataFormatException("invalid exam id");
        }
    }

    private ObjectNode createProductDetailJson(Exam exam){
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("id",exam.getExamId());
        jsonNodes.put("title",exam.getTitle());
        jsonNodes.put("description",exam.getDescription());
        jsonNodes.put("create_by",exam.getCreate_by());
        jsonNodes.put("create_date", String.valueOf(exam.getCreate_date()));
        jsonNodes.put("modify_date", String.valueOf(exam.getModify_date()));

        return jsonNodes;
    }
}
